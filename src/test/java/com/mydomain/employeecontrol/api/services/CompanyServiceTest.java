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

import com.mydomain.employeecontrol.api.entities.Company;
import com.mydomain.employeecontrol.api.repositories.CompanyRepository;
import com.mydomain.employeecontrol.api.services.CompanyService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompanyServiceTest {
	
	@MockBean
	private CompanyRepository empresaRepository;
	
	@Autowired
	private CompanyService empresaService;
	
	private static final String CNPJ = "51463645000100";
	
	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.empresaRepository.findByCnpj(Mockito.anyString())).willReturn(new Company());
		BDDMockito.given(this.empresaRepository.save(Mockito.any(Company.class))).willReturn(new Company());
	}
	
	@Test
	public void testBuscarEmpresaPorCnpj() {
		Optional<Company> empresa = this.empresaService.searchByCnpj(CNPJ);
		
		assertTrue(empresa.isPresent());
	}
	
	@Test
	public void testPersistirEmpresa() {
		Company empresa = this.empresaService.persist(new Company());
		
		assertNotNull(empresa);
	}

}
