import java.io.File

val ROOT_PROJECT_PATH = "/home/argento/projects/idealista/android-studio-project/application/"

val RES_DRAWABLE_PATH = "app/src/main/res/drawable"
val RES_LAYOUT_PATH = "app/src/main/res/layout"

fun main(args: Array<String>) {

    File(ROOT_PROJECT_PATH, RES_DRAWABLE_PATH)
            .listFiles().map { it.nameWithoutExtension }.filter {
        name ->
        File(ROOT_PROJECT_PATH, RES_LAYOUT_PATH).listFiles().filter {
            file ->
            file.readText().contains(name)
        }.isEmpty()
    }.map {
        println(it)
    }

}