package lf1.plp.functional1.expression.set;

import lf1.plp.expressions1.util.*;
import lf1.plp.expressions2.expression.*;
import lf1.plp.expressions2.memory.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ExpPowerSet implements Expressao {
    private Expressao exp;

    public ExpPowerSet(Expressao exp) {
        this.exp = exp;
    }

    @Override
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // 1. Avalia o conjunto original
        ValorConjunto conjuntoOriginal = (ValorConjunto) exp.avaliar(amb);

        // 2. Converte para Lista para facilitar a recursão (acesso por
        // índice/cabeça/cauda)
        List<Valor> listaElementos = new ArrayList<>(conjuntoOriginal.getValor());

        // 3. Calcula o Power Set recursivamente
        HashSet<Valor> resultadoPowerSet = calcularPowerSet(listaElementos);

        return new ValorConjunto(resultadoPowerSet);
    }

    // Algoritmo Recursivo do Power Set
    private HashSet<Valor> calcularPowerSet(List<Valor> elementos) {
        HashSet<Valor> resultado = new HashSet<>();

        // Base da recursão: se a lista é vazia, o power set é {{}} (conjunto contendo o
        // conjunto vazio)
        if (elementos.isEmpty()) {
            resultado.add(new ValorConjunto(new HashSet<>()));
            return resultado;
        }

        // Passo Recursivo:
        // 1. Separa o primeiro elemento (cabeça) do resto (cauda)
        Valor cabeca = elementos.get(0);
        List<Valor> cauda = elementos.subList(1, elementos.size());

        // 2. Calcula o power set do resto (sem a cabeça)
        HashSet<Valor> powerSetCauda = calcularPowerSet(cauda);

        // 3. O resultado é a união de:
        // A. O power set da cauda (subconjuntos que NÃO têm a cabeça)
        // B. O power set da cauda onde ADICIONAMOS a cabeça a cada subconjunto

        // Adiciona A
        resultado.addAll(powerSetCauda);

        // Adiciona B
        for (Valor subConjuntoValor : powerSetCauda) {
            ValorConjunto subConjunto = (ValorConjunto) subConjuntoValor;

            // Clona o subconjunto para não alterar o original
            HashSet<Valor> novoSubConjunto = new HashSet<>(subConjunto.getValor());
            novoSubConjunto.add(cabeca);

            resultado.add(new ValorConjunto(novoSubConjunto));
        }

        return resultado;
    }

    @Override
    public boolean checaTipo(AmbienteCompilacao amb)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        Tipo tipoExp = exp.getTipo(amb);

        if (!(tipoExp instanceof TipoConjunto)) {
            throw new VariavelNaoDeclaradaException(new Id("Operador 'powerset' espera um Conjunto."));
        }

        return exp.checaTipo(amb);
    }

    @Override
    public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        // O tipo de retorno é um Conjunto de Conjuntos do tipo original
        // Ex: Se entra Set<Int>, sai Set<Set<Int>>
        return new TipoConjunto(exp.getTipo(amb));
    }

    @Override
    public Expressao reduzir(AmbienteExecucao amb) {
        return this;
    }

    @Override
    public Expressao clone() {
        return new ExpPowerSet(exp.clone());
    }
}