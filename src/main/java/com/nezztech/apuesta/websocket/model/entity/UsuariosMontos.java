package com.nezztech.apuesta.websocket.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuariosMontos implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4039673533547242096L;
	
	private Integer idUsuario;

	private BigDecimal totalDinero;
	
	private BigDecimal margenLibre;
	
	private BigDecimal margen;



	@Override
	public String toString() {
		return "UsuariosMontos [idUsuario=" + idUsuario + ", totalDinero=" + totalDinero + ", margenLibre="
				+ margenLibre + ", margen=" + margen + "]";
	}
	
	

}
