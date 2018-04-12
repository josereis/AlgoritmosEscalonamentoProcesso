package scheduling_algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import entity.ProcessoAbstract;
import entity.ProcessoPriority;

public class DynamicPriority {
	private static final int QUANTUM = 2; // Valor de um QUANTUM, tempo de execução de um ciclo na CPU
	
	private int priorityMax;
	private int tempoSistema; // Armazena o tempo atual do sistema;
	private int numeroProcessos; // Armazena a quantidadde de processos que serão armazenados
	private float tempoMedioEspera;
	private float tempoMedioRetorno;
	private float tempoMedioResposta;
	
	private Queue<ProcessoPriority> filaProcessos = new LinkedList<ProcessoPriority>();
	private ArrayList<ProcessoPriority> listaPriDynamic = new ArrayList<ProcessoPriority>();
	
	public int getPriorityMax() {
		return priorityMax;
	}

	public void setPriorityMax(int priorityMax) {
		this.priorityMax = priorityMax;
	}
	
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
	
	public Queue<ProcessoPriority> getFilaProcessos() {
		return filaProcessos;
	}

	public void setFilaProcessos(Queue<ProcessoPriority> filaProcessos) {
		this.filaProcessos = filaProcessos;
	}

	public ArrayList<ProcessoPriority> getListaPriDynamic() {
		return listaPriDynamic;
	}

	public void setListaPriDynamic(ArrayList<ProcessoPriority> filaPriDynamic) {
		this.listaPriDynamic = filaPriDynamic;
	}
	
	/**
	 * Adiciona os processos que possuem um tempo de chegada menor ou igual a tempo de sistema a fila de processos (filaRR) utilizada pelo Round Robin
	 */
	public void addProcessoFilaPriorityDynamic() {
		while(this.filaProcessos.peek() != null && this.tempoSistema >= this.filaProcessos.peek().getTempoChegada()) {
			listaPriDynamic.add(this.filaProcessos.poll());
		}
	}
	
	public ProcessoPriority getProcessoPriorityMax() {
		int max = 0;
		ProcessoPriority processo = null;
		
		// busca na lista de processo prontos o processo de maior prioridade
		for(ProcessoPriority p: listaPriDynamic) {
			if(p.getPrioridade() > max) {
				processo = p;
				max = processo.getPrioridade();
			}
		}
		
		if(processo != null) // remove o processo da lista de processos prontos
			listaPriDynamic.remove(processo);
		
		return processo;
	}
	
	public void updatePriorityProcessos(int value) {
		for(ProcessoPriority p: listaPriDynamic) {
			p.setPrioridade(p.getPrioridade() + value);
		}
	}
	
	
	public void start() {
		ProcessoPriority processo = new ProcessoPriority();
		
		addProcessoFilaPriorityDynamic();
		while((this.filaProcessos.peek() != null) || (this.listaPriDynamic.size() > 0)) {
			if(this.listaPriDynamic.size() > 0) {
				processo = getProcessoPriorityMax(); // pega o processo que deve ocupar as CPU
				
				// verifica se é a primeira vez do processo ocupando a CPU
				if(processo.isFirstResponse()) {
					this.tempoMedioResposta += this.tempoSistema - processo.getTempoChegada();
					processo.setFirstResponse(false);
				}
				this.tempoMedioEspera += this.tempoSistema - processo.getTempoEntrada();
				
				int value=0;
				if(processo.getTempoDuracao() < QUANTUM) {
					value = processo.getTempoDuracao();
					this.tempoSistema += value;
					processo.setTempoDuracao(0);
					processo.setPrioridade(processo.getPrioridade() - value);
				} else {
					value = QUANTUM;
					this.tempoSistema += QUANTUM;
					processo.setPrioridade(processo.getPrioridade() - QUANTUM);
					processo.setTempoDuracao(processo.getTempoDuracao() - QUANTUM);
				}
				
				addProcessoFilaPriorityDynamic(); // adiciona o processo que entraram na lista de prontos apos atualização do tempo do sistema
				updatePriorityProcessos(value); // atualiza o valor da prioridade dos processo na lista de prontos
				
				// verificar se o processo foi encerrado ou se ainda deve ser inserido na fila
				if(processo.getTempoDuracao() > 0) {
					processo.setTempoEntrada(this.tempoSistema);
					listaPriDynamic.add(processo);
				} else {
					this.tempoMedioRetorno += this.tempoSistema - processo.getTempoChegada(); // para o caso do processo não ser mais inserido a fila, considera-se que ele ja retornou
				}
				
			} else { // caso nenhum processo tenha chegado no tempo de sistema corrente
				this.tempoSistema = filaProcessos.peek().getTempoChegada();
				addProcessoFilaPriorityDynamic();
			}
		}
	}
	
	public DynamicPriority() {
		this.priorityMax = 5;
		this.tempoSistema = 0;
		this.tempoMedioEspera = 0;
		this.tempoMedioRetorno = 0;
		this.tempoMedioResposta = 0;
	}
}
