package com.fcc.model;

public class Cost {
	private int rowid = 0;
	private String title = "";
	private double plan = 0;
	private double fact = 0;
	
	public Cost(String name, double plan, double fact) {
		this.title = name;
		this.plan = plan;
		this.fact = fact;
	}
	
	public void addPlan(double plan) {
		this.plan += plan;
	}
	public void addFact(double fact) {
		this.fact += fact;
	}
	public int getRowid() {
		return rowid;
	}
	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPlan() {
		return plan;
	}
	public void setPlan(double plan) {
		this.plan = plan;
	}
	public double getFact() {
		return fact;
	}
	public void setFact(double fact) {
		this.fact = fact;
	}
}
