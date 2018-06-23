package com.mydomain.employeecontrol.api.security.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mydomain.employeecontrol.api.entities.Employee;
import com.mydomain.employeecontrol.api.security.JwtUserFactory;
import com.mydomain.employeecontrol.api.security.entities.Usuario;
import com.mydomain.employeecontrol.api.security.services.UsuarioService;
import com.mydomain.employeecontrol.api.services.EmployeeService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@Autowired
	private EmployeeService funcionarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Optional<Usuario> funcionario2 = this.usuarioService.buscarPorEmail(username);
		
		Optional<Employee> funcionario = this.funcionarioService.searchByEmail(username);
		
		
		if (funcionario.isPresent()) {
			return JwtUserFactory.create(funcionario.get());
		}
		
		throw new UsernameNotFoundException("Email nao encontrado");
	}

}
