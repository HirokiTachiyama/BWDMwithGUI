package bwdm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.overturetool.vdmj.lex.LexException;
import org.overturetool.vdmj.syntax.ParserException;

public class BwdmMain {

	public static void main(String[] args) {
		Boolean fileMakeFlag = false;

		try {
			new AnalyzedData("mod2.vdmpp", "mod2.csv", "Cygwin/data/");

			AnalyzedData.printInformation();
		} catch (LexException | ParserException e) {
			e.printStackTrace();
		}

		//抽出した情報から境界値分析、入力値生成
		new BoundaryValueAnalyze();
		BoundaryValueAnalyze.printBoundaryValueTable();
		BoundaryValueAnalyze.printInputValue();

		//入力値をif条件文判定してkey作成
		new EvaluationOfConditions();
		EvaluationOfConditions.printEvaluationResult();

		outputBoundaryValueTestcase(fileMakeFlag);


		System.out.println("／(^o^)＼");

	}


	public static void outputBoundaryValueTestcase(Boolean fileOrStandardOutput){

		String[][] inputData = BoundaryValueAnalyze.getInputData();
		ArrayList<String> evaluationResult = EvaluationOfConditions.getEvaluationResult();

		new DecisionTable(AnalyzedData.getCsvFilePath());

		System.out.println("引数の個数:" + AnalyzedData.getArgumentTypes().size() + "\n");
		System.out.print("引数型: ");
		for(int i=0; i<AnalyzedData.getArgumentTypes().size(); i++){
			System.out.print(AnalyzedData.getArgumentTypes().get(i) + " ");
		}
		System.out.println("\n\n入力値 --> 期待出力値");


		if(fileOrStandardOutput) {
			FileWriter outputFile=null;

			//ファイル作成、引数個、引数の書き込み
			try {
				outputFile =
						new FileWriter(new File(AnalyzedData.getVdmFilePath().replace(".vdmpp", "") + "Testcase.csv"));
				//引数の個数の書き込み
				outputFile.write("引数の個数:" + AnalyzedData.getArgumentTypes().size() + "\n\n");
				//引数型の書き込み
				String tmp = new String();
				for(int i=0; i<AnalyzedData.getArgumentTypes().size(); i++){
					tmp = tmp +"第"+(i+1)+"引数:"+ AnalyzedData.getArgumentTypes().get(i) +",";
				}
				outputFile.write("引数型:," + tmp + "\n\n");
				outputFile.write("テストケースNo. 入力データ  --> 期待出力データ" + "\n");
				System.out.println("ファイル作成成功！");

				for(int i=0; i<inputData.length; i++){
					try {
						outputFile.write("No."+(i+1)+","+String.join(",", inputData[i]) + ",-->,\"" +
								DecisionTable.getBooleanSequenceToAction().get(evaluationResult.get(i)) + "\"\n");
						System.out.println("ファイル書き込み成功！"+evaluationResult.get(i));
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("ファイル書き込みしくった！");
					}
				}

				outputFile.close();
				System.out.println("ファイルくろーず成功！");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("ファイル作成しくった！");
			}
		} else {
			for(int i=0; i<inputData.length; i++){
				System.out.println("("+String.join(",", inputData[i]) + ") --> " +
						DecisionTable.getBooleanSequenceToAction().get(evaluationResult.get(i)));
			}
		}


	}


}
