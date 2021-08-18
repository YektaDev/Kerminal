package command

class Clear : Command(
    command = "clear",
    help = "Clear the screen"
) {
    override fun run() {
        State.console.clear()
    }
}
