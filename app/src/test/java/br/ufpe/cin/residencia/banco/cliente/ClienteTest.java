package br.ufpe.cin.residencia.banco.cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    Cliente cliente;

    @BeforeEach
    void setUp(){
        cliente = new Cliente("123", "Leopoldo", TipoCliente.CLASS);
    }

    @Test
    //void getCpf() {
    public void testGetCpf() {
        assertEquals("123", cliente.getCpf());
    }


    @Test
    //void getNome() {
    //}
    public void testGetNome() {
        assertEquals("Leopoldo", cliente.getNome());
    }

    @Test
    //void getTipo() {
    //}
    public void testGetTipo() {
        assertEquals(TipoCliente.CLASS, cliente.getTipo());
    }

    @Test
    //void setCpf() {
    //}
    public void testSetCpf() {
        cliente.setCpf("456");
        assertEquals("456", cliente.getCpf());
    }

    @Test
    //void setNome() {
    //}
    public void testSetNome() {
        cliente.setNome("Leopoldo2");
        assertEquals("Leopoldo2", cliente.getNome());
    }

    @Test
    //void setTipo() {
    //}
    public void testSetTipo() {
        cliente.setTipo(TipoCliente.VIP);
        assertEquals(TipoCliente.VIP, cliente.getTipo());
    }
}