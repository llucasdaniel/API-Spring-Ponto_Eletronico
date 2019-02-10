package com.lucas.pontoeletronico.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucas.pontoeletronico.api.entities.Funcionario;
import com.lucas.pontoeletronico.api.repositories.FuncionarioRepository;
import com.lucas.pontoeletronico.api.services.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Optional<Funcionario> buscarPorCPF(String cpf) {
        log.info("Find Funcionario By cpf");
        return Optional.ofNullable(funcionarioRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Funcionario> buscarPorEmail(String email) {
        log.info("Find Funcionario By Email");
        return Optional.ofNullable(funcionarioRepository.findByEmail(email));
    }

    @Override
	public Optional<Funcionario> buscarPorId(Long id) {
        log.info("Find Funcionario By ID");
        return funcionarioRepository.findById(id);
    }

    @Override
    public Funcionario add(Funcionario func) {
        log.info("Adding Funcionario = " + func.getEmail());
        return funcionarioRepository.save(func);
    }

}
