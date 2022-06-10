package ast.binExp;

import ast.BoolTypeNode;
import ast.DerExpNode;
import ast.Node;
import util.Effect;
import util.Environment;
import util.SemanticError;
import util.SimpLanPlusLib;

import java.util.ArrayList;

public class BinExpAndNode implements Node {

    private Node left;
    private Node right;
    private String op;
    

    public BinExpAndNode(Node left, Node right, String op) {
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
        //farò una codegen basica anche se quella del compito era più difficile con dei label
        /*
            cgen(stable, e1 && e2) =
                false_branch = new_label();
                end_if = new_label();
                cgen(s, e1)
                $t1 <- false
                push $a0
                beq $a0 $t1 false branch
                cgen(s, e2)
                beq $a0 $t1 false branch
                b end_label
           false_branch: pop
           b end_label: pop
        
         */
        
        return right.codeGeneration() +     //r1 <- cgen(stable, right)                 S->[]
                "lr1\n"+                    //r1 -> top of Stack        S->right        S->[r1]
                left.codeGeneration() +     //r2 <- cgen(stable, left)                  S->[r1]
                "sr2\n"+                    //r2 -> top of Stack        r2<-right       S->[]    
                "and\n";              // add r1 <- r1 && r2       left op right   S->[] 
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
        return "BinAndExp: "+this.left.Analyze()+ this.op + this.right.Analyze() + "\n"; // left + && + right
    }
}
