package workspace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.overturetool.vdmj.Settings;
import org.overturetool.vdmj.lex.LexException;
import org.overturetool.vdmj.lex.LexTokenReader;
import org.overturetool.vdmj.syntax.ParserException;

import bwdm.AnalyzedData;

public class IfElseSyntaxTree {

	private static IfNode root;
	private static BufferedReader if_elseBufferReader;

	/*
	 * if_elseファイル作成時、
	 * 現状は戻り値としてseq of char("を含んでいるもののみ処理しているため)しか
	 * 対応していない
	 */
	/*
	 * TODO
	 * 再帰的にreturnNodeを探す、見つけたらそのreturnNodeまでの条件文を表示する関数
	 * if条件式を満たす入力データ生成
	 */

	public static void main(String[] args) throws ParserException, LexException, IOException{

		if_elseFileGenerate();

		ifElseSyntaxTreeGenerate();

	} //end main


	//if式構文木を作る
	private static void ifElseSyntaxTreeGenerate() throws IOException {
		FileReader if_elseFileReader = new FileReader(new File("KikkawaToolAndExampleData/data/mod2.if_else"));
		//FileReader if_elseFileReader = new FileReader(new File("KikkawaToolAndExampleData/data/syntaxtree.if_else"));
		if_elseBufferReader = new BufferedReader(if_elseFileReader);

		//rootの準備
		String currentLine = if_elseBufferReader.readLine(); //最初はifなので無視
		currentLine = if_elseBufferReader.readLine(); //これは最初のifの条件式
		//多分
		root = ifNodeGenerate(currentLine, null, 0);
		root.isIfNode = true;
		root.isTrueNode = null;
		//これだけでおｋ
		if_elseBufferReader.close();

		recursivePrintNodes(root);

		recursiveReturnNodeFind(root);


	}

	private static IfNode ifNodeGenerate(String _condition,
			                                IfNode _parentNode,
			                                int _nodeLevel) throws IOException{
		IfNode ifNode = new IfNode(_condition, _nodeLevel);
		ifNode.parentNode = _parentNode;
		String nextToken;

		//trueNode
		nextToken = if_elseBufferReader.readLine();
		if(nextToken.equals("if")){//ifの場合、次のtokenは条件式なので読み込む
			String conditionStr = if_elseBufferReader.readLine();
			ifNode.conditionTrueNode = ifNodeGenerate(conditionStr, ifNode, _nodeLevel+1);
		} else {//ifじゃない場合、nextTokenにはreturnが入っている
			ifNode.conditionTrueNode = returnNodeGenerate(nextToken, ifNode, _nodeLevel+1);
		}
		ifNode.conditionTrueNode.isTrueNode = true;

		//else 特にすることは無いので無視
		//ということはif_elseファイルからelseを消しても問題無し？
		nextToken = if_elseBufferReader.readLine();

		//elseの次、falseNode
		//falseNode
		nextToken = if_elseBufferReader.readLine();
		if(nextToken.equals("if")){//ifの場合、次のtokenは条件式なので読み込む
			String conditionStr = if_elseBufferReader.readLine();
			ifNode.conditionFalseNode = ifNodeGenerate(conditionStr, ifNode, _nodeLevel+1);
		} else {//ifじゃない場合、nextTokenにはreturnが入っている
			ifNode.conditionFalseNode = returnNodeGenerate(nextToken, ifNode, _nodeLevel+1);
		}
		ifNode.conditionFalseNode.isTrueNode = false;

		return ifNode;
	}

	private static ReturnNode returnNodeGenerate(String returnStr,
													Node parentNode,
													int _nodeLevel){
		ReturnNode returnNode =  new ReturnNode(returnStr, _nodeLevel);
		returnNode.parentNode = parentNode;
		return returnNode;
	}


	//nodeを末端まで再帰的に情報を表示していく
	private static void recursivePrintNodes(Node node){

		//親がいなければroot
		if(node.parentNode == null){
			System.out.println("root,       nodeID:" + node.ID +
								" nodeLevel:" + node.nodeLevel +
								" condition:"+node.getConditionOrReturnStr());
			IfNode ifnode = (IfNode)node;
			recursivePrintNodes(ifnode.conditionTrueNode);
			recursivePrintNodes(ifnode.conditionFalseNode);
			return ;
		}

		if(node.isIfNode) {
			System.out.println("IfNode,     nodeID:" + node.ID +
								" parentNodeID:" + node.parentNode.ID +
								" nodeLevel:" + node.nodeLevel +
								" boolean:" + node.isTrueNode +
					            " condition:"+node.getConditionOrReturnStr());
			IfNode ifnode = (IfNode)node;

			recursivePrintNodes(ifnode.conditionTrueNode);
			recursivePrintNodes(ifnode.conditionFalseNode);

		} else {
			System.out.println("ReturnNode, nodeID:" + node.ID +
								" parentNodeID:" + node.parentNode.ID +
								" nodeLevel:" + node.nodeLevel +
								" boolean:" + node.isTrueNode +
								" Return:"+node.getConditionOrReturnStr());
		}
	}


	private static void recursiveReturnNodeFind(Node node) {
		if(!node.isIfNode) { //returnNodeならば
			System.out.print(node.getConditionOrReturnStr() + " ");
			Node tmpNode = node;
			while(tmpNode != null){ //下のbreak文が
				System.out.print(tmpNode.parentNode.getConditionOrReturnStr() + tmpNode.isTrueNode + " ");
				tmpNode = tmpNode.parentNode;
				if(tmpNode.parentNode == null) break;
			}
			System.out.println();
		} else {
			IfNode ifNode = (IfNode)node;
			recursiveReturnNodeFind(ifNode.conditionTrueNode);
			recursiveReturnNodeFind(ifNode.conditionFalseNode);
		}

	}



	/*
	 * if_elseファイル生成
	 * 最初のifから終わりのセミコロンまでを抜き出す
	 */
	private static void if_elseFileGenerate() throws LexException, ParserException, IOException{
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

		//System.out.println(ltr.getLast().toString());

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
			//System.out.println(currentToken);
			currentToken = ltr.nextToken().toString();
		}

		ifElseFile.write(";");


		ltr.close();
		ifElseFile.close();

	}


}
