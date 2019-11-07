class ArduinoListener() : CPP14BaseListener() {
    // 配列の何番目かを管理する変数
    private var variableCount: Int = 0
    // setup関数に入ったかどうか．
    private var setupFunction: Boolean = false
    // loop関数に入ったかどうか．
    private var loopFunction: Boolean = false
    // 型，変数名，値が入るMap
    private var variable = mutableMapOf<String, Variable>()
    //    private var variable: MutableMap<String, Variable> = mutableMapOf()
    private var variableType = ""
    private var variableName = ""
    private var variableValue = ""

    init {
        this.variableCount = 0
    }

    override fun enterTranslationunit(ctx: CPP14Parser.TranslationunitContext?) {

    }

    override fun enterTypespecifier(ctx: CPP14Parser.TypespecifierContext?) {

    }

    override fun enterDeclarationseq(ctx: CPP14Parser.DeclarationseqContext?) {

    }

    override fun exitDeclarationseq(ctx: CPP14Parser.DeclarationseqContext?) {

    }

    override fun enterDeclaration(ctx: CPP14Parser.DeclarationContext?) {
    }

    //  型が入ってる
    override fun enterSimpletypespecifier(ctx: CPP14Parser.SimpletypespecifierContext?) {
        println("variableCount: ${this.variableCount}")
        println("Simpletypespecifier: ${ctx?.getChild(0)?.text}")
        // loop関数に入ってないなら
        if (!this.loopFunction) {
            this.variableType = ctx?.getChild(0)?.text ?: "null"
        }
    }

    override fun exitSimpletypespecifier(ctx: CPP14Parser.SimpletypespecifierContext?) {
//        println("exitSimpletypespecifier: ${ctx?.getChild(0)?.text}")
    }

    // 変数名が入ってる．
    override fun enterUnqualifiedid(ctx: CPP14Parser.UnqualifiedidContext?) {
        println("Unqualifiedid: ${ctx?.getChild(0)?.text}")
        if (!this.loopFunction) {
            this.variableName = ctx?.getChild(0)?.text ?: "null"
        }
    }

    override fun exitUnqualifiedid(ctx: CPP14Parser.UnqualifiedidContext?) {
        if (ctx?.getChild(0)?.text == "setup")
            this.setupFunction = true
        if (ctx?.getChild(0)?.text == "loop")
            this.loopFunction = false
    }

    // 変数の中身が入っている．
    override fun enterLiteral(ctx: CPP14Parser.LiteralContext?) {
        println("Literal: ${ctx?.getChild(0)?.text}")
        // loop関数に入ってないなら
        if (!this.loopFunction) {
            this.variable[this.variableName] = Variable(type = this.variableType, value = ctx?.getChild(0)?.text ?: "null")
        }
    }

    override fun exitLiteral(ctx: CPP14Parser.LiteralContext?) {
        if (!this.loopFunction) // loop関数に入ってないなら
            this.variableCount++
    }

    override fun exitDeclaration(ctx: CPP14Parser.DeclarationContext?) {

    }

    // 変数variableを返すゲッター
    fun getVariable(): MutableMap<String, Variable> {
        return this.variable
    }
}