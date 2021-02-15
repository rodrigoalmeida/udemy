package com.mballem.curso.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.curso.security.domain.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

	public Optional<Medico> findByUsuarioId(Long id);
}
