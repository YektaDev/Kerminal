package command

import appScope
import compose.exit

class Exit : Command(
    command = "exit",
    help = "Exit!"
) {
    override fun run() = appScope.exit()
}
