package file_utilites

import java.io.File

/**
 * Checks adjacent directories to see if they contain a file with the same name as [file]. If so, it is deleted.
 */
fun delFromAdjacentDirs(file: File) {
    var filesDeleted = 0
    val adjacentDirs = getAdjacentDirs(file)
    adjacentDirs.forEach {
        if (delFileFromDir(file, it)) {
            println("Deleted ${file.name} in ${it.name}")
            filesDeleted++
        } else
            println("Error deleting ${file.name} in ${it.name}")
    }

    println("Files deleted = $filesDeleted")
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