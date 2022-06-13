package util;

import ast.Node;

public class SimpLanPlusLib {
	private static int labelC =0;
	private static int funLabC =0;
	private static String codeFun ="";

	//Check if type of node a is subtype (<=) of type b
	public static boolean isSubtype (Node a, Node b) {
		return a.printer("").equals(b.printer("")) ;
	}

	public static String newLabel() {
	return "label"+(labelC++);
	}

	public static String newFunLabel() {
	return "function"+(funLabC++);
	}

	public static void putCode(String c) {
	codeFun +="\n"+c; //aggiunge una linea vuota di separazione prima di funzione
	}

	public static String getCode() {
	return codeFun;
	}
}
