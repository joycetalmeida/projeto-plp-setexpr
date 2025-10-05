// Representa o TIPO de um conjunto.
// Usaremos o padrão Singleton para ter apenas uma instância deste tipo.
public class TipoConjunto implements Tipo {

    private static TipoConjunto instancia;

    private TipoConjunto() {}

    public static TipoConjunto getInstancia() {
        if (instancia == null) {
            instancia = new TipoConjunto();
        }
        return instancia;
    }
    
    public String getNome() {
        return "Set";
    }

    public boolean eIgual(Tipo outro) {
        return outro instanceof TipoConjunto;
    }
}