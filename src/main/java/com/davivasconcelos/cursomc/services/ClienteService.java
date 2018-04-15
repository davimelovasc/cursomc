package com.davivasconcelos.cursomc.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.davivasconcelos.cursomc.domain.Cidade;
import com.davivasconcelos.cursomc.domain.Cliente;
import com.davivasconcelos.cursomc.domain.Endereco;
import com.davivasconcelos.cursomc.domain.enums.TipoCliente;
import com.davivasconcelos.cursomc.dto.ClienteDTO;
import com.davivasconcelos.cursomc.dto.ClienteNewDTO;
import com.davivasconcelos.cursomc.repositories.ClienteRepository;
import com.davivasconcelos.cursomc.repositories.EnderecoRepository;
import com.davivasconcelos.cursomc.services.exceptions.DataIntegrityException;
import com.davivasconcelos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Cliente c = repo.findById(id).orElse(null);
		if(c == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id +
					" , Tipo: " + Cliente.class.getName());
		}
		return c;
	}
	
	public Cliente insert(Cliente c) {
		c.setId(null);
		c = repo.save(c);
		c.setEnderecos(enderecoRepository.saveAll(c.getEnderecos()));
		return c;
	}

	
	public Cliente update(Cliente c) {
		Cliente newCliente = find(c.getId());
		updateData(newCliente, c);
		return repo.save(newCliente);
	}
	
	public void delete(Integer id) {
		try {
			Cliente c = find(id);
			
			if(c.getPedidos().size() == 0) {
				repo.delete(c);
			} else {
				throw new DataIntegrityException("Não é possível excluir um cliente com pedidos");
			}
			
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente com pedidos");
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
	
	public Cliente fromDTO(ClienteNewDTO clienteDTO) {
		Cliente c = new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteDTO.getTipo()));
		Cidade cidade = new Cidade(clienteDTO.getCidadeId(), null, null);
		Endereco e = new Endereco(null, clienteDTO.getLogradouro(), clienteDTO.getNumero(), clienteDTO.getComplemento(), clienteDTO.getBairro(), clienteDTO.getCep(), c, cidade);
		
		c.getEnderecos().add(e);
		c.getTelefones().add(clienteDTO.getTelefone1());
		if(clienteDTO.getTelefone2() != null)
			c.getTelefones().add(clienteDTO.getTelefone2());
		if(clienteDTO.getTelefone3() != null)
			c.getTelefones().add(clienteDTO.getTelefone3());
		
		return c;
	} 
	
	private void updateData(Cliente newCliente, Cliente c) {
		newCliente.setNome(c.getNome());
		newCliente.setEmail(c.getEmail());
	}

}
