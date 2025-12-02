public class ExpIf implements Expressao {
  private Expressao condicao;
  private Expressao entao;
  private Expressao senao;

  public ExpIf(Expressao cond, Expressao e1, Expressao e2) {
    this.condicao = cond;
    this.entao = e1;
    this.senao = e2;
  }

  public Valor avaliar(AmbienteExecucao amb) {
    Valor v = condicao.avaliar(amb);
    
    if (!(v instanceof ValorBooleano)) {
      return null;
    }
    
    boolean b = ((ValorBooleano) v).getValor();
    return b ? entao.avaliar(amb) : senao.avaliar(amb);
  }

  public boolean checaTipo(AmbienteCompilacao amb) {
    if (!condicao.checaTipo(amb)) return false;
    Tipo tcond = condicao.getTipo(amb);
    if (!(tcond instanceof TipoBooleano)) return false;
    
    if (!entao.checaTipo(amb)) return false;
    if (!senao.checaTipo(amb)) return false;
    
    Tipo t1 = entao.getTipo(amb);
    Tipo t2 = senao.getTipo(amb);
    
    return t1.eIgual(t2);
  }

  public Tipo getTipo(AmbienteCompilacao amb) {
    return entao.getTipo(amb);
  }
}
