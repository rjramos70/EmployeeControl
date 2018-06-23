package com.mydomain.employeecontrol.api.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.mydomain.employeecontrol.api.entities.Employee;
import com.mydomain.employeecontrol.api.enums.ProfileEnum;


public class JwtUserFactory {
	
	public JwtUserFactory() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Converte e gera um JwtUser com base nos dados de um funcionario.
	 * 
	 * @param usuario
	 * @return JwtUser
	 */
	public static JwtUser create(Employee usuario) {
		return new JwtUser(usuario.getId(), usuario.getEmail(), usuario.getPassword(), mapToGrantedAuthorities(usuario.getProfile()));
	}

	
	/**
	 * Converte o perfil do usuario para o formato utilizado pelo Spring Security
	 * 
	 * @param perfilEnum
	 * @return List<GrantedAuthority>
	 */
	private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(ProfileEnum perfilEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
		
		return authorities;
	}

}
