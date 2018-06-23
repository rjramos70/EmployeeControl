package com.mydomain.employeecontrol.api.dtos;

public class CompanyDto {
	
	private Long id;
	private String company_name;
	private String cnpj;
	
	public CompanyDto() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return company_name;
	}

	public void setCompanyName(String company_name) {
		this.company_name = company_name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "CompanyDto [id=" + id + ", company_name=" + company_name + ", cnpj=" + cnpj + "]";
	}
	
	

}
