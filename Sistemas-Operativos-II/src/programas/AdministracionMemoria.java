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
		
		lista.eliminar("Prueba3");
		System.out.println(lista);
		
		lista.eliminar("Prueba4");
		System.out.println(lista);
		
		lista.eliminar("Prueba1");
		System.out.println(lista);
	}
}
