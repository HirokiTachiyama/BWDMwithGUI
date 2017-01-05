package testcases;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.overturetool.vdmj.lex.LexException;
import org.overturetool.vdmj.syntax.ParserException;

import bwdm.AnalyzedData;

public class TestCase_AnalyzedData_Arg1 {

	@BeforeClass
	public static void BeforeClassは１回だけ() throws LexException, ParserException, FileNotFoundException, IOException{
		new AnalyzedData ("Arg1.vdmpp", "Arg1.csv", "KikkawaToolAndExampleData/data/");
		System.out.println(AnalyzedData.getSpecificationAllText());
	}

	@Before
	public void Beforeはテストメソッド実行前に１回ずつ() { }

	@Test
	public void vdmFilePathとcsvFilePathのテスト() {
		assertThat(AnalyzedData.getVdmFilePath(), is(equalTo("KikkawaToolAndExampleData/data/Arg1.vdmpp")));
		assertThat(AnalyzedData.getCsvFilePath(), is(equalTo("KikkawaToolAndExampleData/data/Arg1.csv")));
	}

	@Test
	public void argumentTypesJoinedとformalArgumentsJoinedのテスト() {
		assertThat(AnalyzedData.getArgumentTypesJoined(), is(equalTo("nat")));
		assertThat(AnalyzedData.getFormalArgumentsJoined(), is(equalTo("a")));
	}

	@Test
	public void intNumとnatNumとnat1Numのテスト(){
		assertThat(AnalyzedData.getIntNum(), is(equalTo(0)));
		assertThat(AnalyzedData.getNatNum(), is(equalTo(0)));
		assertThat(AnalyzedData.getNat1Num(), is(equalTo(0)));
	}

	@Test
	public void argumentTypesのテスト(){
		fail("テスト未記述");
	}
	@Test
	public void formalArgumentsのテスト(){
		fail("テスト未記述");
	}
	@Test
	public void ifConditionsのテスト(){
		fail("テスト未記述");
	}
	@Test
	public void ifConditionsjoinedのテスト(){
		fail("テスト未記述");
	}
	@Test
	public void ifConditionsJoinedInCameForwardのテスト(){
		fail("テスト未記述");
	}
	@Test
	public void preConditionsJoinedのテスト(){
		fail("テスト未記述 事前条件に関する機能自体未実装");
	}
	@Test
	public void preConditionsのテスト(){
		fail("テスト未記述 事前条件に関する機能自体未実装");
	}

}

