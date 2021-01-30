package com.mballem.curso.security.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.security.domain.Especialidade;
import com.mballem.curso.security.service.EspecialidadeService;

@Controller
@RequestMapping("especialidades")
public class EspecialidadeController {
	
	private EspecialidadeService especialidadeService;
	
	public EspecialidadeController(EspecialidadeService especialidadeService) {
		super();
		this.especialidadeService = especialidadeService;
	}

	@GetMapping({"", "/"})
	public String abrir(Especialidade especialidade) {
		return "especialidade/especialidade";
	}
	
	@PostMapping("/salvar")
	public String salvar(Especialidade especialidade, RedirectAttributes redirectAttributes) {
		especialidadeService.salvar(especialidade);
		redirectAttributes.addAttribute("Sucesso", "Operação realizada com sucesso");
		return "redirect:/especialidades";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getEspecialidades(HttpServletRequest request){
		return ResponseEntity.ok(especialidadeService.buscarEspecialidades(request));
	}
	
	@GetMapping("editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("especialidade", especialidadeService.buscarPorId(id));
		return "especialidade/especialidade";
	}
	
	@GetMapping("excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		especialidadeService.remover(id);
		attr.addFlashAttribute("sucesso","Operação realizada com sucesso.");
		return "redirect:/especialidades";
	}
	
	
}
