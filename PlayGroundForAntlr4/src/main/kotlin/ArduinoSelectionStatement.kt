import CPP.CPP14Parser
import org.antlr.v4.runtime.tree.ParseTree

/**
 * 式を評価する
 */
data class FormulaInt(
    var leftSideExpression: Double,
    var rightSideExpression: Double
)

data class FormulaBoolean(
    var leftSideExpression: Boolean,
    var rightSideExpression: Boolean
)

class ArduinoSelectionStatement {
    // [^0]0以外
    // ^0先頭が0
    private val regex = Regex("""^[0-9]""") // 正規表現を表す文字列．先頭が0〜9で始まる意味

    init {

    }

    /**
     * if文の式を評価する
     * @param expression if文のカッコの中身のParseTree
     * @param variable 変数を管理する可変Map<String, Variable>
     * @return 式を評価した結果 trueだとその式は成立，falseだと不成立
     */
    fun judgeCondition(expression: ParseTree, variable: MutableMap<String, Variable>): Boolean {
        // 0番目は絶対に値が入っている
        val leftSideExpression: ParseTree = expression.getChild(0) // 左辺式
        val relationalOperator: ParseTree
        val rightSideExpression: ParseTree
        if (leftSideExpression.text == "!") {
            //
            return this.notEqual(variable, expression.getChild(1).text, "false", isBooleanLiteralContext = true)
        }
        // 配列の1番目がnullではないということは，ただのif(true)みたいな式だということ
        if (expression.getChild(1) != null) {
            relationalOperator = expression.getChild(1)
        } else {
            return this.equal(variable, leftSideExpression.text, "true", isBooleanLiteralContext = true)
        }
        // 配列の2番目がnullではないということは，ただのif(!true)みたいな式だということ
        if (expression.getChild(2) != null) {
            rightSideExpression = expression.getChild(2)
        } else {
            // ここに入るということは，
            // bool a = true;
            // if(!a)
            // みたいな式だから左辺の式に代入するのはrelationalOperator
            return this.notEqual(variable, leftSideExpression = relationalOperator.text, rightSideExpression = "false")
        }
        when (relationalOperator.text) {
            "==" -> {
                // 左辺，または右辺が真偽値だったら
                if (leftSideExpression is CPP14Parser.BooleanliteralContext || rightSideExpression is CPP14Parser.BooleanliteralContext) {
                    this.equal(
                        variable,
                        leftSideExpression.text,
                        rightSideExpression.text,
                        isBooleanLiteralContext = true
                    )
                } else {
                    this.equal(
                        variable,
                        leftSideExpression.text,
                        rightSideExpression.text
                    )
                }
            }
            ">" -> this.greaterThan(variable, leftSideExpression.text, rightSideExpression.text)
            "<" -> this.lessThan(variable, leftSideExpression.text, rightSideExpression.text)
            ">=" -> this.greaterThanOrEqual(variable, leftSideExpression.text, rightSideExpression.text)
            "<=" -> this.lessThanOrEqual(variable, leftSideExpression.text, rightSideExpression.text)
            "!=" -> // 左辺，または右辺が真偽値だったら
                if (leftSideExpression is CPP14Parser.BooleanliteralContext || rightSideExpression is CPP14Parser.BooleanliteralContext) {
                    this.notEqual(
                        variable,
                        leftSideExpression.text,
                        rightSideExpression.text,
                        isBooleanLiteralContext = true
                    )
                } else {
                    this.notEqual(
                        variable,
                        leftSideExpression.text,
                        rightSideExpression.text
                    )
                }
//            else -> ""
        }
        //
        println("最後まで来たゾイ")
        return false
    }

