package lf1.plp.functional1.expression;

import java.util.HashMap;
import java.util.Map;

import lf1.plp.expressions1.util.Tipo;
import lf1.plp.expressions2.expression.Expressao;
import lf1.plp.expressions2.expression.Id;
import lf1.plp.expressions2.expression.Valor;
import lf1.plp.expressions2.memory.AmbienteCompilacao;
import lf1.plp.expressions2.memory.AmbienteExecucao;
import lf1.plp.expressions2.memory.ContextoCompilacao;
import lf1.plp.expressions2.memory.VariavelJaDeclaradaException;
import lf1.plp.expressions2.memory.VariavelNaoDeclaradaException;
import lf1.plp.functional1.declaration.DeclaracaoFuncional;
import lf1.plp.functional1.memory.AmbienteExecucaoFuncional;
import lf1.plp.functional1.memory.ContextoExecucaoFuncional;
import lf1.plp.functional1.util.DefFuncao;

public class ExpDeclaracao implements Expressao {

	DeclaracaoFuncional declaracao;
	Expressao expressao;

	public ExpDeclaracao(
			DeclaracaoFuncional declaracao,
			Expressao expressao) {
		this.declaracao = declaracao;
		this.expressao = expressao;
	}

	/**
	 * Returns the expressao.
	 * 
	 * @return Expressao
	 */
	public Expressao getExpressao() {
		return expressao;
	}

	/**
	 * Retorna uma representacao String desta expressao. Util para depuracao.
	 * 
	 * @return uma representacao String desta expressao.
	 */
	/*
	 * @Override
	 * public String toString() {
	 * return String.format("let %s in %s",
	 * listToString(seqdecFuncional, ","), expressao);
	 * }
	 */

	public Valor avaliar(AmbienteExecucao ambienteFuncional)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		AmbienteExecucaoFuncional amb = (AmbienteExecucaoFuncional) ambienteFuncional;
		amb.incrementa();
		AmbienteExecucaoFuncional aux = new ContextoExecucaoFuncional();
		aux.incrementa();
		declaracao.elabora(amb, aux);
		declaracao.incluir(amb, aux);
		aux.restaura();
		Valor vresult = expressao.avaliar(amb);
		amb.restaura();
		return vresult;
	}

	/**
	 * Realiza a verificacao de tipos desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return <code>true</code> se os tipos da expressao sao validos;
	 *         <code>false</code> caso contrario.
	 * @exception VariavelNaoDeclaradaException
	 *                                          se existir um identificador nao
	 *                                          declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException
	 *                                          se existir um identificador
	 *                                          declarado mais de uma vez no
	 *                                          mesmo bloco do ambiente.
	 */
	@Override
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		// Escopo 1: Contém a definição "rascunho" (genérica) para permitir recursão
		ambiente.incrementa();

		boolean result = false;

		try {
			// 1. Registra isPrime (Generic) no Escopo 1
			declaracao.elabora(ambiente, ambiente);

			// 2. Checa o corpo da função.
			// Ele vê o isPrime do Escopo 1. O compilador infere os tipos aqui.
			System.out.println("[DIAGNOSTICO] 1. Elaboração inicial concluída.");
			result = declaracao.checaTipo(ambiente);
			System.out.println("[DIAGNOSTICO] 2. Declaração checaTipo retornou: " + result);

			if (result) {
				// --- A MÁGICA DO SOMBREAMENTO ---

				// 3. Criamos o Escopo 2.
				ambiente.incrementa();

				try {

					// 4. Registra isPrime (Concreto: Int->Bool) no Escopo 2.
					// Isso não dá erro de "Já Declarada" porque é um novo escopo.
					// Esta versão ESCONDE a versão do Escopo 1.
					declaracao.incluir(ambiente, ambiente);

					try {
						lf1.plp.expressions2.expression.Id idBusca = new lf1.plp.expressions2.expression.Id("isPrime");
						Object tipoNoAmbiente = ambiente.get(idBusca);
						System.out.println(
								"[DIAGNOSTICO] 3. Tipo efetivo gravado no ambiente para 'isPrime': " + tipoNoAmbiente);
					} catch (Exception e) {
						System.out.println("[DIAGNOSTICO] 3. ERRO: 'isPrime' não encontrada no ambiente após incluir!");
					}

					System.out.println("[DIAGNOSTICO] 5. Iniciando checagem do corpo IN...");
					// 5. O corpo do IN executa olhando para o Escopo 2 (a versão correta).
					result = expressao.checaTipo(ambiente);
					System.out.println("[DIAGNOSTICO] 6. Checagem do corpo retornou: " + result);

				} catch (Exception e) {
					System.out.println("[DIAGNOSTICO] EXCEÇÃO INTERNA: " + e.getMessage());
					e.printStackTrace();
					throw e;
				} finally {
					// Remove Escopo 2
					ambiente.restaura();
				}
			}

		} finally {
			// Remove Escopo 1
			ambiente.restaura();
		}

		return result;
	}

	/**
	 * Retorna os tipos possiveis desta expressao.
	 * 
	 * @param amb
	 *            o ambiente de compila��o.
	 * @return os tipos possiveis desta expressao.
	 * @exception VariavelNaoDeclaradaException
	 *                                          se existir um identificador nao
	 *                                          declarado no ambiente.
	 * @exception VariavelNaoDeclaradaException
	 *                                          se existir um identificador
	 *                                          declarado mais de uma vez no
	 *                                          mesmo bloco do ambiente.
	 * @precondition this.checaTipo();
	 */
	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		AmbienteCompilacao aux = new ContextoCompilacao();
		aux.incrementa();
		declaracao.elabora(ambiente, aux);
		declaracao.incluir(ambiente, aux);
		aux.restaura();
		Tipo vresult = expressao.getTipo(ambiente);
		ambiente.restaura();
		return vresult;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		// TODO Auto-generated method stub
		return null;
	}

	public ExpDeclaracao clone() {
		return new ExpDeclaracao(declaracao.clone(), this.expressao.clone());
	}

}
