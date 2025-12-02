import java.util.HashMap;
import java.util.Map;

// Implementação concreta do ambiente de compilação usando HashMap
public class AmbienteCompilacaoMap implements AmbienteCompilacao {
    
    private Map<String, Tipo> mapa;
    private AmbienteCompilacao pai; // Para suportar escopo aninhado
    
    public AmbienteCompilacaoMap() {
        this.mapa = new HashMap<>();
        this.pai = null;
    }
    
    public AmbienteCompilacaoMap(AmbienteCompilacao pai) {
        this.mapa = new HashMap<>();
        this.pai = pai;
    }
    
    public void add(String id, Tipo tipo) {
        mapa.put(id, tipo);
    }
    
    public Tipo get(String id) {
        if (mapa.containsKey(id)) {
            return mapa.get(id);
        }
        if (pai != null) {
            return pai.get(id);
        }
        return null;
    }
}
