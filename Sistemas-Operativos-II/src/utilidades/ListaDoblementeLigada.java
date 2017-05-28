package utilidades;

import java.util.*;

/**
 *
 * @author carlosmontoya
 */
public class ListaDoblementeLigada
{
    
}

class NodoListaDoblementeLigada
{
	private String NombreProceso;
	private int TiempoDeEjecucion;
	private NodoListaDoblementeLigada Siguiente;
	private NodoListaDoblementeLigada Anterior;
	
	public NodoListaDoblementeLigada(String nombre, int tiempo)
	{
		this.NombreProceso = nombre;
		this.TiempoDeEjecucion = tiempo;
		this.Anterior = null;
		this.Siguiente = null;
	}
	
	public int getTiempoDeEjecucion()
	{
		return this.TiempoDeEjecucion;
	}
}
