package ast;

import util.Effect;

public class STentry {

    private int nl;
    private Node type;
    private int offset;
    private Effect effect;

    public STentry(int n, int os) {
        nl = n;
        offset = os;
        effect = new Effect();
    }

    public STentry(int n, Node t, int os) {
        nl = n;
        type = t;
        offset = os;
        effect = new Effect();
    }

    public void addType(Node t) {
        type = t;
    }

    public Node getType() {
        return type;
    }

    public int getOffset() {
        return offset;
    }

    public int getNestinglevel() {
        return nl;
    }

    public String Analyze() { //
        return "STentry: nestlev " + Integer.toString(nl) +"\n"+
                "STentry: type\n" +
                type.Analyze() +
                "STentry: offset " + Integer.toString(offset) + "\n";
    }
    
    public Effect getEffect(){
        return this.effect;
    }

}