package com.lucas.pontoeletronico.api.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.lucas.pontoeletronico.api.entities.Lancamento;

public interface LancamentoService {

    /**
     * Return lancamento by funcionarioId
     *
     * @param cnpj
     * @return
     */
    Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);

    /**
     * Add Lancamento
     *
     * @param lancamento
     * @return
     */
    Lancamento add(Lancamento lancamento);

    /**
     * Remove Lancamento
     *
     * @param id
     * @return
     */
    void remove(Long id);

    Optional<Lancamento> buscarPorId(Long id);

}
