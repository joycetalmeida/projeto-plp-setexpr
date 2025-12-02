// Representa um let binding: let x = valor in corpo
public class ExpLet implements Expressao {
    
    private String id;
    private Expressao expValor;
    private Expressao corpo;
    
    public ExpLet(String id, Expressao expValor, Expressao corpo) {
        this.id = id;
        this.expValor = expValor;
        this.corpo = corpo;
    }
    
    public Valor avaliar(AmbienteExecucao amb) {
        Valor valor = expValor.avaliar(amb);
        AmbienteExecucao novoAmb = new AmbienteExecucaoMap(amb);
        novoAmb.add(id, valor);
        return corpo.avaliar(novoAmb);
    }
    
    public boolean checaTipo(AmbienteCompilacao amb) {
        if (!expValor.checaTipo(amb)) {
            return false;
        }
        Tipo tipoValor = expValor.getTipo(amb);
        AmbienteCompilacao novoAmb = new AmbienteCompilacaoMap(amb);
        novoAmb.add(id, tipoValor);
        return corpo.checaTipo(novoAmb);
    }
    
    public Tipo getTipo(AmbienteCompilacao amb) {
        Tipo tipoValor = expValor.getTipo(amb);
        AmbienteCompilacao novoAmb = new AmbienteCompilacaoMap(amb);
        novoAmb.add(id, tipoValor);
        return corpo.getTipo(novoAmb);
    }
}
