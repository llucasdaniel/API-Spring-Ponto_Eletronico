package com.lucas.pontoeletronico.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.validation.BindingResult;

import com.lucas.pontoeletronico.api.dtos.CadastroPFDto;
import com.lucas.pontoeletronico.api.entities.Empresa;
import com.lucas.pontoeletronico.api.entities.Funcionario;
//import static org.junit.Assert.;
import com.lucas.pontoeletronico.api.enums.PerfilEnum;
import com.lucas.pontoeletronico.api.utils.PasswordUtils;

public class CadastroPFControllerTest {

	private Empresa emp;
	
	private Funcionario func;
	
	private CadastroPFDto cadastroPFDto;
	
	@Mock
	private CadastroPFController controller;
	
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
		
		cadastroPFDto = new CadastroPFDto();
		cadastroPFDto.setId(10L);
		cadastroPFDto.setNome("Lucas D");
		cadastroPFDto.setEmail("lucas@123.com");
		cadastroPFDto.setCpf("123456789009");
		cadastroPFDto.setCnpj(emp.getCnpj());
		cadastroPFDto.setQtdHorasAlmoco(Optional.of("1"));
		cadastroPFDto.setQtdHorasTrabalhoDia(Optional.of("8"));
		cadastroPFDto.setValorHora(Optional.of("60"));	
		cadastroPFDto.setSenha("123");	
	}
	
	@Test
	public void converterCadastroPFDtoTest() {
		CadastroPFController controller = new  CadastroPFController();
		CadastroPFDto cad = controller.converterCadastroPFDto(this.func);
	
		assertEquals("Lucas D",cad.getNome());
		assertEquals("lucas@123.com",cad.getEmail());
		assertEquals("123456789009",cad.getCpf());
		assertEquals("67134134000176",cad.getCnpj());
		
		assertEquals("1.0",cad.getQtdHorasAlmoco().get());
		assertEquals("8.0",cad.getQtdHorasTrabalhoDia().get());
		assertEquals("60",cad.getValorHora().get());
		assertEquals(new Long(10),cad.getId());
	}	
	
	@Test
	public void converterCadastroPFDtoTest_OptionalNOTPresent() {
		Funcionario funcNotDefault = func;
		funcNotDefault.setQtdHorasAlmoco(null);
		funcNotDefault.setQtdHorasTrabalhoDia(null);
		funcNotDefault.setValorHora(null);
		CadastroPFController controller1 = new  CadastroPFController();
		CadastroPFDto cad = controller1.converterCadastroPFDto(funcNotDefault);
	
		assertEquals("Lucas D",cad.getNome());
		assertEquals("lucas@123.com",cad.getEmail());
		assertEquals("123456789009",cad.getCpf());
		assertEquals("67134134000176",cad.getCnpj());
		assertEquals(new Long(10),cad.getId());
		
		assertEquals(Optional.empty(),cad.getQtdHorasAlmoco());
		assertEquals(Optional.empty(),cad.getQtdHorasTrabalhoDia());
		assertEquals(Optional.empty(),cad.getValorHora());
	}
	
	@Test
	public void converterDtoParaFuncionarioTest() throws NoSuchAlgorithmException {
		this.bindResult = mock(BindingResult.class);
		CadastroPFController controller1 = new  CadastroPFController();
		Funcionario funcionario = controller1.converterDtoParaFuncionario(cadastroPFDto, this.bindResult);
		
		assertEquals("Lucas D",funcionario.getNome());
		assertEquals("lucas@123.com",funcionario.getEmail());
		assertEquals("123456789009",funcionario.getCpf());
		assertEquals(PerfilEnum.ROLE_USUARIO,funcionario.getPerfil());
		
		
		
		assertEquals(new Float(1),funcionario.getQtdHorasAlmoco());
		assertEquals(new Float(8.0),funcionario.getQtdHorasTrabalhoDia());
		assertEquals(new BigDecimal(60),funcionario.getValorHora());
		
	}
		
}