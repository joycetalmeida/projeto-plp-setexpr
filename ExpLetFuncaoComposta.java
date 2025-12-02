import java.util.*;

public class ExpLetFuncaoComposta implements Expressao {
  private String id;
  private List<Token> params;
  private Expressao valor;
  private Expressao corpo;

  public ExpLetFuncaoComposta(String id, List<Token> params, Expressao valor, Expressao corpo) {
    this.id = id;
    this.params = params;
    this.valor = valor;
    this.corpo = corpo;
  }

  public Valor avaliar(AmbienteExecucao amb) {
    // Criar uma função curried
    Expressao funcao = valor;
    for (int i = params.size() - 1; i >= 0; i--) {
      String param = params.get(i).image;
      funcao = new ExpFuncao(param, funcao);
    }
    
    Valor vfuncao = funcao.avaliar(amb);
    AmbienteExecucao novoAmb = new AmbienteExecucaoMap(amb);
    novoAmb.add(id, vfuncao);
    return corpo.avaliar(novoAmb);
  }

  public boolean checaTipo(AmbienteCompilacao amb) {
    return true; // Simplificado
  }

  public Tipo getTipo(AmbienteCompilacao amb) {
    AmbienteCompilacao ambFunc = new AmbienteCompilacaoMap(amb);
    for (Token param : params) {
      ambFunc.add(param.image, TipoInteiro.getInstancia());
    }
    
    Tipo tvalor = valor.getTipo(ambFunc);
    
    AmbienteCompilacao novoAmb = new AmbienteCompilacaoMap(amb);
    novoAmb.add(id, tvalor);
    return corpo.getTipo(novoAmb);
  }
}
