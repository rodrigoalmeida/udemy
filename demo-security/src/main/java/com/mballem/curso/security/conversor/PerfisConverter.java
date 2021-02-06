package com.mballem.curso.security.conversor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.mballem.curso.security.domain.Perfil;

public class PerfisConverter implements Converter<String[], List<Perfil>> {

	@Override
	public List<Perfil> convert(String[] source) {
		List<Perfil> perfis = new ArrayList<>();

		for (String id : source) {
			if (!"0".equals(id)) {
				perfis.add(new Perfil(Long.parseLong(id)));
			}
		}
		return perfis;
	}

}
