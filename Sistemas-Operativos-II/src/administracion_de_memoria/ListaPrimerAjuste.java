package administracion_de_memoria;

/**
 * Clase que permite administrar una lista de segmentos de memoria mediante el
 * algoritmo de primer ajuste.
 * 
 * @author carlosmontoya
 */
public class ListaPrimerAjuste extends ListaSegmentos
{
	public ListaPrimerAjuste(int memoriaTotal)
	{
		super(memoriaTotal);
	}

	/**
	 * Busca el primer hueco en la lista que sea adecuado para el proceso por agregar.
	 * 
	 * @param longitud  La longitud en bytes del proceso por agregar
	 * 
	 * @return  El segmento encontrado para el proceso
	 */
	@Override
	protected Segmento buscarHueco(int longitud)
	{
		Segmento segmento = super.getPrimero();
		while (segmento != null &&
				!(segmento.isHueco() && segmento.getLongitud() >= longitud))
			segmento = segmento.getSiguiente();
		
		return segmento;
	}    
}
