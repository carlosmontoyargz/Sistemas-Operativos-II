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
		// Busca el primer hueco adecuado para el proceso
		Segmento hueco = this.primero;
		boolean encontrado = false;
		while (hueco != null && !encontrado)
			if (hueco.getNombre().equals("H") && hueco.getLongitud() >= longitud)
				encontrado = true;
			else
				hueco = hueco.getSiguiente();
		
		// Si no encontro un hueco con suficiente espacio se rechaza la operacion
		if (hueco == null) return -1;
		
		sustituirHueco(hueco, nombre, longitud);
		return hueco.getDireccion();
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
	
	private void sustituirHueco(Segmento hueco, String nombre, int longitud)
	{
		// Crea el segmento para el proceso nuevo
		Segmento segmentoProceso = new Segmento(nombre, hueco.getDireccion(), longitud);
		segmentoProceso.setAnterior(hueco.getAnterior());
		
		Segmento referenciaAnterior;
		// Si el proceso ocupa menos espacio que el hueco encontrado
		if (longitud < hueco.getLongitud())
		{	
			segmentoProceso.setSiguiente(new Segmento("H",
					hueco.getDireccion() + longitud,
					hueco.getLongitud() - longitud,
					segmentoProceso,
					hueco.getSiguiente()));
			
			referenciaAnterior = segmentoProceso.getSiguiente();
		}
		
		// Si el proceso tiene exactamente el tamaño del hueco
		else
		{
			segmentoProceso.setSiguiente(hueco.getSiguiente());
			referenciaAnterior = segmentoProceso;
		}

		// Actualiza las referencias en los nodos anterior y siguiente al hueco que se sustituye
		if (hueco.getAnterior() != null)
			hueco.getAnterior().setSiguiente(segmentoProceso);
		else
			this.primero = segmentoProceso;
	
		if (hueco.getSiguiente() != null)
			hueco.getSiguiente().setAnterior(referenciaAnterior);
		else
			this.ultimo = referenciaAnterior;
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
