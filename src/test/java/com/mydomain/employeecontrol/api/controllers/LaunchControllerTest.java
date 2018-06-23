package com.mydomain.employeecontrol.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydomain.employeecontrol.api.dtos.LaunchDto;
import com.mydomain.employeecontrol.api.entities.Employee;
import com.mydomain.employeecontrol.api.entities.Launch;
import com.mydomain.employeecontrol.api.enums.TypeEnum;
import com.mydomain.employeecontrol.api.services.EmployeeService;
import com.mydomain.employeecontrol.api.services.LaunchService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LaunchControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private LaunchService lancamentoService;
	
	@MockBean
	private EmployeeService funcionarioService;
	
	private static final String URL_BASE = "/api/lancamentos/";
	private static final Long ID_FUNCIONARIO = 1L;
	private static final Long ID_LANCAMENTO = 1L;
	private static final String TIPO = TypeEnum.INICIO_TRABALHO.name();
	private static final Date DATA = new Date();
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
	@WithMockUser
	public void testCadastrarLancamento() throws Exception {
		Launch lancamento = obterDadosLancamento();
		BDDMockito.given(this.funcionarioService.searchById(Mockito.anyLong())).willReturn(Optional.of(new Employee()));
		BDDMockito.given(this.lancamentoService.persist(Mockito.any(Launch.class))).willReturn(lancamento);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_LANCAMENTO))
				.andExpect(jsonPath("$.data.tipo").value(TIPO))
				.andExpect(jsonPath("$.data.data").value(this.dateFormat.format(DATA)))
				.andExpect(jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
	@WithMockUser
	public void testCadastrarLancamentoFuncionarioIdInvalido() throws Exception {
		BDDMockito.given(this.funcionarioService.searchById(Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Employee nao encontrado. ID inexistente."))
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
	@WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
	public void testRemoverLancamento() throws Exception {
		BDDMockito.given(this.lancamentoService.searchByID(Mockito.anyLong())).willReturn(Optional.of(new Launch()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LANCAMENTO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void testRemoverLancamentoAcessoNegado() throws Exception {
		BDDMockito.given(this.lancamentoService.searchByID(Mockito.anyLong())).willReturn(Optional.of(new Launch()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LANCAMENTO)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());	// status().isForbidden() gera um status 403
	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		LaunchDto lancamentoDto = new LaunchDto();
		lancamentoDto.setId(null);
		lancamentoDto.setDate(this.dateFormat.format(DATA));
		lancamentoDto.setType(TIPO);
		lancamentoDto.setEmployeeId(ID_FUNCIONARIO);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(lancamentoDto);
	}

	private Launch obterDadosLancamento() {
		Launch lancamento = new Launch();
		lancamento.setId(ID_LANCAMENTO);
		lancamento.setDate(DATA);
		lancamento.setType(TypeEnum.valueOf(TIPO));
		lancamento.setEmployee(new Employee());
		lancamento.getEmployee().setId(ID_FUNCIONARIO);
		return lancamento;
	}	

}
