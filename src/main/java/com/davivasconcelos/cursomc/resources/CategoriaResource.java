package com.davivasconcelos.cursomc.resources;



import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.davivasconcelos.cursomc.domain.Categoria;
import com.davivasconcelos.cursomc.dto.CategoriaDTO;
import com.davivasconcelos.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria categoria = service.find(id);
		
		return ResponseEntity.ok().body(categoria);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<CategoriaDTO> categoriasDTO = service.findAllDTO();
		
		return ResponseEntity.ok().body(categoriasDTO);
	}
	

	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		Page<Categoria> pageCat = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> pageDTO = pageCat.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(pageDTO);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO cDTO) {
		Categoria c = service.fromDTO(cDTO);
		c = service.insert(c);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(c.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO cDTO, @PathVariable Integer id) {
		Categoria c = service.fromDTO(cDTO);
		c.setId(id);
		c = service.update(c);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);	
		
		return ResponseEntity.noContent().build();
	}
	

}
