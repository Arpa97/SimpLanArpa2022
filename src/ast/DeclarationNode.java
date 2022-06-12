package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public class DeclarationNode implements Node {
	private Node dec;

	public DeclarationNode(Node node) {
		dec = node;
	}

	@Override
	public String printer(String indent) {
		return indent +"Declaration "+ dec.printer(indent) + "\n";
	}

	@Override
	public Node typeCheck() {
		return dec.typeCheck();
	}

	@Override
	public String codeGeneration() {
		return dec.codeGeneration();
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return dec.checkSemantics(env);
	}

	public Node getDeclaration() {
		return dec;
	}
}
