package ast.binExp;

import ast.DerExpNode;
import ast.IntTypeNode;
import ast.Node;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.util.ArrayList;

public class BinExpMinNode implements Node {

    private Node left;
    private Node right;
    private String op;

    public BinExpMinNode(Node left, Node right, String op) {
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
        if(left.getClass().getName().contains("DerExpNode")){
            DerExpNode exp1 = (DerExpNode) (left);
            exp1.getIdNode().getEntry().getEffect().setUsed();
        }
        if(right.getClass().getName().contains("DerExpNode")){
            DerExpNode exp1 = (DerExpNode) (right);
            exp1.getIdNode().getEntry().getEffect().setUsed();
        }
        return new IntTypeNode();
    }

    @Override
    public String codeGeneration() {
        return right.codeGeneration()+
                "lr1\n"+
                left.codeGeneration()+
                "sr2\n"+
                "sub\n";
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
        return "BinExpMinExp: " +this.left.Analyze()+ this.op + this.right.Analyze() + "\n"; // left + - + right

    }
}
