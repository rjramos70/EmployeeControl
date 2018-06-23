package com.mydomain.employeecontrol.api.security.services;

import java.util.Optional;

import com.mydomain.employeecontrol.api.security.entities.Usuario;

public interface UsuarioService {
	
	/**
	 * Busca e retorna um usuario dado um email
	 * 
	 * @param email
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorEmail(String email);

}
