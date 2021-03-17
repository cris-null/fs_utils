package file_utilites

enum class FileOperationResult(private val code: Int) {

    SUCCESS(1),
    NO_CHANGE(0);

    fun toInt(): Int {
        return code
    }
}