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