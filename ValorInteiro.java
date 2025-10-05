import java.util.Objects;

public class ValorInteiro implements Valor {

    private int valor;

    public ValorInteiro(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return this.valor;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        return this;
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return true;
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoInteiro.getInstancia();
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ValorInteiro other = (ValorInteiro) obj;
        return valor == other.valor;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}