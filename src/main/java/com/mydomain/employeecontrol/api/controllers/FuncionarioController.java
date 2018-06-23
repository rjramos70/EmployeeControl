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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydomain.employeecontrol.api.dtos.EmployeeDto;
import com.mydomain.employeecontrol.api.entities.Employee;
import com.mydomain.employeecontrol.api.response.Response;
import com.mydomain.employeecontrol.api.services.EmployeeService;
import com.mydomain.employeecontrol.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class FuncionarioController {

	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	public FuncionarioController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Update employee data.
	 * 
	 * @param id
	 * @param employeeDto
	 * @param result
	 * @return ResponseEntity<Response<EmployeeDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<EmployeeDto>> update(@PathVariable("id") Long id, 
			@Valid @RequestBody EmployeeDto employeeDto, BindingResult result) throws NoSuchAlgorithmException{
		
		
		log.info("Updating employee : {}", employeeDto.toString());
		Response<EmployeeDto> response = new Response<EmployeeDto>();
		
		Optional<Employee> employee = this.employeeService.searchById(id);
		 
		if (!employee.isPresent()) {
			result.addError(new ObjectError("employee", "Employee not founded."));
		}
		
		this.atualizarDadosFuncionario(employee.get(), employeeDto, result);
		
		if (result.hasErrors()) {
			log.error("Erro to validate employee : {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);	
		}
		
		this.employeeService.persist(employee.get());		
		response.setData(this.converterFuncionarioDto(employee.get()));
		
		return ResponseEntity.ok(response);		
		
	}

	/**
	 * Update Employee base o EmployeeDto.
	 * 
	 * @param employee
	 * @param employeeDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void atualizarDadosFuncionario(Employee employee, EmployeeDto employeeDto,
			BindingResult result) throws NoSuchAlgorithmException {

		employee.setName(employeeDto.getNome());
		
		if (!employee.getEmail().equals(employeeDto.getEmail())) {
			// Employee pode mudar o email contanto que seja um email nao existente na base de dados.
			this.employeeService.searchByEmail(employeeDto.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("email", "Email already exist in the database.")));
			employee.setEmail(employeeDto.getEmail());
		}
		
		employee.setQtdLunchHours(null);
		employeeDto.getQtdLunchHours()
				.ifPresent(qtdHorasAlmoco -> employee.setQtdLunchHours(Float.valueOf(qtdHorasAlmoco)));
		
		employee.setQtdWorkDayHours(null);
		employeeDto.getHourValue()
				.ifPresent(qtdHorasTrabalhoDia -> employee.setQtdWorkDayHours(Float.valueOf(qtdHorasTrabalhoDia)));
		
		employee.setHourValue(null);
		employeeDto.getHourValue()
				.ifPresent(valorHora -> employee.setHourValue(new BigDecimal(valorHora)));
		
		if (employeeDto.getSenha().isPresent()) {
			employee.setPassword(PasswordUtils.gerarBCrypt(employeeDto.getSenha().get()));
		}
	}
	
	/**
	 * Returns a DTO bases on a Emplopyee.
	 * 
	 * @param employee
	 * @return EmployeeDto
	 */
	private EmployeeDto converterFuncionarioDto(Employee employee) {
		EmployeeDto employeeDto = new EmployeeDto();
		
		employeeDto.setId(employee.getId());
		employeeDto.setEmail(employee.getEmail());
		employeeDto.setNome(employee.getName());
		employee.getQtdLunchHoursOpt()
				.ifPresent(qtdHorasAlmoco -> employeeDto.setQtdLunchHours(Optional.of(Float.toString(qtdHorasAlmoco))));
		employee.getQtdWorkDayHoursOpt()
				.ifPresent(qtdHorasTrabDia -> employeeDto.setQtdWorkDayHours(Optional.of(Float.toString(qtdHorasTrabDia))));
		employee.getHourValueOpt()
				.ifPresent(valorHora -> employeeDto.setHourValue(Optional.of(valorHora.toString())));

		return employeeDto;
	}

}
