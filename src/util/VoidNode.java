package util;

import ast.Node;

import java.util.ArrayList;

public class VoidNode implements Node {

    public VoidNode() {
    }

    @Override
    public Node typeCheck() {
        return this;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }

    @Override
    public String Analyze() {
        return "Void Node:\n";
    }
}
