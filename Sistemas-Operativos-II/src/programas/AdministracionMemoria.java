package programas;

import administracion_de_memoria.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author carlosmontoya
 */
public class AdministracionMemoria
{
    public static void main(String[] args)
	{
		LinkedList<Proceso> listaProcesos = new LinkedList<>();
		AdministradorMemoria administrador = new ListaPrimerAjuste(1024);
		
		Thread agregador = new Thread(
				new Agregador(administrador, listaProcesos, "procesos2.txt"));
		Thread eliminador = new Thread(
				new Eliminador(administrador, listaProcesos));
		
		agregador.start();
		eliminador.start();
	}
	
	/**
	 * Método para crear procesos aleatorios en un archivo de texto.
	 */
	public static void guardarArchivoTexto()
	{
		String p = "P";
		int longitud, tiempo;
		
		try (PrintWriter writer = new PrintWriter("procesos1.txt"))
		{
			for (int i = 0; i < 1000; i++)
			{
				longitud = (int) (Math.random() * 256) + 1;
				tiempo = (int) (Math.random() * 15) + 1;
				writer.println(p + i + " " + longitud + " " + tiempo);
			}
		}
		catch(FileNotFoundException e) {}
		
		try (PrintWriter writer = new PrintWriter("procesos2.txt"))
		{
			for (int i = 0; i < 1000; i++)
			{
				longitud = i / 4 + 4;
				tiempo = (int) (Math.random() * 15) + 1;
				writer.println(p + i + " " + longitud + " " + tiempo);
			}
		}
		catch(FileNotFoundException e) {}
		
		try (PrintWriter writer = new PrintWriter("procesos3.txt"))
		{
			for (int i = 0; i < 1000; i++)
			{
				longitud = (1004 - i) / 4;
				tiempo = (int) (Math.random() * 15) + 1;
				writer.println(p + i + " " + longitud + " " + tiempo);
			}
		}
		catch(FileNotFoundException e) {}
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
	
	private int numProcesosRechazados;
	private long milisegundosEjecucion;
	
	public Agregador(AdministradorMemoria adm, LinkedList<Proceso> listaProcesos, 
			String archivo)
	{
		this.adm = adm;
		this.listaProcesos = listaProcesos;
		try { this.reader = new Scanner(new FileReader(archivo)); }
		catch (FileNotFoundException ex) {}
		
		this.numProcesosRechazados = 0;
		this.milisegundosEjecucion = 0;
	}
	
	@Override
	public void run()
	{
		String nombre;
		int longitud, tiempo, retraso, retrasoTotal = 0;
		
		long tiempoInicial = new Date().getTime();
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
					System.out.println("Agregado proceso: [" +
							nombre + " " + longitud + " "  + tiempo + "]\n"
							+ adm + "\n" + listaProcesos + "\n");
				}
				else
				{
					System.out.println("Rechazado proceso: [" +
							nombre + " " + longitud + " "  + tiempo + "]\n");
					this.numProcesosRechazados++;
				}
			}
			retraso = ((int) (Math.random() * 5) + 1) * 100;
			retrasoTotal += retraso;
			try { Thread.sleep(retraso); }
			catch (InterruptedException ex) {}
		}
		long tiempoFinal = new Date().getTime();
		
		this.milisegundosEjecucion = tiempoFinal - tiempoInicial - (long) retrasoTotal;
		
		System.out.println("Total de procesos rechazados: " + this.numProcesosRechazados);
		System.out.println("Total de milisegundos: " + this.milisegundosEjecucion);
		
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
			
			try { Thread.sleep(100); }
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
