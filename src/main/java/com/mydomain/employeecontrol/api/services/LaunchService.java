package com.mydomain.employeecontrol.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.mydomain.employeecontrol.api.entities.Launch;

public interface LaunchService {
	
	/**
	 * Return a list os launches by employee.
	 * 
	 * @param employeeId
	 * @param oageRequest
	 * @return Page<Launch>
	 */
	Page<Launch> searchByEmployeeId(Long employeeId, PageRequest oageRequest);
	
	/**
	 * Return a launch by ID.
	 * 
	 * @param id
	 * @return Optional<Launch>
	 */
	Optional<Launch> searchByID(Long id);
	
	/**
	 * Persiste um lancamento na base de dados.
	 * 
	 * @param launch
	 * @return Launch
	 */
	Launch persist(Launch launch);
	
	/**
	 * Remove a launch base on ID launch.
	 * 
	 * @param id
	 */
	void remove(Long id);

}
