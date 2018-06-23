package com.mydomain.employeecontrol.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydomain.employeecontrol.api.dtos.RegisterJPDto;
import com.mydomain.employeecontrol.api.entities.Company;
import com.mydomain.employeecontrol.api.entities.Employee;
import com.mydomain.employeecontrol.api.enums.ProfileEnum;
import com.mydomain.employeecontrol.api.response.Response;
import com.mydomain.employeecontrol.api.services.CompanyService;
import com.mydomain.employeecontrol.api.services.EmployeeService;
import com.mydomain.employeecontrol.api.utils.PasswordUtils;

/**
 * Controller class responsable to register legal person (companies).
 * 
 * @author renatoramos
 *
 */
@RestController							
@RequestMapping("/api/register-jp")		
@CrossOrigin(origins = "*")				
public class RegisterJPController {
	
	private static final Logger log = LoggerFactory.getLogger(RegisterJPController.class);
	
	@Autowired
	private EmployeeService funcionarioService;
	
	@Autowired
	private CompanyService empresaService;
	
	public RegisterJPController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Register a legal person in the system.
	 * 
	 * @param cadastroPJDto
	 * @param result
	 * @return ResponseEntity<Response<RegisterJPDto>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping			// recebera uma requisicao to tipo POST.
	public ResponseEntity<Response<RegisterJPDto>> cadastrar(@Valid @RequestBody RegisterJPDto cadastroPJDto,
			BindingResult result) throws NoSuchAlgorithmException{
		
		log.info("Registering Legal Person: {}", cadastroPJDto.toString());
		Response<RegisterJPDto> response = new Response<RegisterJPDto>();
		
		validateExistingData(cadastroPJDto, result);
		Company empresa = this.converteDtoToComapny(cadastroPJDto);						// Extrai os dados do DTO e transforma em uma Company.
		Employee funcionario = this.converteDtoParaFuncionario(cadastroPJDto, result);	// Extrai os dados do DTO e transforma em um Employee.
		
		// Se possui erros de validacao.
		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
			// Pega todas as mensagens de erro e retorna ao cliente.
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			// Erro badRequest retorna um erro 400 http definindo o corpo 
			return ResponseEntity.badRequest().body(response);
		}
		
		// Primeiro persisti a empresa
		this.empresaService.persist(empresa);
		// Uma vez persistida a empresa, setar o empresa dentro do funcionario
		funcionario.setComapny(empresa);
		// Depois persistir o funcionario.
		this.funcionarioService.persist(funcionario);
		// Nesse momento ja foi persistido no banco Company e Employee.
		
		response.setData(this.converteCadastroPJDto(funcionario));
		// Retorna uma ResponseEntity com status 200 http
		return ResponseEntity.ok(response);
		
	}

	/**
	 * Verify if company or employee already exists on the database.
	 * 
	 * @param registerJPDto
	 * @param result
	 */
	private void validateExistingData(RegisterJPDto registerJPDto, BindingResult result) {
		this.empresaService.searchByCnpj(registerJPDto.getCnpj())
			.ifPresent(emp -> result.addError(new ObjectError("company", "Company do not exist.")));
		
		this.funcionarioService.searchBySocialSecurityNumber(registerJPDto.getCpf())
			.ifPresent(func -> result.addError(new ObjectError("employee", "Social Security Number already exist in the database.")));
		
		this.funcionarioService.searchByEmail(registerJPDto.getEmail())
			.ifPresent(func -> result.addError(new ObjectError("employee", "Email already exist in the database.")));
		
	}


	/**
	 * Converte DTO to a Company class.
	 * 
	 * @param RegisterJPDto
	 * @return Company
	 */
	private Company converteDtoToComapny(RegisterJPDto registerJPDto) {
		
		Company company = new Company();
		company.setCnpj(registerJPDto.getCnpj());
		company.setCompanyName(registerJPDto.getRazaoSocial());
		
		return company;
	}
	
	
	/**
	 * Convert DTO to employee.
	 * 
	 * @param registerJPDto
	 * @param result
	 * @return Employee
	 * @throws NoSuchAlgorithmException
	 */
	private Employee converteDtoParaFuncionario(RegisterJPDto registerJPDto, BindingResult result) 
			throws NoSuchAlgorithmException{
		
		Employee employee = new Employee();
		employee.setName(registerJPDto.getNome());
		employee.setEmail(registerJPDto.getEmail());
		employee.setSocialSecurityNumber(registerJPDto.getCpf());
		employee.setProfile(ProfileEnum.ROLE_ADMIN);	// Company's admin
		employee.setPassword(PasswordUtils.gerarBCrypt(registerJPDto.getSenha()));
		
		return employee;
	}

	/**
	 * Fill the DTO with company and employee data.
	 * 
	 * @param employee
	 * @return RegisterJPDto
	 */
	private RegisterJPDto converteCadastroPJDto(Employee employee) {
		
		RegisterJPDto registerJPDto = new RegisterJPDto();
		registerJPDto.setId(employee.getId());
		registerJPDto.setNome(employee.getName());
		registerJPDto.setEmail(employee.getEmail());
		registerJPDto.setCpf(employee.getSocialSecurityNumber());
		registerJPDto.setRazaoSocial(employee.getCompany().getCompanyName());
		registerJPDto.setCnpj(employee.getCompany().getCnpj());
		
		return registerJPDto;
	}
	
}
