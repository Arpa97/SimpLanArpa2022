package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.antlr.v4.runtime.*;

public class ErrorListener extends BaseErrorListener {
	@Override
	public void syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
		// The string containing the error to be printed
		String error ="Error at line " + line + " at position " + charPositionInLine + ": " + msg+ "\n"; // Try printing to a file the string error in append; if the file don't exist, create it
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

