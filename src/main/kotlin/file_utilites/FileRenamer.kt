package file_utilites

import java.io.File

/**
 * Renames every directory and file contained inside [dir], so that the filenames are all lowercase
 * and have underscores for spaces.
 *
 * @param dir Is the directory whose children will be renamed.
 */
fun simplifyAllFilenames(dir: File) {
    val filesRenamed = actOnAllFilesInDir(dir) { fileInDir: File ->
        val newName = getSimplifiedFilename(fileInDir.name)
        when {
            fileInDir.name == newName ->
                return@actOnAllFilesInDir FileOperationResult.NO_CHANGE
            rename(fileInDir, newName) -> {
                println("Renamed: ${fileInDir.name} -> $newName")
                return@actOnAllFilesInDir FileOperationResult.SUCCESS
            }
            else -> {
                println("Failed to rename ${fileInDir.name}")
                return@actOnAllFilesInDir FileOperationResult.NO_CHANGE
            }
        }
    }

    println("Filenames simplified = $filesRenamed")
}

fun addPrefixToAllFilenamesInDir(dir: File, prefix: String) {
    val filenamesPrefixed = actOnAllFilesInDir(dir) { fileInDir: File ->
        if (prefixStringToFilename(fileInDir, prefix)) {
            println("Renamed: ${fileInDir.name} -> ${getSimplifiedFilename(prefix)}${fileInDir.name}")
            return@actOnAllFilesInDir FileOperationResult.SUCCESS
        } else {
            println("Could not rename ${fileInDir.name}")
            return@actOnAllFilesInDir FileOperationResult.NO_CHANGE
        }
    }

    println("Filenames prefixed with $prefix = $filenamesPrefixed")
}

fun removePrefixFromAllFilesInDir(dir: File, prefix: String) {
    val prefixesRemoved = actOnAllFilesInDir(dir) { fileInDir: File ->
        if (fileInDir.name.startsWith(prefix)) {
            val newName = fileInDir.name.removePrefix(prefix)
            rename(fileInDir, newName)
            println("Renamed: ${fileInDir.name} -> $newName")
            return@actOnAllFilesInDir FileOperationResult.SUCCESS
        } else {
            println("${fileInDir.name} does not start with prefix $prefix")
            return@actOnAllFilesInDir FileOperationResult.NO_CHANGE
        }
    }

    println("Prefixes removed = $prefixesRemoved")
}

/**
 * Removes all occurrences of [char] in every filename of every file in [dir].
 *
 * @throws IllegalArgumentException if [char] has more than 1 character
 */
fun removeCharFromAllFilesInDir(dir: File, char: String) {
    if (char.length != 1) throw IllegalArgumentException("$char is of length != 1")

    val filenamesChanged = actOnAllFilesInDir(dir) { child: File ->
        if (child.name.contains(char)) {
            val newName = child.name.replace(char, "")
            rename(child, newName)
            println("Renamed: ${child.name} -> $newName")
            return@actOnAllFilesInDir FileOperationResult.SUCCESS
        } else {
            println("${child.name} does not contain $char")
            return@actOnAllFilesInDir FileOperationResult.NO_CHANGE
        }
    }

    println("Filenames changed = $filenamesChanged")
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