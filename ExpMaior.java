public class ExpMaior implements Expressao {
  private Expressao exp1;
  private Expressao exp2;

  public ExpMaior(Expressao e1, Expressao e2) {
    this.exp1 = e1;
    this.exp2 = e2;
  }

  public Valor avaliar(AmbienteExecucao amb) {
    Valor v1 = exp1.avaliar(amb);
    Valor v2 = exp2.avaliar(amb);
    
    if (v1 instanceof ValorInteiro && v2 instanceof ValorInteiro) {
      int i1 = ((ValorInteiro) v1).getValor();
      int i2 = ((ValorInteiro) v2).getValor();
      return new ValorBooleano(i1 > i2);
    }
    return null;
  }

  public boolean checaTipo(AmbienteCompilacao amb) {
    return exp1.checaTipo(amb) && exp2.checaTipo(amb) &&
           exp1.getTipo(amb) instanceof TipoInteiro &&
           exp2.getTipo(amb) instanceof TipoInteiro;
  }

  public Tipo getTipo(AmbienteCompilacao amb) {
    return TipoBooleano.getInstancia();
  }
}
