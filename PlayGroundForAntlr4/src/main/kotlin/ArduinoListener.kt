class ArduinoListener() : CPP14BaseListener() {
    var variable: Variable?  // 変数の情報を持つデータクラス

    init {
        this.variable = Variable("", "", 0)
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

    override fun enterSimpletypespecifier(ctx: CPP14Parser.SimpletypespecifierContext?) {
        //  型が入っている
        var a = ctx?.getChild(0)
    }


    override fun exitDeclaration(ctx: CPP14Parser.DeclarationContext?) {

    }
}