import javax.json.JsonObject

/**
 * Arduinoのコードからそれに応じた
 * ピン番号に変換するクラス
 */
class ArduinoConverter(arduinoPinStatus: ArduinoPinStatus) {
    private var arduinoPinStatus: ArduinoPinStatus

    init {
        this.arduinoPinStatus = arduinoPinStatus
    }

    /**
     * arduinoPinStatusのゲッター
     */
    fun getArduinoPinStatus(): ArduinoPinStatus {
        return arduinoPinStatus
    }

    /**
     * ArduinoPinStatusのセッター
     */
    fun setArduinoPinStatus(arduinoPinStatus: ArduinoPinStatus) {
        this.arduinoPinStatus = arduinoPinStatus
    }

    /**
     *
     */
    fun changePinStatus(pinStatus: String, pinNumber: Int) {
        when (pinStatus) {
            "OUTPUT" -> this.convertPinStatus(pinNumber = pinNumber, isInput = true)
            "INPUT" -> this.convertPinStatus(pinNumber = pinNumber, isInput = false)
            "HIGH" -> this.convertPinStatus2(pinNumber = pinNumber, isLow = false)
            "LOW" -> this.convertPinStatus2(pinNumber = pinNumber, isLow = true)
            else -> println("OUTPUTかINPUTしか受け取りませんょ．")
        }
    }

    /**
     * ピン番号に応じて変換
     * INPUTかOUTPUTかを判断
     */
    private fun convertPinStatus(pinNumber: Int, isInput: Boolean = true) {
        when (pinNumber) {
            // ここからデジタルピン
            0 -> this.arduinoPinStatus.digitalPin00.isInput = isInput
            1 -> this.arduinoPinStatus.digitalPin01.isInput = isInput
            2 -> this.arduinoPinStatus.digitalPin02.isInput = isInput
            3 -> this.arduinoPinStatus.digitalPin03.isInput = isInput
            4 -> this.arduinoPinStatus.digitalPin04.isInput = isInput
            5 -> this.arduinoPinStatus.digitalPin05.isInput = isInput
            6 -> this.arduinoPinStatus.digitalPin06.isInput = isInput
            7 -> this.arduinoPinStatus.digitalPin07.isInput = isInput
            8 -> this.arduinoPinStatus.digitalPin08.isInput = isInput
            9 -> this.arduinoPinStatus.digitalPin09.isInput = isInput
            10 -> this.arduinoPinStatus.digitalPin10.isInput = isInput
            11 -> this.arduinoPinStatus.digitalPin11.isInput = isInput
            12 -> this.arduinoPinStatus.digitalPin12.isInput = isInput
            13 -> this.arduinoPinStatus.digitalPin13.isInput = isInput
            // ここからアナログピン
            14 -> this.arduinoPinStatus.analogPin00.isInput = isInput
            15 -> this.arduinoPinStatus.analogPin01.isInput = isInput
            16 -> this.arduinoPinStatus.analogPin02.isInput = isInput
            17 -> this.arduinoPinStatus.analogPin03.isInput = isInput
            18 -> this.arduinoPinStatus.analogPin04.isInput = isInput
            19 -> this.arduinoPinStatus.analogPin05.isInput = isInput
            else -> println("アナログ，デジタルしか対応してません．")
        }
    }

    /**
     * ピン番号に応じて変換
     * LOWかHIGHかを判断
     */
    private fun convertPinStatus2(pinNumber: Int, isLow: Boolean = true) {
        when (pinNumber) {
            // ここからデジタルピン
            0 -> {
                this.arduinoPinStatus.digitalPin00.isLow = isLow
                this.arduinoPinStatus.digitalPin00.voltValue = 5.0
            }
            1 -> {
                this.arduinoPinStatus.digitalPin01.isLow = isLow
                this.arduinoPinStatus.digitalPin01.voltValue = 5.0
            }
            2 -> {
                this.arduinoPinStatus.digitalPin02.isLow = isLow
                this.arduinoPinStatus.digitalPin02.voltValue = 5.0
            }
            3 -> {
                this.arduinoPinStatus.digitalPin03.isLow = isLow
                this.arduinoPinStatus.digitalPin03.voltValue = 5.0
            }
            4 -> {
                this.arduinoPinStatus.digitalPin04.isLow = isLow
                this.arduinoPinStatus.digitalPin04.voltValue = 5.0
            }
            5 -> {
                this.arduinoPinStatus.digitalPin05.isLow = isLow
                this.arduinoPinStatus.digitalPin05.voltValue = 5.0
            }
            6 -> {
                this.arduinoPinStatus.digitalPin06.isLow = isLow
                this.arduinoPinStatus.digitalPin06.voltValue = 5.0
            }
            7 -> {
                this.arduinoPinStatus.digitalPin07.isLow = isLow
                this.arduinoPinStatus.digitalPin07.voltValue = 5.0
            }
            8 -> {
                this.arduinoPinStatus.digitalPin08.isLow = isLow
                this.arduinoPinStatus.digitalPin08.voltValue = 5.0
            }
            9 -> {
                this.arduinoPinStatus.digitalPin09.isLow = isLow
                this.arduinoPinStatus.digitalPin09.voltValue = 5.0
            }
            10 -> {
                this.arduinoPinStatus.digitalPin10.isLow = isLow
                this.arduinoPinStatus.digitalPin10.voltValue = 5.0
            }
            11 -> {
                this.arduinoPinStatus.digitalPin11.isLow = isLow
                this.arduinoPinStatus.digitalPin11.voltValue = 5.0
            }
            12 -> {
                this.arduinoPinStatus.digitalPin12.isLow = isLow
                this.arduinoPinStatus.digitalPin12.voltValue = 5.0
            }
            13 -> {
                this.arduinoPinStatus.digitalPin13.isLow = isLow
                this.arduinoPinStatus.digitalPin13.voltValue = 5.0
            }
            // ここからアナログピン
            14 -> {
                this.arduinoPinStatus.analogPin00.isLow = isLow
                this.arduinoPinStatus.analogPin00.isLow = isLow
            }
            15 -> {
                this.arduinoPinStatus.analogPin01.isLow = isLow
                this.arduinoPinStatus.analogPin01.isLow = isLow
            }
            16 -> {
                this.arduinoPinStatus.analogPin02.isLow = isLow
                this.arduinoPinStatus.analogPin02.isLow = isLow
            }
            17 -> {
                this.arduinoPinStatus.analogPin03.isLow = isLow
                this.arduinoPinStatus.analogPin03.isLow = isLow
            }
            18 -> {
                this.arduinoPinStatus.analogPin04.isLow = isLow
                this.arduinoPinStatus.analogPin04.isLow = isLow
            }
            19 -> {
                this.arduinoPinStatus.analogPin05.isLow = isLow
                this.arduinoPinStatus.analogPin05.isLow = isLow
            }
            else -> println("アナログ，デジタルしか対応してません．")
        }
    }
}