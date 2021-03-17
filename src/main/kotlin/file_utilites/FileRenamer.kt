package file_utilites

import java.io.File

/**
 * Renames every directory and file contained inside [dir], so that the filenames are all lowercase
 * and have underscores for spaces.
 *
 * @param dir Is the directory whose children will be renamed.
 */
fun simplifyAllFilenames(dir: File) {
    actOnAllFilesInDir(dir) { child: File ->
        val newName = getSimplifiedFilename(child.name)
        when {
            child.name == newName ->
                return@actOnAllFilesInDir FileOperation.FAILURE
            rename(child, newName) -> {
                println("Renamed: ${child.name} -> $newName")
                return@actOnAllFilesInDir FileOperation.SUCCESS
            }
            else -> {
                println("Failed to rename ${child.name}")
                return@actOnAllFilesInDir FileOperation.FAILURE
            }
        }
    }
}

fun addPrefixToAllFilenamesInDir(dir: File, prefix: String) {
    actOnAllFilesInDir(dir) { child: File ->
        if (prefixStringToFilename(child, prefix)) {
            println("Renamed: ${child.name} -> ${getSimplifiedFilename(prefix)}${child.name}")
            return@actOnAllFilesInDir FileOperation.SUCCESS
        } else {
            println("Could not rename ${child.name}")
            return@actOnAllFilesInDir FileOperation.FAILURE
        }
    }
}

fun removePrefixFromAllFilesInDir(dir: File, prefix: String) {
    actOnAllFilesInDir(dir) { child: File ->
        if (child.name.startsWith(prefix)) {
            val newName = child.name.removePrefix(prefix)
            rename(child, newName)
            println("Renamed: ${child.name} -> $newName")
            return@actOnAllFilesInDir FileOperation.SUCCESS
        } else {
            println("${child.name} does not start with prefix $prefix")
            return@actOnAllFilesInDir FileOperation.FAILURE
        }
    }
}

/**
 * Removes all occurrences of [char] in every filename of every file in [dir].
 *
 * @throws IllegalArgumentException if [char] has more than 1 character
 */
fun removeCharFromAllFilesInDir(dir: File, char: String) {
    if (char.length != 1) throw IllegalArgumentException("$char is of length != 1")

    actOnAllFilesInDir(dir) { child: File ->
        if (child.name.contains(char)) {
            val newName = child.name.replace(char, "")
            rename(child, newName)
            println("Renamed: ${child.name} -> $newName")
            return@actOnAllFilesInDir FileOperation.SUCCESS
        } else {
            println("${child.name} does not contain $char")
            return@actOnAllFilesInDir FileOperation.FAILURE
        }
    }
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
    val destinationPath = file.parentFile.path + "/$newName"
    val destination = File(destinationPath)

    return file.renameTo(destination)
}

private fun getSimplifiedFilename(filename: String): String {
    return filename.toLowerCase().replace(" ", "_")
}