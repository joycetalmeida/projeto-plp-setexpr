// Representa uma função lambda: fn x => corpo
public class ExpFuncao implements Expressao {
    
    private String parametro;
    private Expressao corpo;
    
    public ExpFuncao(String parametro, Expressao corpo) {
        this.parametro = parametro;
        this.corpo = corpo;
    }
    
    public Valor avaliar(AmbienteExecucao amb) {
        // Retorna uma closure que captura o ambiente atual
        return new ValorFuncao(parametro, corpo, amb);
    }
    
    public boolean checaTipo(AmbienteCompilacao amb) {
        // Para simplificar, aceitamos qualquer função
        // Em uma implementação completa, precisaríamos de inferência de tipos
        return true;
    }
    
    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoFuncao.getInstancia();
    }
}
