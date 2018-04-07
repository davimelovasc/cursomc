package com.davivasconcelos.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davivasconcelos.cursomc.domain.Categoria;
import com.davivasconcelos.cursomc.repositories.CategoriaRepository;
import com.davivasconcelos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Categoria categoria =  repo.findById(id).orElse(null);
		if(categoria == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id +
					" , Tipo: " + Categoria.class.getName());
		}
		
		return categoria;
	}
	
	public Categoria insert(Categoria c) {
		c.setId(null);
		return repo.save(c);
	}
	
	public Categoria update(Categoria c) {
		find(c.getId());
		return repo.save(c);
	}
}
