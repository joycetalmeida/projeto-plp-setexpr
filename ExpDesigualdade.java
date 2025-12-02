import java.util.Set;

public class ExpDesigualdade implements Expressao {

    private Expressao esq, dir;

    public ExpDesigualdade(Expressao esq, Expressao dir) {
        this.esq = esq;
        this.dir = dir;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        Valor valEsq = esq.avaliar(amb);
        Valor valDir = dir.avaliar(amb);

        // Compara valores baseado em equals
        boolean diferentes = !valEsq.equals(valDir);
        return new ValorBooleano(diferentes);
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return esq.checaTipo(amb) && dir.checaTipo(amb);
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoBooleano.getInstancia();
    }
}
