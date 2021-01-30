package com.mballem.curso.security.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.security.datatables.Datatables;
import com.mballem.curso.security.datatables.DatatablesColunas;
import com.mballem.curso.security.domain.Especialidade;
import com.mballem.curso.security.repository.EspecialidadeRepository;

@Service
public class EspecialidadeService {

	private EspecialidadeRepository especialidadeRepository;
	private Datatables datatables;
	
	public EspecialidadeService(EspecialidadeRepository especialidadeRepository, Datatables datatables) {
		super();
		this.especialidadeRepository = especialidadeRepository;
		this.datatables = datatables;
	}

	@Transactional
	public void salvar(Especialidade especialidade) {
		especialidadeRepository.save(especialidade);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarEspecialidades(HttpServletRequest request){
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.ESPECIALIDADES);
		Page<?> page = datatables.getSearch().isEmpty()
				? especialidadeRepository.findAll(datatables.getPageable())
				: especialidadeRepository.findByTituloLike(datatables.getSearch(), datatables.getPageable()); 
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Especialidade buscarPorId(Long id) {
		return especialidadeRepository.findById(id).get();
	}

	@Transactional
	public void remover(Long id) {
		especialidadeRepository.deleteById(id);
		
	}
}
