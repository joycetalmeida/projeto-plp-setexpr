package lf1.plp.functional1.expression.set;

import java.util.List;
import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.HashSet;

public class ExpLiteralSet implements Expressao {
    private List<Expressao> elementos;
    
    public ExpLiteralSet(List<Expressao> elementos) { this.elementos = elementos; }

   @Override
public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
    // 1. Criar um HashSet vazio para guardar os valores
    HashSet<Valor> valoresDoConjunto = new HashSet<>();

    // 2. Avaliar cada expressão dentro do literal
    for (Expressao exp : this.elementos) {
        // 3. Adicionar o resultado da avaliação ao HashSet
        valoresDoConjunto.add(exp.avaliar(amb));
    }
    
    // 4. Retornar um novo ValorConjunto com o HashSet preenchido
    return new ValorConjunto(valoresDoConjunto);
}
    
    @Override
    public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // Se for vazio, é bem tipado.
        if (elementos.isEmpty()) {
            return true;
        }
        
        // Pega o tipo do primeiro elemento
        Tipo tipoBase = elementos.get(0).getTipo(amb);
        
        // Verifica se todos os outros elementos têm o mesmo tipo
        for (Expressao el : elementos) {
            if (!el.checaTipo(amb)) {
                return false;
            }
            if (!el.getTipo(amb).eIgual(tipoBase)) {
                // Erro! Conjunto não é homogêneo.
                throw new VariavelNaoDeclaradaException(new Id("Conjuntos devem ser homogêneos. Encontrados tipos " 
                    + tipoBase.getNome() + " e " + el.getTipo(amb).getNome()));
            }
        }
        return true;
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        if (elementos.isEmpty()) {
            return new TipoConjunto(new TipoIndeterminado());
        }
        // O tipo do conjunto é o tipo do seu primeiro elemento
        return new TipoConjunto(elementos.get(0).getTipo(amb));
    }
    
    @Override
    public Expressao reduzir(AmbienteExecucao amb) { return this; }
    
    @Override
    public Expressao clone() { return this; /* Placeholder */ }
}