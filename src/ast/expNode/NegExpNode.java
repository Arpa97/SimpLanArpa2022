package ast.expNode;

import ast.IdNode;
import ast.Node;
import ast.STentry;
import ast.TypeNode;

public class NegExpNode extends BaseExpNode {
	private Node expr;

	public NegExpNode(Node expr) {
		super(expr);
		this.expr = expr;

	}

	@Override
	public String printer(String emptySpace) {
		return emptySpace + "negExpNode " + "-" + expr.printer(emptySpace) + "\n";
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
		if(!(exprType.getText().equals("int"))) {
				System.err.println("The operator requires int type");
				System.exit(-1);
		}
		return new TypeNode("int");
	}

	@Override
	public String codeGeneration() {
		return expr.codeGeneration() +   //r1 <- cgen(stable, negExp)        S->[] 
				"lir2 -1\n" +           //li r2 -1                          S->[]
				"mult\n";               //mul r1 r1 r2      (r1* -1)        S->[]

	}
}