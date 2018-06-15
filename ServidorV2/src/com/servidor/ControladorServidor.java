package com.servidor;

import java.util.ArrayList;

import com.Chain.Chain;
import com.Chain.CrearSala;
import com.Chain.DesconectarCliente;
import com.Chain.EnviarMsjASala;
import com.cliente.Cliente;
import com.logs.LoggerCliente;
import com.mensajes.Comandos;
import com.mensajes.Mensaje;
import com.sala.Sala;

/**
 * Singleton. Controlador general del chat. Desde aca se deberia poder hacer
 * todo lo necesario en el funcionamiento publico del chat, como enviar mensajes
 * o logouts. Sera accedida desde multiples hilos al mismo tiempo.
 * <b>Sincronizar!</b>
 * 
 * @author Maxi
 *
 */
public class ControladorServidor {

	private static ControladorServidor instance = null;
	Chain chain;
	private ArrayList<Cliente> clientesEnLobby; // Todos los clientes conectados al chat estan aca. esten o no chateando.
	private ArrayList<Sala> salas;


	protected ControladorServidor() {
		
		clientesEnLobby = new ArrayList<Cliente>();
		salas = new ArrayList<Sala>();
		salas.add(new Sala("Lobby"));
		chain = ensamblarChain();
	}

	public synchronized static ControladorServidor getInstance() {
		if (instance == null) {
			instance = new ControladorServidor();
		}
		return instance;
	}


	public synchronized void manejarMensaje(Mensaje mensaje) {
		chain.manejarPeticion(mensaje);
	}

	private Chain ensamblarChain() {
		CrearSala cs = new CrearSala(salas, clientesEnLobby);
		DesconectarCliente dc = new DesconectarCliente(salas, clientesEnLobby);
		EnviarMsjASala msj = new EnviarMsjASala(clientesEnLobby, salas);

		cs.enlazarSiguiente(dc);
		dc.enlazarSiguiente(msj);
		return cs;
	}





	private Cliente getCliente(String nombre) {
		for (Cliente c : clientesEnLobby) {
			if (c.getNombre().equals(nombre))
				return c;
		}
		return null;
	}

	public synchronized void cerrarSala(String nombreSala) {
		aTodos_SalaCerrada(nombreSala);
	}

	public synchronized void meterEnLobby(Cliente entrante) {
		if (clientesEnLobby.contains(entrante)) {
			LoggerCliente.enviarLog("FALLO: Cliente " + entrante.getNombre() + " intenta loguearse con usuario ya logueado.");
			return;
		}
		LoggerCliente.enviarLog("Cliente " + entrante.getNombre() + " acaba de entrar al chat.");
		clientesEnLobby.add(entrante);
		
		for(Sala s: salas) {
			if(s.getNombre().equals("Lobby")) {
				s.meterCliente(entrante);
				break;
			}
		}

		entrante.getEntrada().setCliente(entrante);
		entrante.getSalida().setCliente(entrante);
		
		aTodos_ClienteConectado(entrante);
		LoggerCliente.enviarLog("Lista de clientes actualizada. Clientes Actuales: " + clientesEnLobby.size());

	}

	private synchronized void entrarASalaPublica(Cliente entrante, String nombreSala) {
		aSala_ClienteEntro(entrante, nombreSala);
	}

	// ----------------- EVENTOS A TODOS----------------------
	private void aTodos_ClienteConectado(Cliente elNuevoEntrante) {

		Mensaje mensaje = new Mensaje(Comandos.ClienteNuevo, elNuevoEntrante.getNombre());

		for (Cliente yaConectados : clientesEnLobby) {
			if (!elNuevoEntrante.equals(yaConectados)) {
				yaConectados.enviarMensaje(new Mensaje(Comandos.ClienteNuevo, elNuevoEntrante.getNombre()));
			} // c.enviarMensaje(mensaje);
		}
		
		for (Cliente c : clientesEnLobby) {
				elNuevoEntrante.enviarMensaje(new Mensaje(Comandos.ClienteNuevo, c.getNombre()));
		}
		LoggerCliente.enviarLog("Se envio a todos el nuevo usuario.");
	}

	private void aTodos_SalaCreada() {
		LoggerCliente.enviarLog("IMPLEMENTAR Controlador.aTodos_SalaCreada");
	}

	private void aTodos_SalaCerrada(String nombreSala) {
		LoggerCliente.enviarLog("IMPLEMENTAR Controlador.aTodos_SalaCerrada");
	}


	private synchronized void aTodos_enviarMensaje(Mensaje mensaje, Cliente emisor) {
		for (Cliente c : clientesEnLobby) {
			c.enviarMensaje(mensaje);
		}
	}

	// --------------EVENTOS A SALA------------------------------
	private void aSala_ClienteEntro(Cliente entrante, String sala) {
		LoggerCliente.enviarLog("IMPLEMENTAR Controlador.aSala_ClienteEntro");
	}

	public synchronized ArrayList<Cliente> getClientesEnLobby() {
		return clientesEnLobby;
	}
	
	public synchronized ArrayList<Sala> getSalas() {
		return salas;
	}

	

	
}
