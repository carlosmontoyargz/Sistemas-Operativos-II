package administracion_de_memoria;

/**
 *
 * @author carlosmontoya
 */
public class ListaProcesos
{
	private NodoListaProcesos Primero;
    private NodoListaProcesos Ultimo;
	private NodoListaProcesos Actual;
	private int NumeroProcesos;
	private final int CantidadMemoria;
	
	public ListaProcesos(int cantidadMemoria)
	{
		this.Primero = null;
		this.Ultimo = null;
		this.Actual = null;
		this.NumeroProcesos = 0;
		this.CantidadMemoria = cantidadMemoria;
	}
	
	
}

class NodoListaProcesos
{
	private Proceso Proceso;
	private NodoListaProcesos Siguiente;
	private NodoListaProcesos Anterior;
	
	public NodoListaProcesos(Proceso proceso)
	{
		this.Proceso = proceso;
		this.Anterior = null;
		this.Siguiente = null;
	}
	
	public void setProceso(Proceso proceso)
	{
		this.Proceso = proceso;
	}
	
	public void setAnterior(NodoListaProcesos anterior)
	{
		this.Anterior = anterior;
	}
	
	public void setSiguiente(NodoListaProcesos siguiente)
	{
		this.Siguiente = siguiente;
	}
	
	public Proceso getProceso()
	{
		return this.Proceso;
	}
	
	public NodoListaProcesos getAnterior()
	{
		return this.Anterior;
	}
	
	public NodoListaProcesos getSiguiente()
	{
		return this.Siguiente;
	}
}
