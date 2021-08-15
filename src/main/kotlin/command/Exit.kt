package command

import appScope
import compose.exit

class Exit : BitaCommand(
    command = "exit",
    help = "Exit!"
) {
    override fun run() {
        appScope.exit()
    }
}
