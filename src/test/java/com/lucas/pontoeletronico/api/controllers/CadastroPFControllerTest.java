package com.lucas.pontoeletronico.api.controllers;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lucas.pontoeletronico.api.dtos.CadastroPFDto;
import com.lucas.pontoeletronico.api.entities.Empresa;
import com.lucas.pontoeletronico.api.entities.Funcionario;
import static org.junit.Assert.*;

public class CadastroPFControllerTest {

	private Empresa emp;
	
	private Funcionario func;
	
	@Before
	public void setUp() {
		emp = new Empresa();
		emp.setCnpj("67134134000176");
		
		Funcionario func = new Funcionario();
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
	public void converterCadastroPFDtoTest() {
		CadastroPFController controller = new  CadastroPFController();
		CadastroPFDto cad = controller.converterCadastroPFDto(func);
	
		assertEquals("Lucas D",cad.getNome());
		assertEquals("lucas@123.com",cad.getEmail());
		assertEquals("123456789009",cad.getCpf());
		assertEquals("67134134000176",cad.getCnpj());
	}
		
}
