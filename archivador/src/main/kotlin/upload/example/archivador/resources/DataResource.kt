package upload.example.archivador.resources

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import upload.example.archivador.utils.DataHelper
import upload.example.archivador.entities.Student
import upload.example.archivador.entities.Teacher

@RestController
class DataResource() {

	@GetMapping("/entities")
	fun uploadFile(): ResponseEntity<*> {

		var entities = DataHelper.findClasses("upload.example.archivador.entities")

		var result: HashMap<String, ArrayList<String>> = HashMap<String, ArrayList<String>>()

		entities.stream().forEach {
			var entitiesFields: ArrayList<String> = ArrayList<String>()

			Class.forName(it.replace("\\", ".")).newInstance()::class.java.declaredFields.forEach {
				entitiesFields.add(
					it.getName()
				)
			}

			result.set(it.split("\\")[1], entitiesFields)
		}

		return ResponseEntity.status(HttpStatus.OK).body(result	)
	}
}
