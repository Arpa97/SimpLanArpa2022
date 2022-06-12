package util;

public class Effect {
	private int val;

	public Effect(boolean initialized) {
		//check to set a variable initialized by constructor
		//in this way, we do not need to do further instruction to initialize a variable
		if(initialized == true)
			this.val = 1;
		else 
			this.val = 0;
			
	}

	//Set method to initialize a variable
	public void setInitialized() {
		if(this.val < 1)
			this.val = 1;
	}
	//Set method to sign a variable as Used
	public void setUsed() {
		if(this.val >= 1)
			this.val = 2;
	}

	//Set method to directly set a value. Used to reset value to 0
	public void setValue(int value) {
		this.val = value;
	}

	//Getter method
	public int getValue() {
		return this.val;
	}

}