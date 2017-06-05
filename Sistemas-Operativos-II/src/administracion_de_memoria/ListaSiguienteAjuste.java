package administracion_de_memoria;

/**
 * Clase que permite administrar una lista de segmentos de memoria mediante el
 * algoritmo de siguiente ajuste.
 * 
 * @author carlosmontoya
 */
public class ListaSiguienteAjuste extends ListaSegmentos
{
	private Segmento actual;
	
	public ListaSiguienteAjuste(int memoriaTotal)
	{
		super(memoriaTotal);
		this.actual = super.getPrimero();
	}
	
	/**
	 * Agrega un nuevo proceso a la lista mediante el algoritmo de siguiente ajuste.
	 * 
	 * @param nombre  El nombre del proceso por agregar
	 * @param longitud  La longitud en bytes del proceso por agregar
	 * 
	 * @return  true si el proceso fue agregado correctamente, o false en caso
	 * contrario
	 */
	@Override
	public boolean agregar(String nombre, int longitud)
	{
		boolean agregado = super.agregar(nombre, longitud);
		if (agregado)
			actual = (actual.getSiguiente() != null)? actual.getSiguiente()
					: super.getPrimero();
		
		return agregado;
	}
	
	@Override
	protected Segmento buscarHueco(int longitud)
	{
		Segmento segmento = super.buscarHueco(longitud, actual, null);
		if (segmento == null)
			segmento = super.buscarHueco(longitud, null, actual);
		
		if (segmento != null) this.actual = segmento;
		
		return segmento;
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
	public boolean eliminar(String nombre)
	{
		Segmento proceso = super.buscarProceso(nombre);
		
		if (proceso != null && actual.isHueco()
				&& (actual == proceso.getAnterior() || actual == proceso.getSiguiente()))
			actual = proceso;
		
		return super.liberarMemoria(proceso);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " : " + this.actual;
	}
}
