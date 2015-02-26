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

package weka;

import regras.Associacao;
import regras.Segmentacao;
import regras.Classificacao;




import java.util.ArrayList;

import weka.associations.Apriori;
import weka.classifiers.trees.J48;
import weka.clusterers.SimpleKMeans;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.Remove;

public class ExtracaoConhecimento {
	
	public static void associacao() throws Exception{
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("1) Regras de Associação (association)                                                            x");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		Discretize disc = new Discretize();
		
		String[] disc_options = new String[6];
		disc_options[0] = "-B";
		disc_options[1] = "10";
		disc_options[2] = "-M";
		disc_options[3] = "-1.0";
		disc_options[4] = "-R";
		disc_options[5] = "first-last";
		
		disc.setOptions(disc_options);
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(disc);
		
		String[] assoc_options = new String[2];
		assoc_options[0] = "-N";
		assoc_options[1] = "10";
		
		Apriori apriori = new Apriori();
		apriori.setOptions(assoc_options);
		
		Associacao associacao = new Associacao("datasets/forestfires_v1.arff", apriori, filters, 1); 
		System.out.println(associacao.learnRulesFromDataset());
	}
	
	public static void segmentacao() throws Exception{
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("2) Segmentação (clustering)                                                                      x");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		Discretize disc = new Discretize();
		
		String[] disc_options = new String[6];
		disc_options[0] = "-B";
		disc_options[1] = "10";
		disc_options[2] = "-M";
		disc_options[3] = "-1.0";
		disc_options[4] = "-R";
		disc_options[5] = "first-last";
		
		disc.setOptions(disc_options);
		ArrayList<Filter> filters = new ArrayList<Filter>();
		filters.add(disc);
		
		String clust_options[] = new String[4];
		clust_options[0] = "-I";
		clust_options[1] = "100";
		clust_options[2] = "-N";
		clust_options[3] = "2";
		
		SimpleKMeans skm = new SimpleKMeans();
		skm.setOptions(clust_options);
		
		//Segmentacao clustering = new Segmentacao("datasets/forestfires_v1.arff", skm, filters, 2, "datasets/forestfires_v1_test.arff");
		Segmentacao clustering = new Segmentacao("datasets/forestfires_v1.arff", skm, filters, 5, 0);
			
		clustering.buildClusters();	
		//clustering.evaluate();
	}
	
	public static void classificacao() throws Exception{
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("3) Classificação (classification)                                                                x");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		Remove r = new Remove();
		String[] r_options = weka.core.Utils.splitOptions("");
		r.setOptions(r_options );
		ArrayList<Filter> filters = new ArrayList<Filter>();		
		filters.add(r); 
		
		//Classificacao classification = new Classificacao("datasets/classificacao/forestfires_v1_training.arff", (new J48()), filters, "datasets/classificacao/forestfires_v1_test2.arff", 0);
		Classificacao classification = new Classificacao("datasets/classificacao/forestfires_v1_training.arff", (new J48()), filters, "datasets/classificacao/forestfires_v1_training.arff", 0);
		classification.buildClassifier("C 0.25 0.5 2");
		classification.evalutate();
		//classification.classify("datasets/classificacao/forestfires_v1_training.arff");
	}
	
	public static void main(String[] args){
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("x                                                                                                x");
		System.out.println("x                                           AEC:SI:MEI                                           x");
		System.out.println("x                                 Extração de Conhecimento - Weka                                x");
		System.out.println("x                                                                                                x");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
		
		try {
			associacao();
			segmentacao();
			classificacao();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
