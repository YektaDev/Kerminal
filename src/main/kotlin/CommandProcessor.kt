import command.toCommand

object CommandProcessor {
    fun run(rawInput: String) {
        EventHandler.beforeCommand(rawInput)

        val parsedCommand = rawInput.cleanCommand()
        when (parsedCommand.size) {
            0 -> { // Ignored
            }
            1 -> runCommand(parsedCommand[0])
            else -> runCommand(parsedCommand[0], parsedCommand.copyOfRange(1, parsedCommand.size))
        }

        EventHandler.afterCommand(rawInput)
    }

    private fun String.cleanCommand(): Array<String> = this.trim()
        .split(' ')
        .filter { it.isNotEmpty() }
        .toTypedArray().apply {
            if (isNotEmpty()) this[0] = this[0].lowercase()
        }

    private fun runCommand(command: String, args: Array<String> = arrayOf()) {
        command.toCommand()?.run(args)
    }
}
