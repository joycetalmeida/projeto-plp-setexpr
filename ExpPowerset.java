import java.util.*;

/**
 * Powerset: Conjunto das partes - gera todos os subconjuntos possíveis
 * powerset {1, 2, 3} = {{}, {1}, {2}, {3}, {1,2}, {1,3}, {2,3}, {1,2,3}}
 * Cardinalidade: |℘(A)| = 2^|A|
 */
public class ExpPowerset implements Expressao {
    
    private Expressao expressao;
    
    public ExpPowerset(Expressao expressao) {
        this.expressao = expressao;
    }
    
    public Valor avaliar(AmbienteExecucao amb) {
        Valor val = expressao.avaliar(amb);
        
        if (!(val instanceof ValorConjunto)) {
            throw new RuntimeException("Operador 'powerset' espera um conjunto");
        }
        
        ValorConjunto conjunto = (ValorConjunto) val;
        Set<Valor> elementos = conjunto.getValor();
        
        // Converte para lista para acessar por índice
        List<Valor> listaElementos = new ArrayList<>(elementos);
        int n = listaElementos.size();
        
        // Conjunto das partes (resultado)
        Set<Valor> powerset = new HashSet<>();
        
        // Gera todos os 2^n subconjuntos usando representação binária
        // Cada número de 0 a 2^n-1 representa um subconjunto
        int totalSubconjuntos = (int) Math.pow(2, n);
        
        for (int i = 0; i < totalSubconjuntos; i++) {
            Set<Valor> subconjunto = new HashSet<>();
            
            // Para cada bit em i, se estiver setado, inclui o elemento correspondente
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subconjunto.add(listaElementos.get(j));
                }
            }
            
            powerset.add(new ValorConjunto(subconjunto));
        }
        
        return new ValorConjunto(powerset);
    }
    
    public boolean checaTipo(AmbienteCompilacao amb) {
        return expressao.checaTipo(amb);
    }
    
    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoConjunto.getInstancia();
    }
}
