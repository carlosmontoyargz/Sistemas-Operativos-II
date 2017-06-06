package administracion_de_memoria;

/**
 * Clase que representa un nodo en una lista doblemente ligada de segmentos de
 * memoria.
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
	
	public static final String nombreHueco = "H";
	
	/**
	 * Construye un nodo para la lista de segmentos.
	 * 
	 * @param nombre  El nombre del segmento
	 * @param direccion  La direccion en memoria del segmento
	 * @param longitud  La longitud en bytes del segmento
	 * @param anterior  El segmento anterior en la lista
	 * @param siguiente  El segmento siguiente en la lista
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
	
	/**
	 * Construye un hueco.
	 * 
	 * @param direccion  La direccion en memoria del hueco
	 * @param longitud  La longitud en bytes del hueco
	 * @param anterior  El segmento anterior en la lista
	 * @param siguiente  El segmento siguiente en la lista
	 */
	public Segmento(int direccion, int longitud, Segmento anterior, Segmento siguiente)
	{
		this(Segmento.nombreHueco, direccion, longitud, anterior, siguiente);
	}
	
	/**
	 * Construye un hueco.
	 *
	 * @param direccion  La direccion en memoria del hueco
	 * @param longitud  La longitud en bytes del hueco
	 */
	public Segmento(int direccion, int longitud)
	{
		this(Segmento.nombreHueco, direccion, longitud, null, null);
	}
	
	public Segmento(String nombre, int direccion, int longitud)
	{
		this(nombre, direccion, longitud, null, null);
	}
	
	/**
	 * Comprueba si el segmento es un hueco.
	 * 
	 * @return  true si el segmento es un hueco, false en caso contrario
	 */
	public boolean isHueco()
	{
		return this.Nombre.equals(Segmento.nombreHueco);
	}
	
	public static boolean isNombreHueco(String nombre)
	{
		return nombreHueco.equals(nombre);
	}
	
	public void setNombre(String Nombre) { this.Nombre = Nombre; }
	
	public void setDireccion(int Direccion) { this.Direccion = Direccion; }
	
	public void setLongitud(int Longitud) { this.Longitud = Longitud; }
	
	public void setAnterior(Segmento anterior) { this.Anterior = anterior; }
	
	public void setSiguiente(Segmento siguiente) { this.Siguiente = siguiente; }
	
	public String getNombre() { return Nombre; }
	
	public int getDireccion() { return Direccion; }
	
	public int getLongitud() { return Longitud; }
	
	public Segmento getAnterior() { return this.Anterior; }
	
	public Segmento getSiguiente() { return this.Siguiente; }
	
	@Override
	public String toString()
	{
		return "[" + this.Nombre + ", " + this.Direccion + ", " + this.Longitud + "]";
	}
}
