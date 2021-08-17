object EventHandler {
    fun onStart() {
        val name = """
            |██╗░░██╗░█████╗░███╗░░██╗░██████╗░█████╗░██╗░░░░░███████╗
            |██║░██╔╝██╔══██╗████╗░██║██╔════╝██╔══██╗██║░░░░░██╔════╝
            |█████═╝░██║░░██║██╔██╗██║╚█████╗░██║░░██║██║░░░░░█████╗░░
            |██╔═██╗░██║░░██║██║╚████║░╚═══██╗██║░░██║██║░░░░░██╔══╝░░
            |██║░╚██╗╚█████╔╝██║░╚███║██████╔╝╚█████╔╝███████╗███████╗
            |╚═╝░░╚═╝░╚════╝░╚═╝░░╚══╝╚═════╝░░╚════╝░╚══════╝╚══════╝
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
