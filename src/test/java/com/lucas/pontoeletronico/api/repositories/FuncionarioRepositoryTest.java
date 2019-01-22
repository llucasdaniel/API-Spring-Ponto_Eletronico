package com.lucas.pontoeletronico.api.repositories;

import static org.junit.Assert.assertEquals;

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
import com.lucas.pontoeletronico.api.entities.Funcionario;
import com.lucas.pontoeletronico.api.enums.PerfilEnum;
import com.lucas.pontoeletronico.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

	@Autowired		
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired		
	private EmpresaRepository empresaRepository;
	
	private static final String CPF = "77431530021";
	private static final String EMAIL = "lucas@test.com";
	
	@Before
	public void setUp() throws Exception{
		Empresa emp = this.empresaRepository.save(obterDadosEmpresa());
		 
		this.funcionarioRepository.save(obterDadosFuncionario(emp));		
	}

	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testFindByEmail() {
		Funcionario func = this.funcionarioRepository.findByEmail(EMAIL);
		
		assertEquals(EMAIL, func.getEmail());
	}
	
	@Test
	public void testFindByCPF() {
		Funcionario func = this.funcionarioRepository.findByCpf(CPF);
		
		assertEquals(CPF, func.getCpf());
	}
	
	 
	@Test
	public void testFindByCPFOrEmail() {
		Funcionario func = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
		
		assertEquals(CPF, func.getCpf());
		assertEquals(EMAIL, func.getEmail());
	}
	
	@Test
	public void testFindByCPFOrEmail_AND_Invalid_Email() {
		Funcionario func = this.funcionarioRepository.findByCpfOrEmail(CPF, "123@lucas.com");
		
		assertEquals(CPF, func.getCpf());
		assertEquals(EMAIL, func.getEmail());
	}
	
	@Test
	public void testFindByCPFOrEmail_AND_Invalid_CPF() {
		Funcionario func = this.funcionarioRepository.findByCpfOrEmail("12345678909", EMAIL);
		
		assertEquals(CPF, func.getCpf());
		assertEquals(EMAIL, func.getEmail());
	}
	
	
	private Funcionario obterDadosFuncionario(Empresa emp) {
		Funcionario func = new Funcionario();
		func.setNome("LUCAS");
		func.setPerfil(PerfilEnum.ROLE_USUARIO);
		func.setSenha(PasswordUtils.generateBCrypt("LUCAS123456"));
		func.setCpf(CPF);
		func.setEmail(EMAIL);
		func.setEmpresa(emp);
		return func;
	}
	
	private Empresa obterDadosEmpresa() {
		Empresa emp = new Empresa();
		emp.setRazaoSocial("Example EMPRESA");
		emp.setCnpj("54053474000120");
		return emp;
	}
}

