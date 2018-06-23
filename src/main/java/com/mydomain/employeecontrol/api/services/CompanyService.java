package com.mydomain.employeecontrol.api.services;

import java.util.Optional;

import com.mydomain.employeecontrol.api.entities.Company;

public interface CompanyService {
	
	/**
	 * Returns a company given a CNPJ.
	 * 
	 * @param cnpj
	 * @return Optional<Company>
	 */
	Optional<Company> searchByCnpj(String cnpj);
	
	/**
	 * Register a new company in the database.
	 * 
	 * @param company
	 * @return Company
	 */
	Company persist(Company company);

}
