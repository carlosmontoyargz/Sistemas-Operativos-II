package administracion_de_memoria;

/**
 *
 * @author carlosmontoya
 */
public class ListasSegmentos
{
	private final ListaAjusteRapido[] listaHuecos;
	private final ListaAjusteRapido listaProcesos;
	
	private int procesosRechazados;
	private int huecosPequeñosGenerados;
	
	public ListasSegmentos(int memoriaTotal)
	{
		this.listaHuecos = new ListaAjusteRapido[7];
		this.listaHuecos[0] = new ListaAjusteRapido(0);// < 50
		this.listaHuecos[1] = new ListaAjusteRapido(50);// 50 - 99
		this.listaHuecos[2] = new ListaAjusteRapido(100);// 100 - 149
		this.listaHuecos[3] = new ListaAjusteRapido(150);// 150 - 199
		this.listaHuecos[4] = new ListaAjusteRapido(200);// 200 - 249
		this.listaHuecos[5] = new ListaAjusteRapido(250);// 250 - 299
		this.listaHuecos[6] = new ListaAjusteRapido(300);// >= 300
		
		int i = buscarListaHueco(memoriaTotal);
		this.listaHuecos[i].agregar(new Segmento(0, memoriaTotal));
		
		this.listaProcesos = new ListaAjusteRapido(0);
		
		this.procesosRechazados = 0;
		this.huecosPequeñosGenerados = 0;
	}
	
	public synchronized boolean ajusteRapido(String nombre, int longitud)
	{
		if (Segmento.isNombreHueco(nombre) || longitud < 1) return false;
		
		// Busca la lista de la que se extraera el hueco necesario
		int i = 0;
		while (i < listaHuecos.length &&
				longitud > listaHuecos[i].getLongitudMayor())
			i++;
		if (i == listaHuecos.length)
		{
			this.procesosRechazados++;
			return false;
		}
		
		Segmento hueco = listaHuecos[i].extraer(longitud);
		
		if (longitud < hueco.getLongitud())
		{
			Segmento huecoGenerado = new Segmento(
					hueco.getDireccion() + longitud,
					hueco.getLongitud() - longitud);
			fusionarHuecoDerecho(huecoGenerado);
			
			if (huecoGenerado.getLongitud() < 8) this.huecosPequeñosGenerados++;
			
			i = listaHuecos.length - 1;
			while (i >= 0 && listaHuecos[i].geLongitudMinimaAceptada() >
					huecoGenerado.getLongitud())
				i--;
			listaHuecos[i].agregar(huecoGenerado);
		}
		
		hueco.setNombre(nombre);
		hueco.setLongitud(longitud);
		
		return listaProcesos.agregar(hueco);
	}
	
	public synchronized boolean eliminar(String nombre)
	{
		if (nombre.equals(Segmento.nombreHueco)) return false;
		
		Segmento proceso = listaProcesos.extraer(nombre);
		
		if (proceso == null) return false;
		
		proceso.setNombre(Segmento.nombreHueco);
		fusionarHuecoIzquierdo(proceso);
		fusionarHuecoDerecho(proceso);
		
		int i = this.buscarListaHueco(proceso.getLongitud());
		
		return this.listaHuecos[i].agregar(proceso);
	}
	
	private synchronized void fusionarHuecoDerecho(Segmento hueco)
	{
		int dirInicio = hueco.getDireccion() + hueco.getLongitud(),
				i = 0;
		Segmento huecoDerecho = null;
		boolean encontrado = false;
		while (i < listaHuecos.length && !encontrado)
		{
			huecoDerecho = listaHuecos[i].extraerDirInicio(dirInicio);
			if (huecoDerecho != null)
				encontrado = true;
			
			i++;
		}
		
		if (huecoDerecho != null)
			hueco.setLongitud(hueco.getLongitud() + huecoDerecho.getLongitud());
	}
	
	private synchronized void fusionarHuecoIzquierdo(Segmento hueco)
	{
		int dirFinal = hueco.getDireccion() - 1,
				i = 0;
		Segmento huecoIzqdo = null;
		boolean encontrado = false;
		while (i < listaHuecos.length && !encontrado)
		{
			huecoIzqdo = listaHuecos[i].extraerDirFinal(dirFinal);
			if (huecoIzqdo != null)
				encontrado = true;
			
			i++;
		}
		
		if (huecoIzqdo != null)
		{
			hueco.setDireccion(huecoIzqdo.getDireccion());
			hueco.setLongitud(hueco.getLongitud() + huecoIzqdo.getLongitud());
		}
	}
	
