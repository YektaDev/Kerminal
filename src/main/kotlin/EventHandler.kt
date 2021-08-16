object EventHandler {
    fun onStart() {
        val separator = buildString { repeat(80) { append("*") } }
        with(State.console) {
            printLine(separator, appConfig.theme.color.primary)
            printLine(
                """
        |                          oooooooooo  o88    o8
        |                           888    888 oooo o888oo  ooooooo
        |                           888oooo88   888  888    ooooo888
        |                           888    888  888  888  888    888
        |                          o888ooo888  o888o  888o 88ooo88 8o
        """.trimMargin(marginPrefix = "|"),
                appConfig.theme.color.secondary
            )
            printLine(separator, appConfig.theme.color.primary)
            printLine("> Hi There!")
        }
    }

    fun beforeCommand(command: String) {

    }

    fun afterCommand(command: String) {

    }

    fun onEnd() {

    }
}
