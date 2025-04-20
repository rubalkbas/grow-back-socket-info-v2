package com.nezztech.apuesta.websocket.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nezztech.apuesta.websocket.model.entity.UsuariosGanPer;
import com.nezztech.apuesta.websocket.model.entity.UsuariosMontos;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ClientesDatosRepository {
	
	@Autowired
	public EntityManager entityManager;
	
	private static final String SQL = "SELECT \r\n"
			+ "    u.id_usuario,\r\n"
			+ "    u.total_dinero AS balance,\r\n"
			+ "    (u.margen_libre + COALESCE(SUM(CASE WHEN ac.estatus_compra = 'ABIERTA' THEN hac.monto_gan_per ELSE 0 END), 0)) AS margenLibre,\r\n"
			+ "    u.margen AS margen\r\n"
			+ "FROM interna.usuarios u\r\n"
			+ "LEFT JOIN interna.apuesta_cliente ac ON u.id_usuario = ac.id_usuario\r\n"
			+ "LEFT JOIN interna.historico_apuesta_cliente hac ON ac.id_apuesta_cliente = hac.id_apuesta_cliente\r\n"
			+ "GROUP BY u.id_usuario, u.total_dinero, u.margen_libre, u.margen\r\n"
			+ "ORDER BY u.id_usuario ASC";
	
	@Transactional
	public List<UsuariosMontos> datosPosiciones() {
		
		List<UsuariosMontos> lista = new ArrayList<>();
			
		String sql = SQL;
		
		Query query = entityManager.createNativeQuery(sql);		
		
		List<Object[]> resultado = query.getResultList();
		
		if (!resultado.isEmpty()) {
			
			for(Object[] datos : resultado) {
				
				UsuariosMontos usuariosMontos = new UsuariosMontos();
				
				usuariosMontos.setIdUsuario((Integer) datos[0]);	
				
				usuariosMontos.setTotalDinero( (BigDecimal) datos[1]);				
				usuariosMontos.setMargenLibre( (BigDecimal) datos[2]);
				usuariosMontos.setMargen( (BigDecimal) datos[3]);
				
				lista.add(usuariosMontos);
				
			}
						
		}
		
		return lista;
	}

}
