import java.util.Set;

public class ExpSuperconjunto implements Expressao {

    private Expressao esq, dir;

    public ExpSuperconjunto(Expressao esq, Expressao dir) {
        this.esq = esq;
        this.dir = dir;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        Valor valEsq = esq.avaliar(amb);
        Valor valDir = dir.avaliar(amb);

        if (valEsq instanceof ValorConjunto && valDir instanceof ValorConjunto) {
            Set<Valor> conjuntoEsq = ((ValorConjunto) valEsq).getValor();
            Set<Valor> conjuntoDir = ((ValorConjunto) valDir).getValor();

            // A superset B significa A contém todos os elementos de B
            boolean eSuperconjunto = conjuntoEsq.containsAll(conjuntoDir);
            return new ValorBooleano(eSuperconjunto);
        } else {
            throw new RuntimeException("Operacao 'superset' exige dois conjuntos como operandos.");
        }
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return esq.checaTipo(amb) && dir.checaTipo(amb);
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoBooleano.getInstancia();
    }
}
