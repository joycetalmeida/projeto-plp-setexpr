// Interface que toda expressão da linguagem deve implementar.
public interface Expressao {
    
    // Avalia o valor da expressão.
    public Valor avaliar(AmbienteExecucao amb);

    // Checa se os tipos da expressão estão corretos.
    public boolean checaTipo(AmbienteCompilacao amb);

    // Retorna o tipo da expressão.
    public Tipo getTipo(AmbienteCompilacao amb);
}
