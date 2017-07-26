import java.io.File
import java.util.*

val ROOT_PROJECT_PATH = "C://idealista-android-copia/"

val RES_DRAWABLE_PATH = "app/src/main/java"
val APP_PATH = "app/src/main"

val MANIFEST_PATH = "AndroidManifest.xml"

val EXTENSION_JAVA = "java"

fun main(args: Array<String>) {
    val startTime = Date().time
    searchNonUsedDrawables(File(ROOT_PROJECT_PATH, APP_PATH), File(ROOT_PROJECT_PATH, RES_DRAWABLE_PATH)).map {
        showToConsole(it)
        //it.delete()
    }
    val endTime = Date().time
    println((endTime - startTime) / 1000)
}


private fun searchNonUsedDrawables(filesRoot: File, drawablesRoot: File): List<File> =
        filesRoot.walkTopDown()
                .filter { isFile(it) }
                .asIterable()
                .map {
                    getNonUsedDrawablesWithNull(it, drawablesRoot)
                }.flatten()
                .filterNotNull()
                .sortedBy {
                    byName(it)
                }.distinct()

private fun showToConsole(it: File) {
    println(it)
}

private fun byName(it: File) = it.nameWithoutExtension

private fun getNonUsedDrawablesWithNull(lf: File, drawablesRoot: File): List<File?> {
    val text = lf.readText()
    return drawablesRoot.listFiles().map {
        df ->
        if (isJavaFile(df) && containsFileInManifest(df)) {
            null
        } else if (containsFilename(text, df)) {
            null
        } else {
            df
        }
    }
}

private fun containsFileInManifest(df: File): Boolean {

    val file = File(APP_PATH, MANIFEST_PATH)
    if(file.exists()){
        println("Android Manifest exist")
    }else{
        println("No existe")
    }
    return file.readText().contains(df.nameWithoutExtension)

}

private fun isFile(it: File) = it.isFile

private fun containsFilename(text: String, df: File) = text.contains(df.nameWithoutExtension)

private fun isJavaFile(file: File): Boolean = file.extension == EXTENSION_JAVA