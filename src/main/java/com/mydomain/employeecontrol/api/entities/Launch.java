package com.mydomain.employeecontrol.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mydomain.employeecontrol.api.enums.TypeEnum;

@Entity
@Table( name = "launch")
public class Launch implements Serializable {
	
	private static final long serialVersionUID = 6524560251526772839L;
	
	private Long id;
	private Date date;
	private String description;
	private String location;
	private Date creation_date;
	private Date updated_date;
	private TypeEnum type;
	private Employee employee;
	
	public Launch() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal( TemporalType.TIMESTAMP )	// Seto esse Temporal para certificar que vai ser pego a date e hora que ocorreu o evento do lancamento.
	@Column( name = "date", nullable = false)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column( name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column( name = "location", nullable = true)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column( name = "creation_date", nullable = false)
	public Date getCreationDate() {
		return creation_date;
	}

	public void setCreationDate(Date creation_date) {
		this.creation_date = creation_date;
	}

	@Column( name = "updated_date", nullable = false)
	public Date getUpdatedDate() {
		return updated_date;
	}

	public void setUpdatedDate(Date updated_date) {
		this.updated_date = updated_date;
	}

	@Enumerated(EnumType.STRING)	
	@Column( name = "type", nullable = false)
	public TypeEnum getType() {
		return type;
	}

	public void setType(TypeEnum type) {
		this.type = type;
	}

	@ManyToOne( fetch = FetchType.EAGER )	// Many lancamentos para um employee, trazer sempre o Employee quando buscar o Launch.
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee funcionario) {
		this.employee = funcionario;
	}
	
	
	@PreUpdate
	public void preUpdate() {
		this.updated_date = new Date();

	}
	
	@PrePersist
	public void prePersist() {
		final Date current = new Date();
		this.creation_date = current;
		this.updated_date = current;
	}

	@Override
	public String toString() {
		return "Launch [id=" + id + ", date=" + date + ", description=" + description + ", location=" + location
				+ ", creation_date=" + creation_date + ", updated_date=" + updated_date + ", type=" + type
				+ ", employee=" + employee + "]";
	}
	
	

}
