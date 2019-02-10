package com.lucas.pontoeletronico.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.lucas.pontoeletronico.api.entities.Empresa;
import com.lucas.pontoeletronico.api.services.EmpresaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmpresaService empresaService;
	
	private static final String ROUTE_EMPRESA_CNPJ_URL = "/api/empresas/cnpj/";
	private static final Long ID = Long.valueOf(11);
	private static final String CNPJ = "57363354000118";
	private static final String RAZAO_SOCIAL  = "Lucas LTDA";
	
	@Test
        @WithMockUser
	public void buscarPorCnpjTest_EmpresaCnpjInvalid() throws Exception{
		 BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());
		 mvc.perform(MockMvcRequestBuilders.get(ROUTE_EMPRESA_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
			 .andExpect(status().isBadRequest())
			 .andExpect(jsonPath("$.errors").value("Empresa não encontrada para o CNPJ " + CNPJ));
	}	
	
	@Test
        @WithMockUser
	public void converterCadastroPFDtoTest_OptionalNOTPresent() throws Exception {
		BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString()))
			.willReturn(Optional.of(this.obterDadosEmpresa()));
		
		 mvc.perform(MockMvcRequestBuilders.get(ROUTE_EMPRESA_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
			 .andExpect(status().isOk())
			 .andExpect(jsonPath("$.data.id").value(ID))
			 .andExpect(jsonPath("$.data.razaoSocial").value(RAZAO_SOCIAL))
			 .andExpect(jsonPath("$.data.cnpj").value(CNPJ))
			 .andExpect(jsonPath("$.errors").isEmpty());

	}
	
	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setId(ID);
		empresa.setRazaoSocial(RAZAO_SOCIAL);
		empresa.setCnpj(CNPJ);
		return empresa;
	}
 
		
}