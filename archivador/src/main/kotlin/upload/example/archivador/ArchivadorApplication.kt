package upload.example.archivador

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("upload.example.archivador")
open class ArchivadorApplication

fun main(args: Array<String>) {
	runApplication<ArchivadorApplication>(*args)
}
