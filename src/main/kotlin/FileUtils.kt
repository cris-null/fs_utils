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
 * @return All the directories at the same level, i.e., the children of the parent
 * of [file], i.e., its siblings
 * @throws IllegalArgumentException If [file] does not exist
 * @throws IllegalArgumentException if [file] does not have a parent
 */
fun getAdjacentDirs(file: File): Array<File> {
    if (!file.exists()) throw IllegalArgumentException("File does not exist")

    val parent = file.parentFile
    if (!parent.exists()) throw IllegalArgumentException("File does not have a parent directory")

    val children = parent.listFiles()
    val adjacentDirs = mutableListOf<File>()
    children.forEach {
        if( it.isDirectory && it.name != file.name)
            adjacentDirs.add(it)
    }

    return adjacentDirs.toTypedArray()
}

fun printAdjacentDirs(file: File) {
    val adjacentDirs = getAdjacentDirs(file)
    println("Total adjacent dirs = ${adjacentDirs.size}")
    adjacentDirs.forEach { println(it.name) }
}