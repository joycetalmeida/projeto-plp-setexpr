package lf1.plp.functional1.expression.set;

import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.HashSet;
public class ExpMap implements Expressao {
    private Expressao expressaoMap;
    private Id id;
    private Expressao expressaoConjunto;
    public ExpMap(Expressao expressaoMap, Id id, Expressao expressaoConjunto) { this.expressaoMap = expressaoMap; this.id = id; this.expressaoConjunto = expressaoConjunto; }

 @Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // 1. Avalia o conjunto base
        ValorConjunto conjuntoBase = (ValorConjunto) expressaoConjunto.avaliar(amb);
        
        // 2. Prepara o conjunto de resultado
        HashSet<Valor> conjuntoResultado = new HashSet<>();
        
        // 3. Itera sobre cada elemento
        for (Valor valorElemento : conjuntoBase.getValor()) {
            // 4. Cria o ambiente temporário
            amb.incrementa();
            amb.map(id, valorElemento);
            
            // 5. Avalia a expressão de mapeamento f(x)
            Valor valorMapeado = expressaoMap.avaliar(amb);
            
            // 6. Adiciona o novo valor ao resultado
            conjuntoResultado.add(valorMapeado);
            
            // 7. Restaura o ambiente
            amb.restaura();
        }
        
        return new ValorConjunto(conjuntoResultado);
    }
    
    @Override
    public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // 1. Verifica se a expressão do conjunto (S) é bem tipada
        if (!expressaoConjunto.checaTipo(amb)) {
            return false;
        }
        
        // 2. Verifica se S é realmente um conjunto
        Tipo tipoConj = expressaoConjunto.getTipo(amb);
        if (!(tipoConj instanceof TipoConjunto)) {
            throw new VariavelNaoDeclaradaException(new Id("Expressão 'in' da compreensão de conjunto espera um Conjunto."));
        }
        
        // 3. Obtém o tipo interno T dos elementos do conjunto S
        Tipo tipoInterno = ((TipoConjunto) tipoConj).getTipoInterno();
        
        // 4. Cria um novo ambiente de tipos temporário
        amb.incrementa();
        amb.map(id, tipoInterno);
        
        // 5. Verifica se a expressão de mapeamento f(x) é bem tipada NESTE novo ambiente
        boolean mapOK = expressaoMap.checaTipo(amb);
        
        // 6. Restaura o ambiente original
        amb.restaura();
        
        return mapOK;
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // Lógica similar ao checaTipo para descobrir o tipo resultante
        
        Tipo tipoConj = expressaoConjunto.getTipo(amb);
        Tipo tipoInterno = ((TipoConjunto) tipoConj).getTipoInterno();
        
        amb.incrementa();
        amb.map(id, tipoInterno);
        
        // O tipo final é o TipoConjunto do tipo da expressão de mapeamento
        Tipo tipoMap = expressaoMap.getTipo(amb);
        
        amb.restaura();
        
        return new TipoConjunto(tipoMap);
    }
    
    @Override
    public Expressao reduzir(AmbienteExecucao amb) { return this; }
    
    @Override
    public Expressao clone() { return new ExpMap(expressaoMap.clone(), id.clone(), expressaoConjunto.clone()); }
}