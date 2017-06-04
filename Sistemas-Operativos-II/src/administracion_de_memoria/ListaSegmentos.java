package administracion_de_memoria;

/**
 * Clase abstracta que permite implemntar un administrador de memoria mediante
 * una lista doblemente ligada.
 *
 * @author carlosmontoya
 */
public abstract class ListaSegmentos implements AdministradorMemoria
{
	private Segmento primero;
	private Segmento ultimo;
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
		this.memoriaTotal = memoriaTotal;
	}
	
	/**
	 * Busca el hueco en el que se almacenara el proceso nuevo.
	 * 
	 * @param longitud  El tamano en bytes del nuevo proceso
	 * @return 
	 */
	protected abstract Segmento buscarHueco(int longitud);
	
	/**
	 * Agrega un nuevo proceso a la lista de segmentos.
	 * 
	 * @param nombre  El nombre del nuevo proceso
	 * @param longitud  El tamano en bytes del nuevo proceso
	 * 
	 * @return  La direccion en la que fue agregado el proceso
	 */
	@Override
	public int agregar(String nombre, int longitud)
	{
		if (nombre.equals("H")) return -1;
		Segmento hueco = buscarHueco(longitud);
		if (hueco == null) return -1;
		sustituirHueco(hueco, nombre, longitud);
		
		return hueco.getDireccion();
	}
	
	/**
	 * Elimina el proceso con el nombre especificado.
	 * 
	 * @param nombre  El nombre del proceso
	 * 
	 * @return  La direccion del hueco generado, o -1 si ningun proceso fue eliminado
	 */
	@Override
	public int eliminar(String nombre)
	{	
		// Si se intenta buscar un hueco se retorna null
		if (nombre.equals("H")) return -1;
		
		Segmento proceso = this.primero;
		while (proceso != null && !(proceso.getNombre().equals(nombre)))
				proceso = proceso.getSiguiente();
		
		if (proceso != null)
		{
			proceso.setNombre("H");
			return proceso.getDireccion();
		}
		
		return -1;
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
	
	public int getMemoriaTotal() { return memoriaTotal; }
	
	protected Segmento getPrimero() { return this.primero; }
	
	protected Segmento getUltimo() { return this.ultimo; }
	
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
