package file_utilites

import java.io.File

/**
 * Used for functions that obtain information about a [File].
 *
 * Do not put functions that modify anything in here.
 */

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
            
            file.parent = ${file.parent}
            file.parentFile = ${file.parentFile}
            file.parentFile.name = ${file.parentFile.name}
            file.parentFile.path = ${file.parentFile.path}
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
    val totalDirectories = actOnAdjacentDirs(file) { adjacentDir: File ->
        println(adjacentDir.name)
        return@actOnAdjacentDirs FileOperationResult.SUCCESS
    }

    println("Total directories: $totalDirectories")
}

fun isFileInAllAdjacentDirs(file: File): Boolean {
    var copiesFound = 0
    val dirsInspected = actOnAdjacentDirs(file) { adjacentDir: File ->
        if (isFileInDir(file, adjacentDir)) copiesFound++
        // Always return success because we want to count how many directories
        // were inspected in total.
        return@actOnAdjacentDirs FileOperationResult.SUCCESS
    }

    val isFileInAllAdjacentDirs = copiesFound == dirsInspected
    println(isFileInAllAdjacentDirs)
    return isFileInAllAdjacentDirs
}

/**
 * @throws IllegalArgumentException If [file] does not exist, or if it's a directory.
 */
fun isFileInDir(file: File, dir: File): Boolean {
    if (!file.exists() || file.isDirectory) throw IllegalArgumentException("$file does not exist, or it's a directory")

    var foundFile = false
    actOnAllFilesInDir(dir) { fileInDir ->
        if (fileInDir.name == file.name) foundFile = true
        return@actOnAllFilesInDir FileOperationResult.UNCHANGED
    }

    return foundFile
}

/**
 * Searches a directory for a [File] with the same name as [file], and returns it.
 * @return The matching [File] if found, else null.
 */
fun getFileMatchFromDir(file: File, dir: File): File? {
    var result: File? = null
    actOnAllFilesInDir(dir) { fileInDir ->
        if (file.name == fileInDir.name)
            result = fileInDir

        return@actOnAllFilesInDir FileOperationResult.UNCHANGED
    }

    return result
}