package com.mballem.curso.security.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.security.domain.Perfil;
import com.mballem.curso.security.repository.PerfilRespository;

@Service
public class PerfilService {

	private PerfilRespository perfilRepository;
	
	public PerfilService(PerfilRespository perfilRepository) {
		super();
		this.perfilRepository = perfilRepository;
	}

	@Transactional(readOnly = true)
	public List<Perfil> buscarPerfis(){
		return perfilRepository.findAll();
	}
}
