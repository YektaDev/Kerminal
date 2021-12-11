package command

import State
import com.github.ajalt.clikt.core.Abort
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.PrintCompletionMessage
import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.ProgramResult
import com.github.ajalt.clikt.core.UsageError

sealed class Command(
    val command: String,
    val help: String
) : CliktCommand(name = command, help = help) {
    fun run(args: Array<String>): Unit = with(State.console) {
        try {
            parse(args)
        } catch (e: Exception) {
            printError(e.message)
        } catch (_: ProgramResult) {
        } catch (e: PrintHelpMessage) {
            if (e.error) printError(e.command.getFormattedHelp())
            else printWarning(e.command.getFormattedHelp())
        } catch (e: PrintCompletionMessage) {
            printInfo(e.message)
        } catch (e: PrintMessage) {
            if (e.error) printError(e.message)
            else printInfo(e.message)
        } catch (e: UsageError) {
            printError(e.helpMessage())
        } catch (e: CliktError) {
            printError(e.message)
        } catch (e: Abort) {
            if (e.error) printError(currentContext.localization.aborted())
            else printInfo(currentContext.localization.aborted())
        }
    }
}
