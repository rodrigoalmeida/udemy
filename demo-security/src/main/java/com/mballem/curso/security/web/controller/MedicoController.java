package com.mballem.curso.security.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.security.domain.Medico;
import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.service.MedicoService;
import com.mballem.curso.security.service.UsuarioService;

@Controller
@RequestMapping("medicos")
public class MedicoController {
	private MedicoService medicoService;
	private UsuarioService usuarioService;

	public MedicoController(MedicoService medicoService, UsuarioService usuarioService) {
		super();
		this.medicoService = medicoService;
		this.usuarioService = usuarioService;
	}

	@GetMapping("/dados")
	public String abrirPorMedico(Medico medico, ModelMap model, @AuthenticationPrincipal User user) {
		Usuario u = usuarioService.buscarUsuarioPorEmail(user.getUsername());
		Medico m = medicoService.buscarPorUsuarioId(u.getId());
		if (m != null) {
			medico = m;
		}
		return "medico/cadastro";
	}

	@PostMapping("/salvar")
	public String salvar(Medico medico, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		if (medico.hasNotId() && medico.getUsuario().hasNotId()) {
			Usuario u = usuarioService.buscarUsuarioPorEmail(user.getUsername());
			Medico m = medicoService.buscarPorUsuarioId(u.getId());
			m.setDtInscricao(medico.getDtInscricao());
			m.setCrm(medico.getCrm());
			m.setEspecialidades(medico.getEspecialidades());
			m.setNome(medico.getNome());
			
			medico = m;
		}		
		medicoService.salvar(medico);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		attr.addFlashAttribute("medico", medico);
		return "redirect:/medicos/dados";
	}

	@PostMapping("/editar")
	public String editar(Medico medico, RedirectAttributes attr) {
		medicoService.editar(medico);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		attr.addFlashAttribute("medico", medico);
		return "redirect:/medicos/dados";
	}
}
