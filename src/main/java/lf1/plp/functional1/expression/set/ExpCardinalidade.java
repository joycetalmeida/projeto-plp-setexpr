package lf1.plp.functional1.expression.set;

import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.HashSet;

public class ExpCardinalidade implements Expressao {
    private Expressao exp;
    public ExpCardinalidade(Expressao exp) { this.exp = exp; }

@Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // 1. Avalia a expressão interna (que deve ser um conjunto)
        Valor valorResultado = exp.avaliar(amb);
        
        // 2. Faz o cast para ValorConjunto (checaTipo já garantiu que é seguro)
        ValorConjunto conjunto = (ValorConjunto) valorResultado;
        
        // 3. Obtém o HashSet interno
        HashSet<Valor> hashSetInterno = conjunto.getValor();
        
        // 4. Calcula o tamanho (cardinalidade)
        int cardinalidade = hashSetInterno.size();
        
        // 5. Retorna o tamanho como um ValorInteiro
        return new ValorInteiro(cardinalidade);
    }
    
    @Override
    public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoExp = exp.getTipo(amb);
        
        if (!(tipoExp instanceof TipoConjunto)) {
            throw new VariavelNaoDeclaradaException(new Id("Operador '#' (cardinalidade) espera um Conjunto."));
        }
        
        return exp.checaTipo(amb);
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return new TipoInteiro();
    }
    
    @Override
    public Expressao reduzir(AmbienteExecucao amb) { return this; }
    
    @Override
    public Expressao clone() { return new ExpCardinalidade(exp.clone()); }
}