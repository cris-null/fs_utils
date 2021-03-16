import java.io.File

fun printFileInfo(file: File) {
    println("""           
            file.exists = ${file.exists()}
            file.isFile = ${file.isFile}
            file.isDirectory = ${file.isDirectory}
            file.isAbsolute = ${file.isAbsolute}
            
            file.name = ${file.name}
            file.path = ${file.path}
        """.trimIndent()
    )
}