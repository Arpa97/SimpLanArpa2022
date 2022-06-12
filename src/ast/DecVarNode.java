package ast;

import java.util.ArrayList;
import java.util.HashMap;

import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

public class DecVarNode implements Node {
	private TypeNode type;
	private IdNode idNode;
	private Node exp;
	private STentry entryVariable;
	private boolean argument = false;
	
	public DecVarNode(Node type, IdNode idNode, Node exp) {
		this.type = (TypeNode)type;
		this.idNode = idNode;
		this.exp = exp;
	}

	@Override
	public String toPrint(String indent) {
		String stringa = " DecVar "+ type.toPrint(indent) + " " + idNode.toPrint(indent);
		if(exp != null) {
			stringa += " = " + exp.toPrint(indent);
		}
		return indent + stringa +"\n";
	}

	@Override
	public Node typeCheck() {
		if(this.exp != null) {
			Node expType = exp.typeCheck();
			if(expType != null) {
				if(!(SimpLanPlusLib.isSubtype(type,expType))) {
					System.err.println("Type of the variable isn't correct");
					System.exit(-1);
				}
				entryVariable.getEffect().setInitialized();
			}else {
				System.err.println("Type of the expression isn't valid.");
				System.exit(-1);
			}
		}
		return null;
	}

	@Override
	public String codeGeneration() {
		if (this.exp != null){
			//se c'è assegnamento lo si memorizza all'indirizzo dell'offset corrispondente
			return exp.codeGeneration() +  // r1 = cgen(stable, exp)
					"swfp " + entryVariable.getOffset() + "\n";  //sw r1 -> entry.offset($fp)
		}

		//se exp è null
		return "";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		if(exp != null) {
            output.addAll(exp.checkSemantics(env));
		}
	    HashMap<String,STentry> hm = env.getSymTable().get(env.getNestingLevel());
	    STentry entry = new STentry(env.getNestingLevel(),type, env.getOffset(),false); 
        if(hm.put(idNode.getId(),entry) != null) {
			output.add(new SemanticError("Var " + idNode.getId()+" is already declared."));
        }
        if(exp != null)
        	entry.getEffect().setInitialized();
        env.decrementOffset();
        entryVariable = entry;
		return output;
	}

	public Node getExp() {
		return exp;
	}

	public STentry getEntry() {
		return entryVariable;
	}

	public void setEntry(STentry entryVariable) {
		this.entryVariable = entryVariable;
	}

	public void setExp(Node exp) {
		this.exp = exp;
	}

	public IdNode getIdNode() {
		return idNode;
	}

	public boolean getArgument() {
		return argument;
	}

	public void setArgument() {
		argument = true;
	}
}
