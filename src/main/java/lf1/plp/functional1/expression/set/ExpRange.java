package lf1.plp.functional1.expression.set;

import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.HashSet;
public class ExpRange implements Expressao {
    private Expressao esq;
    private Expressao dir;
    public ExpRange(Expressao esq, Expressao dir) { this.esq = esq; this.dir = dir; }

 @Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // 1. Avalia os operandos de início e fim
        ValorInteiro vInicio = (ValorInteiro) esq.avaliar(amb);
        ValorInteiro vFim = (ValorInteiro) dir.avaliar(amb);
        
        // 2. Cria o HashSet para o resultado
        HashSet<Valor> conjuntoResultado = new HashSet<>();
        
        // 3. Preenche o conjunto com o intervalo
        for (int i = vInicio.valor(); i <= vFim.valor(); i++) {
            conjuntoResultado.add(new ValorInteiro(i));
        }
        
        // 4. Retorna o novo ValorConjunto
        return new ValorConjunto(conjuntoResultado);
    }
    
    @Override
    public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        boolean esqOK = esq.checaTipo(amb) && esq.getTipo(amb).eInteiro();
        boolean dirOK = dir.checaTipo(amb) && dir.getTipo(amb).eInteiro();
        
        if (!esqOK || !dirOK) {
             throw new VariavelNaoDeclaradaException(new Id("Operador '..' espera dois Inteiros."));
        }
        return true;
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return new TipoConjunto(new TipoInteiro());
    }
    
    @Override
    public Expressao reduzir(AmbienteExecucao amb) { return this; }
    
    @Override
    public Expressao clone() { return new ExpRange(esq.clone(), dir.clone()); }
}