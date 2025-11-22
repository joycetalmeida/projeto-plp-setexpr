package lf1.plp.functional1.expression.set;

import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.HashSet;
public class ExpIn implements Expressao {
    private Expressao esq;
    private Expressao dir;
    public ExpIn(Expressao esq, Expressao dir) { this.esq = esq; this.dir = dir; }

 @Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // 1. Avalia o elemento
        Valor vElem = esq.avaliar(amb);
        // 2. Avalia o conjunto
        ValorConjunto vConj = (ValorConjunto) dir.avaliar(amb);
        
        // 3. Verifica o pertencimento
        boolean pertence = vConj.getValor().contains(vElem);
        
        // 4. Retorna o booleano
        return new ValorBooleano(pertence);
    }
    
    @Override
    public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoElem = esq.getTipo(amb);
        Tipo tipoConj = dir.getTipo(amb);

        if (!(tipoConj instanceof TipoConjunto)) {
            throw new VariavelNaoDeclaradaException(new Id("Operador 'in' espera um Conjunto no operando direito."));
        }
        
        Tipo tipoInternoConj = ((TipoConjunto) tipoConj).getTipoInterno();
        
        if (!(tipoInternoConj instanceof TipoIndeterminado) && !tipoElem.eIgual(tipoInternoConj)) {
             throw new VariavelNaoDeclaradaException(new Id(
                "Tipo do elemento (" + tipoElem.getNome() + ") é incompatível com o tipo do conjunto (" + tipoConj.getNome() + ")"));
        }
        
        return esq.checaTipo(amb) && dir.checaTipo(amb);
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return new TipoBooleano();
    }
    
    @Override
    public Expressao reduzir(AmbienteExecucao amb) { return this; }
    
    @Override
    public Expressao clone() { return new ExpIn(esq.clone(), dir.clone()); }
}