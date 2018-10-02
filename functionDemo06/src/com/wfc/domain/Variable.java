package com.wfc.domain;

public class Variable {
	private String varname;
	private String varunit;
	private String vardescribe;
	private int varid;
	public Variable(String varname, String varunit, String vardescribe) {
		super();
		this.varname = varname;
		this.varunit = varunit;
		this.vardescribe = vardescribe;
	}
	public String getVarname() {
		return varname;
	}
	public void setVarname(String varname) {
		this.varname = varname;
	}
	public String getVarunit() {
		return varunit;
	}
	public void setVarunit(String varunit) {
		this.varunit = varunit;
	}
	public String getVardescribe() {
		return vardescribe;
	}
	public void setVardescribe(String vardescribe) {
		this.vardescribe = vardescribe;
	}
	public int getVarid() {
		return varid;
	}
	public void setVarid(int varid) {
		this.varid = varid;
	}

}
