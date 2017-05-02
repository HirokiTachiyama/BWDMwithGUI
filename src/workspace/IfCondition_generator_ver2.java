package workspace;

import java.io.BufferedReader;

/*
 * TODO
 * if式のインデックスづけ機能
 * 各出力行に至るために満たすべきif条件式の導出
 * if条件式を満たす入力データ生成
 */


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.overturetool.vdmj.Settings;
import org.overturetool.vdmj.lex.LexException;
import org.overturetool.vdmj.lex.LexTokenReader;
import org.overturetool.vdmj.syntax.ParserException;

import bwdm.AnalyzedData;

public class IfCondition_generator_ver2 {


	
	/*
	 * if_elseファイル作成時、
	 * 現状は戻り値としてseq of char("を含んでいるもののみ処理しているため)しか
	 * 対応していない
	 */
	
	public static void main(String[] args) throws ParserException, LexException, IOException{

		//afFileGenerator();
		//indexIfConditions();

		ifElseExtracter();

	} //end main

	private static void ifElseExtracter() throws LexException, ParserException, IOException{
		new AnalyzedData("KikkawaToolAndExampleData/data/mod2.vdmpp",
				 "KikkawaToolAndExampleData/data/mod2.csv", "");
		//AnalyzedData.printInformation();
		//AnalyzedData.printVdmFileText();

		//analyzed file create
		String ifElseFilePath = AnalyzedData.getVdmFilePath().replace(".vdmpp", "") + ".if_else";
		FileWriter ifElseFile = null;
		try {
			ifElseFile = new FileWriter(new File(ifElseFilePath));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("making ifelse file failed, return.");
			System.exit(1);
		}

		//if-else式の抜き取り
		//if, condition, then, returnのみにする
		//conditionは()の有る無しに関わらず全てのTokenをくっつけて、
		//最後に()を消去する
		LexTokenReader ltr = new LexTokenReader
				(new File("KikkawaToolAndExampleData/data/mod2.vdmpp"),
				Settings.dialect);
		String currentToken = ltr.getLast().toString();

		//if開始箇所まで進める
		while(!currentToken.equals("if")){
			//System.out.println(ltr.getLast().toString());
			currentToken = ltr.nextToken().toString();
		}

		System.out.println(ltr.getLast().toString());

		String conditionTmp = ""; //条件式のtokenをくっつけていく
		while(!currentToken.equals(";")){
			switch (currentToken) {
			case "if" :
			case "else":
				ifElseFile.write(currentToken + "\n");
				break;

			case "then":
				ifElseFile.write(conditionTmp.replace("(", "").replace(")", "") + "\n");
				conditionTmp = "";
				break;
			default:
				if(currentToken.contains("\"")){ //もしも戻り値だったら
					ifElseFile.write(currentToken + "\n");
				} else {
					conditionTmp = conditionTmp + currentToken;
				}
				break;
			}
			System.out.println(currentToken);
			currentToken = ltr.nextToken().toString();
		}

		//System.out.println("className is " + className);
		ltr.close();
		ifElseFile.close();

	}


