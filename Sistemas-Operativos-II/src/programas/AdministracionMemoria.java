package programas;

import administracion_de_memoria.*;

/**
 *
 * @author carlosmontoya
 */
public class AdministracionMemoria
{
    public static void main(String[] args)
	{
		ListaSegmentos lista = new ListaPrimerAjuste(1024);
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
		
		lista.eliminar("Prueba3");
		System.out.println(lista);
	}
}
