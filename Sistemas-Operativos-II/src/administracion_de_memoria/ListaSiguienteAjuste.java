package administracion_de_memoria;


public class ListaSiguienteAjuste extends ListaSegmentos
{
	private Segmento actual;
	
	public ListaSiguienteAjuste(int memoriaTotal)
	{
		super(memoriaTotal);
		this.actual = super.getPrimero();
	}
	
	@Override
	public int agregar(String nombre, int longitud)
	{
		int dir = super.agregar(nombre, longitud);
		
		actual = (actual.getSiguiente() != null)? actual.getSiguiente() : super.getPrimero();
		
		return dir;
	}

	@Override
	protected Segmento buscarHueco(int longitud)
	{
		Segmento segmento = buscarHueco(longitud, 0);
		
		if (segmento == null)
		{
			segmento = buscarHueco(longitud, 1);
			
			if (segmento == this.actual) return null;
		}
		
		this.actual = segmento;
		
		return segmento;
	}
	
	@Override
	public int eliminar(String nombre)
	{
		Segmento proceso = super.buscarProceso(nombre);
		
		if (actual.isHueco()
				&& (actual == proceso.getAnterior() || actual == proceso.getSiguiente()))
			actual = proceso;
		
		actual = true ? actual : null;
		
		int dir = eliminarProceso(proceso)? proceso.getDireccion(): -1;
		
		return dir;
	}
	
	private Segmento buscarHueco(int longitud, int tipoBusqueda)
	{
		Segmento segmento, final1;
		if (tipoBusqueda == 0)
		{
			segmento = this.actual;
			final1 = null;
		}
		else
		{
			segmento = super.getPrimero();
			final1 = this.actual;
		}
		
		while (segmento != final1 &&
				!(segmento.isHueco() && segmento.getLongitud() >= longitud))
			segmento = segmento.getSiguiente();
		
		return segmento;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " : " + this.actual;
	}
}

class ListaSiguienteAjusteTest
{
	public static void main(String[] args)
	{
		ListaSegmentos lista = new ListaSiguienteAjuste(1024);
		System.out.println(lista);
		
		lista.agregar("Prueba1", 100);
		System.out.println(lista);
		
		lista.agregar("Prueba2", 200);
		System.out.println(lista);
		
		lista.agregar("Prueba3", 250);
		System.out.println(lista);
		
		lista.agregar("Prueba4", 200);
		System.out.println(lista);
		
		lista.agregar("Prueba5", 273);
		System.out.println(lista);
		
		lista.eliminar("Prueba2");
		System.out.println(lista);
		
		lista.eliminar("Prueba5");
		System.out.println(lista);
		
		lista.agregar("Prueba6", 150);
		System.out.println(lista);
		
		lista.agregar("Prueba7", 160);
		System.out.println(lista);
		
		lista.eliminar("Prueba3");
		System.out.println(lista);
		
		lista.eliminar("Prueba4");
		System.out.println(lista);
		
		lista.eliminar("Prueba1");
		System.out.println(lista);
		
		lista.eliminar("Prueba7");
		System.out.println(lista);
		
		lista.eliminar("Prueba6");
		System.out.println(lista);
	}
}