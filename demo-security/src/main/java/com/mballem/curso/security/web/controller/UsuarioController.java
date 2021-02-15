package com.mballem.curso.security.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.security.domain.Medico;
import com.mballem.curso.security.domain.Perfil;
import com.mballem.curso.security.domain.PerfilTipo;
import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.service.MedicoService;
import com.mballem.curso.security.service.UsuarioService;

@Controller
@RequestMapping("u")
public class UsuarioController {

	private UsuarioService usuarioService;

	private MedicoService medicoService;
	
	

	public UsuarioController(UsuarioService usuarioService, MedicoService medicoService) {
		super();
		this.usuarioService = usuarioService;
		this.medicoService = medicoService;
	}

	@GetMapping("/novo/cadastro/usuario")
	public String cadastroPorAdminParaAdminMedicoPaciente(Usuario usuario) {
		return "usuario/cadastro";
	}

	@GetMapping("/lista")
	public String listarUsuarios() {
		return "usuario/lista";
	}

	@GetMapping("/datatables/server/usuarios")
	public ResponseEntity<?> listaUsuariosDatatables(HttpServletRequest request) {
		return ResponseEntity.ok(usuarioService.buscarTodos(request));
	}

	@PostMapping("/cadastro/salvar")
	public String salvar(Usuario usuario, RedirectAttributes attr) {
		List<Perfil> perfis = usuario.getPerfis();
		if (perfis.size() > 2 && ( 
			perfis.containsAll(Arrays.asList(new Perfil(1L), new Perfil(3L))) ||
			perfis.containsAll(Arrays.asList(new Perfil(2L), new Perfil(3L)))
			)) {
			attr.addFlashAttribute("falha", "Paciente não pode ser Admin e/ou Médico");
			attr.addFlashAttribute("usuario",usuario);
		}else {
			try {
				usuarioService.salvarUsuario(usuario);
				attr.addFlashAttribute("sucesso", "Operacao realizada com sucesso");
			} catch (DataIntegrityViolationException ex) {
				attr.addFlashAttribute("falha", "Cadastro não realizado, e-mail já existente");
			}
		}
		return "redirect:/u/novo/cadastro/usuario";
	}
	
	@GetMapping("/editar/credenciais/usuario/{id}")
	public ModelAndView preEditarCredenciais(@PathVariable("id") Long id) {
		return new ModelAndView(
				"usuario/cadastro", 
				"usuario", 
				usuarioService.buscarPorId(id));
	}
	
	@GetMapping("/editar/dados/usuario/{id}/perfis/{perfis}")
	public ModelAndView preEditarCadastroDadosPessoais(
			@PathVariable("id") Long id,
			@PathVariable("perfis") Long[] perfis) {
		Usuario us  = usuarioService.buscarPorPerfisId(id, perfis);
		boolean isMedico = us.getPerfis().contains(new Perfil(PerfilTipo.MEDICO.getCod()));
		boolean isAdmin = us.getPerfis().contains(new Perfil(PerfilTipo.ADMIN.getCod()));
		boolean isPaciente = us.getPerfis().contains(new Perfil(PerfilTipo.PACIENTE.getCod()));
		if (isAdmin && !isMedico) {
			return new ModelAndView("usuario/cadastro", "usuario", us);
		} else if (isMedico) {
			Medico medico = medicoService.buscarPorUsuarioId(id);
			return medico.hasNotId()
					? new ModelAndView("medico/cadastro", "medico", new Medico(new Usuario(id)))
					: new ModelAndView("medico/cadastro", "medico", medico);
		} else if (isPaciente) {
			ModelAndView model = new ModelAndView("error");
			model.addObject("status", 403);
			model.addObject("error", "Área Restrita");
			model.addObject("message", "Os dados de pacientes são restritos a ele.");
			return model;
		}
		return new ModelAndView("redirect:/u/lista");
	}
}
