package administracion_de_memoria;

/**
 *
 * @author carlosmontoya
 */
public interface AdministradorMemoria
{
	public boolean agregar(String nombre, int longitud);
	
	public boolean eliminar(String nombre);
}
