// Representa o valor de uma função (closure)
public class ValorFuncao implements Valor {
    
    private String parametro;
    private Expressao corpo;
    private AmbienteExecucao ambiente; // Ambiente capturado (closure)
    
    public ValorFuncao(String parametro, Expressao corpo, AmbienteExecucao ambiente) {
        this.parametro = parametro;
        this.corpo = corpo;
        this.ambiente = ambiente;
    }
    
    public String getParametro() {
        return this.parametro;
    }
    
    public Expressao getCorpo() {
        return this.corpo;
    }
    
    public AmbienteExecucao getAmbiente() {
        return this.ambiente;
    }
    
    public Valor avaliar(AmbienteExecucao amb) {
        return this;
    }
    
    public boolean checaTipo(AmbienteCompilacao amb) {
        return true;
    }
    
    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoFuncao.getInstancia();
    }
    
    @Override
    public String toString() {
        return "<funcao>";
    }
}
