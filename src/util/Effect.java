package util;

public class Effect {
	private int status;

	public Effect(boolean initialized) {
		if(initialized == true)
			this.status = 1;
		else 
			this.status = 0;
			
	}

	public boolean isLess(Effect e) {
		return this.status < e.status;
	}

	public void setInitialized() {
		if(this.status < 1)
			this.status = 1;
	}

	public void setUsed() {
		if(this.status >= 1)
			this.status = 2;
	}

	public int getValue() {
		return this.status;
	}

  public void setValue(int value) {
	  this.status = value;
  }
}