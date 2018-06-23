package com.mydomain.employeecontrol.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydomain.employeecontrol.api.entities.Employee;
import com.mydomain.employeecontrol.api.repositories.EmployeeRepository;
import com.mydomain.employeecontrol.api.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Employee persist(Employee funcionario) {
		log.info("Persist employee: {}", funcionario);
		return this.employeeRepository.save(funcionario);
	}

	@Override
	public Optional<Employee> searchBySocialSecurityNumber(String social_security_number) {
		log.info("Search employee by social security number: {}", social_security_number);
		
		return Optional.ofNullable(this.employeeRepository.findBySocialSecurityNumber(social_security_number));
	}

	@Override
	public Optional<Employee> searchByEmail(String email) {
		log.info("Search employee by email: {}", email);
		return Optional.ofNullable(this.employeeRepository.findByEmail(email));
	}

	@Override
	public Optional<Employee> searchById(Long id) {
		log.info("Search employee by ID: {}", id);
		return Optional.ofNullable(this.employeeRepository.findOne(id));
	}

}