	private synchronized int buscarListaHueco(int longitud)
	{
		int i = this.listaHuecos.length - 1;
		while (i >= 0 && listaHuecos[i].geLongitudMinimaAceptada() > longitud)
			i--;
		
		return i;
	}
	
	public int getProcesosRechazados()
	{
		return procesosRechazados;
	}

	public int getHuecosPequeñosGenerados()
	{
		return huecosPequeñosGenerados;
	}
    
	@Override
	public synchronized String toString()
	{
		String str = "[";
		for (ListaAjusteRapido lista : listaHuecos) str += lista + "\n";
		str += this.listaProcesos + "]";
		
		return str;
	}
}

class ListaAjusteRapido
{
	private Segmento primero;
	private Segmento ultimo;
	private final int longitudMinimaAceptada;
	
	public ListaAjusteRapido(int tamanoMinimo)
	{
		this.primero = null;
		this.ultimo = null;
		this.longitudMinimaAceptada = tamanoMinimo;
	}
	
	public Segmento extraer(int longitud)
	{
		Segmento s = this.primero;
		
		while (s != null && s.getLongitud() < longitud)
			s = s.getSiguiente();
		
		eliminar(s);
		
		return s;
	}
	
	public Segmento extraer(String nombre)
	{
		Segmento s = this.primero;
		
		while (s != null && !s.getNombre().equals(nombre))
			s = s.getSiguiente();
		
		eliminar(s);
		
		return s;
	}
	
	public Segmento extraerDirInicio(int dirInicio)
	{
		Segmento s = this.primero;
		
		while (s != null && !(s.getDireccion() == dirInicio))
			s = s.getSiguiente();
		
		eliminar(s);
		
		return s;
	}
	
	public Segmento extraerDirFinal(int dirFinal)
	{
		Segmento s = this.primero;
		
		while (s != null &&
				!((s.getDireccion() + s.getLongitud() - 1) == dirFinal))
			s = s.getSiguiente();
		
		eliminar(s);
		
		return s;
	}
	
	public boolean agregar(Segmento s)
	{
		if (s.getLongitud() < this.longitudMinimaAceptada) return false;
		
		if (this.primero == null)
		{
			this.primero = s;
			this.ultimo = s;
		}
		else
		{
			Segmento mayor = this.primero;
			while (mayor != null && mayor.getLongitud() < s.getLongitud())
				mayor = mayor.getSiguiente();
			
			if (mayor == null)
			{
				s.setAnterior(this.ultimo);
				s.setSiguiente(null);
				this.ultimo.setSiguiente(s);
				this.ultimo = s;
			}
			else
			{
				Segmento anterior = mayor.getAnterior();
				
				s.setAnterior(anterior);
				s.setSiguiente(mayor);
				mayor.setAnterior(s);
				if (anterior != null)
					anterior.setSiguiente(s);
				else
					this.primero = s;
			}
		}
		
		return true;
	}
	
	private void eliminar(Segmento s)
	{
		if (s == null) return;
		
		if (s == this.primero && s == this.ultimo)
		{
			this.primero = null; this.ultimo = null;
		}
		else if (s == this.primero)
		{
			this.primero = s.getSiguiente();
			this.primero.setAnterior(null);
		}
		else if (s == this.ultimo)
		{
			this.ultimo = s.getAnterior();
			this.ultimo.setSiguiente(null);
		}
		else
		{
			s.getAnterior().setSiguiente(s.getSiguiente());
			s.getSiguiente().setAnterior(s.getAnterior());
		}
		
		s.setAnterior(null);
		s.setSiguiente(null);
	}
	
	public int getLongitudMayor()
	{
		if (this.ultimo == null) return 0;
				
		return this.ultimo.getLongitud();
	}
	
	public int geLongitudMinimaAceptada()
	{
		return longitudMinimaAceptada;
	}
	
	@Override
	public String toString()
	{
		if (this.primero == null) return "[]";
		
		Segmento s = this.primero;
		String l = "[";
		while (s != ultimo)
		{
			l += s + ", ";
			s = s.getSiguiente();
		}
		l += this.ultimo + "]";
		
		return l;
	}
}
