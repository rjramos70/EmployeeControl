package com.mydomain.employeecontrol.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydomain.employeecontrol.api.dtos.CompanyDto;
import com.mydomain.employeecontrol.api.entities.Company;
import com.mydomain.employeecontrol.api.response.Response;
import com.mydomain.employeecontrol.api.services.CompanyService;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
public class EmpresaController {
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);

	@Autowired
	private CompanyService companyService;
	
	public EmpresaController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Return a company by CNPJ
	 * 
	 * @return ResponseEntity<Response<CompanyDto>>
	 */
	@GetMapping(value = "/cnpj/{cnpj}")
	public ResponseEntity<Response<CompanyDto>> searchByCnpj(@PathVariable("cnpj") String cnpj){
		
		log.info("Searching company by CNPJ: {}", cnpj);
		Response<CompanyDto> response = new Response<CompanyDto>();
		Optional<Company> empresa = this.companyService.searchByCnpj(cnpj);
		
		if (!empresa.isPresent()) {
			log.info("Company not founded with CNPJ: {}", cnpj);
			response.getErrors().add("Company no founded by CNPJ " + cnpj);
			return ResponseEntity.badRequest().body(response);		
		}
		
		response.setData(this.converteEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);		// return 200 status
		
	}

	/**
	 * Populate DTO object with data company.
	 * 
	 * @param company
	 * @return CompanyDto
	 */
	private CompanyDto converteEmpresaDto(Company company) {
		CompanyDto empresaDto = new CompanyDto();
		empresaDto.setId(company.getId());
		empresaDto.setCnpj(company.getCnpj());
		empresaDto.setCompanyName(company.getCompanyName());
		return empresaDto;
	}

}
