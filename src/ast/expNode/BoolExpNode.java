package ast.expNode;

import java.util.ArrayList;
import ast.Node;
import ast.TypeNode;
import util.Environment;
import util.SemanticError;

public class BoolExpNode implements Node {
	private boolean parseBoolean;

	public BoolExpNode(boolean parseBoolean) {
		this.parseBoolean = parseBoolean;
	}
	
	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env){
		return new ArrayList<SemanticError>();
	}

	@Override
	public String toPrint(String indent) {
		if(parseBoolean == true)
			return "Bool: true" + "\n";
		return "Bool: false" + "\n";
	}

	@Override
	public Node typeCheck() {
		return new TypeNode("bool");
	}

	@Override
	public String codeGeneration() {
		return "";
	}

	public boolean getBoolExp() {
		return parseBoolean;
	}
}
