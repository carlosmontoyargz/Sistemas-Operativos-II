package administracion_de_memoria;

/**
 * Clase que permite administrar una lista de segmentos de memoria mediante el
 * algoritmo de siguiente ajuste.
 * 
 * @author carlosmontoya
 */
public class ListaSiguienteAjuste extends ListaSegmentos
{
	public ListaSiguienteAjuste(int memoriaTotal)
	{
		super(memoriaTotal);
	}
	
	@Override
	protected synchronized Segmento buscarHueco(int longitud)
	{
		Segmento segmento = super.buscarHueco(longitud, 2);
		if (segmento == null)
			segmento = super.buscarHueco(longitud, 1);
		
		return segmento;
	}
	
	@Override
	public synchronized String toString()
	{
		return super.toString() + " : " + super.getActual();
	}
}
