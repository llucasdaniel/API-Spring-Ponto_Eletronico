package com.lucas.pontoeletronico.api.controllers;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import static org.mockito.Mockito.never;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LancamentoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LancamentoService lancamentoService;

    @MockBean
    private FuncionarioService funcionarioService;

    private static final String URL = "/api/lancamentos/";
    private static final Long ID_FUNCIONARIO = Long.valueOf(11);
    private static final Long ID_LANCAMENTO = Long.valueOf(2);
    private static final String TIPO = TipoEnum.INICIO_TRABALHO.name();
    private static final Date DATA = new Date();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    @WithMockUser
    public void atualizarTest() throws Exception {

        BDDMockito.given(this.lancamentoService.add(Mockito.any())).willReturn(obterDadosLancamento());
        BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.any())).willReturn(Optional.of(new Funcionario()));
        BDDMockito.given(this.lancamentoService.buscarPorId(Mockito.any())).willReturn(Optional.of(new Lancamento()));

        mvc.perform(MockMvcRequestBuilders.put(URL + ID_LANCAMENTO)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(obterJsonRequisicaoPost()))
                .andDo(print())
                .andExpect(jsonPath("$.data.id").value(ID_LANCAMENTO))
                .andExpect(jsonPath("$.data.tipo").value(TIPO))
                .andExpect(jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void atualizarTest_FuncionarioInvalido() throws Exception {
        LancamentoDto lancamentoDto = new LancamentoDto();
        lancamentoDto.setFuncionarioId(null);
        lancamentoDto.setId(null);
        lancamentoDto.setData(this.dateFormat.format(DATA));
        lancamentoDto.setTipo(TIPO);

        ObjectMapper mapper = new ObjectMapper();
        String put = mapper.writeValueAsString(lancamentoDto);

        BDDMockito.given(this.lancamentoService.add(Mockito.any())).willReturn(obterDadosLancamento());
        BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.any())).willReturn(Optional.empty());
        BDDMockito.given(this.lancamentoService.buscarPorId(Mockito.any())).willReturn(Optional.of(new Lancamento()));

        mvc.perform(MockMvcRequestBuilders.put(URL + 3)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(put))
                .andDo(print())
                .andExpect(jsonPath("$.errors").value("Funcionário não encontrado."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void atualizarTest_FuncionarioInvalido_IdNaoExistente() throws Exception {
        LancamentoDto lancamentoDto = new LancamentoDto();
        lancamentoDto.setFuncionarioId(10L);
        lancamentoDto.setId(null);
        lancamentoDto.setData(this.dateFormat.format(DATA));
        lancamentoDto.setTipo(TIPO);

        ObjectMapper mapper = new ObjectMapper();
        String put = mapper.writeValueAsString(lancamentoDto);

        BDDMockito.given(this.lancamentoService.add(Mockito.any())).willReturn(obterDadosLancamento());
        BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.any())).willReturn(Optional.empty());
        BDDMockito.given(this.lancamentoService.buscarPorId(Mockito.any())).willReturn(Optional.of(new Lancamento()));

        mvc.perform(MockMvcRequestBuilders.put(URL + 3)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(put))
                .andDo(print())
                .andExpect(jsonPath("$.errors").value("Funcionário não encontrado. ID inexistente."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void atualizarTest_TipoInvalido() throws Exception {
        LancamentoDto lancamentoDto = new LancamentoDto();
        lancamentoDto.setFuncionarioId(10L);
        lancamentoDto.setId(null);
        lancamentoDto.setData(this.dateFormat.format(DATA));

        ObjectMapper mapper = new ObjectMapper();
        String put = mapper.writeValueAsString(lancamentoDto);

        BDDMockito.given(this.lancamentoService.add(Mockito.any())).willReturn(obterDadosLancamento());
        BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.any())).willReturn(Optional.of(new Funcionario()));
        BDDMockito.given(this.lancamentoService.buscarPorId(Mockito.any())).willReturn(Optional.of(new Lancamento()));

        mvc.perform(MockMvcRequestBuilders.put(URL + 3)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .content(put))
                .andDo(print())
                .andExpect(jsonPath("$.errors").value("Tipo inválido."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void listarPorId_ExistLancamento() throws Exception {

        BDDMockito.given(this.lancamentoService.buscarPorId(Mockito.anyLong()))
                .willReturn(Optional.of(obterDadosLancamento()));

        mvc.perform(MockMvcRequestBuilders.get(URL + ID_LANCAMENTO)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(ID_LANCAMENTO))
                .andExpect(jsonPath("$.data.tipo").value(TIPO))
                .andExpect(jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
                .andExpect(jsonPath("$.errors").isEmpty())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void listarPorId_NotPresent() throws Exception {

        BDDMockito.given(this.lancamentoService.buscarPorId(Mockito.anyLong()))
                .willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get(URL + ID_LANCAMENTO)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.errors").value("Lançamento não encontrado para o id " + ID_LANCAMENTO))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void listarPorFuncionarioId_PageZero_WithElements() throws Exception {

        ArrayList<Lancamento> lancaments = new ArrayList<Lancamento>();
        lancaments.add(obterDadosLancamento());

        Page<Lancamento> pages = new PageImpl(lancaments);

        BDDMockito.given(this.lancamentoService.buscarPorFuncionarioId(Mockito.anyLong(), Mockito.any()))
                .willReturn(pages);

        mvc.perform(MockMvcRequestBuilders.get(URL + "/funcionario/" + ID_FUNCIONARIO)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.content[0].id").value(ID_LANCAMENTO))
                .andExpect(jsonPath("$.data.content[0].tipo").value(TIPO))
                .andExpect(jsonPath("$.data.content[0].funcionarioId").value(ID_FUNCIONARIO))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void listarPorFuncionarioId_PageOne_WithNoElements() throws Exception {

        ArrayList<Lancamento> lancaments = new ArrayList<Lancamento>();
        lancaments.add(obterDadosLancamento());

        Page<Lancamento> pages = Mockito.mock(Page.class);

        BDDMockito.given(this.lancamentoService.buscarPorFuncionarioId(Mockito.anyLong(), Mockito.any()))
                .willReturn(pages);

        mvc.perform(MockMvcRequestBuilders.get(URL + "/funcionario/" + ID_FUNCIONARIO + "?pag=1")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void cadastrarLancamentoTest() throws Exception {

        Lancamento lancamento = obterDadosLancamento();
        Funcionario func = new Funcionario();
        func.setCpf("12345678909");
        BDDMockito.given(this.funcionarioService.buscarPorId(ID_FUNCIONARIO)).willReturn(Optional.of(func));
        BDDMockito.given(this.lancamentoService.add(Mockito.any(Lancamento.class))).willReturn(lancamento);

        mvc.perform(MockMvcRequestBuilders.post(URL).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(this.obterJsonRequisicaoPost()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.id").value(ID_LANCAMENTO))
                .andExpect(jsonPath("$.data.tipo").value(TIPO))
                .andExpect(jsonPath("$.data.data").value(this.dateFormat.format(DATA)))
                .andExpect(jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
                .andExpect(jsonPath("$.errors").isEmpty());

        BDDMockito.verify(this.funcionarioService, times(1)).buscarPorId(ID_FUNCIONARIO);
        BDDMockito.verify(this.lancamentoService, times(1)).add(Mockito.any(Lancamento.class));

    }

    @Test
    @WithMockUser
    public void addLancamentosFuncionarioIdInvalido() throws JsonProcessingException, Exception {
        BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.post(URL).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(this.obterJsonRequisicaoPost()))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors").value("Funcionário não encontrado. ID inexistente." + ""));

        BDDMockito.verify(this.funcionarioService, times(1)).buscarPorId(Mockito.anyLong());
    }

    @Test
    @WithMockUser(username="admin@admin.com",roles={"ADMIN"})
    public void testRemoveLancamento() throws Exception {
        BDDMockito.given(this.lancamentoService.buscarPorId(ID_LANCAMENTO)).willReturn(Optional.of(new Lancamento()));
        BDDMockito.willDoNothing().given(this.lancamentoService).remove(ID_LANCAMENTO);

        mvc.perform(MockMvcRequestBuilders.delete(URL + ID_LANCAMENTO).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        BDDMockito.verify(this.lancamentoService, times(1)).buscarPorId(ID_LANCAMENTO);
        BDDMockito.verify(this.lancamentoService, times(1)).remove(ID_LANCAMENTO);
    }
    
     @Test    
    public void testRemoveLancamentoWithoutAccess() throws Exception {

        mvc.perform(MockMvcRequestBuilders.delete(URL + ID_LANCAMENTO).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        BDDMockito.verify(this.lancamentoService, never()).buscarPorId(ID_LANCAMENTO);
        BDDMockito.verify(this.lancamentoService, never()).remove(ID_LANCAMENTO);
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
