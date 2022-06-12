package util;

import java.util.ArrayList;
import java.util.HashMap;

import ast.STentry;

public class Environment {
	
	//symTable, nestinLevel and offset are declared private, so there were also implemented the methods that
	//allows to call them when it's needed

	
	private ArrayList<HashMap<String,STentry>> symTable;
	private int nestingLevel;
	private int offset;
	public int provanesting;
	
	public Environment() {
		//The Symbol Table is implemented as a list of hash

		symTable = new ArrayList<HashMap<String,STentry>>();
		nestingLevel = -1;
		offset = 0;
	}
	

	public ArrayList<HashMap<String,STentry>> getSymTable() {
		return symTable;
	}
	
	public int getNestingLevel() {
		return nestingLevel;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void incrementNestingLevel() {
		nestingLevel++;
	}
	
	
	public void decrementNestingLevel() {
		nestingLevel--;
	}
	
	public void decrementOffset() {
		offset--;
	}
	
	
	
	
}
