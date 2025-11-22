package lf1.plp.functional1.expression.set;

import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.HashSet;
public class ExpUniaoDistribuida implements Expressao {
    private Expressao exp;
    public ExpUniaoDistribuida(Expressao exp) { this.exp = exp; }

@Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // 1. Avalia o conjunto de conjuntos (Set<Set<T>>)
        ValorConjunto vConjDeConjuntos = (ValorConjunto) exp.avaliar(amb);
        
        // 2. Prepara o conjunto de resultado
        HashSet<Valor> conjuntoResultado = new HashSet<>();
        
        // 3. Itera sobre cada elemento (que é um ValorConjunto)
        for (Valor v : vConjDeConjuntos.getValor()) {
            ValorConjunto vConjInterno = (ValorConjunto) v;
            
            // 4. Adiciona todos os elementos do conjunto interno ao resultado
            conjuntoResultado.addAll(vConjInterno.getValor());
        }
        
        return new ValorConjunto(conjuntoResultado);
    }

    @Override
    public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoExp = exp.getTipo(amb);
        
        // A expressão deve ser um Conjunto...
        if (!(tipoExp instanceof TipoConjunto)) {
            throw new VariavelNaoDeclaradaException(new Id("Operador 'UNION' espera um Conjunto de Conjuntos."));
        }
        
        Tipo tipoInterno = ((TipoConjunto) tipoExp).getTipoInterno();
        
        // ...cujo tipo interno é também um Conjunto.
        if (!(tipoInterno instanceof TipoConjunto) && !(tipoInterno instanceof TipoIndeterminado)) {
             throw new VariavelNaoDeclaradaException(new Id("Operador 'UNION' espera um Conjunto de Conjuntos (Set<Set<T>>)."));
        }
        
        return exp.checaTipo(amb);
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // Se a entrada é Set<Set<T>>, a saída é Set<T>
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
    public Expressao clone() { return new ExpUniaoDistribuida(exp.clone()); }
}