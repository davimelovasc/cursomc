package com.davivasconcelos.cursomc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.davivasconcelos.cursomc.domain.Categoria;
import com.davivasconcelos.cursomc.dto.CategoriaDTO;
import com.davivasconcelos.cursomc.repositories.CategoriaRepository;
import com.davivasconcelos.cursomc.services.exceptions.DataIntegrityException;
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
	
	public void delete(Integer id) {
		try {
			repo.delete(find(id));
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<CategoriaDTO> findAllDTO() {
		List<CategoriaDTO> list = new ArrayList<>();
		for(Categoria c : repo.findAll()) {
			CategoriaDTO dto = new CategoriaDTO(c);
			list.add(dto);
		}
		return list;
	}
	
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	
}
