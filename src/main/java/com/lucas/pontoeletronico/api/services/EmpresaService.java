package com.lucas.pontoeletronico.api.services;

import java.util.Optional;

import com.lucas.pontoeletronico.api.entities.Empresa;

public interface EmpresaService {

	/**
	 * Return empresa by CNPJ
	 * 
	 * @param cnpj
	 * @return
	 */
	Optional<Empresa> buscarPorCnpj(String cnpj);
	
	/**
	 * Add Empresa 
	 * @param empresa
	 * @return
	 */
	Empresa add(Empresa empresa);
}
