package com.davivasconcelos.cursomc.domain.enums;

import org.hibernate.boot.model.naming.IllegalIdentifierException;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private Integer cod;
	private String descrica;
	
	private TipoCliente(Integer cod, String descrica) {
		this.cod = cod;
		this.descrica = descrica;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescrica() {
		return descrica;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		if(cod == null)
			return null;
		
		for(TipoCliente x : TipoCliente.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
