package com.nezztech.apuesta.websocket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nezztech.apuesta.websocket.model.entity.ApuestaClienteEntity;


public interface IApuestaClienteRepository extends JpaRepository<ApuestaClienteEntity, Long> {	
	
	List<ApuestaClienteEntity> findByestatusCompra( String estatusCompra ); 	

}
