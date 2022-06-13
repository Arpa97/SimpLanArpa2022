package ast;

import java.util.ArrayList;
import java.util.HashMap;
import util.Environment;
import util.SemanticError;

public class ArgNode implements Node{
	private boolean var = false;
	private Node type;
	private IdNode idNode;
	private STentry entry;

	public ArgNode(boolean var,Node type, IdNode idNode) {
		this.var = var;
		this.type = type;
		this.idNode = idNode;
	}

	@Override
	public String printer(String indent) {
		String stringa = "Arg ";
		if(var == true)
			stringa += "var" + " ";
		stringa += type.printer(indent) + " " + idNode.printer(indent) + " ";
		return indent + stringa + "\n";
	}

	@Override
	public Node typeCheck() {
		return type;
	}

	@Override
	public String codeGeneration() {
		return "";
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		int flag = env.getNestingLevel();
		HashMap<String,STentry> hm = env.getSymTable().get(env.getNestingLevel());
		flag = env.getNestingLevel();
		entry = new STentry(flag,type,env.getOffset(),false);
		if(hm.put(idNode.getId(),entry) != null)
			output.add(new SemanticError("The argument "+idNode.getId()+" is already defined."));
		env.decreaseOffset();
		return output;
	}
	
	public boolean getVar() {
		return this.var;
	}

	public String getId() {
		return this.idNode.getId();
	}
	
	public IdNode getIdNode() {
		return this.idNode;
	}
	
	public STentry getEntry() {
		return entry;
	}

	public void setEntry(STentry entry) {
		this.entry = entry;
	}
}
