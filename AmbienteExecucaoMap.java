import java.util.HashMap;
import java.util.Map;

// Implementação concreta do ambiente de execução usando HashMap
public class AmbienteExecucaoMap implements AmbienteExecucao {
    
    private Map<String, Valor> mapa;
    private AmbienteExecucao pai; // Para suportar escopo aninhado
    
    public AmbienteExecucaoMap() {
        this.mapa = new HashMap<>();
        this.pai = null;
    }
    
    public AmbienteExecucaoMap(AmbienteExecucao pai) {
        this.mapa = new HashMap<>();
        this.pai = pai;
    }
    
    public void add(String id, Valor valor) {
        mapa.put(id, valor);
    }
    
    public Valor get(String id) {
        if (mapa.containsKey(id)) {
            return mapa.get(id);
        }
        if (pai != null) {
            return pai.get(id);
        }
        return null;
    }
}
