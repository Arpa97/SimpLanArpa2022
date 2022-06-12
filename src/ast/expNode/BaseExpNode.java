package ast.expNode;

import ast.Node;

public class BaseExpNode extends ExpNode {
	private Node visit;
	
	public BaseExpNode(Node visit) {
		super(visit);
		this.visit=visit;
	}
	
	@Override
	public String toPrint(String indent) {
		return indent + "BaseExpNode (" + visit.toPrint(indent) + ")" + "\n";
	}
}