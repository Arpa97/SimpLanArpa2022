package ast;

import java.util.ArrayList;
import util.Environment;
import util.SemanticError;

public class IdNode implements Node {
	private String id;
	private STentry entry;
	
	public IdNode(String text) {
		id = text;
	}

	@Override
	public String toPrint(String indent) {
		return indent +"ID "+ id + "\n";
	}

	@Override
	public Node typeCheck() {
		if(entry==null) {
			System.exit(-1);
		}
		return entry.getType();
	}

	@Override
	public String codeGeneration() {
		return "";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		int j=env.getNestingLevel();
		STentry tmp=null; 
		while (j>=0 && tmp==null) {
			tmp=(env.getSymTable().get(j--)).get(id);
		}
	    if(tmp==null){
	    	output.add(new SemanticError("Id "+id+" not declared"));
	    }else{
	    	entry = tmp;
	    }
		return output;
	}

	public STentry getEntry() {
		return entry;
	}

	public void setEntry(STentry entry) {
		this.entry = entry;
	}

	public String getId() {
		return id;
	}
}