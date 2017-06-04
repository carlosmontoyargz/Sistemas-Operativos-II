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
	private int segmentos;
	private final int memoriaTotal;
	
	/**
	 * Construye una lista de segmentos para con la memoria especificada.
	 * 
	 * @param memoriaTotal  La memoria disponible
	 */
	public ListaSegmentos(int memoriaTotal)
	{
		//Crea el segmento vacío inicial
		Segmento nodoInicial = new Segmento("H", 0, memoriaTotal);
		
		this.primero = nodoInicial;
		this.ultimo = nodoInicial;
		this.actual = nodoInicial;
		this.memoriaTotal = memoriaTotal;
	}
	
	/**
	 * Asigna memoria a un proceso mediante el algoritmo de primer ajuste. Si no
	 * se encuentra espacio suficiente, se rechaza la solicitud.
	 * 
	 * @param nombre  El nombre del proceso
	 * @param longitud  La longitud en bytes que necesita el proceso
	 * 
	 * @return  La direccion en la que se agregó el proceso, o -1 en caso de que
	 * la operación haya sido rechazada.
	 */
	public int primerAjuste(String nombre, int longitud)
	{
		if (nombre.equals("H")) return -1;
		Segmento hueco = buscarHueco(longitud);
		if (hueco == null) return -1;
		sustituirHueco(hueco, nombre, longitud);
		
		return hueco.getDireccion();
	}
	
	public int siguienteAjuste(String nombre, int direccion, int longitud)
	{
		// Busca el primer hueco adecuado despues del actual para el proceso
		Segmento actualCopia = this.actual;
		Segmento hueco = null;
		
		// Si no encontro un hueco con suficiente espacio se rechaza la operacion
		if (hueco == null) return -1;
		
		sustituirHueco(hueco, nombre, longitud);
		
		return hueco.getDireccion();
	}
	
	public void mejorAjuste(String nombre, int direccion, int longitud)
	{
		
	}
	
	public void peorAjuste(String nombre, int direccion, int longitud)
	{
		
	}
	
	public int eliminarProceso(String nombre)
	{
		Segmento proceso = buscarProceso(nombre);
		
		return 0;
	}
	
	private void sustituirHueco(Segmento hueco, String nombre, int longitud)
	{
		// Si el hueco no es lo suficientemente grande, retorna
		if (hueco.getLongitud() < longitud) return;
		
		hueco.setNombre(nombre);
		
		// Si el hueco es mas grande que el proceso
		if (hueco.getLongitud() > longitud)
		{
			Segmento nuevoHueco = new Segmento("H",
					hueco.getDireccion() + longitud,
					hueco.getLongitud() - longitud,
					hueco, hueco.getSiguiente());
			
			hueco.setLongitud(longitud);
			
			Segmento siguiente;
			if ((siguiente = hueco.getSiguiente()) != null)
				siguiente.setAnterior(nuevoHueco);
			else
				this.ultimo = nuevoHueco;
			hueco.setSiguiente(nuevoHueco);
		}
	}
	
	private Segmento buscarProceso(String nombre)
	{
		if (nombre.equals("H")) return null;
		
		Segmento segmento = this.primero;
		boolean encontrado = false;
		while (segmento != null && !encontrado)
			if (segmento.getNombre().equals(nombre))
				encontrado = true;
			else
				segmento = segmento.getSiguiente();
		
		return segmento;
	}
	
	private Segmento buscarHueco(Segmento inicio, Segmento final1, int longitud)
	{
		if (inicio == null) inicio = this.primero;
		if (final1 == null) final1 = this.ultimo;
		
		Segmento segmento = inicio;
		while (segmento != final1 &&
				!(segmento.getNombre().equals("H") && segmento.getLongitud() >= longitud))
			segmento = segmento.getSiguiente();
		
		return segmento;
	}
	
	private Segmento buscarHueco(int longitud)
	{
		return buscarHueco(null, null, longitud);
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
	private String Nombre;
	private int Direccion;
	private int Longitud;
	
	private Segmento Siguiente;
	private Segmento Anterior;
	
	/**
	 * Construye un nodo para la lista de segmentos.
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
