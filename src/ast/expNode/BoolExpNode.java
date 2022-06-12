package ast.expNode;

import java.util.ArrayList;
import ast.Node;
import ast.TypeNode;
import util.Environment;
import util.SemanticError;

public class BoolExpNode implements Node {

	private boolean boolP;

	public BoolExpNode(boolean boolP) {
		this.boolP = boolP;
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env){
		return new ArrayList<>();
	}

	@Override
	public String printer(String indent) {
		if(boolP == true)
			return "true" + "\n";
		return "false" + "\n";
	}

	@Override
	public Node typeCheck() {
		return new TypeNode("bool");
	}

	@Override
	public String codeGeneration() {
		return "lir1" + (boolP ?1:0)+"\n";
	}

	public boolean getBoolExp() {
		return boolP;
	}
}
