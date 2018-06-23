package com.mydomain.employeecontrol.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Data Transfer Object class from Launch
 * 
 * @author renato
 *
 */
public class LaunchDto {
	
	private Optional<Long> id = Optional.empty();
	private String date;
	private String type;
	private String description;
	private String location;
	private Long employeeId;
	
	public LaunchDto() {
		// TODO Auto-generated constructor stub
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Date can not be empty.")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "LaunchDto [id=" + id + ", date=" + date + ", type=" + type + ", description=" + description
				+ ", location=" + location + ", employeeId=" + employeeId + "]";
	}

}
