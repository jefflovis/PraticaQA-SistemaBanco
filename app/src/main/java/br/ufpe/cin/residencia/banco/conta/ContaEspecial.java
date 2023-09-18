package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;

public class ContaEspecial extends Conta {

    private double valorChequeEspecial;

    public ContaEspecial(String numeroConta, double saldo, Cliente c, double creditoChequeEspecial) {
        super(numeroConta, saldo, c);
        this.valorChequeEspecial = creditoChequeEspecial;

    }

    public ContaEspecial(String numeroConta, Cliente c) {
        this(numeroConta, 0, c, 0);
    }

    public ContaEspecial(String numeroConta, double saldo, Cliente c) {
        this(numeroConta, saldo, c, 0);
    }

    @Override
    public double getSaldo() {
        return super.getSaldo() + this.valorChequeEspecial;
    }

    public double getValorChequeEspecial() {
        return this.valorChequeEspecial;
    }
}