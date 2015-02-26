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

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public abstract class AlgoritmoDeAprendizagem {
	
	Instances instances;
	protected Instances processedInstances;
	protected Instances testData;
	protected Object algorithm;
	ArrayList<Filter> filters = new ArrayList<Filter>();

	public AlgoritmoDeAprendizagem(String path, Object algorithm, ArrayList<Filter> filters, int removeFlag) throws Exception{
		this.instances = loadDatasetFromFile(path);
		System.out.println("- Numero de Instâncias: " + this.instances.numInstances());
		// Se removeFlag for 1, remove-se a chuva, e o número de instâncias reduz uma unidade
		System.out.println("- Atributos: " + (this.instances.numAttributes()-removeFlag));
		this.processedInstances = this.instances;
		
		// Se queremos remover o atributo rain...
		if(removeFlag == 1){
			Remove remove = new Remove();
			remove.setAttributeIndices("12");
			remove.setInputFormat(processedInstances);
			this.processedInstances = Filter.useFilter(processedInstances, remove);
		}
		
		this.algorithm = algorithm;
		if(filters != null) this.filters = filters;
		this.processedInstances = applyFiltersToData(this.processedInstances);
	}
	
	public Instances loadDatasetFromFile(String path) throws Exception{
		System.out.println("\nDados carregados a partir do ficheiro "+path);
		DataSource source = new DataSource(path);
		return source.getDataSet();
		
	}

	public Instances applyFiltersToData(Instances pi) throws Exception{
		for (int i = 0; i < filters.size(); i++) {
		    Filter f = filters.get(i);		
		    f.setInputFormat(pi);
		    pi = Filter.useFilter(pi, f);		    
		}
		return pi;
	}
}
