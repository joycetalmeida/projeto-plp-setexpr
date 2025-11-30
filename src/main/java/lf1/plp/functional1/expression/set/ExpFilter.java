package lf1.plp.functional1.expression.set;

import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.HashSet;

public class ExpFilter implements Expressao {
    private Id id;
    private Expressao expressaoConjunto;
    private Expressao expressaoCondicao;

    public ExpFilter(Id id, Expressao expressaoConjunto, Expressao expressaoCondicao) {
        this.id = id;
        this.expressaoConjunto = expressaoConjunto;
        this.expressaoCondicao = expressaoCondicao;
    }

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

            // 5. Avalia a condição P(x)
            ValorBooleano condicao = (ValorBooleano) expressaoCondicao.avaliar(amb);

            // 6. Se a condição for verdadeira, adiciona o elemento
            if (condicao.valor()) {
                conjuntoResultado.add(valorElemento);
            }

            // 7. Restaura o ambiente
            amb.restaura();
        }

        return new ValorConjunto(conjuntoResultado);
    }

    @Override
    public boolean checaTipo(AmbienteCompilacao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // 1. Verifica se a expressão do conjunto (S) é bem tipada
        if (!expressaoConjunto.checaTipo(amb)) {
            return false;
        }

        // 2. Verifica se S é realmente um conjunto
        Tipo tipoConj = expressaoConjunto.getTipo(amb);
        if (!(tipoConj instanceof TipoConjunto)) {
            throw new VariavelNaoDeclaradaException(
                    new Id("Expressão 'in' da compreensão de conjunto espera um Conjunto."));
        }

        // 3. Obtém o tipo interno T dos elementos do conjunto S
        Tipo tipoInterno = ((TipoConjunto) tipoConj).getTipoInterno();

        // 4. Cria um novo ambiente de tipos temporário
        amb.incrementa();
        amb.map(id, tipoInterno);

        // --- MUDANÇA AQUI ---

        // Teste 1: A expressão isPrime(x) é válida sintática e semanticamente?
        // (Isso verifica se a função existe e se os argumentos batem)
        if (!expressaoCondicao.checaTipo(amb)) {
            amb.restaura();
            // Se entrar aqui, o problema é:
            // 1. Função não encontrada
            // 2. Ou Tipo do argumento 'x' != Tipo do parâmetro 'n'
            return false;
        }

        // Teste 2: O retorno é Booleano?
        Tipo tipoCondicao = expressaoCondicao.getTipo(amb);
        if (!tipoCondicao.eBooleano()) {
            amb.restaura();
            throw new VariavelNaoDeclaradaException(
                    new Id("A condição do filtro retorna " + tipoCondicao + ", mas esperava Booleano."));
        }

        // // 5. Verifica se a condição P(x) é booleana NESTE novo ambiente
        // boolean condOK = expressaoCondicao.checaTipo(amb) &&
        // expressaoCondicao.getTipo(amb).eBooleano();
        // if (!condOK) {
        // throw new VariavelNaoDeclaradaException(new Id("Condição da compreensão de
        // conjunto deve ser Booleana."));
        // }

        // 6. Restaura o ambiente original
        amb.restaura();
        return true;
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // O tipo de um filtro é o mesmo tipo do conjunto que foi filtrado
        return expressaoConjunto.getTipo(amb);
    }

    @Override
    public Expressao reduzir(AmbienteExecucao amb) {
        return this;
    }

    @Override
    public Expressao clone() {
        return new ExpFilter(id.clone(), expressaoConjunto.clone(), expressaoCondicao.clone());
    }
}