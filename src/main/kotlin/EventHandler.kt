object EventHandler {
    fun onStart() {
        val name = """
            | _                                               _   
            |( )                             _               (_ ) 
            || |/')    __   _ __   ___ ___  (_)  ___     _ _  | | 
            || , <   /'__`\( '__)/' _ ` _ `\| |/' _ `\ /'_` ) | | 
            || |\`\ (  ___/| |   | ( ) ( ) || || ( ) |( (_| | | | 
            |(_) (_)`\____)(_)   (_) (_) (_)(_)(_) (_)`\__,_)(___)
            """.trimMargin()
        val separator = buildString { repeat(60) { append("*") } }

        with(State.console) {
            printLine(separator, appConfig.theme.color.primary)
            printLine(name, appConfig.theme.color.secondary)
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
