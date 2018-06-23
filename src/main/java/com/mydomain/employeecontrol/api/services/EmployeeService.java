package com.mydomain.employeecontrol.api.services;

import java.util.Optional;

import com.mydomain.employeecontrol.api.entities.Employee;

public interface EmployeeService {
	
	/**
	 * Persist a employee in the database.
	 * 
	 * @param employee
	 * @return Employee
	 */
	Employee persist(Employee employee);
	
	/**
	 * Search a employee by Social Security Number.
	 * 
	 * @param social_security_number
	 * @return Optional<Employee>
	 */
	Optional<Employee> searchBySocialSecurityNumber(String social_security_number);
	
	// Esse retorno Optional e novo no Java 8 e ajudar a tratar o retorno NULL de um objeto.
	
	/**
	 * Search employee by email.
	 * 
	 * @param email
	 * @return Optional<Employee>
	 */
	Optional<Employee> searchByEmail(String email);
	
	/**
	 * Search by Employee ID.
	 * 
	 * @param id
	 * @return
	 */
	Optional<Employee> searchById(Long id);
	

}