    /**
     * a == bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @param isBooleanLiteralContext 真偽値を評価する式であるか，trueだとboolean型．デフォルト引数は，false
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun equal(
        variable: MutableMap<String, Variable>, leftSideExpression: String, rightSideExpression:
        String, isBooleanLiteralContext: Boolean = false
    ): Boolean {
        // 式が真偽値の場合
        return if (isBooleanLiteralContext) {
            val formulaBoolean = this.judgeVariableOrBoolean(variable, leftSideExpression, rightSideExpression)
            formulaBoolean.leftSideExpression == formulaBoolean.rightSideExpression
        } else {
            val formulaInt = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
            formulaInt.leftSideExpression == formulaInt.rightSideExpression
        }
    }

    /**
     * a > bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun greaterThan(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): Boolean {
        val formulaInt = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
        return formulaInt.leftSideExpression > formulaInt.rightSideExpression
    }

    /**
     * a < bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun lessThan(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): Boolean {
        val formulaInt = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
        return formulaInt.leftSideExpression < formulaInt.rightSideExpression
    }

    /**
     * a <= bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun lessThanOrEqual(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): Boolean {
        val formulaInt = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
        return formulaInt.leftSideExpression <= formulaInt.rightSideExpression
    }

    /**
     * a >= bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun greaterThanOrEqual(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): Boolean {
        val formulaInt = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
        return formulaInt.leftSideExpression >= formulaInt.rightSideExpression
    }

    /**
     * a != bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @param isBooleanLiteralContext 真偽値を評価する式でであるか，trueだとboolean型．デフォルト引数は，false
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun notEqual(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String,
        isBooleanLiteralContext: Boolean = false
    ): Boolean {
        return if (isBooleanLiteralContext) {
            val formulaBoolean = this.judgeVariableOrBoolean(variable, leftSideExpression, rightSideExpression)
            formulaBoolean.leftSideExpression != formulaBoolean.rightSideExpression
        } else {
            val formulaInt = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
            formulaInt.leftSideExpression != formulaInt.rightSideExpression
        }
    }

    /**
     * 変数名かInt型か判断して変数だった場合にIntとして加工後，返す
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     */
    private fun judgeVariableOrInt(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): FormulaInt {
        val formulaInt = FormulaInt(0.0, 0.0)
        val isLeftSideExpressionInt = this.regex.containsMatchIn(leftSideExpression) // 先頭の始まりが０〜９だったらtrue
        val isRightSideExpressionInt = this.regex.containsMatchIn(rightSideExpression) // 先頭の始まりが０〜９だったらtrue
        // 左辺が数値だった場合
        if (isLeftSideExpressionInt) {
            formulaInt.leftSideExpression = leftSideExpression.toDouble()
            // 左辺が変数の場合
        } else {
            // toInt()メソッドの返り値はInt?なのでIntに強制的にキャストしている．nullだとヌルポになる．
            formulaInt.leftSideExpression = variable[leftSideExpression]?.value?.toDouble() as Double
        }
        // 右辺が数値だった場合
        if (isRightSideExpressionInt) {
            formulaInt.rightSideExpression = rightSideExpression.toDouble()
            // 右辺が変数の場合
        } else {
            formulaInt.rightSideExpression = variable[rightSideExpression]?.value?.toDouble() as Double
        }
        return formulaInt
    }

    /**
     * 変数名かBoolean型か判断して変数だった場合にIntとして加工後，返す
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     */
    private fun judgeVariableOrBoolean(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): FormulaBoolean {
        val formulaBoolean = FormulaBoolean(leftSideExpression = false, rightSideExpression = false)
        // Boolean型かどうかを返すラムダ式
        val convertBoolean = { expression: String ->
            when (expression) {
                "true" -> true
                // それ以外はfalseと見なす
                else -> false
            }
        }
        if (leftSideExpression == "true" || leftSideExpression == "false" || rightSideExpression == "true" ||
            rightSideExpression == "false"
        ) {
            formulaBoolean.leftSideExpression = convertBoolean(leftSideExpression)
            formulaBoolean.rightSideExpression = convertBoolean(rightSideExpression)
            // 変数のとき
        } else {
            formulaBoolean.leftSideExpression = variable[leftSideExpression]?.value?.toBoolean() as Boolean
            formulaBoolean.rightSideExpression = variable[rightSideExpression]?.value?.toBoolean() as Boolean
        }
        return formulaBoolean
    }
}