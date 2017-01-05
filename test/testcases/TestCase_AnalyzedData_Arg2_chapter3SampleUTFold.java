package testcases;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.overturetool.vdmj.lex.LexException;
import org.overturetool.vdmj.syntax.ParserException;

import bwdm.AnalyzedData;

public class TestCase_AnalyzedData_Arg2_chapter3SampleUTFold {

	@BeforeClass
	public static void BeforeClassは１回だけ() throws LexException, ParserException, FileNotFoundException, IOException{
		new AnalyzedData ("Arg2_chapter3SampleUTFold.vdmpp",
				          "Arg2_chapter3SampleUTFold.csv",
				          "KikkawaToolAndExampleData/data/");
		System.out.println(
				"************************************\n" +
                "* TEST MESSAGE                     *\n" +
                "* TEST CASE : Arg2_chapter3_UTFold *\n" +
		        "* START                            *\n" +
                "************************************\n" );

		System.out.println(AnalyzedData.getSpecificationAllText());
		AnalyzedData.printInformation();
	}

	@Before
	public void Beforeはテストメソッド実行前に１回ずつ() { }

	@Test
	public void vdmFilePathとcsvFilePathのテスト() {
		/*
		 * vdmFilePath, csvFilePath
		 */
		assertThat(AnalyzedData.getVdmFilePath(),
				is(equalTo("KikkawaToolAndExampleData/data/Arg2_chapter3SampleUTFold.vdmpp")));
		assertThat(AnalyzedData.getCsvFilePath(),
				is(equalTo("KikkawaToolAndExampleData/data/Arg2_chapter3SampleUTFold.csv")));
	}

	@Test
	public void 引数型情報を格納している変数のテスト(){
		/*
		 * argumentTypesJoined, argumentTypes
		 */
		String 引数型を抽出して繋げたままの文字列 = AnalyzedData.getArgumentTypesJoined();
		ArrayList<String> 引数型を抽出し分割したリスト = AnalyzedData.getArgumentTypes();

		assertThat(引数型を抽出して繋げたままの文字列, equalTo("int*nat"));
		assertThat(引数型を抽出し分割したリスト.get(0), equalTo("int"));
		assertThat(引数型を抽出し分割したリスト.get(1), equalTo("nat"));

	}

	@Test
	public void 仮引数を格納している変数のテスト(){
		/*
		 * formalArgumentsJoined, formalArguments
		 */
		String 仮引数を抽出して繋げたままの文字列 = AnalyzedData.getFormalArgumentsJoined();
		ArrayList<String> 仮引数を抽出して分割したリスト = AnalyzedData.getFormalArguments();

		assertThat(仮引数を抽出して繋げたままの文字列, equalTo("a,b"));
		assertThat(仮引数を抽出して分割したリスト.get(0), equalTo("a"));
		assertThat(仮引数を抽出して分割したリスト.get(1), equalTo("b"));
	}

	@Test
	public void 引数の個数のテスト(){
		/*
		 * intNum, natNum, nat1Num
		 */
		assertThat(AnalyzedData.getIntNum(), is(equalTo(1)));
		assertThat(AnalyzedData.getNatNum(), is(equalTo(1)));
		assertThat(AnalyzedData.getNat1Num(), is(equalTo(0)));
	}

	@Test
	public void if条件式を格納している変数のテスト(){
		/*
		 * ifConditionsJoined, ifConditionsJoinedInCameForward, ifConditions
		 */
		ArrayList<?>[] if条件式繋がったまま = AnalyzedData.getIfConditionsJoined();
		ArrayList<String> if条件式来た順 = AnalyzedData.getIfConditionsJoinedInCameForward();
		HashMap[][] if条件式 = AnalyzedData.getIfConditions();

		//仮引数aのif条件式
		assertThat(if条件式繋がったまま[0].get(0), equalTo("a<=10"));
		assertThat(if条件式繋がったまま[0].get(1), equalTo("-1<a"));
		assertThat(if条件式繋がったまま[0].get(2), equalTo("a<20"));
		//仮引数bのif条件式
		assertThat(if条件式繋がったまま[1].get(0), equalTo("b>0"));
		assertThat(if条件式繋がったまま[1].get(1), equalTo("b<=-3"));

		assertThat(if条件式来た順.get(0), equalTo("a<=10"));
		assertThat(if条件式来た順.get(1), equalTo("-1<a"));
		assertThat(if条件式来た順.get(2), equalTo("b>0"));
		assertThat(if条件式来た順.get(3), equalTo("b<=-3"));
		assertThat(if条件式来た順.get(4), equalTo("a<20"));

		assertThat(if条件式[0][0].get("LeftHand").toString() +
				   if条件式[0][0].get("Symbol").toString()   +
				   if条件式[0][0].get("RightHand").toString() , equalTo("a<=10"));
		assertThat(if条件式[0][1].get("LeftHand").toString() +
				   if条件式[0][1].get("Symbol").toString()   +
				   if条件式[0][1].get("RightHand").toString() , equalTo("-1<a"));
		assertThat(if条件式[0][2].get("LeftHand").toString() +
				   if条件式[0][2].get("Symbol").toString()   +
				   if条件式[0][2].get("RightHand").toString() , equalTo("a<20"));
		assertThat(if条件式[1][0].get("LeftHand").toString() +
				   if条件式[1][0].get("Symbol").toString()   +
				   if条件式[1][0].get("RightHand").toString() , equalTo("b>0"));
		assertThat(if条件式[1][1].get("LeftHand").toString() +
				   if条件式[1][1].get("Symbol").toString()   +
				   if条件式[1][1].get("RightHand").toString() , equalTo("b<=-3"));

	}

	@AfterClass
	public static void おしまい(){
		System.out.println(
				"************************************\n" +
				"* TEST MESSAGE                     *\n" +
				"* FINISHED                         *\n" +
				"* ALL GREEN!!                      *\n" +
				"************************************\n" );
	}

	/*
	@Test
	public void preConditionsJoinedのテスト(){
		fail("テスト未記述 事前条件に関する機能自体未実装");
	}

	@Test
	public void preConditionsのテスト(){
		fail("テスト未記述 事前条件に関する機能自体未実装");
	}
	 */

}

