package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.cliente.Cliente;
import br.ufpe.cin.residencia.banco.excecoes.SaldoInsuficienteException;

public class ContaAbstrata {
	private String numero;
	private double saldo;
	private Cliente cliente;

	public ContaAbstrata(String num, Cliente c) {
		this(num, 0, c);
	}

	public ContaAbstrata(String num, double s, Cliente c) {
		setNumero(num);
		setSaldo(s);
		setCliente(c);
	}

	public void creditar(double valor) {
		saldo = saldo + valor;
	}

	public void debitar(double valor) throws SaldoInsuficienteException {
		if (valor <= getSaldo()) {
			setSaldo(getSaldo() - valor);
		} else {
			throw new SaldoInsuficienteException();
		}
	}



	public Cliente getCliente() {
		return cliente;
	}

	public String getNumero() {
		return numero;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setNumero(String num) {
		this.numero = num;
	}

	protected void setSaldo(double valor) {
		saldo = valor;
	}

	public void transferir(ContaAbstrata contaDestino, double valor) throws SaldoInsuficienteException {
		if (this.getSaldo() >= valor) {
			this.debitar(valor);
			contaDestino.creditar(valor);
		} else {
			throw new SaldoInsuficienteException();
		}
	}

}
