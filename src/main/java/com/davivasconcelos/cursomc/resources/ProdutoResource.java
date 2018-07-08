package com.davivasconcelos.cursomc.resources;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.davivasconcelos.cursomc.domain.Produto;
import com.davivasconcelos.cursomc.dto.ProdutoDTO;
import com.davivasconcelos.cursomc.resources.utils.Url;
import com.davivasconcelos.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
		Produto produto = service.find(id);
		return ResponseEntity.ok().body(produto);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ProdutoDTO>> search(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias) {
		
		List<Integer> ids = Url.decodeIntList(categorias);
		String nomeDecoded = Url.decodeParam(nome);
		
		
		List<Produto> produtos = service.search(nomeDecoded, ids);
		List<ProdutoDTO> produtosDTO = new ArrayList<>();
		
		produtos.forEach((p) -> {
			produtosDTO.add(new ProdutoDTO(p));
		});
		
		return ResponseEntity.ok().body(produtosDTO);
	}

}
