import java.io.File
import java.util.*

val ROOT_PROJECT_PATH = "/home/josedlpozo/workspace/idealista-android"

val RES_DRAWABLE_PATH = "app/src/main/res/drawable"
val RES_LDPI_DRAWABLE_PATH = RES_DRAWABLE_PATH.plus("-ldpi")
val RES_MDPI_DRAWABLE_PATH = RES_DRAWABLE_PATH.plus("-mdpi")
val RES_HDPI_DRAWABLE_PATH = RES_DRAWABLE_PATH.plus("-hdpi")
val RES_XHDPI_DRAWABLE_PATH = RES_DRAWABLE_PATH.plus("-xhdpi")
val RES_XXHDPI_DRAWABLE_PATH = RES_DRAWABLE_PATH.plus("-xxhdpi")
val RES_XXXHDPI_DRAWABLE_PATH = RES_DRAWABLE_PATH.plus("-xxxhdpi")
val RES_V21_DRAWABLE_PATH = RES_DRAWABLE_PATH.plus("-v21")

val APP_PATH = "app/src/main"

fun main(args: Array<String>) {
    val startTime = Date().time


    val folders = listOf(fileFromRelativePath(RES_DRAWABLE_PATH), fileFromRelativePath(RES_LDPI_DRAWABLE_PATH), fileFromRelativePath(RES_MDPI_DRAWABLE_PATH)
            , fileFromRelativePath(RES_HDPI_DRAWABLE_PATH), fileFromRelativePath(RES_XHDPI_DRAWABLE_PATH), fileFromRelativePath(RES_XXHDPI_DRAWABLE_PATH)
            , fileFromRelativePath(RES_XXXHDPI_DRAWABLE_PATH), fileFromRelativePath(RES_V21_DRAWABLE_PATH))
    val listNonUsedFolders = searchNonUsedResourcesFromFolders(folders).map {
        showToConsole(it)
    }

    println("Size: ${listNonUsedFolders.size}")

    val endTime = Date().time
    println((endTime - startTime) / 1000)
}

private fun searchNonUsedResourcesFromFolders(folders: List<File>) : List<File> =
    folders.map {
        val searchUsedResourcesFromFolder = searchUsedResourcesFromFolder(fileFromRelativePath(APP_PATH), it)
        it.listFiles().subtract(searchUsedResourcesFromFolder)
    }.flatten()

private fun searchUsedResourcesFromFolder(folder: File, resourcesFolder: File) : List<File> =
        folder.walkTopDown()
                .filter { isFile(it) }
                .asIterable()
                .map {
                    getUsedResourcesFromFolderInFile(resourcesFolder, it)
                }.flatten()
                .filterNotNull()
                .sortedBy {
                    byName(it)
                }.distinct()

private fun showToConsole(it: File) = println(it.nameWithoutExtension)

private fun byName(it: File) : String = it.nameWithoutExtension

private fun getUsedResourcesFromFolderInFile(resourcesFolder: File, file: File): List<File?> {
    val fileContent = file.readText()
    return resourcesFolder.listFiles().map {
        if (fileContent.containsFilenameOf(it)) it else null
    }
}

private fun isFile(it: File) : Boolean = it.isFile

private fun String.containsFilenameOf(file: File) : Boolean =
    this.contains(file.nameWithoutExtension)

private fun fileFromRelativePath(relativePath : String) : File =
        File(ROOT_PROJECT_PATH, relativePath)