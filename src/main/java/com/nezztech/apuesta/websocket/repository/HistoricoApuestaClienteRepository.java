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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class HistoricoApuestaClienteRepository {
	
	@Autowired
	public EntityManager entityManager;
	
	private static final String SQL = "SELECT id_apuesta_cliente, SUM(monto_gan_per) AS total_suma "
			+ "FROM interna.historico_apuesta_cliente "
			+ "WHERE id_apuesta_cliente IN (:ids)  "
			+ "GROUP BY id_apuesta_cliente";
	
	@Transactional
	public List<UsuariosGanPer> sumaGananciaPerdida(List<Integer> idsAbiertas) {
		
		List<UsuariosGanPer> lista = new ArrayList<>();
			
		String sql = SQL;
		
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("ids", idsAbiertas);
		
		List<Object[]> resultado = query.getResultList();
		
		if (!resultado.isEmpty()) {
			
			for(Object[] datos : resultado) {
				
				UsuariosGanPer usuariosGanPer = new UsuariosGanPer();
				usuariosGanPer.setIdUsuario((Integer) datos[0]);
				usuariosGanPer.setMontoGanPer( (BigDecimal) datos[1]);
				
				lista.add(usuariosGanPer);
				
			}
						
		}
		
		return lista;
	}

}
