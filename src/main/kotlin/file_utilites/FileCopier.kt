package file_utilites

import java.io.File

/**
 * @throws IllegalArgumentException If [file] is not a [File], or if
 * [dir] is not a directory
 * @throws IllegalArgumentException If either of them don't exist
 */
fun copyFileToDir(file: File, dir: File) {
    if (!file.exists() || !dir.exists()) throw IllegalArgumentException("Argument does not exist")
    if (file.isDirectory || !dir.isDirectory) throw IllegalArgumentException("Arguments must be a File and a Directory")

    val filename = file.name
    val destPath = dir.path + "/$filename"
    val dest = File(destPath)

    file.copyTo(dest)
}

fun copyFileToAllAdjacentDirs(file: File) {
    getAdjacentDirs(file).forEach { copyFileToDir(file, it) }
}