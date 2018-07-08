package com.davivasconcelos.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.davivasconcelos.cursomc.domain.Categoria;
import com.davivasconcelos.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>  {
	
	@Transactional(readOnly=true)	
	List<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias);

}
