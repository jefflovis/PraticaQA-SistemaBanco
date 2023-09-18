package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import br.ufpe.cin.residencia.banco.excecoes.ContaInexistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioContasMapTest {

    private RepositorioContasMap repositorio;
    private ContaAbstrata conta1, conta2;

    @BeforeEach
    void setUp() {
        repositorio = new RepositorioContasMap();
        Cliente cliente1 = new Cliente("123", "Leopoldo", TipoCliente.CLASS);
        Cliente cliente2 = new Cliente("333", "Maria", TipoCliente.CLASS);
        conta1 = new Conta("12345", cliente1);
        conta2 = new Conta("54321", 500.0, cliente2);
    }

    @Test
    void inserir() {
        assertEquals(0, repositorio.listar().size());
        repositorio.inserir(conta1);
        assertEquals(1, repositorio.listar().size());
    }

    @Test
    void existe() {
        assertFalse(repositorio.existe("12345"));
        repositorio.inserir(conta1);
        assertTrue(repositorio.existe("12345"));
    }

    @Test
    void atualizar() throws ContaInexistenteException {
        assertThrows(ContaInexistenteException.class, () -> repositorio.atualizar(conta1));
        repositorio.inserir(conta1);
        conta1.creditar(100.0);
        repositorio.atualizar(conta1);
        assertEquals(100.0, repositorio.procurar("12345").getSaldo(), 0.001);
    }

    @Test
    void procurar() throws ContaInexistenteException {
        assertThrows(ContaInexistenteException.class, () -> repositorio.procurar("12345"));
        repositorio.inserir(conta1);
        assertEquals(conta1, repositorio.procurar("12345"));
    }

    @Test
    void remover() throws ContaInexistenteException {
        assertThrows(ContaInexistenteException.class, () -> repositorio.remover("12345"));
        repositorio.inserir(conta1);
        assertEquals(1, repositorio.listar().size());
        repositorio.remover("12345");
        assertEquals(0, repositorio.listar().size());
    }

    @Test
    void listar() {
        assertEquals(0, repositorio.listar().size());
        repositorio.inserir(conta1);
        repositorio.inserir(conta2);
        List<ContaAbstrata> contas = repositorio.listar();
        assertEquals(2, contas.size());
        assertTrue(contas.contains(conta1));
        assertTrue(contas.contains(conta2));
    }
}
