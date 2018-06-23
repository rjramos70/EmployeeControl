package com.mydomain.employeecontrol.api.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mydomain.employeecontrol.api.dtos.LaunchDto;
import com.mydomain.employeecontrol.api.entities.Employee;
import com.mydomain.employeecontrol.api.entities.Launch;
import com.mydomain.employeecontrol.api.enums.TypeEnum;
import com.mydomain.employeecontrol.api.response.Response;
import com.mydomain.employeecontrol.api.services.EmployeeService;
import com.mydomain.employeecontrol.api.services.LaunchService;

@RestController
@RequestMapping("/api/launches")
@CrossOrigin(origins = "*")
public class LaunchController {
	
	// generate log
	private static final Logger log = LoggerFactory.getLogger(LaunchController.class);
	// date formate
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private LaunchService launchService;
	
	@Autowired
	private EmployeeService employeeService;
	
	// Load from "application.properties" page size
	@Value("${pagination.qtd_per_page}")
	private int qtd_per_page;
	
	public LaunchController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Return a list of launches from a employee.
	 * 
	 * @param employeeId
	 * @param pag
	 * @param ord
	 * @param dir
	 * @return ResponseEntity<Response<Page<LaunchDto>>>
	 */
	@GetMapping(value = "/employee/{employeeId}")
	public ResponseEntity<Response<Page<LaunchDto>>> listByEmployeeId(
			@PathVariable("employeeId") Long employeeId,
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir){
		
		// log
		log.info("Searching launches by employee ID: {}", employeeId, pag);
		
		// Create the response
		Response<Page<LaunchDto>> response = new Response<Page<LaunchDto>>();
		
		// Create page response
		PageRequest pageRequest = new PageRequest(pag, this.qtd_per_page, Direction.valueOf(dir), ord);
		
		// Create launches page
		Page<Launch> launchList = this.launchService.searchByEmployeeId(employeeId, pageRequest);
		
		// Creates launchDto
		Page<LaunchDto> launchDto = launchList.map(launch -> this.converteLaunchToDto(launch));
		
		response.setData(launchDto);
		return ResponseEntity.ok(response);
		
	}

