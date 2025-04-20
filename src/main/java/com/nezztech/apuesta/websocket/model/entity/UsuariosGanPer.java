package com.nezztech.apuesta.websocket.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuariosGanPer implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4039673533547242096L;
	
	private Integer idUsuario;

	private BigDecimal montoGanPer;

	@Override
	public String toString() {
		return "usuariosGanPer [idUsuario=" + idUsuario + ", montoGanPer=" + montoGanPer + "]";
	}

}