	/*
	 * 現在未使用
	 * VDMJの字句解析を行い、Tokenを一つ一つ改行しながら並べたファイルを生成する
	 */
	private static void afFileGenerator() throws LexException, ParserException{
		new AnalyzedData("KikkawaToolAndExampleData/data/mod2.vdmpp",
				 "KikkawaToolAndExampleData/data/mod2.csv", "");
		//AnalyzedData.printInformation();
		//AnalyzedData.printVdmFileText();

		//analyzed file create
		String analyzedFilePath = AnalyzedData.getVdmFilePath().replace(".vdmpp", "") + ".af";
		FileWriter analyzedFile = null;
		try {
			analyzedFile = new FileWriter(new File(analyzedFilePath));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("making analyzed file failed, return.");
			System.exit(1);
		}

		//字句解析開始 字句解析は二回行う
		LexTokenReader ltr = new LexTokenReader
				(new File("KikkawaToolAndExampleData/data/mod2.vdmpp"),
				Settings.dialect);
		String currentToken, className="";
		//class名を一回通ったフラグ class名は最初から二番目と最後の二回登場する
		Boolean firstClassNameIsPassed = false;
		currentToken = ltr.getLast().toString();

		//1回目, クラス名取得
		while(!currentToken.equals("end")){
			if(currentToken.equals("class")){
				currentToken = className = ltr.nextToken().toString();
			}
			currentToken = ltr.nextToken().toString();
		}
		//System.out.println("className is " + className);
		ltr.close();

		//2回目, analyzed fileに書き込む クラス記述の最後はクラス名
		ltr = new LexTokenReader(new File("KikkawaToolAndExampleData/data/mod2.vdmpp"),
				 Settings.dialect);
		currentToken = ltr.getLast().toString();
		//1回目で取得したクラス名 かつ 2回目の登場ならおしまい
		while( !(currentToken.equals(className) && firstClassNameIsPassed) ) {
			//class名が来たらフラグをtrueにする
			if(currentToken.equals(className)){
				firstClassNameIsPassed = true;
			}
			//System.out.println(currentToken);
			try {
				analyzedFile.write(currentToken + "\n");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("writing analyzed file failed, return.");
				System.exit(1);
			}
			currentToken = ltr.nextToken().toString();
		}
		try {
			analyzedFile.write(currentToken + "\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("writing analyzed file failed, return.");
			System.exit(1);
		}
		ltr.close();

		//file closing
		try {
			analyzedFile.close();
			System.out.println("Output Analyzed File is succeeded.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("closing analyzed file failed, return.");
			System.exit(1);
		}

	}


	/*
	 * 現在未使用
	 */
	private static void indexIfConditions() {

		ArrayList<String> afTokens = new ArrayList<String>();
		ArrayList<Integer> index = new ArrayList<Integer>();

		try {
            FileReader afRedaer =
            		new FileReader(new File("KikkawaToolAndExampleData/data/mod2.af"));
            BufferedReader br = new BufferedReader(afRedaer);

            String line;
            while ((line = br.readLine()) != null) {
            	afTokens.add(line);
            	//System.out.println(line);
            }

            //終了処理
            br.close();
            afRedaer.close();

        } catch (IOException ex) {
            //例外発生時処理
            ex.printStackTrace();
        }

		int tokenCounter = 0;
 		int ifNestLevel = 0;
		while(tokenCounter < afTokens.size()){
			if(afTokens.get(tokenCounter).equals("if")){
				ifNestLevel++;
			} else if(afTokens.get(tokenCounter).equals("else")){
				index.add(ifNestLevel);
				tokenCounter++;
				if(afTokens.get(tokenCounter).equals("if")){
					ifNestLevel++;
				}
			}

			index.add(ifNestLevel);
			tokenCounter++;

		}

		tokenCounter = 0;
		while(tokenCounter < afTokens.size()) {
			System.out.println(index.get(tokenCounter) + " " + afTokens.get(tokenCounter) );
			tokenCounter++;
		}

	}


	/*
	 * tokens <- getAllTokensArray()
	 * int currentTokenIndex = 0
	 * int currentNextLevel  = 0
	 * String currentToken = ""
	 *
	 * while(currentToken != "if") {
	 *   currentToken <- tokens.get(currentTokenIndex++)
	 * }
	 *
	 * recursiveIfElseReader(currentNestLevel, currentTokenIndex)
	 *
	 *
	 *
	 *
	 * recursiveIfElseReader(int currentNestLevel, int currentTokenIndex) then
	 *   while() then
	 *     currentToken <- tokens.get(currentTokenIndex)
	 *     if currentToken == "if" then
	 *       recursiveIfElseReader(currentNestLevel + 1, currentTokenIndex)
	 *     else if currentToken == "else" then
	 *
	 *
	 *
	 *
	 *   end
	 * end
	 *
	 */


	/*
	 * 現在未使用
	 * if-elseの範囲内を再帰的にインデックス付けしようと試みたが
	 * 必要なくなった
	 */
	private static void recursiveIfElseReader(ArrayList<String> _afTokens,
											ArrayList<Integer> _index,
											int _tokenCounter, int _ifNestLevel){
		if(_afTokens.get(_tokenCounter).equals("if")){
			_ifNestLevel++;
		}else if(_afTokens.get(_tokenCounter).equals("else")){
			_index.add(_ifNestLevel);
			_tokenCounter++;
			if(_afTokens.get(_tokenCounter).equals("if")){
				_ifNestLevel++;
			}
		}else{
			_index.add(_ifNestLevel);
			_tokenCounter++;
		}

		_index.add(_ifNestLevel);
		_tokenCounter++;


	}


}


