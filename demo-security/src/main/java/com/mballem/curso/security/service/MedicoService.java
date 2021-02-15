package com.mballem.curso.security.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.security.domain.Medico;
import com.mballem.curso.security.repository.MedicoRepository;

@Service
public class MedicoService {
	
	MedicoRepository medicoRepository;

	public MedicoService(MedicoRepository medicoRepository) {
		super();
		this.medicoRepository = medicoRepository;
	}
	
	@Transactional(readOnly = true)
	public Medico buscarPorUsuarioId(Long id) {
		return medicoRepository.findByUsuarioId(id)
				.orElse(new Medico());
	}

	@Transactional
	public void salvar(Medico medico) {
		medicoRepository.save(medico);
	}

	@Transactional
	public void editar(Medico medico) {
		Medico m2 = medicoRepository.findById(medico.getId()).get();
		m2.setCrm(medico.getCrm());
		m2.setDtInscricao(medico.getDtInscricao());
		m2.setNome(medico.getNome());
		if(!medico.getEspecialidades().isEmpty()) {
			m2.getEspecialidades().addAll(medico.getEspecialidades());
		}
		
	}
	
	

}
