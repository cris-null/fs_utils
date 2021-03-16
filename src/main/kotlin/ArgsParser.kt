import java.io.File

fun parse(args: Array<String>) {
    if (args.isEmpty()) return

    if (args[0] == "info" && args.size == 2) {
        printFileInfo(File(args[1]))
    }
}