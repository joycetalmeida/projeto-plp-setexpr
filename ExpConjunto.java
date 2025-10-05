import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

// Representa uma expressão de conjunto, como {1, 2, 3}
public class ExpConjunto implements Expressao {

    private List<Expressao> elementos;

    public ExpConjunto(List<Expressao> elementos) {
        this.elementos = elementos;
    }

    // A avaliação de uma expressão de conjunto envolve avaliar cada elemento
    // e adicioná-lo a um novo ValorConjunto.
    public Valor avaliar(AmbienteExecucao amb) {
        Set<Valor> valores = new HashSet<Valor>();
        for (Expressao exp : elementos) {
            valores.add(exp.avaliar(amb));
        }
        return new ValorConjunto(valores);
    }
    
    // Deixaremos a checagem de tipo para depois.
    public boolean checaTipo(AmbienteCompilacao amb) {
        return true; 
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoConjunto.getInstancia(); // Usaremos um Singleton
    }
}