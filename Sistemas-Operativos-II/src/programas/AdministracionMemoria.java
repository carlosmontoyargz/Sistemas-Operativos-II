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
		new AdministracionMemoria().pruebas();
	}
	
	public void pruebas()
	{
		System.out.println("-------------------------------- Primer ajuste --------------------------------");
		pruebas(new ListaPrimerAjuste(1024));
		System.out.println();
		
		System.out.println("-------------------------------- Siguiente ajuste --------------------------------");
		pruebas(new ListaSiguienteAjuste(1024));
	}
	
	public void pruebas(AdministradorMemoria adm)
	{
		System.out.println(adm);
		
		adm.agregar("P1", 100);
		System.out.println(adm);
		
		adm.agregar("P2", 200);
		System.out.println(adm);
		
		adm.agregar("P3", 250);
		System.out.println(adm);
		
		adm.agregar("P4", 200);
		System.out.println(adm);
		
		adm.agregar("P5", 273);
		System.out.println(adm);
		
		adm.eliminar("P2");
		System.out.println(adm);
		
		adm.eliminar("P5");
		System.out.println(adm);
		
		adm.agregar("P6", 150);
		System.out.println(adm);
		
		adm.agregar("P7", 110);
		System.out.println(adm);
		
		adm.agregar("P8", 250);
		System.out.println(adm);
		
		adm.eliminar("P3");
		System.out.println(adm);
		
		adm.eliminar("P4");
		System.out.println(adm);
		
		adm.eliminar("P1");
		System.out.println(adm);
		
		adm.eliminar("P7");
		System.out.println(adm);
		
		adm.eliminar("P6");
		System.out.println(adm);
	}
}
