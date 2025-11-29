package lf1.plp.expressions2.expression;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions1.util.TipoPrimitivo;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;

/**
 * Um objeto desta classe representa uma Expressao de Maior Que (>).
 */
public class ExpGt extends ExpBinaria {

    /**
     * Controi uma Expressao de Maior Que com as sub-expressoes especificadas.
     * Assume-se que estas sub-expressoes resultam em <code>ValorInteiro</code>
     * quando avaliadas.
     * 
     * @param esq Expressao da esquerda
     * @param dir Expressao da direita
     */
    public ExpGt(Expressao esq, Expressao dir) {
        super(esq, dir, ">");
    }

    /**
     * Retorna o valor da Expressao de Maior Que
     */
    public Valor avaliar(AmbienteExecucao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return new ValorBooleano(
                ((ValorInteiro) getEsq().avaliar(amb)).valor() > ((ValorInteiro) getDir().avaliar(amb)).valor());
    }

    /**
     * Realiza a verificacao de tipos desta expressao.
     *
     * @param ambiente o ambiente de compilacao.
     * @return <code>true</code> se os tipos da expressao sao validos;
     *         <code>false</code> caso contrario.
     * @exception VariavelNaoDeclaradaException se existir um identificador
     *                                          nao declarado no ambiente.
     * @exception VariavelNaoDeclaradaException se existir um identificador
     *                                          declarado mais de uma vez no mesmo
     *                                          bloco do ambiente.
     */
    protected boolean checaTipoElementoTerminal(AmbienteCompilacao ambiente)
            throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
        return (getEsq().getTipo(ambiente).eInteiro() && getDir().getTipo(ambiente).eInteiro());
    }

    /**
     * Retorna os tipos possiveis desta expressao.
     *
     * @param ambiente o ambiente de compilacao.
     * @return os tipos possiveis desta expressao.
     */
    public Tipo getTipo(AmbienteCompilacao ambiente) {
        return TipoPrimitivo.BOOLEANO;
    }

    @Override
    public ExpBinaria clone() {
        return new ExpGt(esq.clone(), dir.clone());
    }
}
