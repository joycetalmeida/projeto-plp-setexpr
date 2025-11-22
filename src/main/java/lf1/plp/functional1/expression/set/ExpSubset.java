package lf1.plp.functional1.expression.set;

import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.HashSet;
public class ExpSubset implements Expressao {
    private Expressao esq;
    private Expressao dir;
    public ExpSubset(Expressao esq, Expressao dir) { this.esq = esq; this.dir = dir; }

@Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // 1. Avalia o conjunto A (subconjunto)
        ValorConjunto vEsq = (ValorConjunto) esq.avaliar(amb);
        // 2. Avalia o conjunto B (superconjunto)
        ValorConjunto vDir = (ValorConjunto) dir.avaliar(amb);
        
        // 3. Verifica se B contém todos os elementos de A
        boolean eSubconjunto = vDir.getValor().containsAll(vEsq.getValor());
        
        return new ValorBooleano(eSubconjunto);
    }
    
    @Override
    public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoEsq = esq.getTipo(amb);
        Tipo tipoDir = dir.getTipo(amb);

        if (!(tipoEsq instanceof TipoConjunto) || !(tipoDir instanceof TipoConjunto)) {
            throw new VariavelNaoDeclaradaException(new Id("Operador 'subset' espera dois Conjuntos."));
        }
        
        if (!tipoEsq.eIgual(tipoDir)) {
             throw new VariavelNaoDeclaradaException(new Id(
                "Operador 'subset' espera dois Conjuntos do mesmo tipo. Encontrados " 
                + tipoEsq.getNome() + " e " + tipoDir.getNome()));
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
    public Expressao clone() { return new ExpSubset(esq.clone(), dir.clone()); }
}