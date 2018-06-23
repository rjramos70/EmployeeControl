package com.mydomain.employeecontrol.api.dtos;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

/**
 * Classe DTO (Data Transfer Object) de Cadastro, responsavel pelo transito dos dados, dados vindos do Request.
 * Classe responsavel pelas validações mais basicas tais como de campo vazio, tamanho do campo, valdação de email e de social_security_number, entre outras.
 * 
 * @author renato
 *
 */
public class RegisterJPDto {

	private Long id;
	private String name;
	private String email;
	private String password;
	private String social_security_number;
	private String company_name;
	private String cnpj;
	
	public RegisterJPDto() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Name  can not be empty..")
	@Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters.")
	public String getNome() {
		return name;
	}
	
	public void setNome(String nome) {
		this.name = nome;
	}

	@NotEmpty(message = "Email can not be empty.")
	@Length(min = 5, max = 200, message = "Email must contain between 5 and 200 characters.")
	@Email(message = "Invalid JP Email.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "Password can not be empty.")
	public String getSenha() {
		return password;
	}

	public void setSenha(String senha) {
		this.password = senha;
	}

	@NotEmpty(message = "Social Security Number can not be empty.")
	@CPF(message = "Invalid JP Social Security Number.")
	public String getCpf() {
		return social_security_number;
	}

	public void setCpf(String cpf) {
		this.social_security_number = cpf;
	}

	@NotEmpty(message = "Company Name can not be empty.")
	@Length(min = 5, max = 200, message = "Company Name must contain between 5 and 200 characters.")
	public String getRazaoSocial() {
		return company_name;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.company_name = razaoSocial;
	}

	@NotEmpty(message = "CNPJ can not be empty.")
	@CNPJ(message = "Invalid JP CNPJ.")
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "CadastroPjDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", social_security_number=" + social_security_number
				+ ", company_name=" + company_name + ", cnpj=" + cnpj + "]";
	}	
}
