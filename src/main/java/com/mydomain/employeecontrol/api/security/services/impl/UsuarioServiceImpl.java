package com.mydomain.employeecontrol.api.security.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydomain.employeecontrol.api.security.entities.Usuario;
import com.mydomain.employeecontrol.api.security.repositories.UsuarioRepository;
import com.mydomain.employeecontrol.api.security.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Optional<Usuario> buscarPorEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}

}
