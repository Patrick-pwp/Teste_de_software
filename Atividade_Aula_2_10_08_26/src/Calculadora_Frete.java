import java.util.Locale;
import java.util.Scanner;

public class Calculadora_Frete {

	public static void main(String[] argumentos) {
		
		Locale.setDefault(Locale.US);
		Scanner leitorEntrada = new Scanner(System.in);
		
		System.out.print("Digite o valor da compra: ");
		double valorCompra = leitorEntrada.nextDouble();
		System.out.print("Digite a distância: ");
		double distancia = leitorEntrada.nextDouble();
		
		double valorTotal = calculadoraFrete(valorCompra, distancia);
		System.out.println("O valor final da compra ficou: R$ " + valorTotal);
	
		leitorEntrada.close();
	}

	public static double calculadoraFrete(double valorCompra, double distancia) {
		double valorTotal = valorCompra;
		if(valorCompra >= 100) {
			valorTotal += 0.00;
		} else if(distancia <= 3) {
			valorTotal += 6.99;
		} else if(distancia > 3 && distancia <= 6) {
			valorTotal += 9.99;
		} else if(distancia > 6) {
			valorTotal += 14.99;
		}
		return valorTotal;
	}
	
}
