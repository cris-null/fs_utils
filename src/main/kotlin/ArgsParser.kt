import java.io.File

fun parse(args: Array<String>) {
    if (args.isEmpty()) return

    val commands = arrayOf("info", "copy", "print-adj-dirs", "mass-copy", "verify")

    val chosenCommand = args[0]
    if (chosenCommand == commands[0] && args.size == 2)
        printFileInfo(File(args[1]))
    else if (chosenCommand == commands[1] && args.size == 3)
        copyFileToDir(File(args[1]), File(args[2]))
    else if (chosenCommand == commands[2] && args.size == 2)
        printAdjacentDirs(File(args[1]))
    else if (chosenCommand == commands[3] && args.size == 2)
        copyFileToAllAdjacentDirs(File(args[1]))
    else if (chosenCommand == commands[4] && args.size == 2)
        println(isFileInAllAdjacentDirs(File(args[1])))
}