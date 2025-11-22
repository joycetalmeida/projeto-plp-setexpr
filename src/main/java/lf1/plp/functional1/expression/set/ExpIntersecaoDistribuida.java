package lf1.plp.functional1.expression.set;

import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.HashSet; // <-- Importado
import java.util.Iterator; // <-- Importado
public class ExpIntersecaoDistribuida implements Expressao {
    private Expressao exp;
    public ExpIntersecaoDistribuida(Expressao exp) { this.exp = exp; }

 @Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // 1. Avalia o conjunto de conjuntos (Set<Set<T>>)
        ValorConjunto vConjDeConjuntos = (ValorConjunto) exp.avaliar(amb);
        
        // 2. Pega o iterador
        Iterator<Valor> it = vConjDeConjuntos.getValor().iterator();
        
        // 3. Se o conjunto de conjuntos for vazio, retorna conjunto vazio
        if (!it.hasNext()) {
            return new ValorConjunto(new HashSet<Valor>());
        }
        
        // 4. Inicializa o resultado com uma cópia do *primeiro* conjunto interno
        ValorConjunto primeiroConjunto = (ValorConjunto) it.next();
        HashSet<Valor> conjuntoResultado = new HashSet<>(primeiroConjunto.getValor());
        
        // 5. Itera sobre os conjuntos restantes
        while (it.hasNext()) {
            ValorConjunto vConjInterno = (ValorConjunto) it.next();
            // 6. Mantém apenas os elementos em comum
            conjuntoResultado.retainAll(vConjInterno.getValor());
        }
        
        return new ValorConjunto(conjuntoResultado);
    }
    
    @Override
    public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoExp = exp.getTipo(amb);
        
        if (!(tipoExp instanceof TipoConjunto)) {
            throw new VariavelNaoDeclaradaException(new Id("Operador 'INTER' espera um Conjunto de Conjuntos."));
        }
        
        Tipo tipoInterno = ((TipoConjunto) tipoExp).getTipoInterno();
        
        if (!(tipoInterno instanceof TipoConjunto) && !(tipoInterno instanceof TipoIndeterminado)) {
             throw new VariavelNaoDeclaradaException(new Id("Operador 'INTER' espera um Conjunto de Conjuntos (Set<Set<T>>)."));
        }
        
        return exp.checaTipo(amb);
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoExp = exp.getTipo(amb);
        Tipo tipoInterno = ((TipoConjunto) tipoExp).getTipoInterno();
        
        if (tipoInterno instanceof TipoConjunto) {
            return tipoInterno; // Retorna o tipo interno (Set<T>)
        }
        
        return new TipoConjunto(new TipoIndeterminado());
    }
    
    @Override
    public Expressao reduzir(AmbienteExecucao amb) { return this; }
    
    @Override
    public Expressao clone() { return new ExpIntersecaoDistribuida(exp.clone()); }
}