import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import entity.ProcessoAbstract;
import scheduling_algorithms.DynamicPriority;
import scheduling_algorithms.RoundRobin;
import utils.FileManipulation;

public class Main {
	
	public static void main(String[] args) {
		RoundRobin RR; DynamicPriority pd;
		try {
			Queue<ProcessoAbstract> filaProcessos = FileManipulation.getFilaProcessosFile();
			System.out.println("ROUND ROBIN = " + filaProcessos.size());
			RR = new RoundRobin(filaProcessos);
			
			RR.start();
			
			RR.setTempoMedioEspera(RR.getTempoMedioEspera()/RR.getNumeroProcessos());
			RR.setTempoMedioRetorno(RR.getTempoMedioRetorno()/RR.getNumeroProcessos());
			RR.setTempoMedioResposta(RR.getTempoMedioResposta()/RR.getNumeroProcessos());
			
			System.out.println("TEMPO MEDIO DE RETORNO: " + RR.getTempoMedioRetorno());
			System.out.println("TEMPO MEDIO DE REPOSTA: " + RR.getTempoMedioResposta());
			System.out.println("TEMPO MEDIO DE ESPERA: " + RR.getTempoMedioEspera());
			
			filaProcessos = FileManipulation.getFilaProcessosFile();
			System.out.println("PRIORIDADE DINAMICA = " + filaProcessos.size());
			pd = new DynamicPriority(filaProcessos);
			
			pd.start();
			
			pd.setTempoMedioEspera(pd.getTempoMedioEspera()/pd.getNumeroProcessos());
			pd.setTempoMedioRetorno(pd.getTempoMedioRetorno()/pd.getNumeroProcessos());
			pd.setTempoMedioResposta(pd.getTempoMedioResposta()/pd.getNumeroProcessos());
			
			System.out.println("TEMPO MEDIO DE RETORNO: " + pd.getTempoMedioRetorno());
			System.out.println("TEMPO MEDIO DE REPOSTA: " + pd.getTempoMedioResposta());
			System.out.println("TEMPO MEDIO DE ESPERA: " + pd.getTempoMedioEspera());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
