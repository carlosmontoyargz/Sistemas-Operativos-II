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
	private Segmento actual;
	
	/**
	 * Construye una lista de segmentos para con la memoria especificada.
	 * 
	 * @param memoriaTotal  La memoria disponible
	 */
	protected ListaSegmentos(int memoriaTotal)
	{
		//Crea el hueco inicial
		Segmento nodoInicial = new Segmento(0, memoriaTotal, null, null);
		
		this.primero = nodoInicial;
		this.ultimo = nodoInicial;
		this.actual = nodoInicial;
	}
		
	/**
	 * Agrega un nuevo proceso a la lista de segmentos. La operacion se cancela
	 * si se intenta agregar un hueco.
	 * 
	 * @param nombre  El nombre del proceso por agregar
	 * @param longitud  El tamano en bytes del proceso por agregar
	 * 
	 * @return  true si el proceso fue agregado correctamente, o false en caso contrario
	 */
	@Override
	public synchronized boolean agregar(String nombre, int longitud)
	{
		return agregarProceso(nombre, longitud);
	}

	private synchronized boolean agregarProceso(String nombre, int longitud)
	{
		// Si se intenta agregar un hueco o un proceso con longitud no positiva
		// la operacion se cancela
		if (Segmento.isNombreHueco(nombre) || longitud < 1) return false;
		
		// Se busca el hueco adecuado para el proceso
		Segmento hueco = buscarHueco(longitud);
		
		// Si no se encuentra un hueco adecuado o el segmento no es un hueco la operacion se cancela
		if (hueco == null) return false;
		if (!hueco.isHueco()) return false;
		
		//Se guarda el proceso en el hueco encontrado
		guardarProceso(hueco, nombre, longitud);
		
		// Actualiza la referencia actual
		actual = (hueco.getSiguiente() != null) ? hueco.getSiguiente()
				:this.primero;
		
		return true;
	}
	
	/**
	 * Busca el hueco en el que se almacenara el proceso nuevo. Las subclases deben
	 * sobreescribir este metodo para cambiar el algoritmo de busqueda del hueco.
	 * Si el metodo no se sobreescribe se utiliza el algoritmo de primer ajuste.
	 * Si no se encuentra un hueco adecuado, el proceso debe retornar null.
	 * El segmento retornado debe ser un hueco.
	 * 
	 * @param longitud  El tamano en bytes del nuevo proceso
	 * 
	 * @return  El hueco adecuado en el que guardar el proceso nuevo
	 */
	protected synchronized Segmento buscarHueco(int longitud)
	{
		return buscarHueco(longitud, 0);
	}
	
	/**
	 * Busca el primer hueco en la lista con la longitud en bytes necesaria para el
	 * proceso. Retorna null si no se ha encontrado un hueco adecuado en el rango
	 * especificado.
	 * 
	 * @param longitud  La longitud en bytes del proceso
	 * @param tipo  El  tipo de busqueda dentro de la lista:
	 *					0 para buscar desde el inicio hasta el final,
	 *					1 para buscar desde el inicio hasta el actual,
	 *					2 para buscar desde el actual hasta el final
	 * 
	 * @return  El hueco encontrado, o null si no se ha encontrado un hueco adecuado
	 */
	protected synchronized Segmento buscarHueco(int longitud, int tipo)
	{
		Segmento inicio = this.primero, final1 = null;
		
		if (tipo == 1)
			final1 = this.actual;
		else if (tipo == 2)
			inicio = this.actual;
		
		boolean encontrado = false;
		Segmento segmento = inicio;
		while (segmento != final1 && !encontrado)
		{
			if (segmento.isHueco() && segmento.getLongitud() >= longitud)
				encontrado = true;
			else
				segmento = segmento.getSiguiente();
		}
		
		if (segmento == final1 && !encontrado) segmento = null;
		
		return segmento;
	}
	
	private synchronized void guardarProceso(Segmento hueco, String nombre, int longitud)
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
	
	/**
	 * Elimina el proceso con el nombre especificado.
	 * 
	 * @param nombre  El nombre del proceso a eliminar
	 * 
	 * @return  true si el proceso fue eliminado correctamente, o false si no se
	 * ha eliminado nada en la lista
	 */
	@Override
	public synchronized boolean eliminar(String nombre)
	{
		return liberarMemoria(buscarProceso(nombre));
	}
	
	/**
	 * Busca en la lista el proceso con el nombre especificado.
	 * 
	 * @param nombre  El nombre del proceso a buscar
	 * 
	 * @return  La referencia al proceso buscado, o null en caso de no ser encontrado
	 */
	protected synchronized Segmento buscarProceso(String nombre)
	{
		// Si se intenta buscar un hueco la operacion se cancela
		if (Segmento.isNombreHueco(nombre)) return null;
		
		// Busca el proceso en la lista
		Segmento proceso = this.primero;
		while (proceso != null && !(proceso.getNombre().equals(nombre)))
				proceso = proceso.getSiguiente();
		
		return proceso;
	}
	
	/**
	 * Convierte en hueco el proceso especificado y fusiona los huecos adyacentes.
	 * 
	 * @param proceso  El proceso a ser convertido en hueco
	 * 
	 * @return  true si el proceso fue convertido en hueco, false si el argumento
	 * es null
	 */
	protected synchronized boolean liberarMemoria(Segmento proceso)
	{
		if (proceso == null) return false;
		
		proceso.setNombre(Segmento.nombreHueco);
		this.actual = proceso;
		
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
		
		return true;
	}
	
	protected synchronized Segmento getPrimero()
	{
		return this.primero;
	}
	
	protected synchronized Segmento getUltimo()
	{
		return this.ultimo;
	}
	
	protected synchronized Segmento getActual()
	{
		return this.actual;
	}
	
	@Override
	public synchronized String toString()
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
