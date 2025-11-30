package lf1.plp.expressions1.util;

import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.expressions2.expression.Id; // <-- ADICIONADO: Import necessário para o Id

public class TipoConjunto implements Tipo {

    private Tipo tipoInterno;

    public TipoConjunto(Tipo tipoInterno) {
        this.tipoInterno = tipoInterno;
    }

    public Tipo getTipoInterno() {
        return this.tipoInterno;
    }

    @Override
    public String getNome() {
        return "Set<" + tipoInterno.getNome() + ">";
    }

    @Override
    public boolean eIgual(Tipo outroTipo) {
        if (outroTipo instanceof lf1.plp.functional1.util.TipoPolimorfico) {
            return true;
        }
        // 2. Caso contrário, verificamos a igualdade estrita de classe
        if (outroTipo instanceof TipoConjunto) {
            TipoConjunto outroTipoConjunto = (TipoConjunto) outroTipo;

            // Correção: Usar 'instanceof' para verificar TipoIndeterminado
            boolean thisIsIndeterminado = this.tipoInterno instanceof TipoIndeterminado;
            boolean otherIsIndeterminado = outroTipoConjunto.tipoInterno instanceof TipoIndeterminado;

            return thisIsIndeterminado || otherIsIndeterminado ||
                    this.tipoInterno.eIgual(outroTipoConjunto.tipoInterno);
        }
        return false;
    }

    // --- ADICIONADO: Métodos em falta da interface Tipo ---
    // @Override
    // public boolean eIndeterminado() {
    // return false;
    // }

    @Override
    public boolean eValido() {
        return tipoInterno.eValido();
    }

    @Override
    public boolean eBooleano() {
        return false;
    }

    @Override
    public boolean eInteiro() {
        return false;
    }

    @Override
    public boolean eString() {
        return false;
    }
    // --- FIM DA ADIÇÃO ---

    @Override // Agora este método existe na interface
    public Tipo intersecao(Tipo outroTipo) throws VariavelNaoDeclaradaException {
        if (this.eIgual(outroTipo)) {
            return this;
        }
        // --- CORRIGIDO: Passa um new Id() em vez de uma String ---
        throw new VariavelNaoDeclaradaException(new Id("Interseção de tipos de conjunto inválida"));
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public int hashCode() {
        return tipoInterno.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        return this.eIgual((Tipo) obj);
    }
}