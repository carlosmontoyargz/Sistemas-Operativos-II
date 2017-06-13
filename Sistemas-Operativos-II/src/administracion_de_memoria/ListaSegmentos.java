package administracion_de_memoria;

/**
 * Clase abstracta que permite implementar un administrador de memoria mediante
 * una lista doblemente ligada.
 *
 * @author carlosmontoya
 */
public class ListaSegmentos
{
	private Segmento primero;
	private Segmento ultimo;
	private Segmento actual;
	
	/**
	 * Construye una lista de segmentos con la memoria especificada.
	 * 
	 * @param memoriaTotal  La memoria disponible
	 */
	public ListaSegmentos(int memoriaTotal)
	{
		//Crea el hueco inicial
		Segmento nodoInicial = new Segmento(0, memoriaTotal, null, null);
		
		this.primero = nodoInicial;
		this.ultimo = nodoInicial;
		this.actual = nodoInicial;
	}
	
	/**
	 * Agrega un nuevo proceso a la lista de segmentos mediante el algoritmo de
	 * primer ajuste.
	 * 
	 * @param nombre  El nombre del proceso por agregar
	 * @param longitud  El tamano en bytes del proceso por agregar
	 * 
	 * @return  true si el proceso fue agregado correctamente, o false en caso
	 * contrario
	 */
	public boolean primerAjuste(String nombre, int longitud)
	{
		if (Segmento.isNombreHueco(nombre) || longitud < 1) return false;
		
		// Se busca el hueco adecuado para el proceso
		Segmento hueco = buscarHueco(longitud, 0);
		
		// Si no se encuentra un hueco adecuado la operacion se cancela
		if (hueco == null) return false;
		
		//Se guarda el proceso en el hueco encontrado
		guardarProceso(hueco, nombre, longitud);
		
		// Actualiza la referencia actual
		actual = (hueco.getSiguiente() != null) ? hueco.getSiguiente()
				:this.primero;
		
		return true;
	}
	
	/**
	 * Agrega un nuevo proceso a la lista de segmentos mediante el algoritmo de
	 * siguiente ajuste.
	 * 
	 * @param nombre  El nombre del proceso por agregar
	 * @param longitud  El tamano en bytes del proceso por agregar
	 * 
	 * @return  true si el proceso fue agregado correctamente, o false en caso
	 * contrario
	 */
	public boolean siguienteAjuste(String nombre, int longitud)
	{
		if (Segmento.isNombreHueco(nombre) || longitud < 1) return false;
		
		// Se busca el hueco adecuado para el proceso
		Segmento hueco = buscarHueco(longitud, 2);
		if (hueco == null) hueco = buscarHueco(longitud, 1);
		
		// Si no se encuentra un hueco adecuado la operacion se cancela
		if (hueco == null) return false;
		
		//Se guarda el proceso en el hueco encontrado
		guardarProceso(hueco, nombre, longitud);
		
		// Actualiza la referencia actual
		actual = (hueco.getSiguiente() != null) ? hueco.getSiguiente()
				:this.primero;
		
		return true;
	}
	
	/**
	 * Agrega un nuevo proceso a la lista de segmentos mediante el algoritmo de
	 * mejor ajuste.
	 * 
	 * @param nombre  El nombre del proceso por agregar
	 * @param longitud  El tamano en bytes del proceso por agregar
	 * 
	 * @return  true si el proceso fue agregado correctamente, o false en caso
	 * contrario
	 */
	public boolean mejorAjuste(String nombre, int longitud)
	{
		if (Segmento.isNombreHueco(nombre) || longitud < 1) return false;
		
		// Busca el primer hueco compatible
		Segmento hueco = buscarHueco(longitud, 0);
		if (hueco != null)
		{
			int longitudMenor = hueco.getLongitud();
			Segmento s = hueco.getSiguiente();
			while (s != null)
			{
				if (s.isHueco() && s.getLongitud() >= longitud && s.getLongitud() < longitudMenor)
				{
					hueco = s;
					longitudMenor = hueco.getLongitud();
				}
				else
					s = s.getSiguiente();
			}
		}
		
		// Si no se encuentra un hueco adecuado la operacion se cancela
		if (hueco == null) return false;
		
		//Se guarda el proceso en el hueco encontrado
		guardarProceso(hueco, nombre, longitud);
		
		// Actualiza la referencia actual
		actual = (hueco.getSiguiente() != null) ? hueco.getSiguiente()
				:this.primero;
		
		return true;
	}
	
	/**
	 * Agrega un nuevo proceso a la lista de segmentos mediante el algoritmo de
	 * peor ajuste.
	 * 
	 * @param nombre  El nombre del proceso por agregar
	 * @param longitud  El tamano en bytes del proceso por agregar
	 * 
	 * @return  true si el proceso fue agregado correctamente, o false en caso
	 * contrario
	 */
	public boolean peorAjuste(String nombre, int longitud)
	{
		if (Segmento.isNombreHueco(nombre) || longitud < 1) return false;
		
		// Busca el primer hueco compatible
		// Busca el primer hueco compatible
		Segmento hueco = buscarHueco(longitud, 0);
		if (hueco != null)
		{
			int longitudMayor = hueco.getLongitud();
			Segmento s = hueco.getSiguiente();
			while (s != null)
			{
				if (s.isHueco() && s.getLongitud() > longitudMayor)
				{
					hueco = s;
					longitudMayor = hueco.getLongitud();
				}
				else
					s = s.getSiguiente();
			}
		}
		
		// Si no se encuentra un hueco adecuado la operacion se cancela
		if (hueco == null) return false;
		
		//Se guarda el proceso en el hueco encontrado
		guardarProceso(hueco, nombre, longitud);
		
		// Actualiza la referencia actual
		actual = (hueco.getSiguiente() != null) ? hueco.getSiguiente()
				:this.primero;
		
		return true;
	}
	
	/**
	 * Busca el primer hueco en la lista con la longitud en bytes necesaria para
	 * el proceso. Retorna null si no se ha encontrado un hueco adecuado en el
	 * rango especificado.
	 * 
	 * @param longitud  La longitud en bytes del proceso
	 * @param tipo  El  tipo de busqueda dentro de la lista:
	 *					0 para buscar desde el inicio hasta el final,
	 *					1 para buscar desde el inicio hasta el actual,
	 *					2 para buscar desde el actual hasta el final
	 * 
	 * @return  El hueco encontrado, o null si no se ha encontrado un hueco
	 * adecuado
	 */
	private synchronized Segmento buscarHueco(int longitud, int tipo)
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
	
	private synchronized void guardarProceso(Segmento hueco, String nombre,
			int longitud)
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
	public synchronized boolean eliminar(String nombre)
	{
		return liberarMemoria(buscarProceso(nombre));
	}
	
	/**
	 * Busca en la lista el proceso con el nombre especificado.
	 * 
	 * @param nombre  El nombre del proceso a buscar
	 * 
	 * @return  La referencia al proceso buscado, o null en caso de no ser
	 * encontrado
	 */
	private synchronized Segmento buscarProceso(String nombre)
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
	private synchronized boolean liberarMemoria(Segmento proceso)
	{
		if (proceso == null) return false;
		
		proceso.setNombre(Segmento.nombreHueco);
		
		// Fusiona los huecos contiguos
		Segmento anterior = proceso.getAnterior();
		Segmento siguiente = proceso.getSiguiente();
		if (anterior != null && anterior.isHueco())
		{
			if (actual == anterior) actual = proceso;
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
			if (actual == siguiente) actual = proceso;
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
		lista += nodo + "] : " + actual;
		
		return lista;
	}
}
