package ast;

public class NegExpNode extends BaseExpNode{
    public NegExpNode(Node exp) {
        super(exp);
    }
    @Override
    public String Analyze() {
        return "\n"+"negExpNode"+this.exp.Analyze();
    }
}