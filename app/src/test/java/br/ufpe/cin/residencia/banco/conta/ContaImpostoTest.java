package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import br.ufpe.cin.residencia.banco.excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaImpostoTest {

    private ContaImposto contaImposto;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("123", "Leopoldo", TipoCliente.CLASS);
        contaImposto = new ContaImposto("12345", 100, cliente);
    }

    @Test
    void debitar_valorMenorQueSaldo() throws SaldoInsuficienteException {
        contaImposto.debitar(50);
        assertEquals(49.95, contaImposto.getSaldo(), 0.001); // Verifica se o débito e o imposto foram aplicados corretamente
    }

    @Test
    void debitar_valorIgualASaldo() throws SaldoInsuficienteException {
        contaImposto.debitar(99.9);
        assertEquals(0, contaImposto.getSaldo(), 0.001); // Verifica se o débito e o imposto foram aplicados corretamente
    }

    @Test
    void debitar_valorMaiorQueSaldo() {
        assertThrows(SaldoInsuficienteException.class, () -> contaImposto.debitar(200)); // Verifica se é lançada a exceção de saldo insuficiente
        assertEquals(100, contaImposto.getSaldo(), 0.001); // Verifica se o saldo permanece o mesmo após a tentativa de débito inválida
    }

}

