package com.lucas.pontoeletronico.api.controllers;

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
import com.lucas.pontoeletronico.api.dtos.LancamentoDto;
import com.lucas.pontoeletronico.api.entities.Funcionario;
import com.lucas.pontoeletronico.api.entities.Lancamento;
import com.lucas.pontoeletronico.api.enums.TipoEnum;
import com.lucas.pontoeletronico.api.services.FuncionarioService;
import com.lucas.pontoeletronico.api.services.LancamentoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") 
@WithMockUser 
public class LancamentoControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private LancamentoService lancamentoService;
	
	@MockBean
	private FuncionarioService funcionarioService;
	
	private static final String URL = "/api/lancamentos";
	private static final Long ID_FUNCIONARIO = Long.valueOf(11);
	private static final Long ID_LANCAMENTO = Long.valueOf(2);
	private static final String TIPO  = TipoEnum.INICIO_TRABALHO.name();
	private static final Date DATA  = new Date();
	
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
	public void cadastrarLancamentoTest() throws Exception{

		Lancamento lancamento = obterDadosLancamento();
		
		BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
		BDDMockito.given(this.lancamentoService.add(Mockito.any(Lancamento.class))).willReturn(lancamento);
		
		 mvc.perform(MockMvcRequestBuilders.post(URL)
				 .accept(MediaType.APPLICATION_JSON)
				 .contentType(MediaType.APPLICATION_JSON)
				 .content(this.obterJsonRequisicaoPost())
				 )		 
			 .andExpect(status().isOk())
			 .andExpect(jsonPath("$.data.id").value(ID_LANCAMENTO))
			 .andExpect(jsonPath("$.data.tipo").value(TIPO))
			 .andExpect(jsonPath("$.data.data").value(this.dateFormat.format(DATA)))
			 .andExpect(jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
			 .andExpect(jsonPath("$.errors").isEmpty());
			  
	}	
	
	@Test
	public void addLancamentosFuncionarioIdInvalido() throws JsonProcessingException, Exception {
		BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.obterJsonRequisicaoPost()))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.data").isEmpty())
		.andExpect(jsonPath("$.errors").value("Funcionário não encontrado. ID inexistente."));
	}
	
	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		LancamentoDto lancamentoDto = new LancamentoDto();
		lancamentoDto.setId(null);
		lancamentoDto.setData(this.dateFormat.format(DATA));
		lancamentoDto.setTipo(TIPO);
		lancamentoDto.setFuncionarioId(ID_FUNCIONARIO);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(lancamentoDto);
	}
	
	private Lancamento obterDadosLancamento() {
		Lancamento lancamento = new Lancamento();
		lancamento.setId(ID_LANCAMENTO);
		lancamento.setData(DATA);
		lancamento.setTipo(TipoEnum.valueOf(TIPO));
		lancamento.setFuncionario(new Funcionario());
		lancamento.getFuncionario().setId(ID_FUNCIONARIO);
		return lancamento;
	}
 
		
}