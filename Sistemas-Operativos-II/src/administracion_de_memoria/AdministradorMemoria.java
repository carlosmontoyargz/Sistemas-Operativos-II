package administracion_de_memoria;

/**
 * Interface para un administrador de memoria.
 * 
 * @author carlosmontoya
 */
public interface AdministradorMemoria
{
	/**
	 * Asigna memoria a un proceso.
	 * 
	 * @param nombre  El nombre del proceso a guardar.
	 * @param longitud  El tamano en bytes del proceso.
	 * @return 
	 */
	public boolean agregar(String nombre, int longitud);
	
	/**
	 * Elimina un proceso guardado en memoria.
	 * 
	 * @param nombre  El nombre del proceso a eliminar
	 * @return 
	 */
	public boolean eliminar(String nombre);
}

/*
// Agrega el proceso
		long t1 = new Date().getTime();
		boolean agregado = agregarProceso(nombre, longitud);
		long t2 = new Date().getTime();
		
		//Actualiza estadisticas
		if (!agregado) this.numProcesosRechazados ++;
		this.tiempoEjecucionAgregacion += t2 - t1;
		System.out.println(t2 + " "  + t1);
		return agregado;


System.out.println("Tiempo de ejecucion de agregacion: " +
				adm.tiempoEjecucionAgregacion() + "\n" + 
				"Tiempo de ejecucion de eliminacion: " +
				adm.tiempoEjecucionEliminacion() + "\n" +
				"Total de procesos rechazados: " +
				adm.numProcesosRechazados() + "\n");
*/