package administracion_de_memoria;

/**
 *
 * @author carlosmontoya
 */
public class AdministracionMemoria
{
    public static void main(String[] args)
	{
		ListaSegmentos lista = new ListaSegmentos(1024);
		System.out.println(lista);
		
		lista.primerAjuste("Prueba1", 100);
		System.out.println(lista);
		
		lista.primerAjuste("Prueba2", 200);
		System.out.println(lista);
		
		lista.primerAjuste("Prueba3", 250);
		System.out.println(lista);
		
		lista.primerAjuste("Prueba4", 200);
		System.out.println(lista);
		
		lista.primerAjuste("Prueba5", 274);
		System.out.println(lista);
	}
}
