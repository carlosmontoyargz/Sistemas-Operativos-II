package administracion_de_memoria;

/**
 *
 * @author carlosmontoya
 */
public class Segmento
{
	private String Nombre;
	private int Direccion;
	private int Longitud;
	
	private Segmento Siguiente;
	private Segmento Anterior;
	
	/**
	 * Construye un nodo para la lista de segmentos.
	 * 
	 * @param nombre
	 * @param direccion
	 * @param longitud
	 * @param anterior
	 * @param siguiente
	 */
	public Segmento(String nombre, int direccion, int longitud, Segmento anterior,
			Segmento siguiente)
	{
		this.Nombre = nombre;
		this.Direccion = direccion;
		this.Longitud = longitud;
		this.Anterior = anterior;
		this.Siguiente = siguiente;
	}
	
	public Segmento(String nombre, int direccion, int longitud)
	{
		this(nombre, direccion, longitud, null, null);
	}

	@Override
	public String toString()
	{
		return "[" + this.Nombre + ", " + this.Direccion + ", " + this.Longitud + "]";
	}
	
	public void setNombre(String Nombre)
	{
		this.Nombre = Nombre;
	}

	public void setDireccion(int Direccion)
	{
		this.Direccion = Direccion;
	}

	public void setLongitud(int Longitud)
	{
		this.Longitud = Longitud;
	}
	
	public void setAnterior(Segmento anterior)
	{
		this.Anterior = anterior;
	}
	
	public void setSiguiente(Segmento siguiente)
	{
		this.Siguiente = siguiente;
	}
	
	public Segmento getAnterior()
	{
		return this.Anterior;
	}
	
	public String getNombre()
	{
		return Nombre;
	}

	public int getDireccion()
	{
		return Direccion;
	}

	public int getLongitud()
	{
		return Longitud;
	}
		
	public Segmento getSiguiente()
	{
		return this.Siguiente;
	}
}
