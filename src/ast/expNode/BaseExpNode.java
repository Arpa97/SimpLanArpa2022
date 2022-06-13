package ast.expNode;

import ast.Node;

public class BaseExpNode extends ExpNode {

	private Node node;
	
	public BaseExpNode(Node nod) {
		super(nod);
		this.node=nod;
	}
	
	@Override
	public String printer(String emptySpace) {
		return emptySpace + "BaseExpNode (" + node.printer(emptySpace) + ")" + "\n";
	}
}