package com.davivasconcelos.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davivasconcelos.cursomc.domain.Cliente;
import com.davivasconcelos.cursomc.repositories.ClienteRepository;
import com.davivasconcelos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Cliente c = repo.findById(id).orElse(null);
		if(c == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id +
					" , Tipo: " + Cliente.class.getName());
		}
		return c;
	}

}
