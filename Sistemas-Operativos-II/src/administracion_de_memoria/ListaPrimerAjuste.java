package administracion_de_memoria;


public class ListaPrimerAjuste extends ListaSegmentos
{
	public ListaPrimerAjuste(int memoriaTotal)
	{
		super(memoriaTotal);
	}

	@Override
	protected Segmento buscarHueco(int longitud)
	{
		Segmento segmento = super.getPrimero();
		while (segmento != null &&
				!(segmento.getNombre().equals("H") && segmento.getLongitud() >= longitud))
			segmento = segmento.getSiguiente();
		
		return segmento;
	}    
}
