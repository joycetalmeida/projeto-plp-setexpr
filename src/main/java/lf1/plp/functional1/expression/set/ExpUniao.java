package lf1.plp.functional1.expression.set;

import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.HashSet;
public class ExpUniao implements Expressao {
    private Expressao esq;
    private Expressao dir;
    public ExpUniao(Expressao esq, Expressao dir) { this.esq = esq; this.dir = dir; }

@Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // 1. Avalia os dois conjuntos
        ValorConjunto vEsq = (ValorConjunto) esq.avaliar(amb);
        ValorConjunto vDir = (ValorConjunto) dir.avaliar(amb);
        
        // 2. Obtém os HashSets internos
        HashSet<Valor> setEsq = vEsq.getValor();
        HashSet<Valor> setDir = vDir.getValor();
        
        // 3. Cria um novo conjunto (baseado no da esquerda)
        HashSet<Valor> conjuntoResultado = new HashSet<>(setEsq);
        
        // 4. Adiciona todos os elementos do conjunto da direita
        conjuntoResultado.addAll(setDir);
        
        return new ValorConjunto(conjuntoResultado);
    }
    @Override
    public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoEsq = esq.getTipo(amb);
        Tipo tipoDir = dir.getTipo(amb);

        if (!(tipoEsq instanceof TipoConjunto) || !(tipoDir instanceof TipoConjunto)) {
            throw new VariavelNaoDeclaradaException(new Id("Operador 'union' espera dois Conjuntos."));
        }
        
        if (!tipoEsq.eIgual(tipoDir)) {
             throw new VariavelNaoDeclaradaException(new Id(
                "Operador 'union' espera dois Conjuntos do mesmo tipo. Encontrados " 
                + tipoEsq.getNome() + " e " + tipoDir.getNome()));
        }
        
        return esq.checaTipo(amb) && dir.checaTipo(amb);
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoEsq = esq.getTipo(amb);
        Tipo tipoDir = dir.getTipo(amb);
        
        // Se um dos conjuntos for vazio (Indeterminado), o tipo final é o do outro conjunto
        if(((TipoConjunto)tipoEsq).getTipoInterno() instanceof TipoIndeterminado) {
            return tipoDir;
        }
        return tipoEsq;
    }
    
    @Override
    public Expressao reduzir(AmbienteExecucao amb) { return this; }
    
    @Override
    public Expressao clone() { return new ExpUniao(esq.clone(), dir.clone()); }
}