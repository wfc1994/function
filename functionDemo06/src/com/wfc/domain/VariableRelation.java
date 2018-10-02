package com.wfc.domain;

public class VariableRelation {
	private String independentvar;
	private String dependentvar;
	private String functionname;
	private int varrelid;
	private String varrelation;
	


	public VariableRelation(String independentvar, String dependentvar, String functionname) {
		super();
		this.independentvar = independentvar;
		this.dependentvar = dependentvar;
		this.functionname = functionname;
	}
	
	public String getVarrelation() {
		return varrelation;
	}
	public void setVarrelation(String independentvar, String dependentvar, String functionname) {
		this.varrelation = dependentvar+"="+functionname+"("+independentvar+")";
	}
	public int getVarrelid() {
		return varrelid;
	}
	public void setVarrelid(int varrelid) {
		this.varrelid = varrelid;
	}	
	public String getIndependentvar() {
		return independentvar;
	}
	public void setIndependentvar(String independentvar) {
		this.independentvar = independentvar;
	}
	public String getDependentvar() {
		return dependentvar;
	}
	public void setDependentvar(String dependentvar) {
		this.dependentvar = dependentvar;
	}
	public String getFunctionname() {
		return functionname;
	}
	public void setFunctionname(String functionname) {
		this.functionname = functionname;
	}
	
	
}
