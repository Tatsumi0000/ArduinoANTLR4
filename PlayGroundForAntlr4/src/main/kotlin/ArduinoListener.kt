import org.antlr.v4.runtime.tree.ParseTree

class ArduinoListener() : CPP14BaseListener() {
    // デバッグ用のカウント
    private var debugCount = 0
    // 配列の何番目かを管理する変数
    private var variableCount: Int = 0
    // setup関数に入ったかどうか．
    private var isSetupFunction: Boolean = false
    // loop関数に入ったかどうか．
    private var isLoopFunction: Boolean = false
    // 型，変数名，値が入る可変Map
    private var variable: MutableMap<String, Variable> = mutableMapOf()
    //    private var variable: MutableMap<String, Variable> = mutableMapOf()
    private var variableType = ""
    private var variableName = ""
    private var variableValue = ""
    private var arduinoConverter: ArduinoConverter // Arduinoのピン番号や状態（HIGHやOUTPUT）を変換する
    private var arduinoPinStatus: ArduinoPinStatus // Arduinoのピン状態を持つ
    private var arduinoSelectionStatement: ArduinoSelectionStatement

    init {
        this.variableCount = 0
        this.arduinoPinStatus = ArduinoPinStatus()
        this.arduinoConverter = ArduinoConverter(arduinoPinStatus = this.arduinoPinStatus)
        this.arduinoSelectionStatement = ArduinoSelectionStatement()
    }

    override fun enterTranslationunit(ctx: CPP14Parser.TranslationunitContext?) {

    }

    override fun enterTypespecifier(ctx: CPP14Parser.TypespecifierContext?) {

    }

//    override fun enterDeclarationseq(ctx: CPP14Parser.DeclarationseqContext?) {
//        println("DEBUG: ${ctx?.getChild(0)?.getChild(0)?.getChild(0)?.text}")
//
//    }
//
//    override fun enterSimpledeclaration(ctx: CPP14Parser.SimpledeclarationContext?) {
//        println("${ctx?.getChild(0)?.getChild(0)?.text}")
//    }

    override fun exitDeclarationseq(ctx: CPP14Parser.DeclarationseqContext?) {

    }

    override fun enterDeclaration(ctx: CPP14Parser.DeclarationContext?) {
    }

    //  型が入ってる
//    override fun enterSimpletypespecifier(ctx: CPP14Parser.SimpletypespecifierContext?) {
//        // 型の名前がintかStringならtrue
//        if (typeList.contains(ctx?.getChild(0)?.text)) {
//            // loop関数に入ってないなら(false)
//            if (!this.isLoopFunction) {
//                this.variableType = ctx?.getChild(0)?.text ?: "null"
//                println("Simpletypespecifier: ${ctx?.getChild(0)?.text}")
//            }
//        }
//    }

    /**
     * このノードから[0]型，[1]変数名=値，[2];に分岐
     * このノードに入った時に呼ばれる
     */
    override fun enterSimpledeclaration(ctx: CPP14Parser.SimpledeclarationContext?) {
        val simpledeclarationChildCount = ctx?.childCount ?: 0
        val initDeclaratorListTree: ParseTree
        // int i = 0;みたいな時
        if (simpledeclarationChildCount == 3) {
//            println("int i = 0;みたいな時")
            initDeclaratorListTree = ctx?.getChild(1) ?: run {
                print("｛変数名=値｝のところがnullでヤンス．")
                return
            }
            this.variableType = ctx.getChild(0)?.text ?: "Not Found Type"
            this.variableName = initDeclaratorListTree.getChild(0).getChild(0).text
            this.variableValue = initDeclaratorListTree.getChild(0).getChild(1).getChild(0).getChild(1).text
            this.variable[this.variableName] = Variable(type = this.variableType, value = this.variableValue)

        } else if (simpledeclarationChildCount == 2) { // i = 0;みたいな時
            //println("i = 0;みたいな時")
            initDeclaratorListTree = ctx?.getChild(0) ?: run {
                print("｛変数名=値｝のところがnullでヤンス．")
                return
            }
            // ここの書き方は，もっとおしゃれな書き方がきっとあると思います．
            this.variableName = initDeclaratorListTree.getChild(0).getChild(0).getChild(0).text
            this.variableValue = initDeclaratorListTree.getChild(0).getChild(1).getChild(0).getChild(1).text
            // 変数名をKeyとして変数の値を書き変える．
            this.variable[this.variableName]?.value = this.variableValue
        }
    }

