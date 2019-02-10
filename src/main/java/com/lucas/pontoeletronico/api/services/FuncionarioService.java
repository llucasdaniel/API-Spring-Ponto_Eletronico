package com.lucas.pontoeletronico.api.services;

import java.util.Optional;

import com.lucas.pontoeletronico.api.entities.Funcionario;

public interface FuncionarioService {

    /**
     * Return Funcionario by CPF
     *
     * @param CPF
     * @return
     */
    Optional<Funcionario> buscarPorCPF(String cpf);

    /**
     * Return Funcionario by Email
     *
     * @param Email
     * @return
     */
    Optional<Funcionario> buscarPorEmail(String email);

    /**
     * Return Funcionario by ID
     *
     * @param ID
     * @return
     */
    Optional<Funcionario> buscarPorId(Long id);

    /**
     * Add Funcionario
     *
     * @param Funcionario
     * @return
     */
    Funcionario add(Funcionario funccionario);
}
