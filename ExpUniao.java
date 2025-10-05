import java.util.Set;
import java.util.HashSet;

public class ExpUniao implements Expressao {

    private Expressao esq, dir;

    public ExpUniao(Expressao esq, Expressao dir) {
        this.esq = esq;
        this.dir = dir;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        System.out.println("DEBUG: Iniciando a avaliacao de uma uniao...");

        Valor valEsq = esq.avaliar(amb);
        Valor valDir = dir.avaliar(amb);

        System.out.println("DEBUG: Lado esquerdo avaliou para: " + valEsq);
        System.out.println("DEBUG: Lado direito avaliou para: " + valDir);

        if (valEsq instanceof ValorConjunto && valDir instanceof ValorConjunto) {
            Set<Valor> conjuntoEsq = ((ValorConjunto) valEsq).getValor();
            Set<Valor> conjuntoDir = ((ValorConjunto) valDir).getValor();

            Set<Valor> resultado = new HashSet<>(conjuntoEsq);
            System.out.println("DEBUG: Conjunto resultado criado com a copia da esquerda: " + resultado);

            resultado.addAll(conjuntoDir);
            System.out.println("DEBUG: Conjunto resultado APOS a uniao: " + resultado);

            System.out.println("DEBUG: Retornando o resultado final...");

            return new ValorConjunto(resultado);
        } else {
            throw new RuntimeException("Operacao 'union' exige dois conjuntos como operandos.");
        }
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return true;
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoConjunto.getInstancia();
    }
}