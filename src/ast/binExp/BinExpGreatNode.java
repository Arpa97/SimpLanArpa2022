package ast.binExp;

import ast.IntTypeNode;
import ast.Node;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.util.ArrayList;

public class BinExpGreatNode implements Node {

    private Node left;
    private Node right;
    private String op;

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
        /*
        cgen(s, e2)
        push $a0
        cgen(s, e1)
        $t1 <- top //ha il valore di e2
        beq $a0 $t1 true_branch:
        //case false
        push 0
        
        true_branch:
        push 1
        b true_branch
         */

        String true_branch = SimpLanPlusLib.freshFunLabel();
        String end_if = SimpLanPlusLib.freshFunLabel();
        return right.codeGeneration() +
                "lr1\n" +
                left.codeGeneration() +
                "sr2\n" +
                "beq " + true_branch + "\n" +
                "lir1 0\n" +
                "b" + end_if + "\n" +
                true_branch + ":\n" +
                "lir1 1\n" +
                end_if + ":\n";
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
        return null;
    }

    public BinExpGreatNode(Node left, Node right, String op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }
}
