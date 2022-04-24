package ast;


public class BaseExpNode extends ExpNode{
    public BaseExpNode(Node exp) {
        super(exp);
    }
    @Override
    public String Analyze() {
        return "\n"+"BaseExpNode"+this.exp.Analyze();
    }
}
