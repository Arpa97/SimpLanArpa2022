package ast;

import util.Effect;

public class STentry {
 
  private int nl;
  private Node type;
  private int offset;
  private Effect effect;
  
  private DecFunNode reference;
  
  public STentry (int nl, int offset,boolean initialized){
	  this.nl = nl;
	  this.offset = offset;
	  effect = new Effect(initialized);
  } 
   
  public STentry (int nl, Node type, int offset,boolean initialized){
	  this.nl = nl;
	  this.type = type;
	  this.offset = offset;
	  effect = new Effect(initialized);
  }

  public void addType (Node type){
	  this.type = type;
  }
  
  public Node getType (){
	  return type;
  }

  public int getOffset (){
	  return offset;
  }
  
  public int getNestinglevel (){
	  return nl;
  }

  public Effect getEffect() {
	  return effect;
  }
 
  public String toPrint(String s) { //
	   return s+"STentry: nestlev " + Integer.toString(nl) +"\n"+
			  s+"STentry: type" +  type.printer(s+"  ") +
		      s+"\nSTentry: offset " + Integer.toString(offset) + "\n"+
		      s+"STentry: initialized is " + (effect.getValue() < 1) + "\n"+
	   		  s+"STentry: used is " + (effect.getValue() < 2) + "\n";
  }

    public void setReference(DecFunNode reference){
        this.reference = reference; // reference to the function declaration
    }

    public DecFunNode getReference(){
        return this.reference;
    }
}  