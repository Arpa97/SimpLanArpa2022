package util;

public class Effect {
    
    //inizialmente le settiamo entrambe a non utilizzate
    
    // 0 dichiarata, 1 inizializzata, 2 usata
    private int varEffect;

    public Effect() {
        this.varEffect = 0;
    }

    public boolean getUsed() {
        return this.varEffect == 2;
    }

    public boolean getInitialized() {
        return this.varEffect == 1;
    }
    
    public void setInitialized(){
        if(this.varEffect < 1) {
            this.varEffect = 1;
        }
    }
    
    public void setUsed(){
        
        if(this.varEffect >= 1) this.varEffect = 2;
    }
    
    public int getVarEffect(){return this.varEffect;}
            
}
