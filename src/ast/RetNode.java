package ast;

import java.util.ArrayList;

import ast.expNode.DerExpNode;
import util.Environment;
import util.SemanticError;

public class RetNode implements Node {
	private Node exp;
	
	public RetNode(Node exp) {
		this.exp = exp;
	}

	@Override
	public String toPrint(String indent) {
		if(exp != null)
			return indent + "return " + exp.toPrint(indent) + "\n";
		return indent + "return" + "\n";
	}

	@Override
	public Node typeCheck() {
		//la exp.typeCheck() deve ritornare qualcosa in questo caso! (forse in tutti)
		if(exp != null) {
			Node expType = exp.typeCheck();
			if(exp.getClass().getName().contains("DerExpNode")) {
				DerExpNode exp1 = (DerExpNode)(exp);
				IdNode variable = exp1.getIdNode();
				STentry entryVariable = variable.getEntry();
				entryVariable.getEffect().setUsed();
			}
			return expType;
		}
		return null;
	} //deve ritornare il tipo del return che va confrontato con il valore della funzione

	@Override
	public String codeGeneration() {
		return exp.codeGeneration() + "srv\n";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		if(this.exp != null) {
			return this.exp.checkSemantics(env);
		}
		return new ArrayList<SemanticError>();
	}

	public Node getExp() {
		return this.exp;
	}
}
