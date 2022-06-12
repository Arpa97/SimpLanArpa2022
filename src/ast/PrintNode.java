package ast;

import java.util.ArrayList;

import ast.expNode.DerExpNode;
import util.Environment;
import util.SemanticError;

public class PrintNode implements Node {
	private Node exp;
	
	public PrintNode(Node exp) {
		this.exp = exp;
	}

	@Override
	public String toPrint(String indent) {
		return indent + " print " + exp.toPrint(indent) + "\n";
	}

	@Override
	public Node typeCheck() {
		if(exp != null) {
			Node type = exp.typeCheck();
			if(exp.getClass().getName().contains("DerExpNode")) {
				DerExpNode exp1 = (DerExpNode)(exp);
				exp1.getIdNode().getEntry().getEffect().setUsed();
			}
			return type;
		}else {
			System.err.println("The expression is null.");
			System.exit(-1);
		}
		return null;
	}

	@Override
	public String codeGeneration() {

		return exp.codeGeneration()
				+ "print\n";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		if(exp != null) {
			output.addAll(exp.checkSemantics(env));
		}else
			output.add(new SemanticError("The expression is null."));
		return output;
	}
	
	public Node getExp() {
		return exp;
	}
}