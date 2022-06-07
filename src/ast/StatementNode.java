package ast;

import jdk.nashorn.internal.ir.Block;
import util.Environment;
import util.SemanticError;

import java.util.ArrayList;

public class StatementNode implements Node {

    private Node statement;
    private Boolean retValue; //per vedere se lo statement ha un tipo di ritorno

    public StatementNode(Node statement) {
        this.statement = statement;
    }
    
    public void checkRetValue(){
        if (statement instanceof BlockNode){
            this.retValue = ((BlockNode) statement).checkRetValue(); //da sistemare in BlockNode
        } /*else if(statement instanceof  ProgramNode){
            this.retValue = ((ProgramNode) statement).checkRetValue();
        }*/ 
        else {
            if (statement instanceof ReturnNode){
                this.retValue = true;
            }
            else this.retValue = false;
        }
    }
    
    public boolean getCheckRet(){
        checkRetValue();
        return this.retValue;
    }

    public Node getStatement() {
        return statement;
    }

    public Boolean getRetValue() {
        return retValue;
    }

    public void setStatement(Node statement) {
        this.statement = statement;
    }

    public void setRetValue(Boolean retValue) {
        this.retValue = retValue;
    }

    @Override
    public Node typeCheck() {
        return statement.typeCheck(); //ste
    }

    @Override
    public String codeGeneration() {
        return statement.codeGeneration(); //ste
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if (statement != null) {
            res.addAll(this.statement.checkSemantics(env));
        }
        return res;
    }

    @Override
    public String Analyze() {
        return "Statement: " + this.statement.Analyze() + "\n";
    }
}