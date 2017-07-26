import java.io.File
import java.util.*

val ROOT_PROJECT_PATH = "/home/vfrancisco/develop/idealista-android"

val RES_DRAWABLE_PATH = "app/src/main/res/drawable"
val RES_HDPI_DRAWABLE_PATH = RES_DRAWABLE_PATH.plus("-hdpi")
val APP_PATH = "app/src/main"

fun main(args: Array<String>) {
    val startTime = Date().time

    val folders = listOf(fileFromRelativePath(RES_DRAWABLE_PATH), fileFromRelativePath(RES_HDPI_DRAWABLE_PATH))
    searchNonUsedResourcesFromFolders(folders).map {
        showToConsole(it)
    }

    val endTime = Date().time
    println((endTime - startTime) / 1000)
}

private fun searchNonUsedResourcesFromFolders(folders: List<File>) : List<File> =
    folders.map {
        searchNonUsedResourcesFromFolder(fileFromRelativePath(APP_PATH), it)
    }.flatten()

private fun searchNonUsedResourcesFromFolder(folder: File, resourcesFolder: File) : List<File> =
        folder.walkTopDown()
                .filter { isFile(it) }
                .asIterable()
                .map {
                    getNonUsedDrawablesWithNull(it, resourcesFolder)
                }.flatten()
                .filterNotNull()
                .sortedBy {
                    byName(it)
                }.distinct()

private fun showToConsole(it: File) = println(it.nameWithoutExtension)

private fun byName(it: File) : String = it.nameWithoutExtension

private fun getNonUsedDrawablesWithNull(lf: File, drawablesRoot: File): List<File?> {
    val text = lf.readText()
    return drawablesRoot.listFiles().map {
        df ->
        if (containsFilename(text, df)) null else df
    }
}

private fun isFile(it: File) : Boolean = it.isFile

private fun containsFilename(text: String, df: File) : Boolean =
    text.contains(Regex("^${df.nameWithoutExtension}$"))

private fun fileFromRelativePath(relativePath : String) : File =
        File(ROOT_PROJECT_PATH, relativePath)