package com.lucas.pontoeletronico.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.lucas.pontoeletronico.api.dtos.CadastroPFDto;
import com.lucas.pontoeletronico.api.entities.Empresa;
import com.lucas.pontoeletronico.api.entities.Funcionario;
//import static org.junit.Assert.;

public class CadastroPFControllerTest {

	private Empresa emp;
	
	private Funcionario func;
	
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
	}
	
	@Test
	public void converterCadastroPFDtoTest_OptionalPresent() {
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
		func.setQtdHorasAlmoco(null);
		func.setQtdHorasTrabalhoDia(null);
		func.setValorHora(null);
		
		CadastroPFController controller = new  CadastroPFController();
		CadastroPFDto cad = controller.converterCadastroPFDto(this.func);
	
		assertEquals("Lucas D",cad.getNome());
		assertEquals("lucas@123.com",cad.getEmail());
		assertEquals("123456789009",cad.getCpf());
		assertEquals("67134134000176",cad.getCnpj());
		assertEquals(new Long(10),cad.getId());
		
		assertEquals(Optional.empty(),cad.getQtdHorasAlmoco());
		assertEquals(Optional.empty(),cad.getQtdHorasTrabalhoDia());
		assertEquals(Optional.empty(),cad.getValorHora());
	}
		
}
