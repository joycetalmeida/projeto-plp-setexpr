import java.util.Objects;

public class ValorBooleano implements Valor {

    private boolean valor;

    public ValorBooleano(boolean valor) {
        this.valor = valor;
    }

    public boolean getValor() {
        return this.valor;
    }

    public Valor avaliar(AmbienteExecucao amb) {
        return this;
    }

    public boolean checaTipo(AmbienteCompilacao amb) {
        return true;
    }

    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoBooleano.getInstancia();
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
        ValorBooleano other = (ValorBooleano) obj;
        return valor == other.valor;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}
