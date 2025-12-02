// Representa o TIPO de um booleano.
public class TipoBooleano implements Tipo {

    private static TipoBooleano instancia;

    private TipoBooleano() {}

    public static TipoBooleano getInstancia() {
        if (instancia == null) {
            instancia = new TipoBooleano();
        }
        return instancia;
    }
    
    public String getNome() {
        return "Boolean";
    }

    public boolean eIgual(Tipo outro) {
        return outro instanceof TipoBooleano;
    }
}
