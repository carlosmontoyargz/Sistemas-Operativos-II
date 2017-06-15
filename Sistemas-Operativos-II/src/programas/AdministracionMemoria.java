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
		new AdministracionMemoria().ejecutar();
	}
	
	public void ejecutar()
	{
		ejecutarHilos("aleatorio.txt");
		ejecutarHilos("ascendente.txt");
		ejecutarHilos("descendente.txt");
	}
	
	public void ejecutarHilos(String file)
	{
		ListaSegmentos lista = new ListaSegmentos(1024);
		LinkedList<Proceso> procesosEjecucion = new LinkedList<>();
		
		long t1 = new Date().getTime();
		
		try
		{
			Thread agregador = new Thread(
					new Agregador(lista, procesosEjecucion, file));
			Thread eliminador = new Thread(
					new Eliminador(lista, procesosEjecucion));

			agregador.start();
			eliminador.start();

			agregador.join();
			eliminador.join();
		}
		catch (InterruptedException | FileNotFoundException e)
		{}
		
		long t2 = new Date().getTime();
		double segundos = (double)(t2 - t1) / 1000;
		
		System.out.println("Archivo ejecutado: " + file + "\n" +
				"Total de procesos rechazados: " +
				lista.getProcesosRechazados() + "\n" +
				"Total de huecos pequeños creados: " +
				lista.getHuecosPequeñosGenerados() + "\n" +
				"Tiempo de ejecución total: " + segundos + " segundos\n");
	}
	
	/**
	 * Método para crear procesos aleatorios en un archivo de texto.
	 * 
	 * @throws java.io.FileNotFoundException
	 */
	public void guardarArchivosProcesos() throws FileNotFoundException
	{
		String p = "P";
		int longitud, tiempo, retraso;
		
		// Procesos de tamano y tiempo aleatorio
		try (PrintWriter pw1 = new PrintWriter("aleatorio.txt");
			 PrintWriter pw2 = new PrintWriter("ascendente.txt");
			 PrintWriter pw3 = new PrintWriter("descendente.txt"))
		{
			// Procesos de tamano y tiempo aleatorios
			for (int i = 0; i < 1000; i++)
			{
				longitud = (int) (Math.random() * 256) + 1;
				tiempo = (int) (Math.random() * 15) + 1;
				retraso = ((int) (Math.random() * 6) + 1);
				pw1.println(p + i + " " + longitud + " " + tiempo + " " + retraso);
			}
			
			// Procesos de tamano creciente y tiempo aleatorio
			for (int i = 0; i < 1000; i++)
			{
				longitud = i / 4 + 4;
				tiempo = (int) (Math.random() * 15) + 1;
				retraso = ((int) (Math.random() * 6) + 1);
				pw2.println(p + i + " " + longitud + " " + tiempo + " " + retraso);
			}
			
			// Procesos de tamano decreciente y tiempo aleatorio
			for (int i = 0; i < 1000; i++)
			{
				longitud = (1000 - i) / 4 + 4;
				tiempo = (int) (Math.random() * 15) + 1;
				retraso = ((int) (Math.random() * 6) + 1);
				pw3.println(p + i + " " + longitud + " " + tiempo + " " + retraso);
			}
		}
	}
}

/**
 * Clase que permite crear un hilo para agregar procesos.
 * 
 * @author carlosmontoya
 */
class Agregador implements Runnable
{
	private final ListaSegmentos lista;
	private final LinkedList<Proceso> procesosEjecucion;
	private final Scanner reader;
	
	public Agregador(ListaSegmentos lista, LinkedList<Proceso> procesos,
			String archivo)
		throws FileNotFoundException
	{
		this.lista = lista;
		this.procesosEjecucion = procesos;
		this.reader = new Scanner(new FileReader(archivo));
	}
	
	@Override
	public void run()
	{
		try
		{
			int retraso;
			while (reader.hasNext())
			{
				agregarProceso();
				retraso = reader.nextInt();
				Thread.sleep(retraso * 50);
			}
		}
		catch (InterruptedException ex)
		{}
		reader.close();
	}
	
	private void agregarProceso()
	{
		synchronized (procesosEjecucion)
		{
			String nombre = reader.next();
			int longitud = reader.nextInt();
			int tiempo = reader.nextInt();

			if (lista.primerAjuste(nombre, longitud))
			{
				procesosEjecucion.add(new Proceso(nombre, tiempo));
				System.out.println("Agregado proceso: [" + 
						nombre + " " + longitud + " "  + tiempo + "]\n" +
						lista + "\n" +
						"Tiempos de ejecucion: " + procesosEjecucion + "\n");
			}
			else
				System.out.println("Rechazado proceso: [" +
						nombre + " " + longitud + " "  + tiempo + "]\n");
		}
	}
}

/**
 * Clase que permite crear un hilo para eliminar procesos.
 * 
 * @author carlosmontoya
 */
class Eliminador implements Runnable
{
	private final ListaSegmentos lista;
	private final LinkedList<Proceso> procesosEjecucion;
	
	private boolean ejecucion;
	
	public Eliminador(ListaSegmentos lista, LinkedList<Proceso> procesos)
	{
		this.lista = lista;
		this.procesosEjecucion = procesos;
		this.ejecucion = true;
	}
	
	@Override
	public void run()
	{
		try
		{
			while (ejecucion)
			{
				eliminarProcesos();
				Thread.sleep(50);
			}
		}
		catch (InterruptedException ex) {}
	}
	
	private void eliminarProcesos()
	{
		synchronized (procesosEjecucion)
		{
			Iterator<Proceso> listaIterator = procesosEjecucion.iterator();
			Proceso p;

			while (listaIterator.hasNext())
			{
				p = listaIterator.next();

				if (p.terminado())
				{
					if (lista.eliminar(p.getNombre()))
					{
						listaIterator.remove();

						System.out.println("Eliminado proceso: [" + p.getNombre() + "]\n"
								+ lista + "\n");
						
						if (p.getNombre().equals("P999"))
							this.ejecucion = false;
					}
				}
				else
					p.decrementarTiempo();
			}
			System.out.println("Tiempos de ejecucion: " + procesosEjecucion + "\n");
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
