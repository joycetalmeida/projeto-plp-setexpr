import java.util.Set;
import java.util.HashSet;

public class ExpDisjuntos implements Expressao {

    private Expressao esq, dir;

    public ExpDisjuntos(Expressao esq, Expressao dir) {
        this.esq = esq;
        this.dir = dir;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        Valor valEsq = esq.avaliar(amb);
        Valor valDir = dir.avaliar(amb);

        if (valEsq instanceof ValorConjunto && valDir instanceof ValorConjunto) {
            Set<Valor> conjuntoEsq = ((ValorConjunto) valEsq).getValor();
            Set<Valor> conjuntoDir = ((ValorConjunto) valDir).getValor();

            // Dois conjuntos são disjuntos se sua interseção é vazia
            Set<Valor> intersecao = new HashSet<>(conjuntoEsq);
            intersecao.retainAll(conjuntoDir);
            
            boolean saoDisjuntos = intersecao.isEmpty();
            return new ValorBooleano(saoDisjuntos);
        } else {
            throw new RuntimeException("Operacao 'disjoint' exige dois conjuntos como operandos.");
        }
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return esq.checaTipo(amb) && dir.checaTipo(amb);
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoBooleano.getInstancia();
    }
}
