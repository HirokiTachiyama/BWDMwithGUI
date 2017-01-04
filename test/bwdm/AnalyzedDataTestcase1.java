package bwdm;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.overturetool.vdmj.lex.LexException;
import org.overturetool.vdmj.syntax.ParserException;

public class AnalyzedDataTestcase1 {
	
	//ファイル名、パス
	private String directory=null;
	private String vdmFileName=null;
	private static String vdmFilePath=null;
	private static String csvFileName=null;
	private static String csvFilePath=null;

	//型情報
	private static String argumentTypesJoined=null;    //引数型のばらす前 int*nat*nat1
	private static ArrayList<String> argumentTypes;    //引数型 int nat nat1...
	private static int intNum=0, natNum=0, nat1Num=0; //それぞれの型の数

	//仮引数情報
	private static String formalArgumentsJoined=null; //仮引数のばらす前 a,b,c
	private static ArrayList<String> formalArguments; //仮引数 a b c...

	//if条件文情報
	//上から仮引数1つ目、2つ目...で格納してる
	//ifConditionsJoined = {"4<a", "a<7",
	//                      "-3<b","b>100","0<b",
	//                      "c<10","3<c","c>-29","c<-40"};
	//という感じ

	@SuppressWarnings("rawtypes")
	private static ArrayList[] ifConditionsJoined;
	//入力値をifConditionsについてtrue/false判定するときに
	//条件文の来た順が必要だった(吉川ツールが来た順に並んでるため)
	private static ArrayList<String> ifConditionsJoinedInCameForward;
	@SuppressWarnings("rawtypes")
	private static HashMap[][] ifConditions; //条件文を演算子と両辺の3つに分解
	//ifConditions = {"4", "<a", "a", "<", "7"
	//                       "-3", "<", "b", "b", ">", "100","0", "<", "b",
	//                       "c", "<", "10","3", "<", "c","c", ">", "-29","c", "<" ,"-40"};

	//事前条件文情報 中身はif条件文情報と似た感じ
	@SuppressWarnings("rawtypes")
	private static ArrayList[] preConditionsJoined; //事前条件文
	/* 使う前に初期化してから使うこと！*/
	@SuppressWarnings("rawtypes")
	private static HashMap[][] preConditions; //条件文を演算子と両辺の3つに分解

	
	
	@Before
	private void setupVariablesForTest(){
		
	}

	@Test
	public void test() throws LexException, ParserException {
		AnalyzedData ad = new AnalyzedData("Arg1.csv", "Arg1.vdmpp", "data/");
		
		
		
		
		//fail("まだ実装されていません");
	}

}
