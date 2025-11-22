package lf1.plp.expressions1.util;

import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;

public class TipoBooleano implements Tipo {

    @Override
    public String getNome() {
        return "Booleano";
    }

    @Override
    public boolean eIgual(Tipo outroTipo) {
        return outroTipo instanceof TipoBooleano;
    }

    // @Override
    // public boolean eIndeterminado() {
    //     return false;
    // }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eBooleano() {
        return true;
    }

    @Override
    public boolean eInteiro() {
        return false;
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