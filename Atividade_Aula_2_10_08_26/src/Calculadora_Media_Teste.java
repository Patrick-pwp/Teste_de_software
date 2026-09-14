
/* 
 * O erro humano foi dividir os 3 valores por 2, calculando a média errado.
 * O defeito está na função "calculadora".
 * A falha observado pelo usuário foi a saída, devolveu o valor 12 (o correto era 8).
*/
public class Calculadora_Media_Teste {

	public static void main(String[] argumentos) {
		
		int resultadoMedia = calculadora(6,8,10);
		System.out.println(resultadoMedia);

	}
	
	public static int calculadora(int primeiraNota, int segundaNota, int terceiraNota) {
		return (primeiraNota + segundaNota + terceiraNota) / 2;
	}
	
}
