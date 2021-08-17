package command

import kotlin.reflect.full.primaryConstructor

fun String.toCommand(): Command? {
    for (kClass in Command::class.sealedSubclasses) {
        if (this == (kClass.primaryConstructor?.call() as Command).command) {
            return kClass.primaryConstructor?.call()
        }
    }
    return null
}
