package scheduling_algorithms;

import java.util.ArrayList;

import entity.ProcessoAbstract;

public class Loteria {
	private static final int QUANTUM = 2; // Valor de um QUANTUM, tempo de execução de um ciclo na CPU
	
	private int tempoSistema;
	private int numeroProcessos; // Armazena a quantidadde de processos que serão armazenados
	private float tempoMedioEspera;
	private float tempoMedioRetorno;
	private float tempoMedioResposta;
	private ArrayList<ProcessoAbstract> listaProcessos = new ArrayList<ProcessoAbstract>(); // Fila de processos prontos
	private ArrayList<ProcessoAbstract> listaProcessoLoteria = new ArrayList<>(); // lista contendo todos os processos que entrarão no esquema de sorteio para uso da CPU
	
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

	public ArrayList<ProcessoAbstract> getListaProcessos() {
		return listaProcessos;
	}

	public void setListaProcessos(ArrayList<ProcessoAbstract> listaProcessos) {
		this.listaProcessos = listaProcessos;
	}

	public ArrayList<ProcessoAbstract> getListaProcessoLoteria() {
		return listaProcessoLoteria;
	}

	public void setListaProcessoLoteria(ArrayList<ProcessoAbstract> listaProcessoLoteria) {
		this.listaProcessoLoteria = listaProcessoLoteria;
	}
	
	public void start() {
		
	}

	public Loteria() {
		
	}
	
	public Loteria(ArrayList<ProcessoAbstract> listaProcessos) {
		this.tempoSistema = 0;
		this.tempoMedioEspera = 0;
		this.tempoMedioRetorno = 0;
		this.tempoMedioResposta = 0;
		this.listaProcessos = listaProcessos;
		this.numeroProcessos = listaProcessos.size();
	}
}
