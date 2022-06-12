package ast.expNode;

import ast.IdNode;
import ast.Node;
import ast.STentry;
import ast.TypeNode;

public class NotExpNode extends BaseExpNode {
	private Node expr;

	public NotExpNode(Node expr) {
		super(expr);
		this.expr = expr;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + "NotExpNode " + "!" + expr.toPrint(indent) + "\n";
	}

	@Override
	public Node typeCheck() {
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
		TypeNode exprType = (TypeNode) expr.typeCheck();
		if(!(exprType.getText().equals("bool"))) {
				System.err.println("The operator requires bool type");
				System.exit(-1);
		}
		return new TypeNode("bool");
	}

	@Override
	public String codeGeneration() {
		return expr.codeGeneration() +           // r1 <- cgen(stable, expNode);                 s -> []
				"not\n";
	}
}