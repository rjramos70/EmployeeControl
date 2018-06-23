package com.mydomain.employeecontrol.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mydomain.employeecontrol.api.entities.Company;
import com.mydomain.employeecontrol.api.entities.Employee;
import com.mydomain.employeecontrol.api.enums.ProfileEnum;
import com.mydomain.employeecontrol.api.repositories.CompanyRepository;
import com.mydomain.employeecontrol.api.repositories.EmployeeRepository;
import com.mydomain.employeecontrol.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {
	
	@Autowired
	private EmployeeRepository funcionarioRepository;
	
	@Autowired
	private CompanyRepository empresaRepository;
	
	private static final String EMAIL = "email@email.com";
	private static final String CPF = "24291173474";
	
	@Before
	public void setUp() throws Exception{
		Company empresa = this.empresaRepository.save(obterDadosEmpresa());
		this.funcionarioRepository.save(obterDadosFuncionario(empresa));
	}
	
	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testBuscarFuncionarioPorEmail() {
		Employee funcionario = this.funcionarioRepository.findByEmail(EMAIL);
		
		assertEquals(EMAIL, funcionario.getEmail());
	}

	@Test
	public void testBuscarFuncionarioPorCpf() {
		Employee funcionario = this.funcionarioRepository.findBySocialSecurityNumber(CPF);
		
		assertEquals(CPF, funcionario.getSocialSecurityNumber());
	}
	
	@Test
	public void testBuscarFuncionarioPorEmailECpf() {
		Employee funcionario = this.funcionarioRepository.findBySocialSecurityNumberOrEmail(CPF, EMAIL);
		
		assertNotNull(funcionario);
	}
	
	@Test
	public void testBuscarFuncionarioPorEmailOuCpfParaEmailInvalido() {
		Employee funcionario = this.funcionarioRepository.findBySocialSecurityNumberOrEmail(CPF, "email@invalido.com");

		assertNotNull(funcionario);
	}
	
	@Test
	public void testBuscarFuncionarioPorEmailECpfParaCpfInvalido() {
		Employee funcionario = this.funcionarioRepository.findBySocialSecurityNumberOrEmail("12345678901", EMAIL);

		assertNotNull(funcionario);
	}
	
	
	private Employee obterDadosFuncionario(Company empresa) throws NoSuchAlgorithmException {
		Employee funcionario = new Employee();
		funcionario.setName("Fulano de Tal");
		funcionario.setProfile(ProfileEnum.ROLE_USUARIO);
		funcionario.setPassword(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setSocialSecurityNumber(CPF);
		funcionario.setEmail(EMAIL);
		funcionario.setComapny(empresa);
		return funcionario;
	}

	private Company obterDadosEmpresa() {
		Company empresa = new Company();
		empresa.setCompanyName("Company de exemplo");
		empresa.setCnpj("51463645000100");
		return empresa;
	}

}
