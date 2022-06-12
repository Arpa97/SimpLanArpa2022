package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.antlr.v4.runtime.*;

public class ErrorListener extends BaseErrorListener {
	@Override
	public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
		// error String contains the error that will be printed
		String error ="Error at line " + line + " at position " + charPositionInLine + "\n: " + msg+ "\n";
		//create a new file (if does not exist yet) and append errors to it
		File file = new File("errori.txt");
		file.delete();
		FileWriter w;
		try{
			w = new FileWriter(file,true);
			w.write(error);
			w.flush();
		}catch(IOException e1){
			e1.printStackTrace();
		}
	}
}

