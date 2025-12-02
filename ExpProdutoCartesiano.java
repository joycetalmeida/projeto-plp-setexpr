import java.util.*;

/**
 * Produto Cartesiano: A cross B = {(a,b) | a ∈ A, b ∈ B}
 * Gera todos os pares ordenados possíveis entre elementos dos dois conjuntos
 */
public class ExpProdutoCartesiano implements Expressao {
    
    private Expressao esquerda;
    private Expressao direita;
    
    public ExpProdutoCartesiano(Expressao esquerda, Expressao direita) {
        this.esquerda = esquerda;
        this.direita = direita;
    }
    
    public Valor avaliar(AmbienteExecucao amb) {
        Valor valEsq = esquerda.avaliar(amb);
        Valor valDir = direita.avaliar(amb);
        
        if (!(valEsq instanceof ValorConjunto)) {
            throw new RuntimeException("Operador 'cross' espera conjunto à esquerda");
        }
        
        if (!(valDir instanceof ValorConjunto)) {
            throw new RuntimeException("Operador 'cross' espera conjunto à direita");
        }
        
        ValorConjunto conjEsq = (ValorConjunto) valEsq;
        ValorConjunto conjDir = (ValorConjunto) valDir;
        
        Set<Valor> conjuntoEsq = conjEsq.getValor();
        Set<Valor> conjuntoDir = conjDir.getValor();
        
        // Produto cartesiano: gera pares ordenados (representados como conjuntos de 2 elementos)
        Set<Valor> resultado = new HashSet<>();
        
        for (Valor a : conjuntoEsq) {
            for (Valor b : conjuntoDir) {
                // Par ordenado representado como conjunto {a, b}
                // Nota: em teoria, deveríamos usar tuplas, mas representamos como conjuntos
                Set<Valor> par = new HashSet<>();
                par.add(a);
                par.add(b);
                resultado.add(new ValorConjunto(par));
            }
        }
        
        return new ValorConjunto(resultado);
    }
    
    public boolean checaTipo(AmbienteCompilacao amb) {
        return esquerda.checaTipo(amb) && direita.checaTipo(amb);
    }
    
    public Tipo getTipo(AmbienteCompilacao amb) {
        return TipoConjunto.getInstancia();
    }
}
