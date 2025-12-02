import java.util.Set;

public class ExpIgualdade implements Expressao {

    private Expressao esq, dir;

    public ExpIgualdade(Expressao esq, Expressao dir) {
        this.esq = esq;
        this.dir = dir;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        Valor valEsq = esq.avaliar(amb);
        Valor valDir = dir.avaliar(amb);

        // Compara valores baseado em equals
        boolean iguais = valEsq.equals(valDir);
        return new ValorBooleano(iguais);
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return esq.checaTipo(amb) && dir.checaTipo(amb);
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoBooleano.getInstancia();
    }
}
