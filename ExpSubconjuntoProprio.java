import java.util.Set;

public class ExpSubconjuntoProprio implements Expressao {

    private Expressao esq, dir;

    public ExpSubconjuntoProprio(Expressao esq, Expressao dir) {
        this.esq = esq;
        this.dir = dir;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        Valor valEsq = esq.avaliar(amb);
        Valor valDir = dir.avaliar(amb);

        if (valEsq instanceof ValorConjunto && valDir instanceof ValorConjunto) {
            Set<Valor> conjuntoEsq = ((ValorConjunto) valEsq).getValor();
            Set<Valor> conjuntoDir = ((ValorConjunto) valDir).getValor();

            // A psubset B significa A ⊂ B (subconjunto próprio: A ⊆ B e A ≠ B)
            boolean eSubconjunto = conjuntoDir.containsAll(conjuntoEsq);
            boolean naoIgual = !conjuntoEsq.equals(conjuntoDir);
            return new ValorBooleano(eSubconjunto && naoIgual);
        } else {
            throw new RuntimeException("Operacao 'psubset' exige dois conjuntos como operandos.");
        }
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return esq.checaTipo(amb) && dir.checaTipo(amb);
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoBooleano.getInstancia();
    }
}
