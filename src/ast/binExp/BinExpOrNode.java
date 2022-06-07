package ast.binExp;

import ast.BoolTypeNode;
import ast.Node;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.util.ArrayList;

public class BinExpOrNode implements Node {

    private Node left;
    private Node right;
    private String op;

    public BinExpOrNode(Node left, Node right, String op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public Node typeCheck() {
        if(!SimpLanPlusLib.isSubtype(left.typeCheck(), new BoolTypeNode()) &&
                !SimpLanPlusLib.isSubtype(right.typeCheck(), new BoolTypeNode())){
            System.out.println("Incompatible operands type for the operator " + op +"\n");
            System.exit(0);

        }
        return new BoolTypeNode();
    }

    @Override
    public String codeGeneration() {
        return right.codeGeneration()+
                "lr1\n"+
                left.codeGeneration()+
                "sr2\n"+
                "or\n";
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
        return "\n"+"BinOrExp: "+this.left.Analyze()+ this.op + this.right.Analyze(); // left + || + right

    }
}
