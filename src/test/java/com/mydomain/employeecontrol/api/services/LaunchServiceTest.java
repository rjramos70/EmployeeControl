package com.mydomain.employeecontrol.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.mydomain.employeecontrol.api.entities.Launch;
import com.mydomain.employeecontrol.api.repositories.LaunchRepository;
import com.mydomain.employeecontrol.api.services.LaunchService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LaunchServiceTest {

	// cria um Mock do nosso LaunchRepository
	@MockBean
	private LaunchRepository lancamentoRepository;

	// Faz a injecao de nosso LaunchService
	@Autowired
	private LaunchService lancamentoService;

	@Before
	public void setUp() throws Exception {
		BDDMockito
				.given(this.lancamentoRepository.findByEmployeeId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
				.willReturn(new PageImpl<Launch>(new ArrayList<Launch>()));
		BDDMockito.given(this.lancamentoRepository.findOne(Mockito.anyLong())).willReturn(new Launch());
		BDDMockito.given(this.lancamentoRepository.save(Mockito.any(Launch.class))).willReturn(new Launch());
	}

	@Test
	public void testBuscarLancamentoPorFuncionarioId() {
		Page<Launch> lancamento = this.lancamentoService.searchByEmployeeId(1L, new PageRequest(0, 10));

		assertNotNull(lancamento);
	}

	@Test
	public void testBuscarLancamentoPorId() {
		Optional<Launch> lancamento = this.lancamentoService.searchByID(1L);

		assertTrue(lancamento.isPresent());
	}

	@Test
	public void testPersistirLancamento() {
		Launch lancamento = this.lancamentoService.persist(new Launch());

		assertNotNull(lancamento);
	}

}

