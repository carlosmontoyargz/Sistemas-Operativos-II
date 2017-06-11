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
		AdministracionMemoria am = new AdministracionMemoria();
		
		try
		{
//			am.guardarArchivosProcesos();
			am.ejecutarHilos("procesos1.txt", new AjusteRapido(1024));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("error en la creacion o lectura de los datos");
		}
		catch (InterruptedException e1)
		{
			System.out.println("error en la ejecucion de los hilos");
		}
	}
	
	public void ejecutarHilos(String file, AdministradorMemoria adm)
			throws FileNotFoundException, InterruptedException
	{
		// Lista para llevar registro del tiempo de ejecucion de los procesos
		LinkedList<Proceso> procesosEjecucion = new LinkedList<>();
		Estadisticas estadisticas = new Estadisticas();
		
		long t1 = new Date().getTime();
		Thread agregador = new Thread(new Agregador(adm, procesosEjecucion, file, estadisticas));
		Thread eliminador = new Thread(new Eliminador(adm, procesosEjecucion));
		agregador.start();
		eliminador.start();
		agregador.join();
		eliminador.join();
		long t2 = new Date().getTime();
		
		estadisticas.setTiempoEjecucion(t2 - t1);
		System.out.println(estadisticas);
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
		try (PrintWriter pw1 = new PrintWriter("procesos1.txt");
			 PrintWriter pw2 = new PrintWriter("procesos2.txt");
			 PrintWriter pw3 = new PrintWriter("procesos3.txt"))
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
	private final AdministradorMemoria adm;
	private final LinkedList<Proceso> procesosEjecucion;
	private final Estadisticas estadisticas;
	private final Scanner reader;
	
	public Agregador(AdministradorMemoria adm,
			LinkedList<Proceso> procesos, String archivo, Estadisticas est)
			throws FileNotFoundException
	{
		this.adm = adm;
		this.procesosEjecucion = procesos;
		this.estadisticas = est;
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

			if (adm.agregar(nombre, longitud))
			{
				procesosEjecucion.add(new Proceso(nombre, tiempo));
//				System.out.println("Agregado proceso: [" + nombre + " " + longitud + " "  + tiempo + "]\n" +
//						adm + "\n" +
//						"Tiempos de ejecucion: " + procesosEjecucion + "\n");
			}
			else
			{
//				System.out.println("Rechazado proceso: [" +
//						nombre + " " + longitud + " "  + tiempo + "]\n");
				
				this.estadisticas.incrementarProcesosRechazados();
			}
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
	private final AdministradorMemoria adm;
	private final LinkedList<Proceso> procesosEjecucion;
	
	private boolean ejecucion;
	
	public Eliminador(AdministradorMemoria adm, LinkedList<Proceso> procesos)
	{
		this.adm = adm;
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
					if (adm.eliminar(p.getNombre()))
					{
						listaIterator.remove();

//						System.out.println("Eliminado proceso: [" + p.getNombre() + "]\n"
//								+ adm + "\n");
						
						if (p.getNombre().equals("P999"))
							this.ejecucion = false;
					}
				}
				else
					p.decrementarTiempo();
			}
//			System.out.println("Tiempos de ejecucion: " + procesosEjecucion + "\n");
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

class Estadisticas
{
	private int procesosRechazados;
	private long tiempoEjecucion;
	
	public Estadisticas()
	{
		this.procesosRechazados = 0;
		this.tiempoEjecucion = 0;
	}
	
	public void incrementarProcesosRechazados()
	{
		this.procesosRechazados ++;
	}

	public void setTiempoEjecucion(long tiempo)
	{
		this.tiempoEjecucion = tiempo;
	}
	
	@Override
	public String toString()
	{
		return "Total de procesos rechazados: " + procesosRechazados + "\n" +
				"Tiempo de ejecución total: " + ((double) tiempoEjecucion / 1000) + " segundos\n";
	}
}
