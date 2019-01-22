package com.lucas.pontoeletronico.api.repositories;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucas.pontoeletronico.api.entities.Empresa;
import com.lucas.pontoeletronico.api.entities.Funcionario;
import com.lucas.pontoeletronico.api.entities.Lancamento;
import com.lucas.pontoeletronico.api.enums.PerfilEnum;
import com.lucas.pontoeletronico.api.enums.TipoEnum;
import com.lucas.pontoeletronico.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	private Long funcionarioId;

	@Before
	public void setUp() throws Exception {
		Empresa emp = this.empresaRepository.save(obterDadosEmpresa());

		Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(emp));
		this.funcionarioId = funcionario.getId();
		
		this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
		this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
	}

	 
	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testFindByFuncionarioId() {
		List<Lancamento> func = this.lancamentoRepository.findByFuncionarioId(funcionarioId);

		assertEquals(2, func.size());
	}
	
	@Test
	public void testFindByFuncionarioIdPageable() {
		PageRequest page = new PageRequest(0,10);
		Page<Lancamento> func = this.lancamentoRepository.findByFuncionarioId(funcionarioId,page);

		assertEquals(2, func.getTotalElements());
	}
 
	private Funcionario obterDadosFuncionario(Empresa emp) {
		Funcionario func = new Funcionario();
		func.setNome("LUCAS");
		func.setPerfil(PerfilEnum.ROLE_USUARIO);
		func.setSenha(PasswordUtils.generateBCrypt("LUCAS123456"));
		func.setCpf("12345678909");
		func.setEmail("lucas@123.com");
		func.setEmpresa(emp);
		return func;
	}

	private Empresa obterDadosEmpresa() {
		Empresa emp = new Empresa();
		emp.setRazaoSocial("Example EMPRESA");
		emp.setCnpj("54053474000120");
		return emp;
	}
	
	private Lancamento obterDadosLancamentos(Funcionario funcionario) {
		Lancamento lanc = new Lancamento();
		lanc.setData(new Date());
		lanc.setTipo(TipoEnum.INICIO_ALMOCCO);
		lanc.setFuncionario(funcionario);
		return lanc;
	}
}
