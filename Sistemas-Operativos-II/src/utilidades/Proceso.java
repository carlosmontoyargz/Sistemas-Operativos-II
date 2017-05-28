package utilidades;

/**
 *
 * @author carlosmontoya
 */
public class Proceso
{
    private final int ID;
	private int TiempoAsignado;
	private String Estado;
	
	public Proceso(int id, int tiempo)
	{
		this.ID = id;
		this.TiempoAsignado = tiempo;
		this.Estado = "listo";
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	public int getTiempo()
	{
		return this.TiempoAsignado;
	}
	
	public String getEstado()
	{
		return this.Estado;
	}
	
	public void setTiempo(int tiempo)
	{
		this.TiempoAsignado = tiempo;
	}
	
	public void decrementarTiempo()
	{
		this.TiempoAsignado --;
	}
	
	public void cambiarEstado(String estado)
	{
		this.Estado = estado;
	}
}
