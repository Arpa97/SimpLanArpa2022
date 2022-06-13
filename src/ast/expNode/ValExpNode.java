package ast.expNode;

import java.util.ArrayList;
import ast.Node;
import ast.TypeNode;
import util.Environment;
import util.SemanticError;

public class ValExpNode implements Node {
	private int expVal;

	public ValExpNode(int ExpVal) {
		this.expVal = ExpVal;
	}

	@Override
	public String printer(String indent) {
		return indent+ "ValueExpNode "+this.expVal	+ "\n";
	}

	@Override
	public Node typeCheck() {
		return new TypeNode("int");
	}

	@Override
	public String codeGeneration() {
		return "lir1 "+expVal+"\n"; //li r1 val   s->[]
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		return new ArrayList<SemanticError>();
	}

	public int getExpVal() {
		return expVal;
	}
}