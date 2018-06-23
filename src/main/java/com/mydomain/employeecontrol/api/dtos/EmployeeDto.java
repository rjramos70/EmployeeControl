package com.mydomain.employeecontrol.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Data Transfer Object class from Employee
 * 
 * @author renato
 *
 */
public class EmployeeDto {
	
	private Long id;
	private String name;
	private String email;
	private Optional<String> password = Optional.empty();
	private Optional<String> hour_value = Optional.empty();
	private Optional<String> qtd_work_day_hours = Optional.empty();
	private Optional<String> qtd_lunch_hours = Optional.empty();
	
	
	public EmployeeDto() {
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
	public String getNome() {
		return name;
	}


	public void setNome(String nome) {
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


	public Optional<String> getSenha() {
		return password;
	}


	public void setSenha(Optional<String> senha) {
		this.password = senha;
	}


	public Optional<String> getHourValue() {
		return hour_value;
	}


	public void setHourValue(Optional<String> hour_value) {
		this.hour_value = hour_value;
	}


	public Optional<String> getQtdWorkDayHours() {
		return qtd_work_day_hours;
	}


	public void setQtdWorkDayHours(Optional<String> qtd_work_day_hours) {
		this.qtd_work_day_hours = qtd_work_day_hours;
	}


	public Optional<String> getQtdLunchHours() {
		return qtd_lunch_hours;
	}


	public void setQtdLunchHours(Optional<String> qtd_lunch_hours) {
		this.qtd_lunch_hours = qtd_lunch_hours;
	}


	@Override
	public String toString() {
		return "EmployeeDto [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", hour_value="
				+ hour_value + ", qtd_work_day_hours=" + qtd_work_day_hours + ", qtd_lunch_hours=" + qtd_lunch_hours
				+ "]";
	}

}
