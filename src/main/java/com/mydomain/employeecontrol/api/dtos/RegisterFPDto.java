package com.mydomain.employeecontrol.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

/**
 * Data Transfer Object (DTO) class responsable to register person, this data coming from the Request.
 * Class responsible for the most basic validations such as empty field, field size, email and social security number validation, among others.
 *
 * @author renatoramos
 *
 */
public class RegisterFPDto {
	
	private Long id;
	private String name;
	private String email;
	private String password;
	private String social_security_number;
	private Optional<String> hourValue = Optional.empty();			// valor opcional iniciando como vazio.
	private Optional<String> qtdWorkDayHours = Optional.empty();	// valor opcional iniciando como vazio.
	private Optional<String> qtdLunchHours = Optional.empty();		// valor opcional iniciando como vazio.
	private String cnpj;												// CNPJ para associar um usuario a uma empresa.
	
	public RegisterFPDto() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Name can not be empty.")
	@Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters.")
	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	@NotEmpty(message = "Email can not be empty.")
	@Length(min = 5, max = 200, message = "Email must contain between 5 and 200 characters.")
	@Email(message = "Invalid Email.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "Password can not be empty.")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotEmpty(message = "Social Security Number can not be empty.")
	@CPF(message = "Invalid Social Security Number.")
	public String getSocialSecurityNumber() {
		return social_security_number;
	}

	public void setSocialSecurityNumber(String social_security_number) {
		this.social_security_number = social_security_number;
	}

	public Optional<String> getHourValue() {
		return hourValue;
	}

	public void setHourValue(Optional<String> hour_value) {
		this.hourValue = hour_value;
	}

	public Optional<String> getQtdWorkDayHours() {
		return qtdWorkDayHours;
	}

	public void setQtdWorkDayHours(Optional<String> qtdWorkDayHours) {
		this.qtdWorkDayHours = qtdWorkDayHours;
	}

	public Optional<String> getQtdLunchHours() {
		return qtdLunchHours;
	}

	public void setQtdLunchHours(Optional<String> qtd_lunch_hours) {
		this.qtdLunchHours = qtd_lunch_hours;
	}

	@NotEmpty(message = "CNPJ can not be empty.")
	@CNPJ(message = "Invalid CNPJ.")
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "RegisterFPDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", social_security_number=" + social_security_number
				+ ", hourValue=" + hourValue + ", qtdWorkDayHours=" + qtdWorkDayHours + ", qtdLunchHours="
				+ qtdLunchHours + ", cnpj=" + cnpj + "]";
	}

}
