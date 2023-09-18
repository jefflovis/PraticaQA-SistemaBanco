package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import br.ufpe.cin.residencia.banco.excecoes.ContaExistenteException;
import br.ufpe.cin.residencia.banco.excecoes.ContaInexistenteException;
import br.ufpe.cin.residencia.banco.excecoes.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CadastroContasTest {

    private CadastroContas cadastroContas;

    @BeforeEach
    public void setUp() {
        IRepositorioContas rep = new RepositorioContasArray();
        cadastroContas = new CadastroContas(rep);
    }

    @Test
    public void testCadastrar() throws ContaExistenteException {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));

        // Testando o cadastro de uma conta
        cadastroContas.cadastrar(conta);

        assertTrue(cadastroContas.existe("123"));
    }

    @Test
    public void testCadastrarContaExistente() throws ContaExistenteException {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));

        try {
            // Cadastrando a conta pela primeira vez
            cadastroContas.cadastrar(conta);
        } catch (ContaExistenteException e) {
            throw new RuntimeException(e);
        }

        // Tentando cadastrar a mesma conta novamente (deve lançar uma exceção)
        assertThrows(ContaExistenteException.class, () -> cadastroContas.cadastrar(conta));
    }

    @Test
    public void testCreditar() throws ContaInexistenteException, ContaExistenteException {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));
        try {
            cadastroContas.cadastrar(conta);
        } catch (ContaExistenteException e) {
            throw new RuntimeException(e);
        }

        // Creditando um valor na conta
        cadastroContas.creditar("123", 100.0);

        assertEquals(100.0, conta.getSaldo(), 0.001);
    }

    @Test
    void testCreditarContaInexistente() {
        assertThrows(ContaInexistenteException.class, () -> cadastroContas.creditar("123", 100.0));
    }

    @Test
    void testDebitar() throws ContaInexistenteException, SaldoInsuficienteException, ContaExistenteException {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));
        try {
            cadastroContas.cadastrar(conta);
        } catch (ContaExistenteException e) {
            throw new RuntimeException(e);
        }

        // Creditando um valor na conta
        cadastroContas.creditar("123", 100.0);

        // Debitando um valor na conta
        cadastroContas.debitar("123", 50.0);

        assertEquals(50.0, conta.getSaldo(), 0.001);
    }

    @Test
    void testDebitarContaInexistente() {
        assertThrows(ContaInexistenteException.class, () -> cadastroContas.debitar("123", 100.0));
    }

    @Test
    void testDebitarSaldoInsuficiente() throws ContaInexistenteException, ContaExistenteException {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));
        try {
            cadastroContas.cadastrar(conta);
        } catch (ContaExistenteException e) {
            throw new RuntimeException(e);
        }

        // Creditando um valor na conta
        cadastroContas.creditar("123", 100.0);

        // Debitando um valor maior que o saldo da conta (deve lançar uma exceção)
        assertThrows(SaldoInsuficienteException.class, () -> cadastroContas.debitar("123", 150.0));
    }

    @Test
    void testProcurar() throws ContaInexistenteException, ContaExistenteException {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));
        try {
            cadastroContas.cadastrar(conta);
        } catch (ContaExistenteException e) {
            throw new RuntimeException(e);
        }

        assertEquals(conta, cadastroContas.procurar("123"));
    }

    @Test
    void testProcurarContaInexistente() {
        assertThrows(ContaInexistenteException.class, () -> cadastroContas.procurar("123"));
    }

    @Test
    void testTransferir() throws ContaInexistenteException, SaldoInsuficienteException, ContaExistenteException {
        ContaAbstrata origem = new ContaAbstrata("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));
        ContaAbstrata destino = new ContaAbstrata("124", new Cliente("123", "Leo", TipoCliente.CLASS));
        cadastroContas.cadastrar(origem);
        cadastroContas.cadastrar(destino);
        cadastroContas.creditar("123", 100.0);

        cadastroContas.transferir("123", "124", 100.0);

//        assertEquals(100.0, origem.getSaldo(), 0.001);
        assertEquals(0.0, destino.getSaldo(), 0.001);
    }


    @Test
    void transferirContaInexistente() {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));
        ContaAbstrata conta2 = new Conta("231", new Cliente("231", "Maria", TipoCliente.CLASS));
        // Tenta transferir de uma conta que não existe
        assertThrows(ContaInexistenteException.class, () -> {
            cadastroContas.transferir("contaInexistente", conta2.getNumero(), 50.0);
        });
    }

    @Test
    void transferirSaldoInsuficiente() throws ContaExistenteException {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));
        ContaAbstrata conta2 = new Conta("231", new Cliente("231", "Maria", TipoCliente.CLASS));
        // Adiciona as contas ao repositório
        cadastroContas.cadastrar(conta);
        cadastroContas.cadastrar(conta2);

        // Tenta transferir um valor maior do que o saldo disponível na conta de origem
        assertThrows(SaldoInsuficienteException.class, () -> {
            cadastroContas.transferir(conta.getNumero(), conta2.getNumero(), 200.0);
        });
    }

    @Test
    void testRemover() throws ContaInexistenteException, ContaExistenteException {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));
        try {
            cadastroContas.cadastrar(conta);
        } catch (ContaExistenteException e) {
            throw new RuntimeException(e);
        }

        cadastroContas.remover("123");

        assertFalse(cadastroContas.existe("123"));
    }

    @Test
    void testRemoverContaInexistente() {
        assertThrows(ContaInexistenteException.class, () -> cadastroContas.remover("123"));
    }

    @Test
    void testAtualizar() throws ContaInexistenteException, ContaExistenteException {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));
        try {
            cadastroContas.cadastrar(conta);
        } catch (ContaExistenteException e) {
            throw new RuntimeException(e);
        }
        conta.setSaldo(100.0);
        cadastroContas.atualizar(conta);
        assertEquals(conta, cadastroContas.procurar(conta.getNumero()));
    }

    @Test
    void testAtualizarContaInexistente() {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));
        assertThrows(ContaInexistenteException.class, () -> cadastroContas.atualizar(conta));
    }

    @Test
    void testExiste() throws ContaExistenteException {
        ContaAbstrata conta = new Conta("123", new Cliente("123", "Leopoldo", TipoCliente.CLASS));
        cadastroContas.cadastrar(conta);
        assertTrue(cadastroContas.existe(conta.getNumero()));
    }

}