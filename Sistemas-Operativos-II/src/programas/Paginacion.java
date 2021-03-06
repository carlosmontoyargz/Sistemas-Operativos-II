package programas;

import java.util.Scanner;
import paginacion.*;

/**
 *
 * @author carlosmontoya
 */
public class Paginacion
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		System.out.println("Ingresar el numero de bits para la pagina, marco y desplazamiento");
		int bitsPag = in.nextInt();
		int bitsMarco = in.nextInt();
		int bitsDesp = in.nextInt();
		
		MMU mmu = new MMU(bitsPag, bitsMarco, bitsDesp);
		System.out.println("\n" + mmu);
		
		while (true)
		{
			System.out.println("\nIngresar la direccion virtual y el numero de marco");
			String dirVirtual = in.next();
			int marco = in.nextInt();

			String dirFisica = mmu.virtualAFisica(dirVirtual, marco);
			System.out.println("La direccion fisica es: " + dirFisica);
		}
	}
}
