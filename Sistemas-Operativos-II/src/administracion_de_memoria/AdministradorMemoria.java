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
	 * Elimina un proceso guardado memoria.
	 * 
	 * @param nombre  El nombre del proceso a eliminar
	 * @return 
	 */
	public boolean eliminar(String nombre);
	
	/**
	 * @return  El tiempo de ejecucion total agregando procesos.
	 */
	public long tiempoEjecucionAgregacion();
	
	/**
	 * @return  El tiempo de ejecucion total eliminando procesos.
	 */
	public long tiempoEjecucionEliminacion();
	
	/**
	 * @return  El total de procesos rechazados
	 */
	public int numProcesosRechazados();
}
