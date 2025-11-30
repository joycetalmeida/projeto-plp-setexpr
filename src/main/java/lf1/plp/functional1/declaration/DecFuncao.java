package lf1.plp.functional1.declaration;

import static lf1.plp.expressions1.util.ToStringProvider.listToString;
import java.util.ArrayList;
import java.util.List;
import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.memory.AmbienteExecucaoFuncional;
import lf1.plp.functional1.util.DefFuncao;
import lf1.plp.functional1.util.TipoFuncao;
import lf1.plp.functional1.util.TipoPolimorfico;

public class DecFuncao implements DeclaracaoFuncional {

	private Id id;
	private DefFuncao funcao;

	public DecFuncao(Id idFun, List<Id> argsId, Expressao exp) {
		this.id = idFun;
		this.funcao = new DefFuncao(argsId, exp);
	}

	public Id getId() {
		return id;
	}

	public List<Id> getListaId() {
		return funcao.getListaId();
	}

	public Expressao getExpressao() {
		return funcao.getExp();
	}

	public int getAridade() {
		return funcao.getAridade();
	}

	public DefFuncao getFuncao() {
		return funcao;
	}

	@Override
	public String toString() {
		return String.format("fun %s (%s) = %s", id, listToString(funcao.getListaId(), ","), funcao.getExp());
	}

	// --- CORREÇÃO 1: Checagem manual para garantir persistência ---
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		// 1. Mapeia a própria função com genéricos (para recursão)
		List<Tipo> paramsGen = new ArrayList<>(getAridade());
		for (int i = 0; i < getAridade(); i++)
			paramsGen.add(new TipoPolimorfico());
		ambiente.map(id, new TipoFuncao(paramsGen, new TipoPolimorfico()));

		// 2. Mapeia os argumentos
		// Nota: O DefFuncao.checaTipo faz isso, mas delegar cegamente pode esconder
		// erros.
		boolean result = funcao.checaTipo(ambiente);

		ambiente.restaura();
		return result;
	}

	// --- CORREÇÃO 2: O método CRUCIAL ---
	// Este método precisa retornar o tipo CONCRETO (ex: int -> bool), não genérico.
	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		amb.incrementa();

		// 1. Criamos tipos polimórficos para os argumentos
		List<Tipo> tiposArgumentos = new ArrayList<>();
		for (int i = 0; i < getAridade(); i++) {
			tiposArgumentos.add(new TipoPolimorfico());
		}

		// 2. Mapeia a função (recursão)
		Tipo tipoFuncaoRecursao = new TipoFuncao(new ArrayList<>(tiposArgumentos), new TipoPolimorfico());
		amb.map(id, tipoFuncaoRecursao);

		// 3. Mapeia os argumentos no ambiente
		// IMPORTANTE: Guardamos a referência da lista 'tiposArgumentos'
		List<Id> idsArgs = getListaId();
		for (int i = 0; i < getAridade(); i++) {
			amb.map(idsArgs.get(i), tiposArgumentos.get(i));
		}

		// 4. Requisita o tipo da expressão do corpo (Isso força a inferência!)
		// Ao rodar isso, o compilador vai atualizar os objetos dentro de
		// 'tiposArgumentos'
		// ex: O TipoPolimorfico que era vazio vira um TipoInteiro (devido a n < 2)
		Tipo tipoRetorno = funcao.getExp().getTipo(amb);

		// 5. DEBUG: Isso vai te mostrar no console o que ele descobriu
		// System.out.println("DEBUG DecFuncao: Args=" + tiposArgumentos + " Ret=" +
		// tipoRetorno);

		// 6. Constrói o tipo da função usando os argumentos AGORA ATUALIZADOS
		TipoFuncao tipoFinal = new TipoFuncao(tiposArgumentos, tipoRetorno);

		amb.restaura();
		return tipoFinal;
	}

	// --- MÉTODOS DE ELABORAÇÃO/INCLUSÃO (Mantidos simples) ---

	public void elabora(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException {
		// Cria versão genérica inicial
		List<Tipo> params = new ArrayList<>();
		for (int i = 0; i < getAridade(); i++)
			params.add(new TipoPolimorfico());
		aux.map(getId(), new TipoFuncao(params, new TipoPolimorfico()));
	}

	public void incluir(AmbienteCompilacao amb, AmbienteCompilacao aux) throws VariavelJaDeclaradaException {
		// Usa o getTipo(amb) que acabamos de corrigir para pegar a versão concreta
		try {
			Tipo tipoConcreto = this.getTipo(amb);
			amb.map(getId(), tipoConcreto);
		} catch (VariavelNaoDeclaradaException e) {
			e.printStackTrace();
		}
	}

	// Métodos de execução (sem alteração de lógica, apenas delegate)
	public void elabora(AmbienteExecucaoFuncional amb, AmbienteExecucaoFuncional aux)
			throws VariavelJaDeclaradaException {
		aux.mapFuncao(getId(), getFuncao());
	}

	public void incluir(AmbienteExecucaoFuncional amb, AmbienteExecucaoFuncional aux)
			throws VariavelJaDeclaradaException {
		amb.mapFuncao(getId(), aux.getFuncao(getId()));
	}

	public DecFuncao clone() {
		DefFuncao aux = this.funcao.clone();
		return new DecFuncao(this.id.clone(), aux.getListaId(), aux.getExp());
	}
}