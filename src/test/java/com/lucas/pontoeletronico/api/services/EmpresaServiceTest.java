package com.lucas.pontoeletronico.api.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucas.pontoeletronico.api.entities.Empresa;
import com.lucas.pontoeletronico.api.repositories.EmpresaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {

	@MockBean
	private EmpresaRepository empresaRepository;

	@Autowired
	private EmpresaService empresaService;

	private static final String CNPJ = "54053474000120";

	@Before
	public void setUp() {
 
		Empresa emp = new Empresa();
		emp.setRazaoSocial("TEST RAZAO");
		
		Empresa emp1 = new Empresa();
		emp1.setRazaoSocial("TEST ADD");
		
		when(empresaRepository.findByCnpj(Mockito.any(String.class))).thenReturn(emp);
		when(empresaRepository.save(Mockito.any(Empresa.class))).thenReturn(emp1);
		
	}

	@Test
	public void testBuscarEmpresaPorCnpj() {	
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(CNPJ);
		assertTrue(empresa.isPresent());
		Empresa emp1 = empresa.get();
		assertEquals("TEST RAZAO", emp1.getRazaoSocial());
		verify(empresaRepository,Mockito.times(1)).findByCnpj(Mockito.any(String.class));
	}

	@Test
	public void testAddEmpresa() {
		Empresa  emp = this.empresaService.add(new Empresa());
		assertEquals("TEST ADD", emp.getRazaoSocial());
		verify(empresaRepository,Mockito.times(1)).save(Mockito.any(Empresa.class));
	}
}
