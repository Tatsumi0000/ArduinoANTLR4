import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import okio.Buffer
import java.io.IOException


fun main(args: Array<String>) {
//    val charStream = CharStreams.fromFileName("./arduino_example.ino")
//    val cpP14Lexer = CPP14Lexer(charStream)
//    val stream = CommonTokenStream(cpP14Lexer)
//    val cpP14Parser = CPP14Parser(stream)
//    val parseTree = cpP14Parser.translationunit()
//    val walker = ParseTreeWalker()
//    val arduinoListener = ArduinoListener()
//    walker.walk(arduinoListener, parseTree)
//    println(arduinoListener.getVariable())

    val arduinoPinStatus = ArduinoPinStatus() // とりあえず適当にjson化したいclassを初期化
    val adapter = Moshi.Builder().build().adapter(ArduinoPinStatus::class.java)
    val jsonText = adapter.toJson(arduinoPinStatus)
    val buffer = Buffer()
    val prettyPrint = JsonWriter.of(buffer)
    prettyPrint.indent = "  "
    adapter.toJson(prettyPrint, arduinoPinStatus)
    val json = buffer.readUtf8()
    println(json)

    println(jsonText)

    println("finished...")


//    var variable: MutableMap<String, Variable> = mutableMapOf()
//    variable["a"] = Variable("", "")
//    println(variable)

}