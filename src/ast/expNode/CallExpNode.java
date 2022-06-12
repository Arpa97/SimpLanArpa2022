package ast.expNode;

import java.util.ArrayList;
import ast.Node;
import util.Environment;
import util.SemanticError;

public class CallExpNode implements Node {
	public Node visit;

	public CallExpNode(Node visit) {
		this.visit = visit;
	}

	@Override
	public String printer(String indent) {
		return indent + "Call " + visit.printer(indent) + "\n";
	}

	@Override
	public Node typeCheck() {
		return visit.typeCheck();
	}

	@Override
	public String codeGeneration() {
		return visit.codeGeneration();
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return visit.checkSemantics(env);
	}

	public Node getCall() {
		return visit;
	}
}
