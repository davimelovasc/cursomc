package com.davivasconcelos.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davivasconcelos.cursomc.domain.Pedido;
import com.davivasconcelos.cursomc.repositories.PedidoRepository;
import com.davivasconcelos.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;

	public Pedido buscar(Integer id) {
		Pedido pedido =  repo.findById(id).orElse(null);
		if(pedido == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id +
					" , Tipo: " + Pedido.class.getName());
		}
		
		return pedido;
	}
}
