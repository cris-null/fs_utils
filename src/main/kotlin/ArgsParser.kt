import java.io.File

fun parse(args: Array<String>) {
    if (args.isEmpty()) return

    if (args[0] == "mass-copy" && args.size == 2) {
        val fileToCopy: File = File(args[1])
        printFileInfo(fileToCopy)
    }
}