package util;

import java.util.ArrayList;
import java.util.HashMap;

import ast.STentry;

public class Environment {
	
	private ArrayList<HashMap<String,STentry>> symTable;
	private int nestingLevel;
	private int offset;
	public int provanesting;


	public Environment() {
		symTable = new ArrayList<>();
		nestingLevel = -1;
		offset = 0;
	}
	

	//GETTER METHODS
	public ArrayList<HashMap<String,STentry>> getSymTable() {
		return this.symTable;
	}
	public int getNestingLevel() {
		return this.nestingLevel;
	}
	public int getOffset() {
		return this.offset;
	}
	
	//SETTER METHODS
	public void increaseNesting() {
		this.nestingLevel++;
	}
	public void decreaseNesting() {
		this.nestingLevel--;
	}
	public void decreaseOffset() {
		this.offset--;
	}
	
	
	
	
}
