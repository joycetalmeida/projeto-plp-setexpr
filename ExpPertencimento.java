import java.util.Set;

public class ExpPertencimento implements Expressao {

    private Expressao elemento;
    private Expressao conjunto;

    public ExpPertencimento(Expressao elemento, Expressao conjunto) {
        this.elemento = elemento;
        this.conjunto = conjunto;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        Valor valElemento = elemento.avaliar(amb);
        Valor valConjunto = conjunto.avaliar(amb);

        if (valConjunto instanceof ValorConjunto) {
            Set<Valor> conjuntoSet = ((ValorConjunto) valConjunto).getValor();
            boolean pertence = conjuntoSet.contains(valElemento);
            return new ValorBooleano(pertence);
        } else {
            throw new RuntimeException("Operacao 'in' exige um conjunto como segundo operando.");
        }
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return elemento.checaTipo(amb) && conjunto.checaTipo(amb);
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoBooleano.getInstancia();
    }
}
