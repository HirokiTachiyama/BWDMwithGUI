package workspace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oldBWDM_codes.AnalyzedData;
import oldBWDM_codes.BoundaryValueAnalyze;
import oldBWDM_codes.DecisionTable;
import oldBWDM_codes.EvaluationOfConditions;

import org.overturetool.vdmj.lex.LexException;
import org.overturetool.vdmj.syntax.ParserException;

public class FX extends Application {

	String vdmPath, dtPath;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(final Stage primaryStage) {

    	HBox hbox = new HBox();
        VBox vboxLeft = new VBox();
        VBox vboxRight = new VBox();
        hbox.getChildren().addAll(vboxLeft, vboxRight);


        //デシジョンテーブル選択ボタンとラベル
        Button dtBtn = new Button();
        dtBtn.setText("Select decision table");
        final FileChooser fc2 = new FileChooser();
        fc2.setTitle("Select decision table");
        final Label dtLbl = new Label();
        dtBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File importFile = fc2.showOpenDialog(primaryStage);
                if (importFile != null) {
                	dtPath = importFile.getPath().toString();
                    dtLbl.setText(dtPath);
                }
            }
        });
        vboxLeft.getChildren().addAll(dtBtn, dtLbl);

        //VDMファイル選択ボタンとラベル
        Button vdmBtn = new Button();
        vdmBtn.setText("Select vdm++ specific");
        final FileChooser fc = new FileChooser();
        fc.setTitle("Select vdm++ specific");
        final Label vdmLbl = new Label();
        vdmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File importFile = fc.showOpenDialog(primaryStage);
                if (importFile != null) {
                	vdmPath = importFile.getPath().toString();

                	try {
    					new AnalyzedData(vdmPath, dtPath, "");
    				} catch (LexException e) {
    					// TODO 自動生成された catch ブロック
    					e.printStackTrace();
    				} catch (ParserException e) {
    					// TODO 自動生成された catch ブロック
    					e.printStackTrace();
    				}

                	String lblText = new String("");
                	lblText += "Selected File:" + vdmPath + "\n\n";
                	try {
    					lblText += AnalyzedData.getSpecificationAllText();
    				} catch (FileNotFoundException e) {
    					// TODO 自動生成された catch ブロック
    					e.printStackTrace();
    				} catch (IOException e) {
    					// TODO 自動生成された catch ブロック
    					e.printStackTrace();
    				}
                    vdmLbl.setText(lblText);
                }
            }
        });
        vboxLeft.getChildren().addAll(vdmBtn, vdmLbl);

    	//境界値分析実行ボタンとラベル
    	Button bvBtn = new Button();
        final Label bvLabel = new Label();
        bvBtn.setText("Do Boundary Value Anlysis");
        bvBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
        		//抽出した情報から境界値分析、入力値生成
        		new BoundaryValueAnalyze();
        		BoundaryValueAnalyze.printBoundaryValueTable();
        		BoundaryValueAnalyze.printInputValue();
            	bvLabel.setText(BoundaryValueAnalyze.getBoundaryValueTableString());
            }
        });
        vboxRight.getChildren().addAll(bvBtn, bvLabel);

    	//もろもろをやるボタン
    	Button moromoroBtn = new Button();
        final Label moromoroLbl = new Label();
        moromoroBtn.setText("Output Testcases");
        moromoroBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
        		//入力値をif条件文判定してkey作成
        		new EvaluationOfConditions();
        		new DecisionTable(dtPath);

        		String[][] inputData = BoundaryValueAnalyze.getInputData();
        		ArrayList<String> evaluationResult = EvaluationOfConditions.getEvaluationResult();
        		HashMap<String, String> booleanSequenceToAction = DecisionTable.getBooleanSequenceToAction();
        		String str = "";

    			for(int i=0; i<inputData.length; i++){
    				str += "(" + String.join(",", inputData[i]) + ") --> " +
    						booleanSequenceToAction.get(evaluationResult.get(i)) + "\n";
    			}

            	moromoroLbl.setText(str);
            }
        });
        vboxRight.getChildren().addAll(moromoroBtn, moromoroLbl);


        StackPane root = new StackPane();
        root.getChildren().addAll(hbox);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("BWDMwithGUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}


