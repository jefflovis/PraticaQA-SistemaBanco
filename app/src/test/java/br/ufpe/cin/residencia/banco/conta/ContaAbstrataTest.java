package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import br.ufpe.cin.residencia.banco.excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaAbstrataTest {

    private Cliente cliente;

    private Cliente cliente2;
    private ContaAbstrata conta;

    private ContaAbstrata conta2;

    @BeforeEach
    void setup() {
        cliente = new Cliente("124", "João", TipoCliente.CLASS);
        cliente2 = new Cliente("132", "Maria", TipoCliente.CLASS); // Substitua "João" pelo nome do cliente desejado
        conta = new ContaAbstrata("12456", 100.00, cliente);
        conta2 = new ContaAbstrata("00001", 100.00, cliente2);
    }

    @Test
    public void testCreditar() {
        conta.creditar(100.0);
        assertEquals(200.0, conta.getSaldo(), 0.01);
    }

    @Test
    public void testDebitar() throws SaldoInsuficienteException {
        conta.debitar(100.0);
        assertEquals(0.0, conta.getSaldo(), 0.01);
    }


    @Test
    public void testDebitarComSaldoSuficiente() throws SaldoInsuficienteException {

        conta.debitar(50.0);

        assertEquals(50.0, conta.getSaldo(), 0.01);
    }

    @Test
    public void testDebitarComSaldoInsuficiente() {

        assertThrows(SaldoInsuficienteException.class, () -> {
            conta.debitar(150.0);
        });

        assertEquals(100.0, conta.getSaldo(), 0.01);
    }


    @Test
    public void testTransferir() throws SaldoInsuficienteException {

        conta.transferir(conta2, 50.0);
        assertEquals(50.0, conta.getSaldo(), 0.01);
        assertEquals(150.0, conta2.getSaldo(), 0.01);
    }

    @Test
    public void testTransferirComSaldoInsuficiente() throws SaldoInsuficienteException {

        assertThrows(SaldoInsuficienteException.class, () -> {
            conta.transferir(conta2, 150.0);
        });

        // Verifica se os saldos permanecem os mesmos após a tentativa de transferência
        assertEquals(100.0, conta.getSaldo(), 0.01);
        assertEquals(100.0, conta2.getSaldo(), 0.01);
    }


    @Test
    void testGetCliente() {
        assertEquals(cliente, conta.getCliente());
    }

    @Test
    void getNumero() {
        assertEquals("12456", conta.getNumero());
    }

    @Test
    void getSaldo() {
        assertEquals(100.00, conta.getSaldo());
    }

    @Test
    void setCliente() {
        assertEquals(cliente, conta.getCliente(), "O cliente deve ser João");

        conta.setCliente(cliente2);

        assertEquals(cliente2, conta.getCliente(), "Agora o cliente deve ser Maria");
    }

    @Test
    void setNumero() {
        assertEquals("12456", conta.getNumero(), "O número da conta deve ser 12345");

        conta.setNumero("54321");

        assertEquals("54321", conta.getNumero(), "Agora o número da conta deve ser 54321");
    }

    @Test
    void setSaldo() {
        conta.creditar(100.0); // Credita um valor

        // Verifica se o saldo foi definido corretamente ao usar o método creditar
        assertEquals(200.0, conta.getSaldo(), 0.01);

        conta.creditar(50.0); // Credita outro valor

        // Verifica se o saldo foi atualizado corretamente após outra operação de crédito
        assertEquals(250.0, conta.getSaldo(), 0.01);
    }

}