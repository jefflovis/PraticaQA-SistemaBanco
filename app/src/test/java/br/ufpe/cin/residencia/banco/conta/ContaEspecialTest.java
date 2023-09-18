package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaEspecialTest {

    private ContaEspecial contaEspecial;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("123", "Leopoldo", TipoCliente.ESPECIAL);
        contaEspecial = new ContaEspecial("67890", 100, cliente, 200);
    }

    @Test
    void getSaldo() {
        // Verifica se o saldo inclui o valor do saldo normal mais o cheque especial
        assertEquals(300.0, contaEspecial.getSaldo());
    }

    @Test
    void constructorWithCredit() {
        // Verifica se o saldo inicial e o cheque especial são configurados corretamente
        assertEquals(300.0, contaEspecial.getSaldo());
        assertEquals(200.0, contaEspecial.getValorChequeEspecial());
    }

    @Test
    void constructorWithoutCredit() {
        // Testa o construtor sem valor de cheque especial
        ContaEspecial contaSemChequeEspecial = new ContaEspecial("12345", 50, new Cliente("134", "José", TipoCliente.ESPECIAL));
        assertEquals(50.0, contaSemChequeEspecial.getSaldo());
        assertEquals(0.0, contaSemChequeEspecial.getValorChequeEspecial());
    }

    @Test
    void constructorWithDefaultValues() {
        // Testa o construtor padrão (sem saldo e sem cheque especial)
        ContaEspecial contaSemSaldo = new ContaEspecial("54321", new Cliente("312", "Miranda", TipoCliente.ESPECIAL));
        assertEquals(0.0, contaSemSaldo.getSaldo());
        assertEquals(0.0, contaSemSaldo.getValorChequeEspecial());
    }
}
