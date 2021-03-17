package file_utilites

import java.io.File

/**
 * Behaviors common among many [File] operations, such as acting on all files
 * inside directory X, should be abstracted out into their own function for re-usability.
 */

/**
 * Performs some action on all children of a given directory. The children can be
 * both files or directories.
 *
 * Prints out to console the total number of altered [File].
 *
 * @param dir A directory whose children will be acted upon.
 * @param act A function that performs some action on a child of [dir], and returns
 * a 1 if the action was successful, or a 0 if unsuccessful.
 * @throws IllegalArgumentException If [dir] is not a directory, or if it doesn't exist.
 */
fun actOnAllFilesInDir(dir: File, act: (File) -> FileOperation) {
    if (!dir.exists() || !dir.isDirectory)
        throw IllegalArgumentException("Invalid argument. $dir must be a directory and exists.")

    var filesAltered = 0

    dir.listFiles().forEach { child: File ->
        filesAltered += act(child).toInt()
    }

    if (filesAltered != 0) println("Files altered: $filesAltered")
}

/**
 * Non-directory [File] objects will be ignored. Only acts on adjacent directories.
 *
 * @param file Is the file whose adjacent directories will be acted upon. Must not be a directory.
 * @throws IllegalArgumentException if [file] does not exist, or if it's a directory.
 */
fun actOnAdjacentDirs(file: File, act: (adjacentDir: File) -> FileOperation) {
    if (!file.exists() || file.isDirectory)
        throw IllegalArgumentException("Invalid argument. $file must exist, and it can't be a directory.")

    var filesAltered = 0

    val siblings = file.parentFile.listFiles()
    siblings.forEach {
        if (it.isDirectory) filesAltered += act(it).toInt()
    }

    if (filesAltered != 0) println("Files altered: $filesAltered")
}