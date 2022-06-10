package ast;

import java.util.ArrayList;

import util.Effect;
import util.Environment;
import util.SemanticError;

public interface Node {
    
    //per espressioni, torna il tipo int o bool (o void?)
    //per una dichiarazione, null
    Node typeCheck();
    String codeGeneration();
    ArrayList<SemanticError> checkSemantics(Environment env);
    String Analyze();
}
