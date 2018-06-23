package com.mydomain.employeecontrol.api.repositories;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mydomain.employeecontrol.api.entities.Company;
import com.mydomain.employeecontrol.api.entities.Employee;
import com.mydomain.employeecontrol.api.entities.Launch;
import com.mydomain.employeecontrol.api.enums.ProfileEnum;
import com.mydomain.employeecontrol.api.enums.TypeEnum;
import com.mydomain.employeecontrol.api.repositories.CompanyRepository;
import com.mydomain.employeecontrol.api.repositories.EmployeeRepository;
import com.mydomain.employeecontrol.api.repositories.LaunchRepository;
import com.mydomain.employeecontrol.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LaunchRepositoryTest {
	
	@Autowired
	private LaunchRepository lancamentoRepository;
	
	@Autowired
	private EmployeeRepository funcionarioRepository;
	
	@Autowired
	private CompanyRepository empresaRepository;
	
	private Long funcionarioId;

	@Before
	public void setUp() throws Exception {
		Company empresa = this.empresaRepository.save(obterDadosEmpresa());
		
		Employee funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
		this.funcionarioId = funcionario.getId();
		
		this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
		this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
	}

	@After
	public void tearDown() throws Exception {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarLancamentosPorFuncionarioId() {
		List<Launch> lancamentos = this.lancamentoRepository.findByEmployeeId(funcionarioId);
		
		assertEquals(2, lancamentos.size());
	}
	
	@Test
	public void testBuscarLancamentosPorFuncionarioIdPaginado() {
		PageRequest page = new PageRequest(0, 10);
		Page<Launch> lancamentos = this.lancamentoRepository.findByEmployeeId(funcionarioId, page);
		
		assertEquals(2, lancamentos.getTotalElements());
	}
	
	private Launch obterDadosLancamentos(Employee funcionario) {
		Launch lancameto = new Launch();
		lancameto.setDate(new Date());
		lancameto.setType(TypeEnum.INICIO_ALMOCO);
		lancameto.setEmployee(funcionario);
		return lancameto;
	}

	private Employee obterDadosFuncionario(Company empresa) throws NoSuchAlgorithmException {
		Employee funcionario = new Employee();
		funcionario.setName("Fulano de Tal");
		funcionario.setProfile(ProfileEnum.ROLE_USUARIO);
		funcionario.setPassword(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setSocialSecurityNumber("24291173474");
		funcionario.setEmail("email@email.com");
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