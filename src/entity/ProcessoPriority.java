package entity;

public class ProcessoPriority extends ProcessoAbstract {
	private int prioridade;
	
	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public ProcessoPriority() {
		super();
		this.prioridade = 5;
	}
}
