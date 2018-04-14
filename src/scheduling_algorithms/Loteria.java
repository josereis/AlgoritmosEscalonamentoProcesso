package scheduling_algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import entity.ProcessoAbstract;

public class Loteria {
	private static final int QUANTUM = 2; // Valor de um QUANTUM, tempo de execução de um ciclo na CPU
	
	private int tempoSistema;
	private int numeroProcessos; // Armazena a quantidadde de processos que serão armazenados
	private float tempoMedioEspera;
	private float tempoMedioRetorno;
	private float tempoMedioResposta;
	private Queue<ProcessoAbstract> filaProcessos = new LinkedList<ProcessoAbstract>(); // Fila de processos prontos
	private List<ProcessoAbstract> listaProcessoLoteria = new ArrayList<>(); // lista contendo todos os processos que entrarão no esquema de sorteio para uso da CPU
	
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

	public List<ProcessoAbstract> getListaProcessoLoteria() {
		return listaProcessoLoteria;
	}

	public void setListaProcessoLoteria(List<ProcessoAbstract> listaProcessoLoteria) {
		this.listaProcessoLoteria = listaProcessoLoteria;
	}

	// adiciona os processos que chegam ao escalonador de acordo com o tempo
	private void InserirProcessoListaProntos() {
		while(filaProcessos.peek() != null && filaProcessos.peek().getTempoChegada() <= this.tempoSistema) {
			listaProcessoLoteria.add(filaProcessos.poll());
		}
	}
	
	private ProcessoAbstract assumirCPU() {
		Random r = new Random();
		int indice = r.nextInt(listaProcessoLoteria.size());
		
		ProcessoAbstract processo = listaProcessoLoteria.get(indice);
		listaProcessoLoteria.remove(indice); // remove da lista de prontos
		
		return processo;
	}
	
	public void start() {
		InserirProcessoListaProntos(); // adiciona os processo que entraram no sistema
		
		// enquanto existir algum processo na lista do escalonador ou na fila de processos
		while(!listaProcessoLoteria.isEmpty() || filaProcessos.peek() != null) {
			if(listaProcessoLoteria.isEmpty()) {
				this.tempoSistema = filaProcessos.peek().getTempoChegada(); // atualiza o tempo do sistema para o tempo de chegada do proximo processo a entrar no estado de pronto
				InserirProcessoListaProntos();
			} else {
				ProcessoAbstract processo = assumirCPU();
//				System.out.println("Processo que assumiu CPU: " + processo.toString());
				
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
					processo.setTempoDuracao(0);
				} else {
					this.tempoSistema += QUANTUM;
					processo.setTempoDuracao(processo.getTempoDuracao() - QUANTUM);
				}
					
				InserirProcessoListaProntos(); // atualiza a lista de processos prontos com relação ao tempo de sistema
				
				// verifica se o processo foi encerrado
				if(processo.getTempoDuracao() <= 0) {
					this.tempoMedioRetorno += this.tempoSistema - processo.getTempoChegada();
				} else {
					processo.setTempoEntrada(this.tempoSistema);
					listaProcessoLoteria.add(processo);
				}
				
			}
		}
	}
	
	public Loteria(Queue<ProcessoAbstract> listaProcessos) {
		this.tempoSistema = 0;
		this.tempoMedioEspera = 0;
		this.tempoMedioRetorno = 0;
		this.tempoMedioResposta = 0;
		this.filaProcessos = listaProcessos;
		this.numeroProcessos = listaProcessos.size();
	}
}
