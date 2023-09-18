package br.ufpe.cin.residencia.banco.cliente;

import br.ufpe.cin.residencia.banco.excecoes.ClienteInexistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioClientesArrayTest {

    Cliente cliente;

    RepositorioClientesArray repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new RepositorioClientesArray();
        cliente = new Cliente("123", "Leopoldo", TipoCliente.CLASS);
    }

    @Test
    void atualizar() throws ClienteInexistenteException {
        repositorio.inserir(cliente);
        cliente.setNome("Leopoldo2");
        repositorio.atualizar(cliente);
        assertEquals(cliente, repositorio.procurar(cliente.getCpf()));
    }

    @Test
    void atualizarClienteInexistente() {
        assertThrows(ClienteInexistenteException.class, () -> repositorio.atualizar(cliente));
    }

    @Test
    void existe() {
        assertFalse(repositorio.existe(cliente.getCpf()));
    }

    @Test
    void inserir() {
        repositorio.inserir(cliente);
        assertTrue(repositorio.existe(cliente.getCpf()));
    }

    @Test
    void inserirInexistente() {
        assertFalse(repositorio.existe(cliente.getCpf()));
    }

    @Test
    void procurar() throws ClienteInexistenteException {
        repositorio.inserir(cliente);
        assertEquals(cliente, repositorio.procurar(cliente.getCpf()));
    }

    @Test
    void procurarClienteInexistente() {
        assertThrows(ClienteInexistenteException.class, () -> repositorio.procurar(cliente.getCpf()));
    }

    @Test
    void remover() throws ClienteInexistenteException {
        repositorio.inserir(cliente);
        repositorio.remover(cliente.getCpf());
        assertFalse(repositorio.existe(cliente.getCpf()));
    }

    @Test
    void removerClienteInexistente() {
        assertThrows(ClienteInexistenteException.class, () -> repositorio.remover(cliente.getCpf()));
    }

    @Test
    void testListar() {
        List<Cliente> ListaClientes = new ArrayList<>();
        ListaClientes.add(cliente);
        repositorio.inserir(cliente);
        for (Cliente c : ListaClientes) {
            assertTrue(repositorio.listar().contains(c));
        }
    }

    @Test
    void testProcurarIndiceCPFEncontrado() {
        Cliente clienteEncontrado = new Cliente("123", "Leopoldo", TipoCliente.CLASS);
        repositorio.inserir(clienteEncontrado);

        int indice = repositorio.procurarIndice("123");
        assertEquals(0, indice);
    }

    @Test
    void testProcurarIndiceCPFNaoEncontrado() {
        int indice = repositorio.procurarIndice("999"); // Supondo que "999" não existe na lista
        assertEquals(-1, indice);
    }

    @Test
    void testProcurarIndiceCPFMetadeDoArray() {
        Cliente cliente1 = new Cliente("111", "Cliente1", TipoCliente.CLASS);
        Cliente cliente2 = new Cliente("222", "Cliente2", TipoCliente.CLASS);
        Cliente cliente3 = new Cliente("333", "Cliente3", TipoCliente.CLASS);
        Cliente cliente4 = new Cliente("444", "Cliente4", TipoCliente.CLASS);

        repositorio.inserir(cliente1);
        repositorio.inserir(cliente2);
        repositorio.inserir(cliente3);
        repositorio.inserir(cliente4);

        int indice = repositorio.procurarIndice("333");
        assertEquals(2, indice);
    }

    @Test
    void testProcurarIndiceCPFNoFinalDoArray() {
        Cliente cliente1 = new Cliente("111", "Cliente1", TipoCliente.CLASS);
        Cliente cliente2 = new Cliente("222", "Cliente2", TipoCliente.CLASS);
        Cliente cliente3 = new Cliente("333", "Cliente3", TipoCliente.CLASS);
        Cliente cliente4 = new Cliente("444", "Cliente4", TipoCliente.CLASS);

        repositorio.inserir(cliente1);
        repositorio.inserir(cliente2);
        repositorio.inserir(cliente3);
        repositorio.inserir(cliente4);

        int indice = repositorio.procurarIndice("444");
        assertEquals(3, indice);
    }

}