package br.ufpe.cin.residencia.banco.cliente;


import br.ufpe.cin.residencia.banco.excecoes.ClienteInexistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioClientesMapTest {

    Cliente cliente;

    RepositorioClientesMap repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new RepositorioClientesMap();
        cliente = new Cliente("123", "Leopoldo", TipoCliente.CLASS);
    }

    @Test
    void testAtualizar() throws ClienteInexistenteException {
        repositorio.inserir(cliente);
        cliente.setNome("Leopoldo2");
        repositorio.atualizar(cliente);
        assertEquals(cliente, repositorio.procurar(cliente.getCpf()));
    }

    @Test
    void testAtualizarClienteInexistente() {
        assertThrows(ClienteInexistenteException.class, () -> repositorio.atualizar(cliente));
    }

    @Test
    void testExiste() {
        assertFalse(repositorio.existe(cliente.getCpf()));
    }

    @Test
    void testInserir() {
        repositorio.inserir(cliente);
        assertTrue(repositorio.existe(cliente.getCpf()));
    }

    @Test
    void testInserirInexistente() {
        assertFalse(repositorio.existe(cliente.getCpf()));
    }

    @Test
    void testProcurar() throws ClienteInexistenteException {
        repositorio.inserir(cliente);
        assertEquals(cliente, repositorio.procurar(cliente.getCpf()));
    }

    @Test
    void testProcurarClienteInexistente() {
        assertThrows(ClienteInexistenteException.class, () -> repositorio.procurar(cliente.getCpf()));
    }

    @Test
    void testRemover() throws ClienteInexistenteException {
        repositorio.inserir(cliente);
        repositorio.remover(cliente.getCpf());
        assertFalse(repositorio.existe(cliente.getCpf()));
    }

    @Test
    void testRemoverClienteInexistente() {
        assertThrows(ClienteInexistenteException.class, () -> repositorio.remover(cliente.getCpf()));
    }

    @Test
    void testListar() {
        List<Cliente> clientes = new ArrayList<>();
        assertEquals(clientes, repositorio.listar());
    }
}