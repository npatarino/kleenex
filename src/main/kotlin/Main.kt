import java.io.File
import java.util.*

val ROOT_PROJECT_PATH = "/home/argento/projects/idealista/android-studio-project/application/"

val RES_DRAWABLE_PATH = "app/src/main/res/drawable"
val APP_PATH = "app/src/main"

fun main(args: Array<String>) {

    val startTime = Date().time

    searchNonUsedDrawables(File(ROOT_PROJECT_PATH, APP_PATH), File(ROOT_PROJECT_PATH, RES_DRAWABLE_PATH))

    val endTime = Date().time

    println((endTime - startTime) / 1000)

    /*File(ROOT_PROJECT_PATH, RES_DRAWABLE_PATH)
            .listFiles().filter {
        drawableFile ->
        File(ROOT_PROJECT_PATH, APP_PATH).listFiles().filter {
            file ->
            file.readText().contains(drawableFile.nameWithoutExtension)
        }.isEmpty()
    }.map {
        println(it.nameWithoutExtension)
    }*/

}


private fun searchNonUsedDrawables(filesRoot: File, drawablesRoot: File) {
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
            .map {
                showToConsole(it)
            }
}

private fun showToConsole(it: File) {
    println(it.nameWithoutExtension)
}

private fun byName(it: File) = it.nameWithoutExtension

private val getNudwn: (File, File) -> List<File?> = {
    lf, drawablesRoot ->
    val text = lf.readText()
    drawablesRoot.listFiles().map {
        df ->
        if (containsFilename(text, df)) null else df
    }
}

private fun getNonUsedDrawablesWithNull(lf: File, drawablesRoot: File): List<File?> {
    val text = lf.readText()
    return drawablesRoot.listFiles().map {
        df ->
        if (containsFilename(text, df)) null else df
    }
}

private fun isFile(it: File) = it.isFile

private fun containsFilename(text: String, df: File) = text.contains(df.nameWithoutExtension)