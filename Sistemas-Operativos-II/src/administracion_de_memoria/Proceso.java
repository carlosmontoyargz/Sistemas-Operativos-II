package administracion_de_memoria;

/**
 *
 * @author carlosmontoya
 */
public class Proceso
{
    private final String Nombre;
	private final int Direccion;
	private final int Longitud;
	private int Tiempo;
	
	public Proceso(String nombre, int direccion, int longitud, int tiempo)
	{
		this.Nombre = nombre;
		this.Direccion = direccion;
		this.Longitud = longitud;
		this.Tiempo = tiempo;
	}
	
	public void decrementarTiempo()
	{
		this.Tiempo--;
	}
	
	@Override
	public String toString()
	{
		return "[" + this.Nombre + ", " + this.Direccion + ", " + this.Longitud +
				", " + this.Tiempo + "]";
	}
}
