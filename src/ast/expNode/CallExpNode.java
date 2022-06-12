package ast.expNode;

import java.util.ArrayList;
import ast.Node;
import util.Environment;
import util.SemanticError;

public class CallExpNode implements Node {
	public Node visitCall;

	public CallExpNode(Node visitCall) {
		this.visitCall = visitCall;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "Call " + visitCall.toPrint(indent) + "\n";
	}

	@Override
	public Node typeCheck() {
		return visitCall.typeCheck();
	}

	@Override
	public String codeGeneration() {
		return "";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return visitCall.checkSemantics(env);
	}

	public Node getCall() {
		return visitCall;
	}
}
