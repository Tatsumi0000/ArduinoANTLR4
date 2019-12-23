import com.squareup.moshi.Moshi
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker


fun main() {
    val charStream = CharStreams.fromFileName("./arduino_example.ino")
    val cpP14Lexer = CPP14Lexer(charStream)
    val stream = CommonTokenStream(cpP14Lexer)
    val cpP14Parser = CPP14Parser(stream)
    val parseTree = cpP14Parser.translationunit()
    val walker = ParseTreeWalker()
    val arduinoListener = ArduinoListener()
    walker.walk(arduinoListener, parseTree)
    println(arduinoListener.getVariable())
    val adapter = Moshi.Builder().build().adapter(ArduinoPinStatus::class.java)
    val jsonText = adapter.indent("   ").toJson(arduinoListener.getArduinoPinStatus())
    println(jsonText)

//    val regex = Regex("""^[0-9]""")
//    val a = "z012aaaa"
//    println(regex.containsMatchIn(a))

    /**
     * 参考サイト
     * https://qiita.com/toastkidjp/items/d4d208a22727c6832da7
     * https://stackoverflow.com/a/42739533
     */
    /**
    // 適当にdata classを初期化
    val arduinoPinStatus = ArduinoPinStatus()
    // Moshiオブジェクトを指定後，adapterメソッドで変換対象のクラスを指定
    val adapter = Moshi.Builder().build().adapter(ArduinoPinStatus::class.java)
    // indentを設定後，json化したいやつを入れる
    val jsonText = adapter.indent("   ").toJson(arduinoPinStatus)
    println(jsonText)
    println("finished...")
    */

    // ここから下は消さないように
//    val outputData = OutputData(ApiInfo(timestamp = LocalDateTime.now().toString(), programingLanguage = "Arduino", apiVer = 1.0))
//    val adapter2 = Moshi.Builder().build().adapter(OutputData::class.java)
//    val json = adapter2.indent("   ").toJson(outputData)
//    println(json)

}