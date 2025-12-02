public class ExpLetComposta implements Expressao {
  private String id;
  private Expressao valor;
  private Expressao corpo;

  public ExpLetComposta(String id, Expressao valor, Expressao corpo) {
    this.id = id;
    this.valor = valor;
    this.corpo = corpo;
  }

  public Valor avaliar(AmbienteExecucao amb) {
    Valor v = valor.avaliar(amb);
    AmbienteExecucao novoAmb = new AmbienteExecucaoMap(amb);
    novoAmb.add(id, v);
    return corpo.avaliar(novoAmb);
  }

  public boolean checaTipo(AmbienteCompilacao amb) {
    if (!valor.checaTipo(amb)) return false;
    Tipo tvalor = valor.getTipo(amb);
    AmbienteCompilacao novoAmb = new AmbienteCompilacaoMap(amb);
    novoAmb.add(id, tvalor);
    return corpo.checaTipo(novoAmb);
  }

  public Tipo getTipo(AmbienteCompilacao amb) {
    Tipo tvalor = valor.getTipo(amb);
    AmbienteCompilacao novoAmb = new AmbienteCompilacaoMap(amb);
    novoAmb.add(id, tvalor);
    return corpo.getTipo(novoAmb);
  }
}
