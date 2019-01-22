package com.lucas.pontoeletronico.api.repositories;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucas.pontoeletronico.api.entities.Empresa;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTests {

	@Autowired		
	private EmpresaRepository empresaRepository;
	
	private static final String CNPJ = "54053474000120";
	
	private Long empresaId ;
	@Before
	public void setUp() throws Exception{
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("exemplo");
		empresa.setCnpj(CNPJ);
		Empresa emp = this.empresaRepository.save(empresa);		
		this.empresaId = emp.getId();
	}

	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testFindByCnpj() {
		Empresa emp = this.empresaRepository.findByCnpj(CNPJ);
		
		assertEquals(CNPJ, emp.getCnpj());
	}
	
	@Test
	public void testFindById() {
		Optional<Empresa> emp = this.empresaRepository.findById(this.empresaId);
		
		assertEquals(this.empresaId,  emp.get().getId());
	}
}

