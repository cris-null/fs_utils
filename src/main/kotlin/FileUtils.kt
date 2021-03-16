import java.io.File

fun printFileInfo(file: File) {
    println("""           
            file.exists = ${file.exists()}
            file.isFile = ${file.isFile}
            file.isDirectory = ${file.isDirectory}
            file.isAbsolute = ${file.isAbsolute}
            
            file.name = ${file.name}
            file.path = ${file.path}
            file.absolutePath = ${file.absolutePath}
            file.canonicalPath = ${file.canonicalPath}
        """.trimIndent()
    )
}

/**
 * @throws IllegalArgumentException when [file] does not exist
 * @throws IllegalArgumentException when [file] is a directory
 * @throws IllegalArgumentException when [file] does not have a parent directory
 */
fun printAdjacentDirs(file: File) {
    if (!file.exists()) throw IllegalArgumentException("File does not exist")
    if (file.isDirectory) throw IllegalArgumentException("File cannot be a directory")

    val parentPathname = file.parent
    val parent = File(parentPathname)
    if (!parent.isDirectory) throw IllegalArgumentException("File does not have a parent directory")

    val children = parent.listFiles()
    val adjacentDirs = mutableListOf<File>()
    children.forEach {
        if(it.isDirectory) adjacentDirs.add(it)
    }

    println("Total adjacent dirs = ${adjacentDirs.size}")
    adjacentDirs.forEach { println(it.name) }
}