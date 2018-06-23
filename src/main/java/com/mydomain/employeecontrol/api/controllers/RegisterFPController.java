package com.mydomain.employeecontrol.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

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

import com.mydomain.employeecontrol.api.dtos.RegisterFPDto;
import com.mydomain.employeecontrol.api.entities.Company;
import com.mydomain.employeecontrol.api.entities.Employee;
import com.mydomain.employeecontrol.api.enums.ProfileEnum;
import com.mydomain.employeecontrol.api.response.Response;
import com.mydomain.employeecontrol.api.services.CompanyService;
import com.mydomain.employeecontrol.api.services.EmployeeService;
import com.mydomain.employeecontrol.api.utils.PasswordUtils;

/**
 * Controller class responsible for the registration of fisical person.
 * 
 * @author renatoramos
 *
 */
@RestController
@RequestMapping("/api/register-fp")	
@CrossOrigin(origins = "*")			
public class RegisterFPController {
	
	private static final Logger log = LoggerFactory.getLogger(RegisterFPController.class);
	 
	@Autowired
	private CompanyService empresaService;
	@Autowired
	private EmployeeService funcionarioService;
	
	public RegisterFPController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Register an individual employee in the system.
	 * 
	 * @param cadastroPFDto
	 * @param result
	 * @return ResponseEntity<Response<RegisterFPDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping												
	public ResponseEntity<Response<RegisterFPDto>> cadastrar(@Valid @RequestBody RegisterFPDto cadastroPFDto,
			BindingResult result) throws NoSuchAlgorithmException {
		
		log.info("Registering a physical person: {}", cadastroPFDto.toString());
		Response<RegisterFPDto> response = new Response<RegisterFPDto>();

		validateExistingData(cadastroPFDto, result);
		Employee funcionario = this.convertsDtoToEmployee(cadastroPFDto, result);

		if (result.hasErrors()) {
			log.error("Error validating registration data for Physical Person: {}", result.getAllErrors());
			
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			// Returns 400 error (badRequest)
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Company> company = this.empresaService.searchByCnpj(cadastroPFDto.getCnpj());
		
		company.ifPresent(emp -> funcionario.setComapny(emp));
		
		this.funcionarioService.persist(funcionario);

		response.setData(this.converterCadastroPFDto(funcionario));
		// returns error 200
		return ResponseEntity.ok(response);
	}

	/**
	 * Checks if the company is registered and the employee does not exist in the database.
	 * 
	 * @param cadastroPFDto
	 * @param result
	 */
	private void validateExistingData(RegisterFPDto cadastroPFDto, BindingResult result) {
		Optional<Company> company = this.empresaService.searchByCnpj(cadastroPFDto.getCnpj());
		if (!company.isPresent()) {
			result.addError(new ObjectError("comapny", "Company not registered in the system."));
		}
		
		this.funcionarioService.searchBySocialSecurityNumber(cadastroPFDto.getSocialSecurityNumber())
			.ifPresent(func -> result.addError(new ObjectError("employee", "Invalid Social Security Number.")));
		// Verifica com base no EMAIL
		this.funcionarioService.searchByEmail(cadastroPFDto.getEmail())
			.ifPresent(func -> result.addError(new ObjectError("employee", "Email already existing in our register.")));
	}

	/**
	 * Converts data from DTO to employee.
	 * 
	 * @param cadastroPFDto
	 * @param result
	 * @return Employee
	 * @throws NoSuchAlgorithmException
	 */
	private Employee convertsDtoToEmployee(RegisterFPDto cadastroPFDto, BindingResult result)
			throws NoSuchAlgorithmException {
		Employee employee = new Employee();
		employee.setName(cadastroPFDto.getName());
		employee.setEmail(cadastroPFDto.getEmail());
		employee.setSocialSecurityNumber(cadastroPFDto.getSocialSecurityNumber());
		employee.setProfile(ProfileEnum.ROLE_USUARIO);	// Employee da Company
		employee.setPassword(PasswordUtils.gerarBCrypt(cadastroPFDto.getPassword()));
		cadastroPFDto.getQtdLunchHours()
				.ifPresent(qtd_lunch_hours -> employee.setQtdLunchHours(Float.valueOf(qtd_lunch_hours)));
		cadastroPFDto.getQtdWorkDayHours()
				.ifPresent(qtd_work_day_hours -> employee.setQtdWorkDayHours(Float.valueOf(qtd_work_day_hours)));
		cadastroPFDto.getHourValue().ifPresent(hour_value -> employee.setHourValue(new BigDecimal(hour_value)));

		return employee;
	}

	/**
	 * Popula o DTO de cadastro com os dados do funcionário e empresa.
	 * 
	 * @param employee
	 * @return RegisterFPDto
	 */
	private RegisterFPDto converterCadastroPFDto(Employee employee) {
		RegisterFPDto registerFPDto = new RegisterFPDto();
		registerFPDto.setId(employee.getId());
		registerFPDto.setName(employee.getName());
		registerFPDto.setEmail(employee.getEmail());
		registerFPDto.setSocialSecurityNumber(employee.getSocialSecurityNumber());
		registerFPDto.setCnpj(employee.getCompany().getCnpj());
		employee.getQtdLunchHoursOpt().ifPresent(qtd_lunch_hours -> registerFPDto
				.setQtdLunchHours(Optional.of(Float.toString(qtd_lunch_hours))));
		employee.getQtdWorkDayHoursOpt().ifPresent(
				qtd_work_day_hours -> registerFPDto.setQtdWorkDayHours(Optional.of(Float.toString(qtd_work_day_hours))));
		employee.getHourValueOpt()
				.ifPresent(hour_value -> registerFPDto.setHourValue(Optional.of(hour_value.toString())));

		return registerFPDto;
	}


}
