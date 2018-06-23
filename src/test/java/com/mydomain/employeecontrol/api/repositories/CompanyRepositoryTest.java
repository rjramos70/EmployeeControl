package com.mydomain.employeecontrol.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mydomain.employeecontrol.api.entities.Company;
import com.mydomain.employeecontrol.api.repositories.CompanyRepository;

@RunWith(SpringRunner.class)
@SpringBootTest			// o Spring criara um contexto de teste usando a classe 'SpringRunner' 
@ActiveProfiles("test")	// defina o Profile 'test' para usar nossas configuracoes de teste.
public class CompanyRepositoryTest {
	
	@Autowired
	private CompanyRepository empresaRepository;
	
	private static final String CNPJ = "51463645000100";
	
	@Before
	public void setUp() throws Exception{
		// Antes do teste sera criado uma empresa no banco de dados H2
		Company empresa = new Company();
		empresa.setCompanyName("Company de exemplo");
		empresa.setCnpj(CNPJ);
		this.empresaRepository.save(empresa);
	}
	
	@After
	public final void tearDown() {
		// depois do teste sera removido toda empresa testada no banco de dados.
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testBuscarPorCnpj() {
		Company empresa = this.empresaRepository.findByCnpj(CNPJ);
		
		assertEquals(CNPJ, empresa.getCnpj());
	}

}
