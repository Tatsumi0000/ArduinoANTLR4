import org.antlr.v4.gui.TestRig
import org.antlr.v4.gui.TreeViewer
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

fun main(args : Array<String>) {
    val charStream = CharStreams.fromFileName("./arduino_example.ino")
//    println(charStream)
//    val cLexer = CLexer(charStream)
//    val commonTokenStream = CommonTokenStream(cLexer)
//    val cParser = CParser(commonTokenStream)
//    val parseTree = cParser.translationUnit()
//    println("done...")
//    val cpP14Lexer = CPP14Lexer(charStream)
//    val commonTokenStream = CommonTokenStream(cpP14Lexer)
//    val cpP14Parser = CPP14Parser(commonTokenStream)
//    val parseTree = cpP14Parser.translationunit()

    val cpP14Lexer = CPP14Lexer(charStream)
    val stream = CommonTokenStream(cpP14Lexer)
    val cpP14Parser = CPP14Parser(stream)


    val a = cpP14Parser.translationunit().declarationseq().declarationseq().declaration().functiondefinition().declarator().ptrdeclarator().noptrdeclarator().noptrdeclarator().declaratorid().idexpression().unqualifiedid()
//    val a = cpP14Parser.translationunit().declarationseq().declarationseq().declaration().functiondefinition().declarator().ptrdeclarator().noptrdeclarator().noptrdeclarator().declaratorid().idexpression().unqualifiedid()
//    println(message = a.children[0])

//    val arduinoParser = ArduinoParser(charStream)
//    arduinoParser.judgeFunction()

    println("finished...")
}
