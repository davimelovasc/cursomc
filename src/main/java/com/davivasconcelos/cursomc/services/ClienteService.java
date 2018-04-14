package com.davivasconcelos.cursomc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.davivasconcelos.cursomc.domain.Cliente;
import com.davivasconcelos.cursomc.dto.ClienteDTO;
import com.davivasconcelos.cursomc.repositories.ClienteRepository;
import com.davivasconcelos.cursomc.services.exceptions.DataIntegrityException;
import com.davivasconcelos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Cliente c = repo.findById(id).orElse(null);
		if(c == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id +
					" , Tipo: " + Cliente.class.getName());
		}
		return c;
	}
	
	public Cliente update(Cliente c) {
		Cliente newCliente = find(c.getId());
		updateData(newCliente, c);
		return repo.save(newCliente);
	}
	
	public void delete(Integer id) {
		try {
			repo.delete(find(id));
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente com entidades relacionadas");
		}
	}
	
	public List<ClienteDTO> findAllDTO() {
		List<ClienteDTO> list = new ArrayList<>();
		for(Cliente c : repo.findAll()) {
			ClienteDTO dto = new ClienteDTO(c);
			list.add(dto);
		}
		return list;
	}
	
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newCliente, Cliente c) {
		newCliente.setNome(c.getNome());
		newCliente.setEmail(c.getEmail());
	}

}
