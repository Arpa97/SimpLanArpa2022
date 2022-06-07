package ast.binExp;

import ast.IntTypeNode;
import ast.Node;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.util.ArrayList;

public class BinExpPlusNode implements Node {

    private Node left;
    private Node right;
    private String op;

    public BinExpPlusNode(Node left, Node right, String op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public Node typeCheck() {
        if(!SimpLanPlusLib.isSubtype(left.typeCheck(), new IntTypeNode()) &&
                !SimpLanPlusLib.isSubtype(right.typeCheck(), new IntTypeNode())){
            System.out.println("Incompatible operands type for the operator " + op +"\n");
            System.exit(0);

        }
        return new IntTypeNode();
    }

    @Override
    public String codeGeneration() {
        return right.codeGeneration()+
                "lr1\n"+
                left.codeGeneration()+
                "sr2\n"+
                "add\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(this.left!=null) {
            res.addAll(left.checkSemantics(env));
        }
        if(this.right!=null) {
            res.addAll(right.checkSemantics(env));
        }
        return res;
    }

    @Override
    public String Analyze() {
        return "BinExpPlusExp: " +this.left.Analyze()+ this.op + this.right.Analyze() + "\n"; // left + + + right
    }
}
