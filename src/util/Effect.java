package util;

public class Effect {
    
    //inizialmente le settiamo entrambe a non utilizzate
    
    // 0 dichiarata, 1 inizializzata, 2 usata
    private int varEffect;

    public Effect() {
        this.varEffect = 0;
    }

    public int getVarEffect() {
        return varEffect;
    }

    public void setVarEffect(int varEffect) {
        this.varEffect = varEffect;
    }
    
    public void setInitialized(){
        if(this.varEffect < 1) {
            this.varEffect = 1;
        }
    }
    
    public void setUsed(){
        this.varEffect = 2;
    }
}
