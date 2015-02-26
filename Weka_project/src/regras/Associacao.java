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

import weka.associations.Apriori;
import weka.filters.Filter;

public class Associacao extends AlgoritmoDeAprendizagem {
	
	public Associacao(String path, Object algorithm, ArrayList<Filter> filters, int removeFlag) throws Exception {
		super(path, algorithm, filters, removeFlag);
	}
	
	public String learnRulesFromDataset() throws Exception{
		Apriori apriori = (Apriori) this.algorithm;
		apriori.buildAssociations(this.processedInstances);
		return apriori.toString();
	}
}
