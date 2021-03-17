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

fun addPrefixToAllFilenamesInDir(dir: File, prefix: String) {
    actOnAllFilesInDir(dir) { child: File ->
        if (prefixStringToFilename(child, prefix)) {
            println("Renamed: ${child.name} -> ${getSimplifiedFilename(prefix)}${child.name}")
            return@actOnAllFilesInDir FileOperation.SUCCESS.toInt()
        } else {
            println("Could not rename ${child.name}")
            return@actOnAllFilesInDir FileOperation.FAILURE.toInt()
        }
    }
}

fun removePrefixFromAllFilesInDir(dir: File, prefix: String) {
    actOnAllFilesInDir(dir) { child: File ->
        if (child.name.startsWith(prefix)) {
            val newName = child.name.removePrefix(prefix)
            rename(child, newName)
            println("Renamed: ${child.name} -> $newName")
            return@actOnAllFilesInDir FileOperation.SUCCESS.toInt()
        } else {
            println("Could not rename ${child.name}")
            return@actOnAllFilesInDir FileOperation.FAILURE.toInt()
        }
    }
}

/**
 * @throws IllegalArgumentException If [dir] is not a directory, or if it doesn't exist.
 */
fun removeCharFromAllFilesInDir(dir: File, char: String) {
    if (!dir.exists() || !dir.isDirectory) throw IllegalArgumentException("Invalid argument. Must exist, must be a directory.")

    var filesRenamed = 0
    dir.listFiles().forEach {
        if (it.name.contains(char)) {
            rename(it, it.name.replace(char, ""))
            println("Renamed: ${it.name} -> ${it.name.replace(char, "")}")
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