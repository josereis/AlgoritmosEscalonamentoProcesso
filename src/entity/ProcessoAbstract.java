package entity;

/**
 * 
 * @author José Reis Ribeiro Santiago
 * 
 * Classe que tem por objetivo simular um processo que será executado na CPU
 * Os seus principais atributos consistem em armazenar o tempo de chegada, a
 * duração necessária para o processo execultar e o seu ID.
 *
 */
public abstract class ProcessoAbstract implements Comparable<ProcessoAbstract> {
	private int id;
	private int tempoChegada;
	private int tempoDuracao;
	private int tempoEntradaEstadoPronto;
	private boolean isFirstResponse;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTempoChegada() {
		return tempoChegada;
	}

	public void setTempoChegada(int tempoChegada) {
		this.tempoChegada = tempoChegada;
	}

	public int getTempoDuracao() {
		return tempoDuracao;
	}

	public void setTempoDuracao(int tempoDuracao) {
		this.tempoDuracao = tempoDuracao;
	}

	public int getTempoEntrada() {
		return tempoEntradaEstadoPronto;
	}

	public void setTempoEntrada(int tempoEntrada) {
		this.tempoEntradaEstadoPronto = tempoEntrada;
	}
	

	public boolean isFirstResponse() {
		return isFirstResponse;
	}

	public void setFirstResponse(boolean isFirstResponse) {
		this.isFirstResponse = isFirstResponse;
	}

	@Override
	public int compareTo(ProcessoAbstract processo) {
		if(equals(processo)) {
			return 0;
		}
		
		if(this.tempoDuracao > processo.getTempoDuracao()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public String toString() {
		return "PID: " + getId()+" - Tempo Chegada: " + getTempoChegada() + " - Duração: " + getTempoDuracao() + " - Entrada no Estado de Pronto: " + getTempoEntrada();
	}
	
	public ProcessoAbstract() {
		
	}
	
	public ProcessoAbstract(int id, int tempoChegada, int tempoDuracao) {
		this.id = id;
		this.tempoChegada = tempoChegada;
		this.tempoDuracao = tempoDuracao;
		this.tempoEntradaEstadoPronto = this.tempoChegada;
		this.isFirstResponse = true;
	}
	
}
