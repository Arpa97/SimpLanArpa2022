package ast.binExp;

import ast.BoolTypeNode;
import ast.DerExpNode;
import ast.IntTypeNode;
import ast.Node;
import com.sun.org.apache.xpath.internal.operations.Bool;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.util.ArrayList;

public class BinExpEqNode implements Node {

    private Node left;
    private Node right;
    private String op;

    public BinExpEqNode(Node left, Node right, String op) {
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
        beq $a0 $t1 true_branch
        //case false
        push 0
        b end_if
        
        true_branch: push 1
        end_if:
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
        return "BinEqExp: "+this.left.Analyze()+ this.op + this.right.Analyze() + "\n"; // left + == + right
    }
}
