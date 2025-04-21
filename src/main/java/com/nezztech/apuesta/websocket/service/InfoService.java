package com.nezztech.apuesta.websocket.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nezztech.apuesta.websocket.handler.MyWebSocketHandler;
import com.nezztech.apuesta.websocket.handler.MyWebSocketHandler2;
import com.nezztech.apuesta.websocket.model.entity.ApuestaClienteEntity;
import com.nezztech.apuesta.websocket.model.entity.UsuarioInterno;
import com.nezztech.apuesta.websocket.model.entity.UsuariosMontos;
import com.nezztech.apuesta.websocket.model.entity.UsuariosGanPer;
import com.nezztech.apuesta.websocket.repository.ClientesDatosRepository;
import com.nezztech.apuesta.websocket.repository.HistoricoApuestaClienteRepository;
import com.nezztech.apuesta.websocket.repository.IApuestaClienteRepository;
import com.nezztech.apuesta.websocket.repository.UsuarioInternoRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author NEZZTECH
 * @version 1.0
 * @since 2024
 *
 */
@Service
@Slf4j
public class InfoService {

	@Autowired
	private UsuarioInternoRepository usuarioInternoRepository;


	
	@Autowired
	private HistoricoApuestaClienteRepository historicoApuestaClienteRepository;
	
	@Autowired
	private IApuestaClienteRepository iApuestaClienteRepository;
	
	@Autowired
	private ClientesDatosRepository clientesDatosRepository;

	@Autowired
	private MyWebSocketHandler webSocketHandler;

	@Autowired
	private MyWebSocketHandler2 webSocketHandler2;

	// OBTENER INFORMACION BALANCE , MARGEN LIBRE Y MARGEN DE CADA USUARIO
//	public List<UsuarioInterno> consultaClientesBalance() {
//		return usuarioInternoRepository.findAll();
//	}

	@Scheduled(fixedRate = 1000) // Ejecutar cada segundo
	public void sendPeriodicUpdates() {
		
		List<UsuariosMontos> lista = clientesDatosRepository.datosPosiciones();	

		webSocketHandler.sendMessageToAllClients(lista);
		
	}
	
	
	// OBTENER INFORMACION DE CADA POSICION ABIERTA DE LA GANANCIA PERDIDA QUE OBTIENE
	@Scheduled(fixedRate = 1000) // Ejecutar cada segundo
	public void consultaClientesGanPerPosiciones() {
		
		List<ApuestaClienteEntity> abiertas = iApuestaClienteRepository.findByestatusCompra("ABIERTA");
		
		if (abiertas == null || abiertas.isEmpty()) {
            // Si no hay apuestas abiertas, no hacer nada
            return;
        }
		
		List<Long> idsAbiertas = abiertas.stream() .map(ApuestaClienteEntity::getIdApuestaCliente) .collect(Collectors.toList());
		
		List<UsuariosGanPer> lista = historicoApuestaClienteRepository.sumaGananciaPerdida(idsAbiertas);
		
		webSocketHandler2.sendMessageToAllClients(lista);
		
	}

}
