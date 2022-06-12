package ast.expNode;

import java.util.ArrayList;

import ast.IdNode;
import ast.Node;
import ast.STentry;
import ast.TypeNode;
import util.*;

public class BinExpNode implements Node {
	private Node first;
	private Node second;
	private String operator;

	public BinExpNode(Node first, Node second, String operator) {
		this.first = first;
		this.second = second;
		this.operator = operator;
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env){
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		if(this.first != null) {
			output.addAll(first.checkSemantics(env));
		}
		if(this.second != null) {
			output.addAll(second.checkSemantics(env));
		}
		return output;
	}

	@Override
	public String toPrint(String indent) {
		return "BinExp " + first.toPrint(indent) + " " + operator + " " + second.toPrint(indent)+ "\n";
	}

	@Override
	public Node typeCheck() {
		if (!(SimpLanPlusLib.isSubtype(first.typeCheck(),second.typeCheck()))){
			System.err.println("Terms type mismatch");
			System.exit(-1);
		}
		String className1 = first.getClass().getName();
		String className2 = second.getClass().getName();
		if(className1.contains("DerExpNode")) {
			DerExpNode variable = (DerExpNode)(first);
			IdNode detailVariable = variable.getIdNode();
			STentry entryStat = detailVariable.getEntry();
			if(entryStat.getEffect().getValue() < 1) {
				System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
				System.exit(-1);
			}
			entryStat.getEffect().setUsed();
		}
		if(className2.contains("DerExpNode")) {
			DerExpNode variable = (DerExpNode)(second);
			IdNode detailVariable = variable.getIdNode();
			STentry entryStat = detailVariable.getEntry();
			if(entryStat.getEffect().getValue() < 1) {
				System.err.println("The value of the variable " + detailVariable.getId() + " isn't defined");
				System.exit(-1);
			}
			entryStat.getEffect().setUsed();
		}
		TypeNode firstType = (TypeNode) first.typeCheck();
		if(operator.contains("'+'") || operator.contains("'-'") || operator.contains("'*'") || operator.contains("'/'") || operator.contains("'<'") || operator.contains("'>'")
				|| operator.contains("'<='") || operator.contains("'>='")) {
			if(!(firstType.getText().equals("int"))) {
				System.err.println("The operator requires int type");
				System.exit(-1);
			}
			if(operator.contains("'+'") || operator.contains("'-'") || operator.contains("'*'") || operator.contains("'/'")) {
				return new TypeNode("int");
			}
			if(operator.contains("'<'") || operator.contains("'>'") || operator.contains("'<='") || operator.contains("'>='")) {
				return new TypeNode("bool");
			}
			
		}
		if(operator.contains("'&&'") || operator.contains("'||'")) {
			if(!(firstType.getText().equals("bool"))) {
				System.err.println("The operator requires bool type");
				System.exit(-1);
			}
			return new TypeNode("bool");
			
		}
		if(operator.contains("==") || operator.contains("!=")) {
			return new TypeNode("bool");
		}
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}

	public Node getFirst() {
		return first;
	}

	public Node getSecond() {
		return second;
	}
}