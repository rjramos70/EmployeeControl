package com.mydomain.employeecontrol.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydomain.employeecontrol.api.entities.Company;
import com.mydomain.employeecontrol.api.repositories.CompanyRepository;
import com.mydomain.employeecontrol.api.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService{

	private static final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public Optional<Company> searchByCnpj(String cnpj) {
		log.info("Search company based on CNPJ {}", cnpj);
		return Optional.ofNullable(companyRepository.findByCnpj(cnpj));	
	}

	@Override
	public Company persist(Company company) {
		log.info("Persist company: {}", company);
		return this.companyRepository.save(company);
	}

}
