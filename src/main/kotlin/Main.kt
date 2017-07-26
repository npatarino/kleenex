import java.io.File
import java.util.*

val ROOT_PROJECT_PATH = "C://copy-idealista-android/"

val RES_DRAWABLE_PATH = "app/src/main/res/drawable"
val APP_PATH = "app/src/main"

fun main(args: Array<String>) {
    val startTime = Date().time
    searchNonUsedDrawables(File(ROOT_PROJECT_PATH, APP_PATH), File(ROOT_PROJECT_PATH, RES_DRAWABLE_PATH)).map {
        showToConsole(it)
    }
    val endTime = Date().time
    println((endTime - startTime) / 1000)
}

private fun searchNonUsedDrawables(filesRoot: File, drawablesRoot: File) =
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
    println(it.nameWithoutExtension)
}

private fun byName(it: File) = it.nameWithoutExtension

private fun getNonUsedDrawablesWithNull(lf: File, drawablesRoot: File): List<File?> {
    val text = lf.readText()
    return drawablesRoot.listFiles().map {
        df ->
        if (containsFilename(text, df)) null else df
    }
}

private fun isFile(it: File) = it.isFile

private fun containsFilename(text: String, df: File) = text.contains(df.nameWithoutExtension)