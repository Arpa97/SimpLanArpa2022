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
        //farò una codegen basica anche se quella del compito era più difficile con dei label
        /*
            cgen(stable, e1 && e2) =
                true_branch = new_label();
                end_if = new_label();
                cgen(s, e1)
                $t1 <- true
                push $a0
                beq $a0 $t1 true branch //caso true or (qualcos'altro) va direttamente a true
                cgen(s, e2)
                beq $a0 $t1 true branch //caso false (perchè non passa prima) or true del secondo elemento
                //caso false or false
                b endif
           true_branch: pop
           end_if: pop
        
         */
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
