package com.lucas.pontoeletronico.api.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucas.pontoeletronico.api.entities.Lancamento;
import com.lucas.pontoeletronico.api.repositories.LancamentoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

	@MockBean
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private LancamentoService lancamentoService;
 
	@Before
	public void setUp() {
 
		Lancamento lanc1 = new Lancamento();
		lanc1.setDescricao("TEST DESC");
		
		Lancamento lanc = new Lancamento();
		lanc.setDescricao("TEST");
		
		when(lancamentoRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(lanc1));
		when(lancamentoRepository.findByFuncionarioId(Mockito.any(Long.class),Mockito.any(Pageable.class))).thenReturn(new PageImpl<Lancamento>(new ArrayList<Lancamento>()));
		when(lancamentoRepository.save(Mockito.any(Lancamento.class))).thenReturn(lanc);
		Mockito.doNothing().when(lancamentoRepository).deleteById(Mockito.any(Long.class));
		
	}

	@Test
	public void testBuscarLancamentoPorFuncionarioId() {	
		Page<Lancamento> lancamento = this.lancamentoService.buscarPorFuncionarioId(10L, new PageRequest(0,10));
		assertEquals(lancamento.getSize(),0);
 
		verify(lancamentoRepository,Mockito.times(1)).findByFuncionarioId(Mockito.any(Long.class),Mockito.any(Pageable.class));
	}
	
	@Test
	public void testBuscarPorId() {	
		Optional<Lancamento> lancamento = this.lancamentoService.buscarPorId(10L);
		assertEquals(lancamento.get().getDescricao(),"TEST DESC");
 
		verify(lancamentoRepository,Mockito.times(1)).findById(Mockito.any(Long.class));
	}

	@Test
	public void testAddLancamento() {
	
		Lancamento  emp = this.lancamentoService.add(new Lancamento());
		assertEquals("TEST", emp.getDescricao());
		verify(lancamentoRepository,Mockito.times(1)).save(Mockito.any(Lancamento.class));
	}
	
	@Test
	public void testRemoveLancamento() {
		this.lancamentoService.remove(10L); 
		verify(lancamentoRepository,Mockito.times(1)).deleteById(Mockito.any(Long.class));
	}
}
