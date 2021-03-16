import java.io.File

fun parse(args: Array<String>) {
    if (args.isEmpty()) return

    val command = args[0]
    if (command == "info" && args.size == 2) {
        printFileInfo(File(args[1]))
    } else if (command == "copy" && args.size == 3) {
        copyFileToDir(File(args[1]), File(args[2]))
    }
}