// Representa o TIPO de um inteiro.
public class TipoInteiro implements Tipo {

    private static TipoInteiro instancia;

    private TipoInteiro() {}

    public static TipoInteiro getInstancia() {
        if (instancia == null) {
            instancia = new TipoInteiro();
        }
        return instancia;
    }
    
    public String getNome() {
        return "Integer";
    }

    public boolean eIgual(Tipo outro) {
        return outro instanceof TipoInteiro;
    }
}