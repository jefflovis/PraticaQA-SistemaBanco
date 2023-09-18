package br.ufpe.cin.residencia.banco.fachada;

import java.lang.reflect.Field;

import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.conta.*;
import br.ufpe.cin.residencia.banco.excecoes.*;


class FachadaTest {

    Fachada fachada;

    @BeforeEach
    void setUp() {
        fachada = Fachada.obterInstancia();
    }

    @AfterEach
    void tearDown() {
        try {
            Field instance = Fachada.class.getDeclaredField("instancia");
            instance.setAccessible(true);
            instance.set(null, null);
            fachada = null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void obterInstanciaFachada() {
        Fachada f2 = Fachada.obterInstancia();
        assertEquals(fachada, f2);
    }

    @Test
    void atualizarCliente() {
        Cliente cliente = new Cliente("243", "Elton", TipoCliente.CLASS);
        try {
            fachada.cadastrar(cliente);
            cliente.setNome("João da Silva");
            fachada.atualizar(cliente);
            Cliente clienteAtualizado = fachada.procurarCliente("243");
            assertEquals("João da Silva", clienteAtualizado.getNome());
        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }

    @Test
    void atualizarClienteInexistente() {
        Cliente cliente = new Cliente("243", "Elton", TipoCliente.CLASS);
        try {
            fachada.atualizar(cliente);
            fail("Exceção esperada: " + ClienteInexistenteException.class);
        } catch (ClienteInexistenteException e) {
            assertEquals("Cliente inexistente!", e.getMessage());
        }
    }

    @Test
    void procurarCliente() {
        Cliente cliente = new Cliente("321", "Maria", TipoCliente.CLASS);
        try {
            fachada.cadastrar(cliente);
            Cliente clienteProcurado = fachada.procurarCliente("321");
            assertNotNull(clienteProcurado);
        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }

    @Test
    void procurarClienteInexistente() {
        try {
            fachada.procurarCliente("321");
            fail("Exceção esperada: " + ClienteInexistenteException.class);
        } catch (ClienteInexistenteException e) {
            assertEquals("Cliente inexistente!", e.getMessage());
        }
    }

    @Test
    void cadastrarCliente() {
        Cliente cliente = new Cliente("321", "Maria", TipoCliente.CLASS);
        try {
            fachada.cadastrar(cliente);
            Cliente clienteCadastrado = fachada.procurarCliente("321");
            assertNotNull(clienteCadastrado);
        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }

    @Test
    void cadastrarClienteExistente() {
        Cliente cliente = new Cliente("321", "Maria", TipoCliente.CLASS);
        try {
            fachada.cadastrar(cliente);
            fachada.cadastrar(cliente);
            fail("Exceção esperada: " + ClienteExistenteException.class);
        } catch (ClienteExistenteException e) {
            assertEquals("Cliente ja existe!", e.getMessage());
        }
    }

    @Test
    void descadastrarCliente() {
        Cliente cliente = new Cliente("333", "Leandro", TipoCliente.CLASS);
        try {
            fachada.cadastrar(cliente);
            fachada.descadastrarCliente("333");
            assertThrows(ClienteInexistenteException.class, () -> fachada.procurarCliente("555555555"));
        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }

    @Test
    void procurarConta() {
        try {
            Cliente cliente = new Cliente("123", "Joãozinho", TipoCliente.CLASS);
            fachada.cadastrar(cliente);
            ContaAbstrata conta = new Conta("123", fachada.procurarCliente("123"));
            fachada.cadastrar(conta);
            ContaAbstrata contaProcurada = fachada.procurarConta("123");
            assertNotNull(contaProcurada);
        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }

    @Test
    void procurarContaInexistente() {
        try {
            fachada.procurarConta("123");
            fail("Exceção esperada: " + ContaInexistenteException.class);
        } catch (ContaInexistenteException e) {
            assertEquals("Conta Inexistente!", e.getMessage());
        }
    }

    @Test
    void cadastrarConta() {
        try {
            Cliente cliente = new Cliente("233", "Pedro", TipoCliente.CLASS);
            fachada.cadastrar(cliente);
            ContaAbstrata conta = new Conta("233", cliente);
            fachada.cadastrar(conta);
            ContaAbstrata contaCadastrada = fachada.procurarConta("233");
            assertNotNull(contaCadastrada);
        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }


    @Test
    void descadastrarConta() throws ClienteExistenteException, ContaInexistenteException, ContaExistenteException, ClienteInexistenteException, ClienteInvalidoException {

        Cliente cliente = new Cliente("111", "Ana", TipoCliente.CLASS);
        fachada.cadastrar(cliente);
        ContaAbstrata conta = new Conta("111", cliente);
        fachada.cadastrar(conta);
        fachada.descadastrarConta("111");
        assertThrows(ContaInexistenteException.class, () -> fachada.procurarConta("111"));

    }

    @Test
    void descadastrarContaInexistente() {
        try {
            fachada.descadastrarConta("111");
            fail("Exceção esperada: " + ContaInexistenteException.class);
        } catch (ContaInexistenteException e) {
            assertEquals("Conta Inexistente!", e.getMessage());
        }
    }

    @Test
    void creditar() {
        try {
            Cliente cliente = new Cliente("111", "Ana", TipoCliente.CLASS);
            fachada.cadastrar(cliente);
            ContaAbstrata conta = new Conta("111", cliente);
            fachada.cadastrar(conta);
            fachada.creditar("111", 100.0);
            ContaAbstrata contaAtualizada = fachada.procurarConta("111");
            assertEquals(100.0, contaAtualizada.getSaldo(), 0.001);
        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }

    @Test
    void creditarContaInexistente() {
        try {
            fachada.creditar("111", 100.0);
            fail("Exceção esperada: " + ContaInexistenteException.class);
        } catch (ContaInexistenteException e) {
            assertEquals("Conta Inexistente!", e.getMessage());
        }
    }


    @Test
    void debitar() {
        try {
            Cliente cliente = new Cliente("133", "Rafael", TipoCliente.CLASS);
            fachada.cadastrar(cliente);
            ContaAbstrata conta = new Conta("77777", 500.0, cliente);
            fachada.cadastrar(conta);
            fachada.debitar("77777", 100.0);
            ContaAbstrata contaAtualizada = fachada.procurarConta("77777");
            assertEquals(400.0, contaAtualizada.getSaldo(), 0.001);
        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }

    @Test
    void debitarContaInexistente() {
        try {
            fachada.debitar("77777", 100.0);
            fail("Exceção esperada: " + ContaInexistenteException.class);
        } catch (ContaInexistenteException | SaldoInsuficienteException e) {
            assertEquals("Conta Inexistente!", e.getMessage());
        }
    }

    @Test
    void transferir() {
        try {
            Cliente origemCliente = new Cliente("122", "Lucas", TipoCliente.CLASS);
            fachada.cadastrar(origemCliente);
            Cliente destinoCliente = new Cliente("111", "Luana", TipoCliente.CLASS);
            fachada.cadastrar(destinoCliente);

            ContaAbstrata origemConta = new Conta("66666", 1000.0, origemCliente);
            fachada.cadastrar(origemConta);
            ContaAbstrata destinoConta = new Conta("88888", 500.0, destinoCliente);
            fachada.cadastrar(destinoConta);
//            System.out.println(origemConta.getSaldo());

            fachada.transferir("66666", "88888", 300.0);

            System.out.println(origemConta.getSaldo());
            System.out.println(destinoConta.getSaldo());

            ContaAbstrata origemContaAtualizada = fachada.procurarConta("66666");
            ContaAbstrata destinoContaAtualizada = fachada.procurarConta("88888");

            assertEquals(700.0, origemContaAtualizada.getSaldo(), 0.001);
            assertEquals(800.0, destinoContaAtualizada.getSaldo(), 0.001);

        } catch (Exception e) {
            fail("Exceção inesperada: " + e.getMessage());
        }
    }

    @Test
    void transferirContaInexistente() {
        try {
            fachada.transferir("66666", "88888", 300.0);
            fail("Exceção esperada: " + ContaInexistenteException.class);
        } catch (ContaInexistenteException | SaldoInsuficienteException e) {
            assertEquals("Conta Inexistente!", e.getMessage());
        }
    }
}