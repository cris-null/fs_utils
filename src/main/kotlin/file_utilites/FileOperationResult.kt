package file_utilites

enum class FileOperationResult(private val code: Int) {

    SUCCESS(1),
    UNCHANGED(0);

    fun toInt(): Int {
        return code
    }
}