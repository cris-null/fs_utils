package file_utilites

import java.io.File

/**
 * @throws IllegalArgumentException If [file] is not a [File], or if
 * [dir] is not a directory
 * @throws IllegalArgumentException If either of them don't exist
 */
fun copyFileToDir(file: File, dir: File) {
    if (!file.exists() || !dir.exists()) throw IllegalArgumentException("Argument does not exist")
    if (file.isDirectory || !dir.isDirectory) throw IllegalArgumentException("Not given a File and a Directory")

    val filename = file.name
    val destinationPath = dir.path + "/$filename"
    val destination = File(destinationPath)

    file.copyTo(destination)
}

fun copyFileToAllAdjacentDirs(file: File) {
    val copiesMade = actOnAdjacentDirs(file) { adjacentDir: File ->
        copyFileToDir(file, adjacentDir)
        return@actOnAdjacentDirs FileOperation.SUCCESS
    }

    println("Copies made: $copiesMade")
}