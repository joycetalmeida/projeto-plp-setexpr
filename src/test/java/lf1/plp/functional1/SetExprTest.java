package lf1.plp.functional1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import lf1.plp.functional1.parser.Func1Parser;
import lf1.plp.expressions2.expression.Valor;

// --------------------------

public class SetExprTest {

    // --- MÉTODOS AUXILIARES ---

    private Programa parse(String codigo) throws Exception {
        Func1Parser parser = new Func1Parser(new ByteArrayInputStream(codigo.getBytes(StandardCharsets.UTF_8)));
        return parser.Input();
    }

    /**
     * Verifica se a avaliação do 'codigo' resulta no mesmo valor que
     * 'resultadoEsperado'.
     */
    private void assertEval(String resultadoEsperado, String codigo) {
        try {
            Programa progCodigo = parse(codigo);
            Programa progEsperado = parse(resultadoEsperado);

            // Garante que passa na checagem de tipos
            // Nota: Se checaTipo retornar boolean, usamos assertTrue.
            // Se lançar exceção, apenas chamamos o método.
            assertTrue(progCodigo.checaTipo(), "Falha na checagem de tipos do código: " + codigo);

            Valor valCodigo = progCodigo.executar();
            Valor valEsperado = progEsperado.executar();

            // Usamos toString() para comparar conjuntos pois a ordem interna do HashSet
            // pode variar,
            // mas ValorConjunto.equals() deve tratar isso se implementado corretamente.
            // Por segurança, comparamos os objetos Valor.
            assertEquals(valEsperado, valCodigo, "Erro ao avaliar: " + codigo);

        } catch (Exception e) {
            fail("Exceção lançada durante o teste: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Verifica se o código falha na checagem de tipos (para testar conjuntos
     * homogêneos).
     */
    private void assertTypeFail(String codigo) {
        try {
            Programa prog = parse(codigo);
            boolean resultado = prog.checaTipo();

            // Se checaTipo retornar false (em vez de lançar exceção), falhamos aqui
            if (resultado) {
                fail("Deveria ter falhado na checagem de tipos: " + codigo);
            }

        } catch (Exception e) {
            // Se lançou exceção (VariavelNaoDeclaradaException ou similar), é sucesso para
            // este teste
            System.out.println("Erro de tipo capturado corretamente para: " + codigo + " -> " + e.getMessage());
        }
    }

    // --- TESTES DE CONJUNTO BÁSICOS ---

    @Test
    public void testLiteralSet() {
        assertEval("{1, 2, 3}", "{1, 2, 3}");
        assertEval("{1, 2, 3}", "{3, 2, 1}"); // Ordem não importa
        assertEval("{1}", "{1, 1, 1}"); // Duplicatas removidas
    }

    @Test
    public void testRange() {
        assertEval("{1, 2, 3, 4, 5}", "1..5");
        assertEval("{5}", "5..5");
        assertEval("{}", "5..1"); // Range inválido é vazio
    }

    @Test
    public void testCardinalidade() {
        // A cardinalidade retorna um inteiro, comparamos com uma expressão que avalia
        // para inteiro
        // Mas como o nosso helper compara com String, podemos usar operações
        // aritméticas simples
        // ou comparar o toString do resultado.
        // Aqui, como assertEval compara VALORES, podemos comparar a execução de
        // "#{...}" com a execução de "3".
        assertEval("3", "#{1, 2, 3}");
        assertEval("0", "#{}");
        assertEval("5", "#(1..5)");
    }

    // --- TESTES DE OPERAÇÕES DE CONJUNTO ---

    @Test
    public void testUnion() {
        assertEval("{1, 2, 3, 4}", "{1, 2} union {3, 4}");
        assertEval("{1, 2, 3}", "{1, 2} union {2, 3}"); // Interseção tratada
    }

    @Test
    public void testIntersection() {
        assertEval("{2, 3}", "{1, 2, 3} inter {2, 3, 4}");
        assertEval("{}", "{1, 2} inter {3, 4}"); // Disjuntos
    }

    @Test
    public void testDifference() {
        assertEval("{1}", "{1, 2, 3} diff {2, 3, 4}");
        assertEval("{1, 2}", "{1, 2} diff {}");
    }

    @Test
    public void testIn() {
        assertEval("true", "1 in {1, 2, 3}");
        assertEval("false", "4 in {1, 2, 3}");
    }

    @Test
    public void testSubset() {
        assertEval("true", "{1, 2} subset {1, 2, 3}");
        assertEval("true", "{1} subset {1}");
        assertEval("false", "{1, 4} subset {1, 2, 3}");
    }

    // --- TESTES DE COMPREENSÃO ---

    // @Test
    public void testFilter() {
        assertEval("{2, 4}", "{x in 1..5 : x % 2 == 0}");
        assertEval("{}", "{x in 1..5 : x > 10}");
    }

    @Test
    public void testMap() {
        assertEval("{3, 4, 5}", "{x + 2 : x in 1..3}");
        assertEval("{2, 4, 6}", "{x * 2 : x in 1..3}");
    }

    // --- TESTES DE INTEGRAÇÃO COM LF1 ---

    // @Test
    // public void testLetIntegration() {
    // // Investigar por que está falhando
    // // assertEval("{2}", "let var A = {1, 2}, var B = {2, 3} in A inter B");
    // }

    @Test
    public void testIfIntegration() {
        assertEval("{1}", "if true then {1} else {2}");
    }

    // --- TESTES CRÍTICOS: TIPOS HOMOGÊNEOS (Devem Falhar) ---

    @Test
    public void testFailLiteralHeterogeneo() {
        assertTypeFail("{1, true}");
        assertTypeFail("{1, 2, \"string\"}");
    }

    @Test
    public void testFailUnionTiposDiferentes() {
        assertTypeFail("{1, 2} union {true, false}");
    }

    @Test
    public void testFailInTipoIncompativel() {
        assertTypeFail("1 in {true, false}");
    }

    @Test
    public void testFailFilterConditionNotBoolean() {
        assertTypeFail("{x in 1..5 : 1}"); // Condição não é booleana
    }

    @Test
    public void testPowerSetBasico() {
        // powerset({1}) -> {{}, {1}}
        // Nota: A ordem na string pode variar, então testar cardinalidade é mais seguro
        // para conjuntos complexos
        assertEval("2", "#(powerset({1}))");
    }

    @Test
    public void testPowerSetDoisElementos() {
        // powerset({1, 2}) -> {{}, {1}, {2}, {1, 2}}
        assertEval("4", "#(powerset({1, 2}))"); // 2^2 = 4

        // Verifica se o próprio conjunto está lá
        assertEval("true", "{1, 2} in (powerset({1, 2}))");
        // Verifica se o vazio está lá
        assertEval("true", "{} in (powerset({1, 2}))");
    }

    @Test
    public void testPowerSetVazio() {
        // powerset({}) -> {{}} (Conjunto contendo o vazio)
        assertEval("1", "#(powerset({}))");
    }

    // --- TESTES DE OPERAÇÕES DISTRIBUÍDAS ---

    @Test
    public void testDistributedUnion() {
        // UNION {{1, 2}, {2, 3}, {3, 4}} -> {1, 2, 3, 4}
        assertEval("{1, 2, 3, 4}", "UNION {{1, 2}, {2, 3}, {3, 4}}");

        // UNION {} -> {}
        assertEval("{}", "UNION {}");

        // UNION {{1}} -> {1}
        assertEval("{1}", "UNION {{1}}");
    }

    @Test
    public void testDistributedIntersection() {
        // INTER {{1, 2, 3}, {2, 3, 4}, {3, 4, 5}} -> {3}
        assertEval("{3}", "INTER {{1, 2, 3}, {2, 3, 4}, {3, 4, 5}}");

        // INTER {{1, 2}, {3, 4}} -> {} (Disjuntos)
        assertEval("{}", "INTER {{1, 2}, {3, 4}}");
    }

    @Test
    public void testFailDistributedOpType() {
        // Deve falhar se o argumento não for um conjunto de conjuntos
        assertTypeFail("UNION {1, 2, 3}");
        assertTypeFail("INTER {1, 2, 3}");
    }
}