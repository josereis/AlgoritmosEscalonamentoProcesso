package entity;

public class Processo extends ProcessoAbstract {
	private int prioridade;
	
	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public Processo() {
		super();
		this.prioridade = 5;
	}
	
	public String toString() {
		return super.toString() +" - Prioridade: " + getPrioridade();
	}
	
	public Processo(int id, int tempoChegada, int tempoDuracao) {
		super(id, tempoChegada, tempoDuracao);
		this.prioridade = 5;
	}
}