	/**
	 * Return a launch by ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<LaunchDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<LaunchDto>> listarPorId(@PathVariable("id") Long id){
		log.info("Searching Launch by ID: {}", id);
		
		Response<LaunchDto> response = new Response<LaunchDto>();
		Optional<Launch> lancamento = this.launchService.searchByID(id);
		
		// If launch not find
		if (!lancamento.isPresent()) {
			log.info("Launch not found by ID: {}", id);
			response.getErrors().add("Launch not found by ID: {}" + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		// If launch already exists in the database
		response.setData(this.converteLaunchToDto(lancamento.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Insert a new launch.
	 * 
	 * @param launchDto
	 * @param result
	 * @return ResponseEntity<Response<LaunchDto>>
	 * @throws ParseException
	 */
	// como nao foi definido uma URL, sera usado a API /api/lancamentos
	@PostMapping
	public ResponseEntity<Response<LaunchDto>> adicionar(@Valid @RequestBody LaunchDto launchDto,
			BindingResult result) throws ParseException{
		
		log.info("Adding a launch: {}", launchDto.toString());
		
		Response<LaunchDto> response = new Response<LaunchDto>();
		
		// Validate if employee already exists in the database
		validateEmployee(launchDto, result);
		
		// Converte LaunchDto to Launch
		Launch launch = this.converteDtoToLaunch(launchDto, result);
		
		// If has any error
		if (result.hasErrors()) {
			log.error("Erro validando lancamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);	// retorna um erro 400 - BadRequest
		}
		
		launch = this.launchService.persist(launch);
		
		response.setData(this.converteLaunchToDto(launch));
		// Return status 200 - sucess
		return ResponseEntity.ok(response);
	}
	
	
	
	/**
	 * Update launch datas.
	 * 
	 * @param id
	 * @param launchDto
	 * @param result
	 * @return ResponseEntity<Response<LaunchDto>>
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<LaunchDto>> update(@PathVariable("id") long id,
			@Valid @RequestBody LaunchDto launchDto, BindingResult result) throws ParseException{
		
		log.info("Updating launch: {}", launchDto.toString());
		
		Response<LaunchDto> response = new Response<LaunchDto>();
		
		validateEmployee(launchDto, result);
		
		launchDto.setId(Optional.of(id));
		
		Launch lancamento = this.converteDtoToLaunch(launchDto, result);
		
		if (result.hasErrors()) {
			log.error("Erro validating launch: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		lancamento = this.launchService.persist(lancamento);
		response.setData(this.converteLaunchToDto(lancamento));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Remove launch by ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<String>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id){
		
		log.info("Removing launch by ID: {}", id);
		Response<String> response = new Response<String>();
		Optional<Launch> lancamento = this.launchService.searchByID(id);
		
		if (!lancamento.isPresent()) {
			log.info("Error removing launch, invalid ID {}.", id);
			response.getErrors().add("Erro removing launch, ID " + id + " not found.");
			return ResponseEntity.badRequest().body(response);	// Returns 400 error
		}
		
		this.launchService.remove(id);
		return ResponseEntity.ok(new Response<String>());		// Return 200 status - sucess
	}
	
	
	

	/**
	 * Validate a employee verifying if he already exist in the database.
	 * 
	 * @param launchDto
	 * @param result
	 */
	private void validateEmployee(LaunchDto launchDto, BindingResult result) {
		
		if (launchDto.getEmployeeId() == null) {
			result.addError(new ObjectError("employee", "Employee not informed ."));
			return;
		}
		
		log.info("Validating employee by ID {}:", launchDto.getEmployeeId());
		
		Optional<Employee> employee = this.employeeService.searchById(launchDto.getEmployeeId());
		
		if (!employee.isPresent()) {
			result.addError(new ObjectError("employee", "Employee not found. ID do not exist."));
		}
		
		
	}

	/**
	 * Converte Launch to LaunchDto.
	 * 
	 * @param launch
	 * @return LaunchDto
	 */
	private LaunchDto converteLaunchToDto(Launch launch) {
		LaunchDto launchDto = new LaunchDto();
		
		launchDto.setId(Optional.of(launch.getId()));
		launchDto.setDate(this.dateFormat.format(launch.getDate()));
		launchDto.setType(launch.getType().toString());
		launchDto.setDescription(launch.getDescription());
		launchDto.setLocation(launch.getLocation());
		launchDto.setEmployeeId(launch.getEmployee().getId());
		
		return launchDto;
	}
	
	/**
	 * Converte a LaunchDto to a Launch.
	 * 
	 * @param  launchDto
	 * @param  result
	 * @return Launch
	 * @throws ParseException
	 */
	private Launch converteDtoToLaunch(LaunchDto launchDto, BindingResult result) throws ParseException {
		Launch launch = new Launch();

		if (launchDto.getId().isPresent()) {
			Optional<Launch> lanc = this.launchService.searchByID(launchDto.getId().get());
			if (lanc.isPresent()) {
				launch = lanc.get();
			} else {
				result.addError(new ObjectError("launch", "Launch not found."));
			}
		} else {
			launch.setEmployee(new Employee());
			launch.getEmployee().setId(launchDto.getEmployeeId());
		}

		launch.setDescription(launchDto.getDescription());
		launch.setLocation(launchDto.getLocation());
		launch.setDate(this.dateFormat.parse(launchDto.getDate()));

		
		if (EnumUtils.isValidEnum(TypeEnum.class, launchDto.getType())) {
			launch.setType(TypeEnum.valueOf(launchDto.getType()));
		} else {
			result.addError(new ObjectError("type", "Invalid Type."));
		}

		return launch;
	}
}
