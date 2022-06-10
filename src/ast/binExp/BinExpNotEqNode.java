package ast.binExp;

import ast.BoolTypeNode;
import ast.DerExpNode;
import ast.Node;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.util.ArrayList;

public class BinExpNotEqNode implements Node {

    private Node left;
    private Node right;
    private String op;

    public BinExpNotEqNode(Node left, Node right, String op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public Node typeCheck() {
        if( !( SimpLanPlusLib.isSubtype(left.typeCheck(), right.typeCheck()) ||
                SimpLanPlusLib.isSubtype(right.typeCheck(), left.typeCheck()) ) ){

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
        return new BoolTypeNode();
    }

    @Override
    public String codeGeneration() {
        /*
        cgen(s, e2)
        push $a0
        cgen(s, e1)
        $t1 <- top
        beq $a0 $t1 eq_branch:
        //case false
        push 1
        b end_if
        
        eq_branch: push 0
        end_if
         */

        String eq_branch = SimpLanPlusLib.freshFunLabel();
        String end_if = SimpLanPlusLib.freshFunLabel();
        return right.codeGeneration() +
                "lr1\n" +
                left.codeGeneration() +
                "sr2\n" +
                "beq " + eq_branch + "\n" +
                "lir1 1\n" +                    //caso not equal: si setta r1 a 1 (true)
                "b" + end_if + "\n" +
                eq_branch + ":\n" +
                "lir1 0\n" +                    //caso equal: si setta r1 a 0 (false)
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
        return "BinNotEqExp: "+this.left.Analyze()+ this.op + this.right.Analyze() + "\n"; // left + != + right

    }
}
