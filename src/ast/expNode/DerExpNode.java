package ast.expNode;

import java.util.ArrayList;
import ast.IdNode;
import ast.Node;
import util.Environment;
import util.SemanticError;

public class DerExpNode implements Node {
	private IdNode id;

	public DerExpNode(IdNode id) {
		this.id = id;
	}

	@Override
	public String toPrint(String indent) {
		return indent + "DerExpNode " + id.toPrint(indent) + "\n";
	}

	@Override
	public Node typeCheck() {
		return id.typeCheck();
	}

	@Override
	public String codeGeneration() {
		return "";
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return id.checkSemantics(env);
	}

	public String getId() {
		return id.getId();
	}

	public IdNode getIdNode() {
		return id;
	}
}