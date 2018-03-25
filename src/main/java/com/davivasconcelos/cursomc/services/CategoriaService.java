package com.davivasconcelos.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davivasconcelos.cursomc.domain.Categoria;
import com.davivasconcelos.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {
		Categoria categoria =  repo.findById(id).orElse(null);
		return categoria;
	}
}
