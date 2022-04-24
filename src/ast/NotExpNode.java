package ast;

public class NotExpNode extends BaseExpNode{
    public NotExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String Analyze() {
        return "\n"+"NotExpNode "+this.exp.Analyze();
    }
}