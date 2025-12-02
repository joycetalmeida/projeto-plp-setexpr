import java.util.Set;

public class ExpCardinalidade implements Expressao {

    private Expressao expr;

    public ExpCardinalidade(Expressao expr) {
        this.expr = expr;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        Valor val = expr.avaliar(amb);

        if (val instanceof ValorConjunto) {
            Set<Valor> conjunto = ((ValorConjunto) val).getValor();
            int tamanho = conjunto.size();
            return new ValorInteiro(tamanho);
        } else {
            throw new RuntimeException("Operacao '#' (cardinalidade) exige um conjunto como operando.");
        }
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return expr.checaTipo(amb);
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoInteiro.getInstancia();
    }
}
