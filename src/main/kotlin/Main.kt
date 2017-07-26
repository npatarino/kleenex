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
                    getNonUsedResourcesFromFolderInFile(resourcesFolder, it)
                }.flatten()
                .filterNotNull()
                .sortedBy {
                    byName(it)
                }.distinct()

private fun showToConsole(it: File) = println(it.nameWithoutExtension)

private fun byName(it: File) : String = it.nameWithoutExtension

private fun getNonUsedResourcesFromFolderInFile(resourcesFolder: File, file: File): List<File?> {
    val fileContent = file.readText()
    return resourcesFolder.listFiles().map {
        file ->
        if (fileContent.containsFilenameOf(file)) null else file
    }
}

private fun isFile(it: File) : Boolean = it.isFile

private fun String.containsFilenameOf(file: File) : Boolean =
    this.contains(Regex("^${file.nameWithoutExtension}$"))

private fun fileFromRelativePath(relativePath : String) : File =
        File(ROOT_PROJECT_PATH, relativePath)