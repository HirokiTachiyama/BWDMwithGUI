package oldBWDM_codes;

/**
 * BWDM 3rd Edition 2016/01/26 version
 * 主な機能、注意点、追加機能など
 *
 * ・関数定義を読み込み境界値テストケースを出力
 *  -引数型はint, nat, nat1の整数値のみに対応
 *  -引数は2つまで 3つ以上の関数定義はSystem.exit(-1)でメッセージを出力して終了
 *  -引数の変数が (a, abc) みたいな感じだとEvaluationOfConditions辺りでポシャりそう
 * ・if条件文を読み込み境界値を生成
 *  -if条件文の論理式は<, <=, >, >=のみに対応 例外処理等で対応させておいて
 *  -if条件文が2つ以上つながったものには未対応 今のところ、andかorが来たらメッセ出して終了する。
 *  -if条件文内の片方が変数、片方が整数値である必要が有る 両辺が変数のものは、System.exit(-1)で終了
 *
 *
 * ・boundaryValueTable内の要素が重複していたら除去する処理を追記
 * (BoundaryValueAnalyze.recursiveRemoverOfOverlappedElement)
 *  -もしnatMinと0などの重なりが現れた場合の処理を考える必要有り。
 *
 * ・Cygwin/source/lexeme_list.cの207-211をコメントアウトした
 *  原因不明なエラーが出てcsvを生成出来なかった為
 *
 * ・2147....などの型の最大値はやっぱりintMaxなどにした方が、
 *  intMax+1などとの整合性が取れて、見た目などが良い その方向性で今後調整
 * ・正規表現の+はエスケープシーケンスを入れれば対応出来る 再考する
 *
 * 20160202
 * ・*Minを*Minimumへ変更
 *
 * 20160206
 * ・*MinimumをMinへ戻した MinimumにするならMaximumにするべきで、長たらしくてめんどくさい
 *  その関係で、ツール、論文の各部を修正する必要あり。
 *
 * 20160207
 * ・クラス名と関数名が被っててたらformalArg読み込みでバグる
 */
