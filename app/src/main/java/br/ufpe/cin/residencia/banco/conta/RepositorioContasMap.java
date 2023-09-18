package br.ufpe.cin.residencia.banco.conta;

import br.ufpe.cin.residencia.banco.excecoes.ContaInexistenteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class RepositorioContasMap implements IRepositorioContas {

	private Map<String, ContaAbstrata> contas;

	public RepositorioContasMap() {
		contas = new HashMap<>();
	}

	@Override
	public void inserir(ContaAbstrata c) {
		contas.put(c.getNumero(), c);
	}

	@Override
	public boolean existe(String num) {
		return contas.containsKey(num);
	}

	@Override
	public void atualizar(ContaAbstrata c) throws ContaInexistenteException {
		if (existe(c.getNumero())) {
			contas.put(c.getNumero(), c);
		} else {
			throw new ContaInexistenteException();
		}
	}

	@Override
	public ContaAbstrata procurar(String num) throws ContaInexistenteException {
		ContaAbstrata conta = contas.get(num);
		if (conta != null) {
			return conta;
		} else {
			throw new ContaInexistenteException();
		}
	}

	@Override
	public void remover(String num) throws ContaInexistenteException {
		if (existe(num)) {
			contas.remove(num);
		} else {
			throw new ContaInexistenteException();
		}
	}

	@Override
	public List<ContaAbstrata> listar() {
		return new ArrayList<>(contas.values());
	}
}
