public class ExpNegacao implements Expressao {
  private Expressao exp;

  public ExpNegacao(Expressao e) {
    this.exp = e;
  }

  public Valor avaliar(AmbienteExecucao amb) {
    Valor v = exp.avaliar(amb);
    
    if (v instanceof ValorInteiro) {
      int i = ((ValorInteiro) v).getValor();
      return new ValorInteiro(-i);
    }
    return null;
  }

  public boolean checaTipo(AmbienteCompilacao amb) {
    return exp.checaTipo(amb) && exp.getTipo(amb) instanceof TipoInteiro;
  }

  public Tipo getTipo(AmbienteCompilacao amb) {
    return TipoInteiro.getInstancia();
  }
}
