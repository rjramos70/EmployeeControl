package com.mydomain.employeecontrol.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mydomain.employeecontrol.api.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	@Transactional(readOnly = true)
	Company findByCnpj(String cnpj);

}
