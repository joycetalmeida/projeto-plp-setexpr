// Representa o TIPO de uma função.
public class TipoFuncao implements Tipo {

    private static TipoFuncao instancia;

    private TipoFuncao() {}

    public static TipoFuncao getInstancia() {
        if (instancia == null) {
            instancia = new TipoFuncao();
        }
        return instancia;
    }
    
    public String getNome() {
        return "Function";
    }

    public boolean eIgual(Tipo outro) {
        return outro instanceof TipoFuncao;
    }
}
