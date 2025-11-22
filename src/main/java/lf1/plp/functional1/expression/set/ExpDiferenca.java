package lf1.plp.functional1.expression.set;

import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.HashSet;
public class ExpDiferenca implements Expressao {
    private Expressao esq;
    private Expressao dir;
    public ExpDiferenca(Expressao esq, Expressao dir) { this.esq = esq; this.dir = dir; }

 @Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        ValorConjunto vEsq = (ValorConjunto) esq.avaliar(amb);
        ValorConjunto vDir = (ValorConjunto) dir.avaliar(amb);
        
        HashSet<Valor> setEsq = vEsq.getValor();
        HashSet<Valor> setDir = vDir.getValor();
        
        // 3. Cria um novo conjunto (baseado no da esquerda)
        HashSet<Valor> conjuntoResultado = new HashSet<>(setEsq);
        
        // 4. Remove todos os elementos que estão no conjunto da direita
        conjuntoResultado.removeAll(setDir);
        
        return new ValorConjunto(conjuntoResultado);
    }
    @Override
    public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoEsq = esq.getTipo(amb);
        Tipo tipoDir = dir.getTipo(amb);

        if (!(tipoEsq instanceof TipoConjunto) || !(tipoDir instanceof TipoConjunto)) {
            throw new VariavelNaoDeclaradaException(new Id("Operador 'diff' espera dois Conjuntos."));
        }
        
        if (!tipoEsq.eIgual(tipoDir)) {
             throw new VariavelNaoDeclaradaException(new Id(
                "Operador 'diff' espera dois Conjuntos do mesmo tipo. Encontrados " 
                + tipoEsq.getNome() + " e " + tipoDir.getNome()));
        }
        
        return esq.checaTipo(amb) && dir.checaTipo(amb);
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoEsq = esq.getTipo(amb);
        Tipo tipoDir = dir.getTipo(amb);
        
        if(((TipoConjunto)tipoEsq).getTipoInterno() instanceof TipoIndeterminado) {
            return tipoDir;
        }
        return tipoEsq;
    }
    
    @Override
    public Expressao reduzir(AmbienteExecucao amb) { return this; }
    
    @Override
    public Expressao clone() { return new ExpDiferenca(esq.clone(), dir.clone()); }
}