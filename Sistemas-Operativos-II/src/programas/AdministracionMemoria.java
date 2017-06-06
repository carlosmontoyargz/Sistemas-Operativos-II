package programas;

import administracion_de_memoria.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlosmontoya
 */
public class AdministracionMemoria
{
    public static void main(String[] args)
	{
		LinkedList<Proceso> listaProcesos = new LinkedList<>();
		AdministradorMemoria administrador = new ListaSiguienteAjuste(1024);
		
		Thread agregador = new Thread(
				new Agregador(administrador, listaProcesos));
		Thread eliminador = new Thread(
				new Eliminador(administrador, listaProcesos));
		
		agregador.start();
		eliminador.start();
	}
}

/**
 * Clase que permite crear un hilo para agregar procesos.
 * 
 * @author carlosmontoya
 */
class Agregador implements Runnable
{
	private final AdministradorMemoria adm;
	private final LinkedList<Proceso> listaProcesos;
	private Scanner reader;
	
	public Agregador(AdministradorMemoria adm, LinkedList<Proceso> listaProcesos)
	{
		this.adm = adm;
		this.listaProcesos = listaProcesos;
		try { this.reader = new Scanner(new FileReader("procesos.txt")); }
		catch (FileNotFoundException ex) {}
	}
	
	@Override
	public void run()
	{
		String nombre;
		int longitud, tiempo, retraso;
		while (reader.hasNext())
		{
			synchronized (listaProcesos)
			{
				nombre = reader.next();
				longitud = reader.nextInt();
				tiempo = reader.nextInt();
				
				if (adm.agregar(nombre, longitud))
				{
					listaProcesos.add(new Proceso(nombre, tiempo));
					System.out.println( "Agregado proceso: [" +
							nombre + " " + longitud + " "  + tiempo + "]\n"
							+ adm + "\n" + listaProcesos + "\n");
				}
			}
			retraso = (int) (Math.random() * 10) + 1;
			try { Thread.sleep(retraso * 1000); }
			catch (InterruptedException ex) {}
		}
		
		reader.close();
	}
}

/**
 * Clase que permite crear un hilo para eliminar procesos.
 * 
 * @author carlosmontoya
 */
class Eliminador implements Runnable
{
	private final AdministradorMemoria adm;
	private final LinkedList<Proceso> listaProcesos;
	
	public Eliminador(AdministradorMemoria adm, LinkedList<Proceso> listaProcesos)
	{
		this.adm = adm;
		this.listaProcesos = listaProcesos;
	}
	
	@Override
	public void run()
	{
		String nombre;
		while (true)
		{
			synchronized (listaProcesos)
			{
				Iterator<Proceso> listaIterator = listaProcesos.iterator();
				Proceso p;

				while (listaIterator.hasNext())
				{
					p = listaIterator.next();
					
					if (p.terminado())
					{
						nombre = p.getNombre();
						if (adm.eliminar(p.getNombre()))
						{
							listaIterator.remove();
						
							System.out.println( "Eliminado proceso: [" + nombre + "]\n"
									+ adm + "\n" + listaProcesos + "\n");
						}
					}
					else
						p.decrementarTiempo();
				}
				System.out.println(adm + "\n" + listaProcesos + "\n");
			}
			
			try { Thread.sleep(1000); }
			catch (InterruptedException ex) {}
		}
	}
}

/**
 * Clase que almacena nombres de procesos en memoria, y su tiempo de ejecución
 * asignado.
 * 
 * @author carlosmontoya
 */
class Proceso
{
	private final String nombre;
	private int tiempo;
	
	public Proceso(String nombre, int tiempo)
	{
		this.nombre = nombre;
		this.tiempo = (tiempo > 0) ? tiempo : 0;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public void decrementarTiempo()
	{
		this.tiempo --;
	}
	
	public boolean terminado()
	{
		return this.tiempo <= 0;
	}
	
	@Override
	public String toString()
	{
		return "[" + nombre + " " + tiempo + "]";
	}
}
