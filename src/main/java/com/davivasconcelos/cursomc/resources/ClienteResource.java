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

import com.davivasconcelos.cursomc.domain.Cliente;
import com.davivasconcelos.cursomc.dto.ClienteDTO;
import com.davivasconcelos.cursomc.dto.ClienteNewDTO;
import com.davivasconcelos.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente c = service.find(id);
		
		return ResponseEntity.ok(c);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> clientesDTO = service.findAllDTO();
		
		return ResponseEntity.ok().body(clientesDTO);
	}
	

	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		
		Page<Cliente> pageCli = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> pageDTO = pageCli.map(obj -> new ClienteDTO(obj));
		
		return ResponseEntity.ok().body(pageDTO);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO cDTO) {
		Cliente c = service.fromDTO(cDTO);
		c = service.insert(c);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(c.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	
	@RequestMapping(value="{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO cDTO, @PathVariable Integer id) {
		Cliente c = service.fromDTO(cDTO);
		c.setId(id);
		/*Cliente aux = service.find(id);
		c.setCpfOuCnpj(aux.getCpfOuCnpj());
		c.setTipo(aux.getTipo());
		c = service.update(c);*/
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);	
		
		return ResponseEntity.noContent().build();
	}
	

}
