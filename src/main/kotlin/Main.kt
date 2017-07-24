import java.io.File

val ROOT_PROJECT_PATH = "/home/argento/projects/idealista/android-studio-project/application/"

val RES_DRAWABLE_PATH = "app/src/main/res/drawable"
val RES_LAYOUT_PATH = "app/src/main/res/layout"

fun main(args: Array<String>) {

    File(ROOT_PROJECT_PATH, RES_LAYOUT_PATH).listFiles().map{
        lf ->
        val text = lf.readText()
        File(ROOT_PROJECT_PATH, RES_DRAWABLE_PATH).listFiles().map {
            df->
            if(text.contains(df.nameWithoutExtension)) null else df
        }
    }.flatten().filterNotNull().sortedBy {
        it.nameWithoutExtension
    }.distinct().map {
        println(it.nameWithoutExtension)
    }




    /*File(ROOT_PROJECT_PATH, RES_DRAWABLE_PATH)
            .listFiles().filter {
        drawableFile ->
        File(ROOT_PROJECT_PATH, RES_LAYOUT_PATH).listFiles().filter {
            file ->
            file.readText().contains(drawableFile.nameWithoutExtension)
        }.isEmpty()
    }.map {
        println(it.nameWithoutExtension)
    }*/

}