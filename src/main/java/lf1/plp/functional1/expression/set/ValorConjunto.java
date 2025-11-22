package lf1.plp.functional1.expression.set;

import java.util.HashSet;
import java.util.Iterator;
import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;

public class ValorConjunto implements Valor { 
    private HashSet<Valor> valor;
    
    public ValorConjunto(HashSet<Valor> valor) { this.valor = valor; }
    public HashSet<Valor> getValor() { return this.valor; }

    @Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return this;
    }
    
    @Override
    public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return true; // Um valor já avaliado é sempre bem tipado.
    }
    
    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        if (valor.isEmpty()) {
            return new TipoConjunto(new TipoIndeterminado());
        }
        // Pega o tipo do primeiro elemento para definir o tipo do conjunto
        Iterator<Valor> it = valor.iterator();
        Tipo tipoInterno = it.next().getTipo(amb);
        return new TipoConjunto(tipoInterno);
    }
    
    @Override
    public Expressao reduzir(AmbienteExecucao amb) { return this; }
    
    @Override
    public ValorConjunto clone() { return new ValorConjunto(new HashSet<>(this.valor)); }
    
    @Override
    public String toString() { return valor.toString(); }
    
    @Override
    public int hashCode() { return valor.hashCode(); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ValorConjunto outroConjunto = (ValorConjunto) obj;
        return this.valor.equals(outroConjunto.valor);
    }
}