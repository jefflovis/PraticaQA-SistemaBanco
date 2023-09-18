package br.ufpe.cin.residencia.banco.cliente;

import br.ufpe.cin.residencia.banco.excecoes.ClienteExistenteException;
import br.ufpe.cin.residencia.banco.excecoes.ClienteInexistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CadastroClientesTest {

    Cliente cliente;
    CadastroClientes cadastroClientes;

    @BeforeEach
    void setUp() {
        RepositorioClientesMap repositorioClientes = new RepositorioClientesMap();
        cadastroClientes = new CadastroClientes(repositorioClientes);
        cliente = new Cliente("123", "Leopoldo", TipoCliente.CLASS);
    }

    @Test
    void testAtualizar() throws ClienteInexistenteException {
        try {
            cadastroClientes.cadastrar(cliente);
        } catch (ClienteExistenteException e) {
            throw new RuntimeException(e);
        }
        cliente.setNome("Leopoldo2");
        try {
            cadastroClientes.atualizar(cliente);
        } catch (ClienteInexistenteException e) {
            throw new RuntimeException(e);
        }
        assertEquals(cliente, cadastroClientes.procurar(cliente.getCpf()));
    }

    @Test
    void testAtualizarClienteInexistente() {
        assertThrows(ClienteInexistenteException.class, () -> cadastroClientes.atualizar(cliente));
    }

    @Test
    void testCadastrar() throws ClienteInexistenteException {
        try {
            cadastroClientes.cadastrar(cliente);
        } catch (ClienteExistenteException e) {
            throw new RuntimeException(e);
        }
        assertEquals(cliente, cadastroClientes.procurar(cliente.getCpf()));
    }

    @Test
    void testCadastrarClienteExistente() throws ClienteExistenteException {
        try {
            cadastroClientes.cadastrar(cliente);
        } catch (ClienteExistenteException e) {
            throw new RuntimeException(e);
        }
        assertThrows(ClienteExistenteException.class, () -> cadastroClientes.cadastrar(cliente));
    }

    @Test
    void testDescadastrar() {
        try {
            cadastroClientes.cadastrar(cliente);
        } catch (ClienteExistenteException e) {
            throw new RuntimeException(e);
        }
        try {
            cadastroClientes.descadastrar(cliente.getCpf());
        } catch (ClienteInexistenteException e) {
            throw new RuntimeException(e);
        }
        assertThrows(ClienteInexistenteException.class, () -> cadastroClientes.procurar(cliente.getCpf()));
    }

    @Test
    void testDescadastrarLeo() throws ClienteInexistenteException {
        try {
            cadastroClientes.cadastrar(cliente);
        } catch (ClienteExistenteException e) {
            throw new RuntimeException(e);
        }
        cadastroClientes.descadastrar(cliente.getCpf());
    }

    @Test
    void testDescadastrarClienteInexistente() {
        assertThrows(ClienteInexistenteException.class, () -> cadastroClientes.descadastrar(cliente.getCpf()));
    }

    @Test
    void testProcurar() throws ClienteInexistenteException {
        try {
            cadastroClientes.cadastrar(cliente);
        } catch (ClienteExistenteException e) {
            throw new RuntimeException(e);
        }
        assertEquals(cliente, cadastroClientes.procurar(cliente.getCpf()));
    }

    @Test
    void testProcurarClienteInexistente() {
        assertThrows(ClienteInexistenteException.class, () -> cadastroClientes.procurar(cliente.getCpf()));
    }
}