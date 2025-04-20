/**
 * 
 */
package com.nezztech.apuesta.websocket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nezztech.apuesta.websocket.model.entity.UsuarioInterno;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Repository
public interface UsuarioInternoRepository  extends JpaRepository<UsuarioInterno, Integer> {
	
}
