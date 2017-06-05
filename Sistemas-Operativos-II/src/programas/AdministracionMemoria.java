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
		AdministracionMemoria obj = new AdministracionMemoria();
		
		System.out.println("-------------------------------- Primer ajuste --------------------------------");
		obj.pruebas(new ListaPrimerAjuste(1024));
		System.out.println();
		
		System.out.println("-------------------------------- Segundo ajuste --------------------------------");
		obj.pruebas(new ListaSiguienteAjuste(1024));
	}
	
	public void pruebas(AdministradorMemoria adm)
	{
		System.out.println(adm);
		
		adm.agregar("Prueba1", 100);
		System.out.println(adm);
		
		adm.agregar("Prueba2", 200);
		System.out.println(adm);
		
		adm.agregar("Prueba3", 250);
		System.out.println(adm);
		
		adm.agregar("Prueba4", 200);
		System.out.println(adm);
		
		adm.agregar("Prueba5", 273);
		System.out.println(adm);
		
		adm.eliminar("Prueba2");
		System.out.println(adm);
		
		adm.eliminar("Prueba5");
		System.out.println(adm);
		
		adm.agregar("Prueba6", 150);
		System.out.println(adm);
		
		adm.agregar("Prueba7", 160);
		System.out.println(adm);
		
		adm.eliminar("Prueba3");
		System.out.println(adm);
		
		adm.eliminar("Prueba4");
		System.out.println(adm);
		
		adm.eliminar("Prueba1");
		System.out.println(adm);
		
		adm.eliminar("Prueba7");
		System.out.println(adm);
		
		adm.eliminar("Prueba6");
		System.out.println(adm);
	}
}
