package com.mballem.curso.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.curso.security.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByEmailLike(String email);

}
