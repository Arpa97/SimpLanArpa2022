package ast;

import java.util.ArrayList;
import util.Environment;
import util.SemanticError;

public interface Node {
    Node typeCheck();
    String codeGeneration();
    ArrayList<SemanticError> checkSemantics(Environment env);

    String Analyze();
}
