package com.lucas.pontoeletronico.api.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lucas.pontoeletronico.api.entities.Lancamento;
import com.lucas.pontoeletronico.api.repositories.LancamentoRepository;
import com.lucas.pontoeletronico.api.services.LancamentoService;
import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
        log.info("Find Lancamento By cpf");
        return lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
    }

    @Override
    public Optional<Lancamento> buscarPorId(Long id) {
        log.info("Find Lancamento By ID");
        return lancamentoRepository.findById(id);
    }

    @Override
    public Lancamento add(Lancamento func) {
        log.info("Adding Lancamento = " + func.getDescricao());
        return lancamentoRepository.save(func);
    }

    @Override
    public void remove(Long func) {
        log.info("Delete Lancamento com ID= " + func);
        lancamentoRepository.deleteById(func);
    }
}
