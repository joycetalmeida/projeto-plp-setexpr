// Representa aplicação de função: f(x)
public class ExpAplicacao implements Expressao {
    
    private Expressao funcao;
    private Expressao argumento;
    
    public ExpAplicacao(Expressao funcao, Expressao argumento) {
        this.funcao = funcao;
        this.argumento = argumento;
    }
    
    public Valor avaliar(AmbienteExecucao amb) {
        Valor valFuncao = funcao.avaliar(amb);
        
        if (!(valFuncao instanceof ValorFuncao)) {
            throw new RuntimeException("Tentativa de aplicar algo que nao e uma funcao.");
        }
        
        ValorFuncao vf = (ValorFuncao) valFuncao;
        Valor valArgumento = argumento.avaliar(amb);
        
        // Cria novo ambiente estendendo o ambiente capturado pela closure
        AmbienteExecucao novoAmb = new AmbienteExecucaoMap(vf.getAmbiente());
        novoAmb.add(vf.getParametro(), valArgumento);
        
        return vf.getCorpo().avaliar(novoAmb);
    }
    
    public boolean checaTipo(AmbienteCompilacao amb) {
        return funcao.checaTipo(amb) && argumento.checaTipo(amb);
    }
    
    public Tipo getTipo(AmbienteCompilacao amb) {
        // Simplificado - em uma implementação completa precisaríamos de tipos de função
        return funcao.getTipo(amb);
    }
}
