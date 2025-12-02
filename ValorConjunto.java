import java.util.Set;
import java.util.HashSet;

// Representa o VALOR de um conjunto, após a avaliação.
public class ValorConjunto implements Valor {

    private Set<Valor> valor;

    public ValorConjunto(Set<Valor> valor) {
        this.valor = valor;
    }

    public Set<Valor> getValor() {
        return this.valor;
    }

    // A avaliação de um valor é ele mesmo.
    public Valor avaliar(AmbienteExecucao amb) {
        return this;
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return true;
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoConjunto.getInstancia();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ValorConjunto other = (ValorConjunto) obj;
        return valor.equals(other.valor);
    }
    
    @Override
    public int hashCode() {
        return valor.hashCode();
    }
    
    @Override
    public String toString() {
        return valor.toString();
    }
}