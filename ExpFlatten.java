import java.util.*;

/**
 * Flatten: Achata um conjunto de conjuntos em um único nível
 * flatten {{1,2}, {3,4}, {5}} = {1, 2, 3, 4, 5}
 * Equivalente à união de todos os conjuntos internos: ⋃ S
 */
public class ExpFlatten implements Expressao {
    
    private Expressao expressao;
    
    public ExpFlatten(Expressao expressao) {
        this.expressao = expressao;
    }
    
    public Valor avaliar(AmbienteExecucao amb) {
        Valor val = expressao.avaliar(amb);
        
        if (!(val instanceof ValorConjunto)) {
            throw new RuntimeException("Operador 'flatten' espera um conjunto");
        }
        
        ValorConjunto conjExterno = (ValorConjunto) val;
        Set<Valor> conjuntoExterno = conjExterno.getValor();
        
        // Conjunto resultado que conterá todos os elementos achatados
        Set<Valor> resultado = new HashSet<>();
        
        // Para cada elemento do conjunto externo
        for (Valor elemento : conjuntoExterno) {
            // Se for um conjunto, adiciona todos seus elementos ao resultado
            if (elemento instanceof ValorConjunto) {
                ValorConjunto conjInterno = (ValorConjunto) elemento;
                resultado.addAll(conjInterno.getValor());
            } else {
                // Se não for conjunto, adiciona o próprio elemento
                resultado.add(elemento);
            }
        }
        
        return new ValorConjunto(resultado);
    }
    
    public boolean checaTipo(AmbienteCompilacao amb) {
        return expressao.checaTipo(amb);
    }
    
    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoConjunto.getInstancia();
    }
}
