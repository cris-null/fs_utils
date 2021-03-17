package file_utilites

enum class FileOperation(private val code: Int) {

    SUCCESS(1), FAILURE(0);

    fun toInt(): Int {
        return code
    }
}