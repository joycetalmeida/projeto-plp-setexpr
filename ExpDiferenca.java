import java.util.Set;
import java.util.HashSet;

public class ExpDiferenca implements Expressao {

    private Expressao esq, dir;

    public ExpDiferenca(Expressao esq, Expressao dir) {
        this.esq = esq;
        this.dir = dir;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        Valor valEsq = esq.avaliar(amb);
        Valor valDir = dir.avaliar(amb);

        if (valEsq instanceof ValorConjunto && valDir instanceof ValorConjunto) {
            Set<Valor> conjuntoEsq = ((ValorConjunto) valEsq).getValor();
            Set<Valor> conjuntoDir = ((ValorConjunto) valDir).getValor();

            Set<Valor> resultado = new HashSet<>(conjuntoEsq);
            resultado.removeAll(conjuntoDir);

            return new ValorConjunto(resultado);
        } else {
            throw new RuntimeException("Operacao 'diff' exige dois conjuntos como operandos.");
        }
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return esq.checaTipo(amb) && dir.checaTipo(amb);
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoConjunto.getInstancia();
    }
}
