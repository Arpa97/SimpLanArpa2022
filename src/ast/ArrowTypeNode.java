package ast;

import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class ArrowTypeNode implements Node {

    private ArrayList<Node> parlist;
    private Node ret;

    public ArrowTypeNode (ArrayList<Node> p, Node r) {
        parlist=p;
        ret=r;
    }

    public Node getRet () { //
        return ret;
    }

    public ArrayList<Node> getParList () { //
        return parlist;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        // TODO Auto-generated method stub
        return new ArrayList<SemanticError>();
    }

    //da rivedere, guardato da simplan del prof
    @Override
    public String Analyze() {
        String parlstr="";
        for (Node par:parlist)
            parlstr+=par.Analyze()+" ";
            return "ArrowType\n" + parlstr + ret.Analyze()+ "  ->" ;
    }

    //not used
    public Node typeCheck () {
        return null;
    }

    //not used
    public String codeGeneration() {
        return "";
    }

} 