package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.cliente.TipoCliente;
import br.ufpe.cin.residencia.banco.excecoes.ContaInexistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RepositorioContasArrayTest {

    private RepositorioContasArray repositorio;
    private ContaAbstrata conta1, conta2;

    @BeforeEach
    void setUp() {
        repositorio = new RepositorioContasArray();
        Cliente cliente1 = new Cliente("123", "Leopoldo", TipoCliente.CLASS);
        Cliente cliente2 = new Cliente("345", "Maria", TipoCliente.CLASS);
        conta1 = new Conta("12345", cliente1);
        conta2 = new Conta("54321", 500.0, cliente2);
    }

    @Test
    void inserir() {
        assertEquals(100, repositorio.listar().size()); // Verifica se a lista está vazia antes de inserir
        repositorio.inserir(conta1);
        assertEquals(100, repositorio.listar().size()); // Verifica se a lista tem um elemento após a inserção
    }

    @Test
    void existe() {
        assertFalse(repositorio.existe("12345")); // Verifica se a conta não existe inicialmente
        repositorio.inserir(conta1);
        assertTrue(repositorio.existe("12345")); // Verifica se a conta existe após inserção
    }

    @Test
    void atualizar() throws ContaInexistenteException {
        assertThrows(ContaInexistenteException.class, () -> repositorio.atualizar(conta1)); // Verifica se uma exceção é lançada quando a conta não existe
        repositorio.inserir(conta1);
        conta1.creditar(100.0); // Credita 100 na conta
        repositorio.atualizar(conta1);
        assertEquals(100.0, repositorio.procurar("12345").getSaldo(), 0.001); // Verifica se o saldo foi atualizado
    }

    @Test
    void procurar() throws ContaInexistenteException {
        assertThrows(ContaInexistenteException.class, () -> repositorio.procurar("12345")); // Verifica se uma exceção é lançada quando a conta não existe
        repositorio.inserir(conta1);
        assertEquals(conta1, repositorio.procurar("12345")); // Verifica se a conta é encontrada corretamente
    }

    @Test
    void remover() throws ContaInexistenteException {
        assertThrows(ContaInexistenteException.class, () -> repositorio.remover("12345")); // Verifica se uma exceção é lançada quando a conta não existe
        repositorio.inserir(conta1);
        assertEquals(100, repositorio.listar().size()); // Verifica se a lista tem um elemento antes da remoção
        repositorio.remover("12345");
        assertEquals(100, repositorio.listar().size()); // Verifica se a lista está vazia após a remoção
    }

    @Test
    void listar() {
        assertEquals(100, repositorio.listar().size()); // Verifica se a lista está vazia inicialmente
        repositorio.inserir(conta1);
        repositorio.inserir(conta2);
        List<ContaAbstrata> contas = repositorio.listar();
        assertEquals(100, contas.size()); // Verifica se a lista tem dois elementos após inserção
        assertTrue(contas.contains(conta1)); // Verifica se a lista contém a conta1
        assertTrue(contas.contains(conta2)); // Verifica se a lista contém a conta2
    }
}
