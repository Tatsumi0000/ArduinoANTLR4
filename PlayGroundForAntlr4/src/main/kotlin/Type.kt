/**
 * @author T.N.Revolution
 * 型（クラス）を管理するクラス
 */
class Type() {
    private var int: Int = 0
    private var string: String = ""
    private var boolean: Boolean = true
    init {
    }

    /**
     * @param judgeString 変数の中身
     * @param variable 変数を管理する可変Map
     */
    fun judgeType(judgeString: String, variable: MutableMap<String, Variable>) {
        when (judgeString) {
            "true", "false" -> println()
        }
    }
}