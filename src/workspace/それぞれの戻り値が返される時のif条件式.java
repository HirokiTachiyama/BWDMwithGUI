package workspace;

import org.overturetool.vdmj.lex.LexException;
import org.overturetool.vdmj.syntax.ParserException;

import bwdm.AnalyzedData;

public class それぞれの戻り値が返される時のif条件式 {


	public static void main(String[] args) throws ParserException, LexException{

		String kikkawa = "KikkawaToolAndExampleData/";

		new AnalyzedData(kikkawa+"data/mod2.vdmpp",
				kikkawa+"data/mod2.csv", "");

		AnalyzedData.printInformation();

		AnalyzedData.printVdmFileText();

	}


}


