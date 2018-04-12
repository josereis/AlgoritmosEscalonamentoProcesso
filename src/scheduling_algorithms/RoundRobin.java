package scheduling_algorithms;

import java.util.LinkedList;
import java.util.Queue;

import entity.Processo;

public class RoundRobin {
	private final int QUANTUM = 2; // Valor de um QUANTUM, tempo de execução de um ciclo na CPU
	
	private int tempoSistema; // Armazena o tempo atual do sistema;
	private int numeroProcessos; // Armazena a quantidadde de processos que serão armazenados
	private float tempoMedioEspera;
	private float tempoMedioRetorno;
	private float tempoMedioResposta;
	private Queue<Processo> filaProcessos = new LinkedList<Processo>(); // Fila de processos prontos
	private Queue<Processo> filaRR = new LinkedList<Processo>(); // Fila do Round Robin
	
	public int getTempoSistema() {
		return tempoSistema;
	}

	public void setTempoSistema(int tempoSistema) {
		this.tempoSistema = tempoSistema;
	}

	public int getNumeroProcessos() {
		return numeroProcessos;
	}

	public void setNumeroProcessos(int numeroProcessos) {
		this.numeroProcessos = numeroProcessos;
	}

	public float getTempoMedioEspera() {
		return tempoMedioEspera;
	}

	public void setTempoMedioEspera(float tempoMedioEspera) {
		this.tempoMedioEspera = tempoMedioEspera;
	}

	public float getTempoMedioRetorno() {
		return tempoMedioRetorno;
	}

	public void setTempoMedioRetorno(float tempoMedioRetorno) {
		this.tempoMedioRetorno = tempoMedioRetorno;
	}

	public float getTempoMedioResposta() {
		return tempoMedioResposta;
	}

	public void setTempoMedioResposta(float tempoMedioResposta) {
		this.tempoMedioResposta = tempoMedioResposta;
	}

	public Queue<Processo> getFilaProcessos() {
		return filaProcessos;
	}

	public void setFilaProcessos(Queue<Processo> filaProcessos) {
		this.filaProcessos = filaProcessos;
	}
	
	/**
	 * Adiciona os processos que possuem um tempo de chegada menor ou igual a tempo de sistema a fila de processos (filaRR) utilizada pelo Round Robin
	 */
	public void addProcessoFilaRR() {
		while(this.filaProcessos.peek() != null && this.tempoSistema >= this.filaProcessos.peek().getTempoChegada()) {
			filaRR.add(this.filaProcessos.poll());
		}
	}
	
	public void start() {
		Processo processo = new Processo();
		
		addProcessoFilaRR();
		
		while(filaProcessos.peek() != null || filaRR.peek() != null) { // Enquanto existir processos na fila de prontos ou na fila auxiliar criada o escalonador continua a execultar
			// verifica se existe algum processo na fila
			if(filaRR.peek() != null) {
				processo = filaRR.poll(); // processo sai da fila e assume o processador
				
				// verifica se é a primeira vez do processo assumindo a CPU
				if(processo.isFirstResponse()) {
					this.tempoMedioResposta += this.tempoSistema - processo.getTempoChegada();
					processo.setFirstResponse(false); // seta valor informando que o processo ja passou pela CPU ao menos uma vez
				}
				this.tempoMedioEspera += this.tempoSistema - processo.getTempoEntrada();
				
				// verifica se o tempo restante de processamento é menor que um QUANTUM (2) para garantir que o processo só execultara o tempo necessario
				if(processo.getTempoDuracao() < QUANTUM) {
					this.tempoSistema += processo.getTempoDuracao();
					processo.setTempoDuracao(0);
				} else {
					this.tempoSistema += 2;
					processo.setTempoDuracao(processo.getTempoDuracao() - 2);
				}
				
				/**
				 * Apos a atualização do tempo de sistema, é preciso garantir que os processos que possuem tempo de chegada menor ou igual ao tempo atual de sistema
				 * entrem na fila do Round Robin (filaRR) antes de inserir novamente o processo que esta ocupando o processador.
				 */
				addProcessoFilaRR();
				
				// verificar se o processo foi encerrado ou se ainda deve ser inserido na fila
				if(processo.getTempoDuracao() > 0) {
					processo.setTempoEntrada(this.tempoSistema);
					filaRR.add(processo);
				} else {
					this.tempoMedioRetorno += this.tempoSistema - processo.getTempoChegada(); // para o caso do processo não ser mais inserido a fila, considera-se que ele ja retornou
				}
			} else {
				// caso onde nenhum processo chegou a fila do Round Robin (filaRR)
				this.tempoSistema = this.filaProcessos.peek().getTempoChegada();
				addProcessoFilaRR();
			}
		}
	}

	public RoundRobin(Queue<Processo> filaProcessos) {
		this.tempoSistema = 0;
		this.tempoMedioEspera = 0;
		this.tempoMedioRetorno = 0;
		this.tempoMedioResposta = 0;
		this.filaProcessos = filaProcessos;
		this.numeroProcessos = filaProcessos.size();
	}
}
