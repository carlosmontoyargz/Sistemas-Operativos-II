package administracion_de_memoria;

/**
 *
 * @author carlosmontoya
 */
public class ListaSegmentos
{
	private Segmento primero;
    private Segmento ultimo;
	private Segmento actual;
	private int nodos;
	private final int memoriaTotal;
	
	public ListaSegmentos(int memoriaTotal)
	{
		//Crea el segmento vacío inicial
		Segmento nodoInicial = new Segmento("H", 0, 1024);
		
		this.primero = nodoInicial;
		this.ultimo = nodoInicial;
		this.actual = nodoInicial;
		this.memoriaTotal = memoriaTotal;
	}
	
	public void primerAjuste(String nombre, int direccion, int longitud)
	{
		
	}
	
	public void siguienteAjuste(String nombre, int direccion, int longitud)
	{
		
	}
	
	public void mejorAjuste(String nombre, int direccion, int longitud)
	{
		
	}
	
	public void peorAjuste(String nombre, int direccion, int longitud)
	{
		
	}
	
	public int getMemoriaTotal()
	{
		return memoriaTotal;
	}
	
	@Override
	public String toString()
	{
		String lista = "[";
		Segmento nodo = this.primero;
		
		while (nodo != this.ultimo)
		{
			lista += nodo + ", ";
			nodo = nodo.getSiguiente();
		}
		lista += nodo + "]";
		
		return lista;
	}
}

class Segmento
{
	private final String Nombre;
	private final int Direccion;
	private final int Longitud;
	
	private Segmento Siguiente;
	private Segmento Anterior;
	
	/**
	 * Construye un nodo para la lista de segmentos. Inicializa las referencias
	 * anterior y siguiente con null.
	 * @param nombre
	 * @param direccion
	 * @param longitud
	 */
	public Segmento(String nombre, int direccion, int longitud)
	{
		this.Nombre = nombre;
		this.Direccion = direccion;
		this.Longitud = longitud;
		this.Anterior = null;
		this.Siguiente = null;
	}
	
	@Override
	public String toString()
	{
		return "[" + this.Nombre + ", " + this.Direccion + ", " + this.Longitud + "]";
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
		
	public Segmento getSiguiente()
	{
		return this.Siguiente;
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
}
