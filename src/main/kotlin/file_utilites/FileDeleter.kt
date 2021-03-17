package file_utilites

import java.io.File

/**
 * Checks adjacent directories to see if they contain a file with the same name as [file]. If so, it is deleted.
 */
fun delFromAdjacentDirs(file: File) {
    val filesDeleted = actOnAdjacentDirs(file) { adjacentDir: File ->
        if (delMatchFromDir(file, adjacentDir)) {
            println("Deleted ${file.name} in ${adjacentDir.name}")
            return@actOnAdjacentDirs FileOperationResult.SUCCESS
        }
        else
            return@actOnAdjacentDirs FileOperationResult.NO_CHANGE
    }

    println("Files deleted: $filesDeleted")
}

/**
 * Checks [dir] to see if there's a file inside it that has the same name as [file], if so, it's
 * deleted.
 * @param file Is the [File] to compare other files to.
 * @return True if the matching file was deleted, false otherwise.
 */
fun delMatchFromDir(file: File, dir: File): Boolean {
    val fileMatch: File? = getFileMatchFromDir(file, dir)
    return fileMatch?.delete() ?: false
}