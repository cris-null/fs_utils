package file_utilites

import java.io.File

/**
 * Checks adjacent directories to see if they contain a file with the same name as [file]. If so, it is deleted.
 */
fun delFromAdjacentDirs(file: File) {
    val filesDeleted = actOnAdjacentDirs(file) { adjacentDir: File ->
        if (delFileMatchFromDir(file, adjacentDir)) {
            println("Deleted ${file.name} in ${adjacentDir.name}")
            return@actOnAdjacentDirs FileOperationResult.SUCCESS
        }
        else
            return@actOnAdjacentDirs FileOperationResult.UNCHANGED
    }

    println("Files deleted: $filesDeleted")
}

/**
 * Checks [dir] to see if there's a file inside it that has the same name as [file], if so, it's
 * deleted.
 * @return True if the file was deleted, false otherwise.
 */
fun delFileMatchFromDir(file: File, dir: File): Boolean {
    val fileMatch: File? = getFileMatchFromDir(file, dir)
    return fileMatch?.delete() ?: false
}