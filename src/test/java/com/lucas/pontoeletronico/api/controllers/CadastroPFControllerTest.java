package com.lucas.pontoeletronico.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.pontoeletronico.api.dtos.CadastroPFDto;
import com.lucas.pontoeletronico.api.entities.Empresa;
import com.lucas.pontoeletronico.api.entities.Funcionario;
import com.lucas.pontoeletronico.api.enums.PerfilEnum;
import com.lucas.pontoeletronico.api.services.EmpresaService;
import com.lucas.pontoeletronico.api.services.FuncionarioService;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CadastroPFControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmpresaService empresaService;

    @MockBean
    private FuncionarioService funcionarioService;

    private static final String URL = "/api/cadastrar-pf";

    private Empresa emp;

    private Funcionario func;

    private CadastroPFDto cadastro;

//	@Mock
//	private CadastroPFController controller;
    private BindingResult bindResult;

    @Before
    public void setUp() {
        emp = new Empresa();
        emp.setCnpj("67134134000176");

        func = new Funcionario();
        func.setId(10L);
        func.setNome("Lucas D");
        func.setEmail("lucas@123.com");
        func.setCpf("123456789009");
        func.setEmpresa(emp);
        func.setQtdHorasAlmoco(1F);
        func.setQtdHorasTrabalhoDia(8F);
        func.setValorHora(new BigDecimal(60));

        cadastro = new CadastroPFDto();
        cadastro.setId(10L);
        cadastro.setNome("Lucas D");
        cadastro.setEmail("lucas@123.com");
        cadastro.setCpf("123456789009");
        cadastro.setCnpj(emp.getCnpj());
        cadastro.setQtdHorasAlmoco(Optional.of("1"));
        cadastro.setQtdHorasTrabalhoDia(Optional.of("8"));
        cadastro.setValorHora(Optional.of("60"));
        cadastro.setSenha("123");
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    public void cadastrarTest_Invalid() throws Exception {
        BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());
        BDDMockito.given(this.funcionarioService.buscarPorCPF(Mockito.anyString())).willReturn(Optional.of(new Funcionario()));
        BDDMockito.given(this.funcionarioService.buscarPorEmail(Mockito.anyString())).willReturn(Optional.of(new Funcionario()));

        List<String> lista = new ArrayList<>();
        lista.add("Empresa não encontrada.");
        lista.add("CPF já existente.");
        lista.add("Email já existente.");

        mvc.perform(MockMvcRequestBuilders.post(URL).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.obterJsonRequisicaoPost()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors")
                        .value(lista));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    public void cadastrarTest() throws Exception {
        Funcionario func = new Funcionario();

        BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());
        BDDMockito.given(this.funcionarioService.buscarPorCPF(Mockito.anyString())).willReturn(Optional.empty());
        BDDMockito.given(this.funcionarioService.buscarPorEmail(Mockito.anyString())).willReturn(Optional.empty());
        BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.of(new Empresa()));
        BDDMockito.given(this.funcionarioService.add(Mockito.any())).willReturn(func);

        mvc.perform(MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(this.obterJsonRequisicaoPost()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nome").value("Lucas D"))
                .andExpect(jsonPath("$.data.email").value("lucas@123.com"))
                .andExpect(jsonPath("$.data.cpf").value("12345678909"));

    }

    private String obterJsonRequisicaoPost() throws JsonProcessingException {
        CadastroPFDto cadastroPFDto = new CadastroPFDto();
        cadastroPFDto.setId(null);
        cadastroPFDto.setEmail("lucas@123.com");
        cadastroPFDto.setNome("Lucas D");
        cadastroPFDto.setCnpj("85.984.952/0001-40");
        cadastroPFDto.setCpf("12345678909");
        cadastroPFDto.setSenha("123");
        cadastroPFDto.setValorHora(null);
        cadastroPFDto.setQtdHorasTrabalhoDia(null);
        cadastroPFDto.setQtdHorasAlmoco(null);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(cadastroPFDto);
    }

    @Test
    public void converterCadastroPFDtoTest() {
        CadastroPFController controller = new CadastroPFController();
        CadastroPFDto cad = controller.converterCadastroPFDto(this.func);

        assertEquals("Lucas D", cad.getNome());
        assertEquals("lucas@123.com", cad.getEmail());
        assertEquals("123456789009", cad.getCpf());
        assertEquals("67134134000176", cad.getCnpj());

        assertEquals("1.0", cad.getQtdHorasAlmoco().get());
        assertEquals("8.0", cad.getQtdHorasTrabalhoDia().get());
        assertEquals("60", cad.getValorHora().get());
        assertEquals(new Long(10), cad.getId());
    }

    @Test
    public void converterCadastroPFDtoTest_OptionalNOTPresent() {
        Funcionario funcNotDefault = func;
        funcNotDefault.setQtdHorasAlmoco(null);
        funcNotDefault.setQtdHorasTrabalhoDia(null);
        funcNotDefault.setValorHora(null);
        CadastroPFController controller1 = new CadastroPFController();
        CadastroPFDto cad = controller1.converterCadastroPFDto(funcNotDefault);

        assertEquals("Lucas D", cad.getNome());
        assertEquals("lucas@123.com", cad.getEmail());
        assertEquals("123456789009", cad.getCpf());
        assertEquals("67134134000176", cad.getCnpj());
        assertEquals(new Long(10), cad.getId());

        assertEquals(Optional.empty(), cad.getQtdHorasAlmoco());
        assertEquals(Optional.empty(), cad.getQtdHorasTrabalhoDia());
        assertEquals(Optional.empty(), cad.getValorHora());
    }

    @Test
    public void converterDtoParaFuncionarioTest() throws NoSuchAlgorithmException {
        this.bindResult = mock(BindingResult.class);
        CadastroPFController controller1 = new CadastroPFController();
        Funcionario funcionario = controller1.converterDtoParaFuncionario(cadastro, this.bindResult);

        assertEquals("Lucas D", funcionario.getNome());
        assertEquals("lucas@123.com", funcionario.getEmail());
        assertEquals("123456789009", funcionario.getCpf());
        assertEquals(PerfilEnum.ROLE_USUARIO, funcionario.getPerfil());

        assertEquals(new Float(1), funcionario.getQtdHorasAlmoco());
        assertEquals(new Float(8.0), funcionario.getQtdHorasTrabalhoDia());
        assertEquals(new BigDecimal(60), funcionario.getValorHora());

    }

}
