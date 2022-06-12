package ast;

import java.util.ArrayList;
import util.Environment;
import util.SemanticError;

public class StatementNode implements Node {
	private Node statement;
	
	public StatementNode(Node statement) {
		this.statement = statement;
	}

	@Override
	public String toPrint(String indent) {
		return indent +"Statement "+ statement.toPrint(indent) + "\n";
	}

	//OK
	@Override
	public Node typeCheck() {
		return statement.typeCheck();
	}

	@Override
	public String codeGeneration() {
		return statement.codeGeneration();
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		ArrayList<SemanticError> output = new ArrayList<SemanticError>();
		if(this.statement != null) {
			output.addAll(this.statement.checkSemantics(env));
		}
		return output;
	}
	
	public Node getStatement() {
		return this.statement;
	}
}