import org.example.ValidadorService;
import org.junit.Assert;
import org.junit.Test;

public class ValidadorServiceTest {
    private final ValidadorService vs = new ValidadorService();

    // ---------- CPF ----------

    @Test (expected = IllegalArgumentException.class)
    public void validarCpfDeveRejeitarNulo(){
        vs.validarCpf(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void validarCpfDeveRejeitarVazio(){
        vs.validarCpf("   ");
    }

    @Test
    public void cpfValidoComMascara(){
        Assert.assertTrue(vs.validarCpf("529.982.247-25"));
    }

    @Test
    public void cpfValidoSemMascara(){
        Assert.assertTrue(vs.validarCpf("52998224725"));
    }

    @Test
    public void cpfOutroValido(){
        Assert.assertTrue(vs.validarCpf("111.444.777-35"));
    }

    @Test
    public void cpfComPrimeiroDigitoVerificadorErrado(){
        Assert.assertFalse(vs.validarCpf("529.982.247-35"));
    }

    @Test
    public void cpfComSegundoDigitoVerificadorErrado(){
        Assert.assertFalse(vs.validarCpf("529.982.247-24"));
    }

    @Test
    public void cpfComTodosDigitosIguais(){
        Assert.assertFalse(vs.validarCpf("111.111.111-11"));
        Assert.assertFalse(vs.validarCpf("00000000000"));
    }

    @Test
    public void cpfComTamanhoErrado(){
        Assert.assertFalse(vs.validarCpf("529.982.247-2"));
        Assert.assertFalse(vs.validarCpf("529982247255"));
    }

    @Test
    public void cpfComLetras(){
        Assert.assertFalse(vs.validarCpf("529.982.24A-25"));
    }

    // ---------- CNPJ ----------

    @Test (expected = IllegalArgumentException.class)
    public void validarCnpjDeveRejeitarNulo(){
        vs.validarCnpj(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void validarCnpjDeveRejeitarVazio(){
        vs.validarCnpj("");
    }

    @Test
    public void cnpjValidoComMascara(){
        Assert.assertTrue(vs.validarCnpj("11.222.333/0001-81"));
    }

    @Test
    public void cnpjValidoSemMascara(){
        Assert.assertTrue(vs.validarCnpj("11222333000181"));
    }

    @Test
    public void cnpjOutroValido(){
        Assert.assertTrue(vs.validarCnpj("11.444.777/0001-61"));
    }

    @Test
    public void cnpjComPrimeiroDigitoVerificadorErrado(){
        Assert.assertFalse(vs.validarCnpj("11.222.333/0001-91"));
    }

    @Test
    public void cnpjComSegundoDigitoVerificadorErrado(){
        Assert.assertFalse(vs.validarCnpj("11.222.333/0001-82"));
    }

    @Test
    public void cnpjComTodosDigitosIguais(){
        Assert.assertFalse(vs.validarCnpj("00.000.000/0000-00"));
        Assert.assertFalse(vs.validarCnpj("99999999999999"));
    }

    @Test
    public void cnpjComTamanhoErrado(){
        Assert.assertFalse(vs.validarCnpj("11.222.333/0001-8"));
        Assert.assertFalse(vs.validarCnpj("112223330001811"));
    }

    @Test
    public void cnpjComLetras(){
        Assert.assertFalse(vs.validarCnpj("11.222.333/00A1-81"));
    }

    @Test
    public void cpfNaoDeveSerAceitoComoCnpjENemOContrario(){
        Assert.assertFalse(vs.validarCnpj("529.982.247-25"));
        Assert.assertFalse(vs.validarCpf("11.222.333/0001-81"));
    }
}
