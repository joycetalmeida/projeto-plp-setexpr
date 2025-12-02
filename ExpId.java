// Representa uma variável (identificador)
public class ExpId implements Expressao {
    
    private String id;
    
    public ExpId(String id) {
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    
    public Valor avaliar(AmbienteExecucao amb) {
        if (amb == null) {
            throw new RuntimeException("Variavel '" + id + "' nao definida (ambiente vazio).");
        }
        Valor valor = amb.get(id);
        if (valor == null) {
            throw new RuntimeException("Variavel '" + id + "' nao definida.");
        }
        return valor;
    }
    
    public boolean checaTipo(AmbienteCompilacao amb) {
        if (amb == null) {
            return false;
        }
        return amb.get(id) != null;
    }
    
    public Tipo getTipo(AmbienteCompilacao amb) {
        if (amb == null) {
            throw new RuntimeException("Variavel '" + id + "' nao tipada (ambiente vazio).");
        }
        Tipo tipo = amb.get(id);
        if (tipo == null) {
            throw new RuntimeException("Variavel '" + id + "' nao tipada.");
        }
        return tipo;
    }
}
