import org.antlr.v4.gui.TestRig
import org.antlr.v4.gui.TreeViewer
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker

fun main(args: Array<String>) {
    val charStream = CharStreams.fromFileName("./arduino_example.ino")
    val cpP14Lexer = CPP14Lexer(charStream)
    val stream = CommonTokenStream(cpP14Lexer)
    val cpP14Parser = CPP14Parser(stream)
    val parseTree = cpP14Parser.translationunit()
    val walker = ParseTreeWalker()
    val arduinoListener = ArduinoListener()
    walker.walk(arduinoListener, parseTree)
    println(arduinoListener.getVariable())
    println("finished...")
}
