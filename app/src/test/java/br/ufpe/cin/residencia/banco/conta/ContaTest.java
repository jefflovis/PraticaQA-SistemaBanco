package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import br.ufpe.cin.residencia.banco.excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    private Cliente cliente;

    private Cliente cliente2;
    private Conta conta;

    private Conta conta2;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("124", "João", TipoCliente.CLASS);
        cliente2 = new Cliente("132", "Maria", TipoCliente.CLASS); // Substitua "João" pelo nome do cliente desejado
        conta = new Conta("12456", 100.00, cliente);
        conta2 = new Conta("00001", 100.00, cliente2);
    }

    @Test
    void testDebitarComSaldoSuficiente() throws SaldoInsuficienteException, SaldoInsuficienteException {
        conta.debitar(50.0);

        assertEquals(50.0, conta.getSaldo(), 0.01);
    }

    @Test
    void testDebitarComSaldoInsuficiente() {
        assertThrows(SaldoInsuficienteException.class, () -> {
            conta.debitar(150.0);
        });

        assertEquals(100.0, conta.getSaldo(), 0.01);
    }
}