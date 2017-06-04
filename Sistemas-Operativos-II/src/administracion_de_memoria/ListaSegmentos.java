package administracion_de_memoria;

/**
 * Clase abstracta que permite implementar un administrador de memoria mediante
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
		//Crea el hueco inicial
		Segmento nodoInicial = new Segmento(0, memoriaTotal, null, null);
		
		this.primero = nodoInicial;
		this.ultimo = nodoInicial;
		this.memoriaTotal = memoriaTotal;
	}
	
	/**
	 * Busca el hueco en el que se almacenara el proceso nuevo. Si no se encuentra
	 * un hueco adecuado, el proceso debe retornar null. El segmento retornado debe
	 * ser un hueco.
	 * 
	 * @param longitud  El tamano en bytes del nuevo proceso
	 * 
	 * @return  El hueco adecuado en el que guardar el proceso nuevo
	 */
	protected abstract Segmento buscarHueco(int longitud);
	
	/**
	 * Agrega un nuevo proceso a la lista de segmentos. La operacion se cancela
	 * si se intenta agregar un hueco.
	 * 
	 * @param nombre  El nombre del nuevo proceso
	 * @param longitud  El tamano en bytes del nuevo proceso
	 * 
	 * @return  La direccion en la que fue agregado el proceso, o -1 si el proceso
	 * no fue agregado.
	 */
	@Override
	public int agregar(String nombre, int longitud)
	{
		// Si se intenta agregar un hueco la operacion se cancela
		if (Segmento.isNombreHueco(nombre)) return -1;
		
		// Se busca el hueco adecuado para el proceso
		Segmento hueco = buscarHueco(longitud);
		
		// Si no se encuentra un hueco adecuado o el segmento no es un hueco la operacion se cancela
		if (hueco == null) return -1; 
		if (!hueco.isHueco()) return -1;
		
		//Se guarda el proceso en el hueco encontrado
		guardarProceso(hueco, nombre, longitud);
		
		return hueco.getDireccion();
	}
	
	/**
	 * Elimina el proceso con el nombre especificado. Fusiona los huecos adyacentes.
	 * La operacion se cancela si se intenta eliminar un hueco.
	 * 
	 * @param nombre  El nombre del proceso a eliminar
	 * 
	 * @return  La direccion del hueco generado, o -1 si ningun proceso fue
	 * eliminado de la lista.
	 */
	@Override
	public int eliminar(String nombre)
	{
		// Si se intenta eliminar un hueco la operacion se cancela
		if (Segmento.isNombreHueco(nombre)) return -1;
		
		// Busca el proceso en la lista
		Segmento proceso = this.primero;
		while (proceso != null && !(proceso.getNombre().equals(nombre)))
				proceso = proceso.getSiguiente();
		
		if (proceso == null) return -1;
		
		proceso.setNombre(Segmento.nombreHueco);
		
		// Fusiona los huecos contiguos
		Segmento anterior = proceso.getAnterior();
		Segmento siguiente = proceso.getSiguiente();		
		if (anterior != null && anterior.isHueco())
		{
			proceso.setDireccion(anterior.getDireccion());
			proceso.setLongitud(proceso.getLongitud() + anterior.getLongitud());
			proceso.setAnterior(anterior.getAnterior());
			
			anterior = proceso.getAnterior();
			
			if (anterior != null)
				anterior.setSiguiente(proceso);
			else
				this.primero = proceso;
		}
		if (siguiente != null && siguiente.isHueco())
		{
			proceso.setLongitud(proceso.getLongitud() + siguiente.getLongitud());
			proceso.setSiguiente(siguiente.getSiguiente());
			
			siguiente = proceso.getSiguiente();
			
			if (siguiente != null)
				siguiente.setAnterior(proceso);
			else
				this.ultimo = proceso;
		}
		
		return proceso.getDireccion();
	}
	
	private void guardarProceso(Segmento hueco, String nombre, int longitud)
	{
		hueco.setNombre(nombre);
		
		// Si el hueco es mas grande que el proceso
		if (hueco.getLongitud() > longitud)
		{
			Segmento nuevoHueco = new Segmento(
					hueco.getDireccion() + longitud,
					hueco.getLongitud() - longitud,
					hueco, hueco.getSiguiente());
			
			hueco.setLongitud(longitud);
			hueco.setSiguiente(nuevoHueco);
			
			Segmento siguiente = nuevoHueco.getSiguiente();
			if (siguiente != null)
				siguiente.setAnterior(nuevoHueco);
			else
				this.ultimo = nuevoHueco;
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
