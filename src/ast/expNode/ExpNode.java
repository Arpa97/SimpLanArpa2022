package ast.expNode;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.STentry;
import util.Environment;
import util.SemanticError;

public class ExpNode implements Node {
	private Node expr;
	
	public ExpNode(Node expr) {
		this.expr = expr;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "ExpNode " + expr.toPrint(indent) + "\n";
	}

	@Override
	public Node typeCheck() {
		Node type = expr.typeCheck();
		String className1 = expr.getClass().getName();
		if(className1.contains("DerExpNode")) {
			DerExpNode variable = (DerExpNode)(expr);
			IdNode detailVariable = variable.getIdNode();
			STentry entryStat = detailVariable.getEntry();
			if(entryStat.getEffect().getValue() < 1) {
				System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
				System.exit(-1);
			}
			entryStat.getEffect().setUsed();
		}
		return type;
	}

	@Override
	public String codeGeneration() {
		return "";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return expr.checkSemantics(env);
	}
}