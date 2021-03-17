package file_utilites

import java.io.File

/**
 * Renames every directory and file contained inside [dir], so that the filenames are all lowercase
 * and have underscores for spaces.
 *
 * @param dir Is the directory whose children will be renamed.
 * @throws IllegalArgumentException If [dir] is not a directory, or if it doesn't exist.
 */
fun simplifyAllFilenames(dir: File) {
    if (!dir.exists() || !dir.isDirectory) throw IllegalArgumentException("Invalid argument. Must exist, must be a directory.")

    val children = dir.listFiles()
    children.forEach {
        val newName = getSimplifiedFilename(it.name)
        if (rename(it, newName)) {
            println("Renamed: ${it.name} -> $newName")
        } else
            println("Could not rename $it")
    }
}

/**
 * @throws IllegalArgumentException If [dir] is not a directory, or if it doesn't exist.
 */
fun prefixStringToAllFilesInDir(dir: File, string: String) {
    if (!dir.exists() || !dir.isDirectory) throw IllegalArgumentException("Invalid argument. Must exist, must be a directory.")

    var filesRenamed = 0
    dir.listFiles().forEach {
        if (prefixStringToFilename(it, string)) {
            println("Renamed: ${it.name} -> ${getSimplifiedFilename(string)}${it.name}")
            filesRenamed++
        } else
            println("Could not rename ${it.name}")
    }
}

/**
 * @throws IllegalArgumentException If [dir] is not a directory, or if it doesn't exist.
 */
fun removePrefixFromAllFilesInDir(dir: File, prefix: String) {
    if (!dir.exists() || !dir.isDirectory) throw IllegalArgumentException("Invalid argument. Must exist, must be a directory.")

    var filesRenamed = 0
    dir.listFiles().forEach {
        if (it.name.startsWith(prefix)) {
            rename(it, it.name.removePrefix(prefix))
            println("Renamed: ${it.name} -> ${it.name.removePrefix(prefix)}")
            filesRenamed++
        }
    }

    println("Files renamed = $filesRenamed")
}

/**
 * Prefixes the simplified version of [string] to a filename, be it a directory or file.
 */
private fun prefixStringToFilename(file: File, string: String): Boolean {
    val newFilename = "${getSimplifiedFilename(string)}${file.name}"
    return rename(file, newFilename)
}

/**
 * @return True if the file was renamed, false otherwise.
 */
private fun rename(file: File, newName: String): Boolean {
    val destPath = file.parentFile.path + "/$newName"
    val dest = File(destPath)

    return file.renameTo(dest)
}

private fun getSimplifiedFilename(filename: String): String {
    return filename.toLowerCase().replace(" ", "_")
}