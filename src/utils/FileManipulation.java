package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import entity.ProcessoAbstract;
import entity.Processo;

public class FileManipulation {
	
	public static Queue<ProcessoAbstract> getFilaProcessosFile() throws IOException {
		FileReader fileReader = new FileReader("/home/Developer/java/workspace/EscalonamentoProcessos/src/utils/teste.txt");
        BufferedReader reader = new BufferedReader(fileReader);
        Queue<ProcessoAbstract> filaProcessos = new LinkedList<ProcessoAbstract>();
        
        String linha; int proxid = 0;
        while((linha = reader.readLine()) != null) {
        	String s[] = linha.split(" ");
        	filaProcessos.add(new Processo(proxid++, Integer.parseInt(s[0]), Integer.parseInt(s[1])));	
        }
		
		return filaProcessos;
	}
	
}
