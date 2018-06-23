package com.mydomain.employeecontrol.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mydomain.employeecontrol.api.entities.Employee;

@Transactional( readOnly = true)	
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findBySocialSecurityNumber(String social_security_number);
	Employee findByEmail(String email);
	Employee findBySocialSecurityNumberOrEmail(String social_security_number, String email);
}
