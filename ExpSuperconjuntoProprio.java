import java.util.Set;

public class ExpSuperconjuntoProprio implements Expressao {

    private Expressao esq, dir;

    public ExpSuperconjuntoProprio(Expressao esq, Expressao dir) {
        this.esq = esq;
        this.dir = dir;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        Valor valEsq = esq.avaliar(amb);
        Valor valDir = dir.avaliar(amb);

        if (valEsq instanceof ValorConjunto && valDir instanceof ValorConjunto) {
            Set<Valor> conjuntoEsq = ((ValorConjunto) valEsq).getValor();
            Set<Valor> conjuntoDir = ((ValorConjunto) valDir).getValor();

            // A psuperset B significa A ⊃ B (superconjunto próprio: A ⊇ B e A ≠ B)
            boolean eSuperconjunto = conjuntoEsq.containsAll(conjuntoDir);
            boolean naoIgual = !conjuntoEsq.equals(conjuntoDir);
            return new ValorBooleano(eSuperconjunto && naoIgual);
        } else {
            throw new RuntimeException("Operacao 'psuperset' exige dois conjuntos como operandos.");
        }
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return esq.checaTipo(amb) && dir.checaTipo(amb);
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoBooleano.getInstancia();
    }
}
