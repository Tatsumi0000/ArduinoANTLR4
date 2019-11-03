import org.antlr.v4.gui.TestRig
import org.antlr.v4.gui.TreeViewer
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

fun main(args: Array<String>) {
    val charStream = CharStreams.fromFileName("./arduino_example.ino")
    val cpP14Lexer = CPP14Lexer(charStream)
    val stream = CommonTokenStream(cpP14Lexer)
    val cpP14Parser = CPP14Parser(stream)

    val a =
        cpP14Parser.translationunit().declarationseq().declarationseq().declaration().functiondefinition().declarator()
            .ptrdeclarator().noptrdeclarator().parametersandqualifiers()
    println(message = a.getChild(0))

//    val b = cpP14Parser.translationunit().declarationseq().declarationseq().declaration().functiondefinition().declarator()
//        .ptrdeclarator().noptrdeclarator().noptrdeclarator().declaratorid().idexpression().unqualifiedid()
//    val a1 =
//        cpP14Parser.translationunit().declarationseq().declarationseq().declaration().functiondefinition().declarator()
//            .ptrdeclarator().noptrdeclarator().noptrdeclarator().declaratorid().idexpression().unqualifiedid()
//    println(message = a1.getChild(0))

//    for (i in 0 until a.childCount) {
//        println(a.children[i])
//    }


//    val arduinoParser = ArduinoParser(charStream)
//    arduinoParser.judgeFunction()

    println("finished...")
}
