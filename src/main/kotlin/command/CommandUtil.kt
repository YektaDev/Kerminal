package command

import kotlin.reflect.full.primaryConstructor

fun String.toBitaCommand(): BitaCommand? {
    for (kClass in BitaCommand::class.sealedSubclasses) {
        if (this == (kClass.primaryConstructor?.call() as BitaCommand).command) {
            return kClass.primaryConstructor?.call()
        }
    }
    return null
}
