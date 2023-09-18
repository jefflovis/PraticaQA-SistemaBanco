package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaBonificadaTest {

    ContaBonificada contaBonificada;
    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("123", "Leopoldo", TipoCliente.CLASS);
        contaBonificada = new ContaBonificada("12345", cliente);
    }

    @Test
    void creditar() {
        contaBonificada.creditar(100);
        assertEquals(1.0, contaBonificada.getBonus()); // Testa se o bônus foi calculado corretamente

        contaBonificada.creditar(50);
        assertEquals(1.5, contaBonificada.getBonus()); // Testa o acumulo de bônus

        contaBonificada.creditar(-30); // Tentativa de crédito negativo, o bônus não deve ser afetado
        assertEquals(1.2, contaBonificada.getBonus());

        contaBonificada.creditar(0); // Tentativa de crédito de 0, o bônus não deve ser afetado
        assertEquals(1.2, contaBonificada.getBonus());
    }

    @Test
    void renderBonus() {
        contaBonificada.creditar(100); // Gera 1 de bônus
        contaBonificada.renderBonus();
        assertEquals(101.00, contaBonificada.getSaldo()); // Verifica se o bônus foi transferido corretamente para o saldo
        assertEquals(0, contaBonificada.getBonus()); // Verifica se o bônus foi zerado
    }

    @Test
    void getBonus() {
        assertEquals(0, contaBonificada.getBonus()); // Verifica se o bônus inicial é zero
    }
}