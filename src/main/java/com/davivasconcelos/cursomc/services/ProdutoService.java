package com.davivasconcelos.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davivasconcelos.cursomc.domain.Categoria;
import com.davivasconcelos.cursomc.domain.Produto;
import com.davivasconcelos.cursomc.repositories.CategoriaRepository;
import com.davivasconcelos.cursomc.repositories.ProdutoRepository;
import com.davivasconcelos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		Produto produto =  repo.findById(id).orElse(null);
		if(produto == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id +
					" , Tipo: " + Produto.class.getName());
		}
		
		return produto;
	}
	
	
	public List<Produto> search(String nome, List<Integer> ids_categorias){
		List<Categoria> categorias = categoriaRepository.findAllById(ids_categorias);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias);
	}
	
}
