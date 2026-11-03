package org.example;

public class ValidadorService {

    private static final int TAMANHO_CPF = 11;
    private static final int TAMANHO_CNPJ = 14;
    private static final int[] PESOS_CNPJ_DV1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESOS_CNPJ_DV2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    public boolean validarCpf(String cpf) {

        //Regra 1: Validar dados de entrada
        validarEntrada(cpf, "CPF");

        //Regra 2: Remover a mascara (pontos, traco e espacos)
        String numeros = removerMascara(cpf);

        //Regra 3: O CPF precisa ter exatamente 11 digitos numericos
        if (!temApenasDigitos(numeros) || numeros.length() != TAMANHO_CPF) {
            return false;
        }

        //Regra 4: Sequencias de digitos iguais (111.111.111-11) sao invalidas
        if (todosDigitosIguais(numeros)) {
            return false;
        }

        //Regra 5: Conferir o primeiro digito verificador (pesos de 10 a 2 nos 9 primeiros digitos)
        int dv1 = calcularDigitoCpf(numeros, 9);
        if (dv1 != digito(numeros, 9)) {
            return false;
        }

        //Regra 6: Conferir o segundo digito verificador (pesos de 11 a 2 nos 10 primeiros digitos)
        int dv2 = calcularDigitoCpf(numeros, 10);
        return dv2 == digito(numeros, 10);
    }

    public boolean validarCnpj(String cnpj) {

        //Regra 1: Validar dados de entrada
        validarEntrada(cnpj, "CNPJ");

        //Regra 2: Remover a mascara (pontos, barra, traco e espacos)
        String numeros = removerMascara(cnpj);

        //Regra 3: O CNPJ precisa ter exatamente 14 digitos numericos
        if (!temApenasDigitos(numeros) || numeros.length() != TAMANHO_CNPJ) {
            return false;
        }

        //Regra 4: Sequencias de digitos iguais (00.000.000/0000-00) sao invalidas
        if (todosDigitosIguais(numeros)) {
            return false;
        }

        //Regra 5: Conferir o primeiro digito verificador (12 primeiros digitos)
        int dv1 = calcularDigitoCnpj(numeros, PESOS_CNPJ_DV1);
        if (dv1 != digito(numeros, 12)) {
            return false;
        }

        //Regra 6: Conferir o segundo digito verificador (13 primeiros digitos)
        int dv2 = calcularDigitoCnpj(numeros, PESOS_CNPJ_DV2);
        return dv2 == digito(numeros, 13);
    }

    private void validarEntrada(String valor, String nome) {
        // Regra referente a entrada nula ou vazia.
        if (valor == null || valor.trim().isEmpty()) throw
                new IllegalArgumentException(nome + " não pode ser nulo ou vazio");
    }

    private String removerMascara(String valor) {
        return valor.replaceAll("[.\\-/\\s]", "");
    }

    private boolean temApenasDigitos(String valor) {
        for (char c : valor.toCharArray()) {
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    private boolean todosDigitosIguais(String numeros) {
        for (int i = 1; i < numeros.length(); i++) {
            if (numeros.charAt(i) != numeros.charAt(0)) return false;
        }
        return true;
    }

    private int digito(String numeros, int posicao) {
        return numeros.charAt(posicao) - '0';
    }

    private int calcularDigitoCpf(String numeros, int quantidade) {
        int soma = 0;
        for (int i = 0; i < quantidade; i++) {
            soma += digito(numeros, i) * (quantidade + 1 - i);
        }
        return resultadoDigito(soma);
    }

    private int calcularDigitoCnpj(String numeros, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += digito(numeros, i) * pesos[i];
        }
        return resultadoDigito(soma);
    }

    private int resultadoDigito(int soma) {
        // Resto menor que 2 vira 0; nos demais casos o digito e 11 menos o resto.
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    public static void main(String[] args) {
        ValidadorService vs = new ValidadorService();

        System.out.println("CPF 529.982.247-25 (válido): " + vs.validarCpf("529.982.247-25"));
        System.out.println("CPF 52998224725 sem máscara (válido): " + vs.validarCpf("52998224725"));
        System.out.println("CPF 529.982.247-24 (dígito errado): " + vs.validarCpf("529.982.247-24"));
        System.out.println("CPF 111.111.111-11 (sequência igual): " + vs.validarCpf("111.111.111-11"));

        System.out.println("CNPJ 11.222.333/0001-81 (válido): " + vs.validarCnpj("11.222.333/0001-81"));
        System.out.println("CNPJ 11222333000181 sem máscara (válido): " + vs.validarCnpj("11222333000181"));
        System.out.println("CNPJ 11.222.333/0001-82 (dígito errado): " + vs.validarCnpj("11.222.333/0001-82"));
        System.out.println("CNPJ 00.000.000/0000-00 (sequência igual): " + vs.validarCnpj("00.000.000/0000-00"));
    }
}
