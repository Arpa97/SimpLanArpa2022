package ast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import util.Environment;
import util.SemanticError;

public class ArrowTypeNode implements Node {
  private Node ret;
  private HashMap<ArgNode,TypeNode> parlist;
  private ArrayList<STentry> entrylist;

  public ArrowTypeNode (HashMap<ArgNode,TypeNode> parlist, Node ret,ArrayList<STentry> entrylist) {
    this.parlist = parlist;
    this.ret = ret;
    this.entrylist = entrylist;
  }

  public String printer(String s) {
	String parlstr="";
    for (Entry<ArgNode,TypeNode> par: parlist.entrySet())
      parlstr+=par.getKey().printer(s+"  ");
	return s+"ArrowType\n" + parlstr + ret.printer(s+"  ->") ;
  }

  @Override
  public ArrayList<SemanticError> checkSemantics(Environment env) {
	  return new ArrayList<SemanticError>();
  }
  
  //non utilizzato
  @Override
  public Node typeCheck () {
    return null;
  }

  //non utilizzato
  @Override
  public String codeGeneration() {
		return "";
  }

  public Node getRet () {
	  return ret;
  }

  public HashMap<ArgNode,TypeNode> getParList () {
	  return parlist;
  }

  public ArrayList<STentry> getEntry() {
	  return entrylist;
  }
} 