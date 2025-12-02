import java.util.*;

/**
 * Range: Gera conjunto com sequência de inteiros
 * Sintaxe: inicio...fim
 * Exemplo: 1...5 = {1, 2, 3, 4, 5}
 */
public class ExpRange implements Expressao {
    
    private Expressao inicio;
    private Expressao fim;
    
    public ExpRange(Expressao inicio, Expressao fim) {
        this.inicio = inicio;
        this.fim = fim;
    }
    
    public Valor avaliar(AmbienteExecucao amb) {
        Valor valInicio = inicio.avaliar(amb);
        Valor valFim = fim.avaliar(amb);
        
        if (!(valInicio instanceof ValorInteiro)) {
            throw new RuntimeException("Operador '...' espera inteiro à esquerda");
        }
        
        if (!(valFim instanceof ValorInteiro)) {
            throw new RuntimeException("Operador '...' espera inteiro à direita");
        }
        
        ValorInteiro intInicio = (ValorInteiro) valInicio;
        ValorInteiro intFim = (ValorInteiro) valFim;
        
        int start = intInicio.getValor();
        int end = intFim.getValor();
        
        Set<Valor> resultado = new HashSet<>();
        
        // Suporta ranges crescentes e decrescentes
        if (start <= end) {
            // Range crescente: 1...5 = {1, 2, 3, 4, 5}
            for (int i = start; i <= end; i++) {
                resultado.add(new ValorInteiro(i));
            }
        } else {
            // Range decrescente: 5...1 = {5, 4, 3, 2, 1}
            for (int i = start; i >= end; i--) {
                resultado.add(new ValorInteiro(i));
            }
        }
        
        return new ValorConjunto(resultado);
    }
    
    public boolean checaTipo(AmbienteCompilacao amb) {
        return inicio.checaTipo(amb) && fim.checaTipo(amb);
    }
    
    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoConjunto.getInstancia();
    }
}
