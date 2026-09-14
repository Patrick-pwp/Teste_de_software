public class FreteService {

    private static final double BASE_POR_KM = 1.2;
    private static final double EXCESSO_PESO_LIMITE_KG = 5.0;
    private static final double TAXA_EXCESSO_PESO_KG = 2.0;
    private static final double TAXA_FRAGIL = 15.0;
    private static final double TAXA_EXPRESSO = 1.5;
    private static final double VALOR_MINIMO_FRETE = 10;
    private static final double VALOR_MAXIMO_FRETE = 300;

    public final double calcularFrete(Pedido pedido) {
        double valorTotal = pedido.getValorItens();

        //Regra 1: Validar dados de entrada
        validar(pedido);

        //Regra 2: Calcular o valor com base na distancia percorrida
        valorTotal += pedido.getDistanciaKm() * BASE_POR_KM;

        //Regra 3: Calcular o valor com base no excesso de peso
        if (pedido.getPesoKg() > EXCESSO_PESO_LIMITE_KG) {
            double excesso = pedido.getPesoKg() - EXCESSO_PESO_LIMITE_KG;
            valorTotal += excesso * TAXA_EXCESSO_PESO_KG;
        }

        //Regra 4: Verificar se o produto é fragil
        if (pedido.isFragil()) {
            valorTotal += TAXA_FRAGIL;
        }

        //Regra 5: Verificar se o tipo de frete é expresso
        if (pedido.isExpresso()) {
            valorTotal *= TAXA_EXPRESSO;
        }

        //Regra 6: Piso/Teto -> valor minimo e valor maximo de frete
        valorTotal = Math.max(VALOR_MINIMO_FRETE, Math.min(VALOR_MAXIMO_FRETE, valorTotal));

        //Regra 7: Frete Gratis valor > 200 e distancia menor ou igual a 20km e nao for frete gratis.
        if (!pedido.isExpresso() && pedido.getValorItens() >= 200 && pedido.getDistanciaKm() < 20){
            return 0;
        };

        return valorTotal;
    }

    private void validar(Pedido pedido) {
        //Regras referentes a distancia e peso do produto

        if (pedido.getDistanciaKm() < 0) {
            throw new IllegalArgumentException("Distancia nao pode ser negativa.");
        }
        if (pedido.getPesoKg() < 0) {
            throw new IllegalArgumentException("Distancia nao pode ser negativa.");
        }
        if (pedido.getValorItens() < 0) {
            throw new IllegalArgumentException("Valor nao pode ser negativo.");
        }
    }

    public static void main(String[] args) {
//        Pedido pedidoTeste = new Pedido(10, -1, 10, true, true);
        Pedido padrao = new Pedido(150, 30, 5.5, false, false);
        Pedido fragil = new Pedido(80, 10, 8, true, false);
        Pedido expresso = new Pedido(90, 9, 2, false, true);
        Pedido gratis = new Pedido(250, 15, 3, false, false);

        FreteService freteService = new FreteService();

        System.out.println("Padrao: " + freteService.calcularFrete(padrao));
        System.out.println("Fragil: " + freteService.calcularFrete(fragil));
        System.out.println("Expresso: " + freteService.calcularFrete(expresso));
        System.out.println("Gratis: " + freteService.calcularFrete(gratis));
    }
}