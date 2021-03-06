package ast;

import java.util.ArrayList;

import util.Environment;
import util.SemanticError;

public interface Node {
   
  String printer(String indent);

  
  Node typeCheck();
  
  
  String codeGeneration();
  
  
  ArrayList<SemanticError> checkSemantics(Environment env);
  
}  