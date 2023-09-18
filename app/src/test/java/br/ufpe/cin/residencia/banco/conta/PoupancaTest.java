package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PoupancaTest {

    private Poupanca poupanca;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente("123", "Leopoldo", TipoCliente.CLASS);
        poupanca = new Poupanca("12345", 100, cliente);
    }

    @Test
    void renderJuros_comTaxaValida() {
        poupanca.renderJuros(0.05); // Renderiza juros com uma taxa de 5%
        assertEquals(105.0, poupanca.getSaldo(), 0.001); // Verifica se os juros foram aplicados corretamente
    }

    @Test
    void renderJuros_comTaxaZero() {
        poupanca.renderJuros(0); // Renderiza juros com uma taxa de 0%
        assertEquals(100.0, poupanca.getSaldo(), 0.001); // Verifica se o saldo permanece o mesmo
    }
}
