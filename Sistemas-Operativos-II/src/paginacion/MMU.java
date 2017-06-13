package paginacion;

/**
 *
 * @author carlosmontoya
 */
public class MMU
{
	private final int bitsPagina;
    private final int bitsDesplazamiento;
	private final int bitsMarco;
	
	private final int memoriaVirtual;
	private final int memoriaFisica;
	private final int tamanoPagina;
	private final int numPaginas;
	private final int numMarcos;
	
	
	public MMU(int bytesPagina, int bytesMarco, int bytesDesplazamiento)
	{
		this.bitsDesplazamiento = bytesDesplazamiento;
		this.bitsPagina = bytesPagina;
		this.bitsMarco = bytesMarco;
		
		this.tamanoPagina = (int) Math.pow(2, bytesDesplazamiento);
		this.numPaginas = (int) Math.pow(2, bytesPagina);
		this.numMarcos = (int) Math.pow(2, bytesMarco);
		this.memoriaVirtual = tamanoPagina * numPaginas;
		this.memoriaFisica =  tamanoPagina * numMarcos;
	}
	
	private String decimalBinario(int decimal)
	{
		int temp = decimal;
		String resultado = "";
		for (int i = 0; i < bitsMarco; i++)
		{
			if (temp % 2 == 0) resultado = "0" + resultado;
			else resultado = "1" + resultado;
			
			temp = temp / 2;
		}
		return resultado;
	}
	
	public String virtualAFisica(String dirVirtual, int numMarco)
	{
		String marcoB = decimalBinario(numMarco);
		String desplazamiento = dirVirtual.substring(bitsPagina, bitsPagina + bitsDesplazamiento);
		return marcoB + desplazamiento;
	}

	@Override
	public String toString()
	{
		return "Numero de paginas: " + this.numPaginas + " paginas." +
				"\nNumero de marcos: " + this.numMarcos + " marcos." +
				"\nTamano de pagina: " + this.tamanoPagina + " bytes." +
				"\nTamano de memoria virtual: " + this.memoriaVirtual + " bytes." +
				"\nTamano de memoria fisica: " + this.memoriaFisica + " bytes.";
	}
}
