package workspace;

import java.io.File;

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

import org.overturetool.vdmj.lex.LexException;
import org.overturetool.vdmj.syntax.ParserException;

import bwdm.AnalyzedData;
import bwdm.BoundaryValueAnalyze;

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

    	final Label information = new Label();
    	information.setText("hogerafuga");

        Button hogeBtn = new Button();
        hogeBtn.setText("Select hoge++ specific");
        final Label hogeLbl = new Label();
        hogeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        vboxRight.getChildren().addAll(information);


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
                    vdmLbl.setText(vdmPath);
                }
            }
        });
        vboxLeft.getChildren().addAll(vdmBtn, vdmLbl);

        Button dtBtn = new Button();
        dtBtn.setText("Select decision table");
        final FileChooser fc2 = new FileChooser();
        fc2.setTitle("Select decision table");
        final Label dtLbl = new Label();
        dtBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File importFile = fc.showOpenDialog(primaryStage);
                if (importFile != null) {
                	dtPath = importFile.getPath().toString();
                    dtLbl.setText(dtPath);
                }
            }
        });
        vboxLeft.getChildren().addAll(dtBtn, dtLbl);


    	//構文解析実行ボタン
    	Button analyzeBtn = new Button();
        final Label analyzeLabel = new Label();
        analyzeBtn.setText("Do Parsing");
        analyzeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	try {
					new AnalyzedData(vdmPath, dtPath, "");
				} catch (LexException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (ParserException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
            	analyzeLabel.setText(AnalyzedData.getVdmFilePath());
            }
        });
        vboxLeft.getChildren().addAll(analyzeBtn, analyzeLabel);

    	//境界値分析実行ボタン
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
            	bvLabel.setText(BoundaryValueAnalyze.getInputData().toString());
            }
        });
        vboxLeft.getChildren().addAll(bvBtn, bvLabel);





        StackPane root = new StackPane();
        root.getChildren().addAll(hbox);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("BWDMwithGUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}


