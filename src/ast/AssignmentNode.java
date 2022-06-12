package ast;

import java.util.ArrayList;

import ast.expNode.DerExpNode;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

public class AssignmentNode implements Node {
	private IdNode idNode;
	private Node exp;
	private Node type;
	private STentry entryVariable;
	
	public AssignmentNode(IdNode idNode, Node exp) {
		this.idNode = idNode;
		this.exp = exp;
	}

	@Override
	public String toPrint(String indent) {
		return indent +"Assignment "+ idNode.toPrint(indent) + " = " + exp.toPrint(indent) + "\n";
	}

	@Override
	public Node typeCheck() {
		if(exp == null) {
			System.err.println("The expression is null.");
			System.exit(-1);
		}
		if(!(SimpLanPlusLib.isSubtype(type, exp.typeCheck()))){
			System.err.println("Incompatible value for variable "+idNode.getId());
			System.exit(-1);
		}
		entryVariable.getEffect().setInitialized();
		if(exp.getClass().getName().contains("DerExpNode")) {
			DerExpNode variableAssigned = (DerExpNode)(exp);
			IdNode variable1 = variableAssigned.getIdNode();
			STentry entryAssigned = variable1.getEntry();
			entryAssigned.getEffect().setUsed();
		}
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		STentry flag = null;
		int i = env.getNestingLevel();
		while(i>=0 && flag==null) {
			flag = (env.getSymTable().get(i--)).get(this.idNode.getId());
		}
		if(flag != null) {
			setType(flag.getType());
			if(this.exp != null) {
				output.addAll(this.exp.checkSemantics(env));
			}
			entryVariable = flag;
		} else {
			output.add(new SemanticError("Variable " + this.idNode.getId() + " not defined"));
		}
		return output;
	}
	
	private void setType(Node type) {
		this.type=type;
	}

	
	public Node getExp() {
		return exp;
	}

	public IdNode getIdNode() {
		return idNode;
	}
}