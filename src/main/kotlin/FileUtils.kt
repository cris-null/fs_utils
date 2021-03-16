import java.io.File

fun printFileInfo(file: File) {
    println(
        """           
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
 * of [file], i.e., its siblings.
 * @throws IllegalArgumentException If [file] does not exist.
 * @throws IllegalArgumentException if [file] does not have a parent.
 */
fun getAdjacentDirs(file: File): Array<File> {
    if (!file.exists()) throw IllegalArgumentException("File does not exist")

    val parent = file.parentFile
    if (!parent.exists()) throw IllegalArgumentException("File does not have a parent directory")

    val children = parent.listFiles()
    val adjacentDirs = mutableListOf<File>()
    children.forEach {
        if (it.isDirectory && it.name != file.name)
            adjacentDirs.add(it)
    }

    return adjacentDirs.toTypedArray()
}

fun printAdjacentDirs(file: File) {
    val adjacentDirs = getAdjacentDirs(file)
    println("Total adjacent dirs = ${adjacentDirs.size}")
    adjacentDirs.forEach { println(it.name) }
}

fun isFileInAllAdjacentDirs(file: File): Boolean {
    var copiesFound = 0
    val adjacentDirs = getAdjacentDirs(file)
    adjacentDirs.forEach { if (isFileInDir(file, it)) copiesFound++ }

    return copiesFound == adjacentDirs.size
}

/**
 * @throws IllegalArgumentException If [file] does not exist, or if it's a directory.
 * @throws IllegalArgumentException if [dir] does not exist, or if it's not a directory.
 */
fun isFileInDir(file: File, dir: File): Boolean {
    if (!file.exists() || file.isDirectory) throw IllegalArgumentException("File does not exist, or it's a directory")
    if (!dir.exists() || !dir.isDirectory) throw IllegalArgumentException("Dir does not exist, or it's not a directory")

    val children = dir.listFiles()
    children.forEach { if (it.name == file.name) return true }

    return false
}

/**
 * Searches a directory for a [File] with the same name as [file], and returns it.
 *
 * @return The matching [File] if found, else null.
 */
fun getFileMatchFromDir(file: File, dir: File): File? {
    if (!file.exists() || file.isDirectory) throw IllegalArgumentException("File does not exist, or it's a directory")
    if (!dir.exists() || !dir.isDirectory) throw IllegalArgumentException("Dir does not exist, or it's not a directory")

    val children = dir.listFiles()
    children.forEach { if (it.name == file.name) return it }

    return null
}

/**
 * Checks adjacent directories to see if they contain a file with the same name as [file]. If so, it is deleted.
 */
fun delFromAdjacentDirs(file: File) {
    val adjacentDirs = getAdjacentDirs(file)
    adjacentDirs.forEach {
        if (delFileFromDir(file, it))
            println("Deleted file ${file.name} in ${it.name}")
        else
            println("Error deleting file")
    }
}

/**
 * Will only delete [file] if it exists in [dir].
 *
 * @return True if the file was deleted;
 * false if it was not or it was not found in [dir]
 */
fun delFileFromDir(file: File, dir: File): Boolean {
    val match = getFileMatchFromDir(file, dir) ?: return false
    return match.delete()
}