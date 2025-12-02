// Interface que representa o ambiente durante a execução do programa.
// Guarda o valor das variáveis.
public interface AmbienteExecucao {
    void add(String id, Valor valor);
    Valor get(String id);
}
