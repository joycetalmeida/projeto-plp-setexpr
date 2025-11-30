package lf1.plp.expressions1.util;

import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class TipoInteiro implements Tipo {

    @Override
    public String getNome() {
        return "Inteiro";
    }

    @Override
    public boolean eIgual(Tipo outroTipo) {
        // 1. Compatibilidade com Genéricos (Correção anterior)
        if (outroTipo instanceof lf1.plp.functional1.util.TipoPolimorfico) {
            return true;
        }

        // 2. Compatibilidade Semântica:
        // Aceita qualquer coisa que se identifique como Inteiro,
        // seja um objeto TipoInteiro ou uma constante TipoPrimitivo.INTEIRO.
        return outroTipo.eInteiro();
    }

    // @Override
    // public boolean eIndeterminado() {
    // return false;
    // }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eBooleano() {
        return false;
    }

    @Override
    public boolean eInteiro() {
        return true;
    }

    @Override
    public boolean eString() {
        return false;
    }

    @Override
    public Tipo intersecao(Tipo outroTipo) throws VariavelNaoDeclaradaException {
        if (this.eIgual(outroTipo)) {
            return this;
        }
        throw new VariavelNaoDeclaradaException(new Id("Tipo mismatch in intersection"));
    }

    @Override
    public String toString() {
        return getNome();
    }
}