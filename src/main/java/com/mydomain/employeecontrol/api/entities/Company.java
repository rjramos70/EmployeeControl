package com.mydomain.employeecontrol.api.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company implements Serializable {

	private static final long serialVersionUID = 3960436649365666213L;
	
	private Long id;
	private String company_name;
	private String cnpj;
	private Date creationDate;
	private Date updatedDate;
	private List<Employee> employeeList;
	
	public Company() {
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "company_name", nullable = false)
	public String getCompanyName() {
		return company_name;
	}

	public void setCompanyName(String company_name) {
		this.company_name = company_name;
	}

	@Column(name = "cnpj", nullable = false)
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Column(name = "creation_date", nullable = false)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creation_date) {
		this.creationDate = creation_date;
	}

	@Column(name = "updated_date", nullable = false)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updated_date) {
		this.updatedDate = updated_date;
	}

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Employee> getFuncionarios() {
		return employeeList;
	}

	public void setEmployees(List<Employee> funcionarios) {
		this.employeeList = funcionarios;
	}
	
	@PreUpdate
    public void preUpdate() {
        updatedDate = new Date();
    }
     
    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        creationDate = atual;
        updatedDate = atual;
    }

	@Override
	public String toString() {
		return "Company [id=" + id + ", company_name=" + company_name + ", cnpj=" + cnpj + ", creationDate=" + creationDate
				+ ", updatedDate=" + updatedDate + "]";
	}
}
