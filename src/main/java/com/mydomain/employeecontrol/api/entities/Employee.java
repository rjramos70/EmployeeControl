package com.mydomain.employeecontrol.api.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mydomain.employeecontrol.api.enums.ProfileEnum;

@Entity
@Table( name = "employee")
public class Employee implements Serializable{
	
	private static final long serialVersionUID = -5754246207015712518L;
	
	private Long id;
	private String name;
	private String email;
	private String password;
	private String social_security_number;
	private BigDecimal hour_value;
	private Float qtd_work_day_hours;
	private Float qtd_lunch_hours;
	private ProfileEnum profile;
	private Date creation_date;
	private Date updated_date;
	private Company company;
	private List<Launch> launchList;
	
	public Employee() {
		
	}
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO ) 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "name",  nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email",  nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name = "social_security_number",  nullable = false)
	public String getSocialSecurityNumber() {
		return social_security_number;
	}

	public void setSocialSecurityNumber(String social_security_number) {
		this.social_security_number = social_security_number;
	}

	@Column(name = "hour_value",  nullable = true)
	public BigDecimal getHourValue() {
		return hour_value;
	}
	
	@Transient	 
	public Optional<BigDecimal> getHourValueOpt(){
		return Optional.ofNullable(this.hour_value);
	}
	
	public void setHourValue(BigDecimal hour_value) {
		this.hour_value = hour_value;
	}

	@Column(name = "qtd_work_day_hours",  nullable = true)
	public Float getQtdWorkDayHours() {
		return qtd_work_day_hours;
	}
	
	@Transient	 
	public Optional<Float> getQtdWorkDayHoursOpt(){
		return Optional.ofNullable(this.qtd_work_day_hours);
	}

	public void setQtdWorkDayHours(Float qtd_work_day_hours) {
		this.qtd_work_day_hours = qtd_work_day_hours;
	}

	@Column(name = "qtd_lunch_hours",  nullable = true)
	public Float getQtdLunchHours() {
		return qtd_lunch_hours;
	}

	@Transient	 
	public Optional<Float> getQtdLunchHoursOpt(){
		return Optional.ofNullable(this.qtd_lunch_hours);
	}
	
	public void setQtdLunchHours(Float qtd_lunch_hours) {
		this.qtd_lunch_hours = qtd_lunch_hours;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "profile", nullable = false  )
	public ProfileEnum getProfile() {
		return profile;
	}

	public void setProfile(ProfileEnum profile) {
		this.profile = profile;
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
	
	@Column( name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	public Company getCompany() {
		return company;
	}

	public void setComapny(Company company) {
		this.company = company;
	}
	
	// Um funcionario pode ter varios launchList, 
	@OneToMany( mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Launch> getLaunchs() {
		return launchList;
	}

	public void setLaunchs(List<Launch> launchList) {
		this.launchList = launchList;
	}

	@PreUpdate
    public void preUpdate() {
        updated_date = new Date();
    }
     
    @PrePersist
    public void prePersist() {
        final Date current = new Date();
        creation_date = current;
        updated_date = current;
    }

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", social_security_number=" + social_security_number
				+ ", hour_value=" + hour_value + ", qtd_work_day_hours=" + qtd_work_day_hours + ", qtd_lunch_hours="
				+ qtd_lunch_hours + ", profile=" + profile + ", creation_date="
				+ creation_date + ", updated_date=" + updated_date + ", company=" + company + "]";
	}
	
	

}
