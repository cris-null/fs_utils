package file_utilites

import java.io.File

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
fun actOnAllFilesInDir(dir: File, act: (File) -> Int) {
    if (!dir.exists() || !dir.isDirectory)
        throw IllegalArgumentException("Invalid argument. $dir must be a directory and exists.")

    var filesAltered = 0

    dir.listFiles().forEach { child: File ->
        filesAltered += act(child)
    }

    println("Files altered: $filesAltered")
}