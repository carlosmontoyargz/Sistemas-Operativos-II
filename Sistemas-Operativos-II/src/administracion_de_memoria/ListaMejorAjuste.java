package administracion_de_memoria;

/**
 *
 * @author carlosmontoya
 */
public class ListaMejorAjuste extends ListaSegmentos
{
	public ListaMejorAjuste(int memoriaTotal)
	{
		super(memoriaTotal);
	}

	@Override
	protected Segmento buscarHueco(int longitud)
	{
		// Busca el primer hueco compatible
		Segmento menor = super.buscarHueco(longitud, null, null);
		
		if (menor != null)
		{
			int longitudMenor = menor.getLongitud();
			Segmento s = menor.getSiguiente();
			while (s != null)
			{
				if (s.isHueco() && s.getLongitud() >= longitud && s.getLongitud() < longitudMenor)
				{
					menor = s;
					longitudMenor = menor.getLongitud();
				}
				else
					s = s.getSiguiente();
			}
		}
		
		return menor;
	}
}
