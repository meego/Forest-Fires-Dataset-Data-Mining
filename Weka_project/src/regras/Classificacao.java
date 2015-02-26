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

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.CVParameterSelection;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;

public class Classificacao extends AlgoritmoDeAprendizagem{
	Instances testData;
	Evaluation eval;
	
	public Classificacao(String trainPath, Object algorithm, ArrayList<Filter> filters, String testPath, int removeFlag) throws Exception {
		super(trainPath, algorithm, filters, removeFlag);
		this.processedInstances.setClassIndex(this.processedInstances.numAttributes() - 1);
		this.testData = loadDatasetFromFile(testPath);		
		this.testData= applyFiltersToData(this.testData);
		this.testData.setClassIndex(this.testData.numAttributes() - 1);
	}

	public void buildClassifier(String cvparameter) throws Exception{
	    CVParameterSelection ps = new CVParameterSelection();
	    ps.setClassifier((Classifier) this.algorithm);
	    if(cvparameter != null){
	    	ps.setNumFolds(5);  // using 5-fold CV
	    	ps.addCVParameter(cvparameter);
	    }
	    ps.buildClassifier(this.processedInstances);
	    System.out.println("\nParametros do algoritmo otimizado:");
	    System.out.println(Utils.joinOptions(ps.getBestClassifierOptions()));
	    
	    System.out.println("\nAlgoritmo otimizado:");
	    System.out.println(ps.getClassifier());
	}
	
	public void evalutate() throws Exception{
		this.eval = new Evaluation(this.processedInstances);
		this.eval.evaluateModel((Classifier)this.algorithm, this.testData);
		System.out.println(this.eval.toSummaryString("\nResultados nos Dados de Teste", false));		
	}

	public void classify(String path) throws Exception {
		Instances unlabeled;
		unlabeled = loadDatasetFromFile(path);		
		unlabeled = applyFiltersToData(unlabeled );
		unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
		
		Instances labeled = new Instances(unlabeled);		 		
		for (int i = 0; i < unlabeled.numInstances(); i++) {
		   double clsLabel = ((Classifier) this.algorithm).classifyInstance(unlabeled.instance(i));
		   labeled.instance(i).setClassValue(clsLabel);
		 }
		 // save labeled data		 
		 System.out.println(labeled.toString());		 		 
		 
	}
}
