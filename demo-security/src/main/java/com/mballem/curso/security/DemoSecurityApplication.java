package com.mballem.curso.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mballem.curso.security.domain.Perfil;
import com.mballem.curso.security.domain.PerfilTipo;
import com.mballem.curso.security.domain.Usuario;
import com.mballem.curso.security.service.PerfilService;
import com.mballem.curso.security.service.UsuarioService;


@SpringBootApplication
public class DemoSecurityApplication implements CommandLineRunner{

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PerfilService perfilService;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<Perfil> perfis = perfilService.buscarPerfis();
		Usuario usuario = usuarioService.buscarUsuarioPorEmail("root");
		boolean perfisCadastrados = !perfis.isEmpty();
		System.out.println("Perfis cadastrados: "+ perfisCadastrados);
		boolean usuarioAdminCadastrado =  usuario != null;
		System.out.println("Usuario admin cadastrado: "+ usuarioAdminCadastrado);
		
		if (perfisCadastrados && !usuarioAdminCadastrado) {
			System.out.println("Cadastrado usuario admin");
			usuario = new Usuario();
			usuario.setAtivo(true);
			usuario.setEmail("root");
			usuario.setSenha(new BCryptPasswordEncoder().encode("123456"));
			usuario.addPerfil(PerfilTipo.ADMIN);
			usuarioService.cadastrar(usuario);
		}
	}
}
