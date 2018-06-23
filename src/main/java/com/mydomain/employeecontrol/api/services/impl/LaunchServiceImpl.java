package com.mydomain.employeecontrol.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mydomain.employeecontrol.api.entities.Launch;
import com.mydomain.employeecontrol.api.repositories.LaunchRepository;
import com.mydomain.employeecontrol.api.services.LaunchService;

@Service
public class LaunchServiceImpl implements LaunchService {
	
	private static final Logger log = LoggerFactory.getLogger(LaunchServiceImpl.class);
	
	@Autowired
	private LaunchRepository launchRepository;

	@Override
	public Page<Launch> searchByEmployeeId(Long employeeId, PageRequest pageRequest) {
		log.info("Search launch by employee ID {}", employeeId);
		return this.launchRepository.findByEmployeeId(employeeId, pageRequest);
	}

	@Cacheable("launchById")
	public Optional<Launch> searchByID(Long id) {
		log.info("Search by launch ID {}", id);
		return Optional.ofNullable(this.launchRepository.findOne(id));
	}

	@CachePut("launchById")
	public Launch persist(Launch launch) {
		log.info("Persist launch ID  {}", launch);
		return this.launchRepository.save(launch);
	}

	@Override
	public void remove(Long id) {
		log.info("Remove launch by ID {}", id);
		this.launchRepository.delete(id);

	}

}
