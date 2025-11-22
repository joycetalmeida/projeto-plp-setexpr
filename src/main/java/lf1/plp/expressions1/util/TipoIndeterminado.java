package lf1.plp.expressions1.util;

import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.expressions2.expression.Id; // <-- ADICIONADO: Import necessário

public class TipoIndeterminado implements Tipo {

    @Override
    public String getNome() {
        return "Indeterminado";
    }

    @Override
    public boolean eIgual(Tipo outroTipo) {
        return true; // Indeterminado é igual a tudo
    }

    // @Override
    // public boolean eIndeterminado() {
    //     return true;
    // }
    
    // --- ADICIONADO: Métodos em falta da interface Tipo ---
    @Override
    public boolean eValido() {
        return true; // Tipo indeterminado é considerado válido
    }

    @Override
    public boolean eBooleano() { return false; }

    @Override
    public boolean eInteiro() { return false; }
    
    @Override
    public boolean eString() { return false; }

    @Override
    public Tipo intersecao(Tipo outroTipo) throws VariavelNaoDeclaradaException {
        return outroTipo; // Interseção de Indeterminado com T é T
    }
    // --- FIM DA ADIÇÃO ---
    
    @Override
    public String toString() {
        return getNome();
    }
    
    @Override
    public int hashCode() {
        return getNome().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj != null && getClass() == obj.getClass();
    }
}