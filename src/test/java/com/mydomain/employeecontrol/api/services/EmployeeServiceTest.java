package com.mydomain.employeecontrol.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mydomain.employeecontrol.api.entities.Employee;
import com.mydomain.employeecontrol.api.repositories.EmployeeRepository;
import com.mydomain.employeecontrol.api.services.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeServiceTest {
	
	// cria um Mock do repositorio de funcionario
	@MockBean
	private EmployeeRepository funcionarioRepository;
	
	// Injeta um servico de funcionario
	@Autowired
	private EmployeeService funcionarioService;
	
	// antes de carregar a classe
	@Before
	public void setUp() throws Exception {
		// cria os objetos mock
		BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Employee.class))).willReturn(new Employee());
		BDDMockito.given(this.funcionarioRepository.findOne(Mockito.anyLong())).willReturn(new Employee());
		BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Employee());
		BDDMockito.given(this.funcionarioRepository.findBySocialSecurityNumber(Mockito.anyString())).willReturn(new Employee());
		
	}
	
	// Lista de teste(s)
	@Test
	public void testPersistirFuncionario() {
		Employee funcionario = this.funcionarioService.persist(new Employee());
		
		assertNotNull(funcionario);
	}
	
	@Test
	public void testBuscarFuncionarioPorId() {
		Optional<Employee> funcionario = this.funcionarioService.searchById(1L);
		
		assertTrue(funcionario.isPresent());
	}
	
	@Test
	public void testBuscarFuncionarioPorEmail() {
		Optional<Employee> funcionario = this.funcionarioService.searchByEmail("email@email.com");
		
		assertTrue(funcionario.isPresent());
	}
	
	@Test
	public void testBuscarFuncionarioPorCpf() {
		Optional<Employee> funcionario = this.funcionarioService.searchBySocialSecurityNumber("24291173474");
		
		assertTrue(funcionario.isPresent());
	}

}
