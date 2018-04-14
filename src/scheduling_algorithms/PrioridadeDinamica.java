package scheduling_algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import entity.ProcessoAbstract;
import entity.Processo;

public class PrioridadeDinamica {
	private static final int QUANTUM = 2; // Valor de um QUANTUM, tempo de execução de um ciclo na CPU
	
	private int tempoSistema; // Armazena o tempo atual do sistema;
	private int numeroProcessos; // Armazena a quantidadde de processos que serão armazenados
	private float tempoMedioEspera;
	private float tempoMedioRetorno;
	private float tempoMedioResposta;
	
	private Queue<ProcessoAbstract> filaProcessos = new LinkedList<ProcessoAbstract>();
	private List<ProcessoAbstract> listaProcessosEscalonador = new ArrayList<ProcessoAbstract>();
	
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
	
	public Queue<ProcessoAbstract> getFilaProcessos() {
		return filaProcessos;
	}

	public void setFilaProcessos(Queue<ProcessoAbstract> filaProcessos) {
		this.filaProcessos = filaProcessos;
	}
	
	// adiciona os processos que chegam ao escalonador de acordo com o tempo
	private void InserirProcessoListaProntos() {
		while(filaProcessos.peek() != null && filaProcessos.peek().getTempoChegada() <= this.tempoSistema) {
			listaProcessosEscalonador.add(filaProcessos.poll());
		}
	}
	
	private ProcessoAbstract assumirCPU() {
		int indice = 0; // inicializa com o indice do primeiro elemento da lista
		ProcessoAbstract processo = ((Processo) listaProcessosEscalonador.get(indice)); // inicia a busca pelo processo de maior prioridade a partir do primeiro processo da lista
		
		for(int i = 1; i < listaProcessosEscalonador.size(); i++) {
			if(((Processo) processo).getPrioridade() < ((Processo) listaProcessosEscalonador.get(i)).getPrioridade()) {
				indice = i;
				processo = listaProcessosEscalonador.get(indice);
			}
		}
		listaProcessosEscalonador.remove(indice); // remove da lista de prontos o processo que vai assumir a CPU
		
		return processo;
	}
	
	private void atualizarPrioridade(int valor) {
		for(ProcessoAbstract p: listaProcessosEscalonador) {
			((Processo) p).setPrioridade(((Processo) p).getPrioridade() + valor);
		}
	}
	
	public void start() {
		InserirProcessoListaProntos(); // adiciona os processo que entraram no sistema
		
		// enquanto existir algum processo na lista do escalonador ou na fila de processos
		while(!listaProcessosEscalonador.isEmpty() || filaProcessos.peek() != null) {
			// para o caso de não existirem processos prontos no escalonador, mas existirem processo na fila de processo de entrada
			if(listaProcessosEscalonador.isEmpty()) {
				this.tempoSistema = filaProcessos.peek().getTempoChegada(); // atualiza o tempo do sistema para o tempo de chegada do proximo processo a entrar no estado de pronto
				InserirProcessoListaProntos();
			} else {
				ProcessoAbstract processo = assumirCPU(); // proscesso de maior prioridade assume a CPU
				System.out.println("Processo que assumiu CPU: " + ((Processo)processo).toString());
				
				// verifica se é a primeira vez do processo assumindo a CPU
				if(processo.isFirstResponse()) {
					processo.setFirstResponse(false);
					this.tempoMedioResposta += this.tempoSistema - processo.getTempoChegada();
				}
				// pega o valor do perido que o processo ficou no estado de pronto ate sua entrada na CPU
				this.tempoMedioEspera += this.tempoSistema - processo.getTempoEntrada();
				
				// verifica se a duração do processo é menor que o valor de um QUANTUM
				if(processo.getTempoDuracao() < QUANTUM) {
					this.tempoSistema += processo.getTempoDuracao();
					atualizarPrioridade(processo.getTempoDuracao()); // atualizar o valor das prioridades dos processo no estado de pronto
					((Processo) processo).setPrioridade(((Processo) processo).getPrioridade() - processo.getTempoDuracao()); // atualiza o valor da prioridade do processo que esta na CPU
					processo.setTempoDuracao(0);
				} else {
					this.tempoSistema += QUANTUM;
					atualizarPrioridade(QUANTUM);
					((Processo) processo).setPrioridade(((Processo) processo).getPrioridade() - QUANTUM); // atualiza o valor da prioridade do processo que esta na CPU
					processo.setTempoDuracao(processo.getTempoDuracao() - QUANTUM);
				}
					
				InserirProcessoListaProntos(); // atualiza a lista de processos prontos com relação ao tempo de sistema
				
				// verifica se o processo foi encerrado
				if(processo.getTempoDuracao() <= 0) {
					this.tempoMedioRetorno += this.tempoSistema - processo.getTempoChegada();
				} else {
					processo.setTempoEntrada(this.tempoSistema);
					listaProcessosEscalonador.add(listaProcessosEscalonador.size(), processo);
				}
			}
		}
	}
	
	
	public PrioridadeDinamica(Queue<ProcessoAbstract> filaProcessos) {
		this.tempoSistema = 0;
		this.tempoMedioEspera = 0;
		this.tempoMedioRetorno = 0;
		this.tempoMedioResposta = 0;
		this.filaProcessos = filaProcessos;
		this.numeroProcessos = filaProcessos.size();
	}
}
