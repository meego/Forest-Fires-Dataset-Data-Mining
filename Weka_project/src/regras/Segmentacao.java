/*
 * Universidade do Minho
 * Mestrado em Engenharia Informática
 * Perfil Sistemas Inteligentes
 * UC Análise e Extração de Conhecimento
 * Trabalho de grupo - 2.a parte
 * 
 * Alunos:
 * Ana Margarida Ferreira Cruz, pg27747
 * Isabel Maria Ferreira Cruz, pg27746
 * Serafim Miguel da Costa Pinto, pg28506
 * */

package regras;

import java.util.ArrayList;

//import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.clusterers.SimpleKMeans;
import weka.filters.Filter;

public class Segmentacao extends AlgoritmoDeAprendizagem{
	int numClusters;
	
	public Segmentacao(String path, Object algorithm, ArrayList<Filter> filters, int numClusters, int removeFlag) throws Exception {
		super(path, algorithm, filters, removeFlag);
		this.numClusters = numClusters;
	}

	public void buildClusters() throws Exception{
	    Clusterer clusterer = (Clusterer) this.algorithm;	    
	    System.out.println("\nAlgoritmo otimizado:");
	    System.out.println(clusterer.getCapabilities());
	    if (clusterer instanceof SimpleKMeans){
	    	((SimpleKMeans) clusterer).setPreserveInstancesOrder(true);	    	
	    	((SimpleKMeans) clusterer).setNumClusters(this.numClusters);
	    }     
	    clusterer.buildClusterer(this.processedInstances);
	    System.out.println(clusterer.toString());
	}
}
