package administracion_de_memoria;

/**
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
