package ast;

import java.util.ArrayList;
import util.Environment;
import util.SemanticError;

public class TypeNode implements Node {
	private String text;

	public TypeNode(String text) {
		this.text = text;
	}

	@Override
	public String toPrint(String indent) {
		return indent +"Type " + text ;
	}

	@Override
	public Node typeCheck() {
		return null;
	}

	@Override
	public String codeGeneration() {
		return "";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return new ArrayList<SemanticError>();
	}

	public String getText(){
		return text;
	}
}
