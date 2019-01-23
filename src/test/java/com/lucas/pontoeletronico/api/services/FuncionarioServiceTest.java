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

import com.lucas.pontoeletronico.api.entities.Funcionario;
import com.lucas.pontoeletronico.api.repositories.FuncionarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

	@MockBean
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private FuncionarioService funcionarioService;


	@Before
	public void setUp() {
 
		Funcionario func = new Funcionario();
		func.setEmail("funcc@123.com");
		func.setCpf("12345678909");
		
		
		Funcionario func1 = new Funcionario();
		func1.setEmail("func@FUNC.com");
		
		when(funcionarioRepository.findByEmail(Mockito.any(String.class))).thenReturn(func);
		when(funcionarioRepository.findByCpf(Mockito.any(String.class))).thenReturn(func);
		when(funcionarioRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(func1));
		when(funcionarioRepository.save(Mockito.any(Funcionario.class))).thenReturn(func1);
		
	}

	@Test
	public void testBuscarFuncionarioPorCpf() {	
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorCPF("");
		assertTrue(funcionario.isPresent());
		
		Funcionario func = funcionario.get();
		assertEquals("funcc@123.com", func.getEmail());
		verify(funcionarioRepository,Mockito.times(1)).findByCpf(Mockito.any(String.class));
	}
	
	@Test
	public void testBuscarFuncionarioPorEmail() {	
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorEmail("");
		assertTrue(funcionario.isPresent());
		
		Funcionario func = funcionario.get();
		assertEquals("funcc@123.com", func.getEmail());
		verify(funcionarioRepository,Mockito.times(1)).findByEmail(Mockito.any(String.class));
	}
	
	@Test
	public void testBuscarFuncionarioPorId() {	
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(9L);
		assertTrue(funcionario.isPresent());
		
		Funcionario func = funcionario.get();
		assertEquals("func@FUNC.com", func.getEmail());
		verify(funcionarioRepository,Mockito.times(1)).findById(Mockito.any(Long.class));
	}

	@Test
	public void testAddFuncionario() {
		Funcionario  emp = this.funcionarioService.add(new Funcionario());
		assertEquals("func@FUNC.com", emp.getEmail());
		verify(funcionarioRepository,Mockito.times(1)).save(Mockito.any(Funcionario.class));
	}
}
