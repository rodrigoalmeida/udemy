package com.mballem.curso.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mballem.curso.security.domain.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

	Page<Especialidade> findByTituloLike(String search, Pageable pageable);

}
