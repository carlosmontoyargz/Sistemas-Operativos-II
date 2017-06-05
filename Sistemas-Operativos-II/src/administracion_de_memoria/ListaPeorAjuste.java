package administracion_de_memoria;

/**
 * Clase que permite administrar una lista de segmentos de memoria mediante el
 * algoritmo del peor ajuste.
 *
 * @author carlosmontoya
 */
public class ListaPeorAjuste extends ListaSegmentos
{
	public ListaPeorAjuste(int memoriaTotal)
	{
		super(memoriaTotal);
	}

	@Override
	protected Segmento buscarHueco(int longitud)
	{
		// Busca el primer hueco compatible
		Segmento mayor = super.buscarHueco(longitud, null, null);
		
		if (mayor != null)
		{
			int longitudMayor = mayor.getLongitud();
			Segmento s = mayor.getSiguiente();
			while (s != null)
			{
				if (s.isHueco() && s.getLongitud() > longitudMayor)
				{
					mayor = s;
					longitudMayor = mayor.getLongitud();
				}
				else
					s = s.getSiguiente();
			}
		}
		
		return mayor;
	}
}