    /**
     * ここは，
     * pinMode，delay，Serial.begin，digitalWrite
     * などが入っている．
     */
    override fun enterPostfixexpression(ctx: CPP14Parser.PostfixexpressionContext?) {
//        println("Postfixexpression: ${ctx?.text}")
        val postFixExpressionChildCount: Int = ctx?.childCount ?: 0
        val postFixExpressionTree0: ParseTree  // 要素が4つあるときの0個目の要素取得に使用
        val expressionListTree: ParseTree
        /**
         * Postfixexpressionに入りうるパターンが複数存在する．
         * そのため，
         * digital.write(LED_PIN, OUTPUT)
         * みたいな時は，要素が4つに分かれるため要素数が4になるので，それで分岐させる．
         */
        if (postFixExpressionChildCount == 4) {
            postFixExpressionTree0 = ctx?.getChild(0) as ParseTree  // 強制的にキャストしているがここは絶対にヌルポで落ちない
            expressionListTree = ctx.getChild(2)
            if (postFixExpressionTree0.text == "pinMode") {
                println("pinModeだお")
                arduinoConverter.changePinStatus(
                    expressionListTree.getChild(0).getChild(2).text,
                    variable,
                    expressionListTree.getChild(0).getChild(0).text
                )
            }
            if (postFixExpressionTree0.text == "digitalWrite") {
                println("digitalWriteだお")
                arduinoConverter.changePinStatus(
                    expressionListTree.getChild(0).getChild(2).text,
                    variable,
                    expressionListTree.getChild(0).getChild(0).text
                )
            }
        }
    }

    /**
     * if文に入るとき
     */
    override fun enterSelectionstatement(ctx: CPP14Parser.SelectionstatementContext?) {
        val expression: ParseTree =
            ctx?.getChild(2)?.getChild(0)?.getChild(0)?.getChild(0)?.getChild(0)?.getChild(0)?.getChild(0)?.getChild(0)?.getChild(
                0
            )?.getChild(0) ?: run {
                println("nullでヤンス．")
                return
            }
        this.debugCount++ // debug
        val judge: Boolean
        // RelationalexpressionContextなら木をもう1つ深くする
        println("${debugCount}: ${expression.getChild(0).text}")
        judge = if (expression.getChild(0) is CPP14Parser.RelationalexpressionContext) {
            arduinoSelectionStatement.judgeCondition(expression.getChild(0), this.variable)
        } else {
            arduinoSelectionStatement.judgeCondition(expression, this.variable)
        }
        println("${this.debugCount}回目のif文を評価した結果${judge}ですよ．")
        // if文を評価した結果trueだと色々頑張る
        if (judge) {
            val statementSeq = ctx.getChild(3)
//            println("DEBUG:: ${statementSeq.text}")
            for (i in 0..statementSeq.childCount) {
                if (ctx.getChild(i) is CPP14Parser.StatementContext) {
                    val variableName = ctx.getChild(i).getChild(0).getChild(0).getChild(0).text
                    val variableValue = ctx.getChild(i).getChild(0).getChild(0).getChild(2).text
                    this.variable[variableName]?.value = variableValue
                }
            }
        }
    }

    // 変数variableを返すゲッター
    fun getVariable(): MutableMap<String, Variable> {
        return this.variable
    }

    fun getArduinoPinStatus(): ArduinoPinStatus {
        return this.arduinoPinStatus
    }
}