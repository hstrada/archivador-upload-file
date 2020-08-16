package upload.example.archivador.resources

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.multipart.MultipartFile
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMapping
import upload.example.archivador.utils.CSVHelper
import java.io.FileInputStream
import org.springframework.beans.factory.annotation.Autowired
import upload.example.archivador.services.UploadService
import upload.example.archivador.entities.Student
import org.apache.commons.csv.CSVRecord
import net.minidev.json.JSONObject
import upload.example.archivador.entities.Teacher


@RestController
class UploadResource(private val uploadService: UploadService) {

	@PostMapping("/upload")
	fun uploadFile(
		@RequestParam("file") file: MultipartFile,
		@RequestParam(
			name = "hasHeader",
			defaultValue = "true"
		) hasHeader: Boolean,
		@RequestParam("entity") entity: String
	): ResponseEntity<*> {

		try {
			var items: ArrayList<Class<*>> = ArrayList<Class<*>>()
			var csvRecords = uploadService.convertToRecords(file, hasHeader)
			for (record: CSVRecord in csvRecords) {
				var item = Class.forName("upload.example.archivador.entities.Student")?.newInstance()
				var item2 = Class.forName("upload.example.archivador.entities.Student")?.kotlin
				var item3 = Class.forName("upload.example.archivador.entities.Student").newInstance()::class.java.getDeclaredMethods().forEach {
					it.setAccessible(true)
				}

			}
			return ResponseEntity.status(HttpStatus.OK).body(items)
		} catch (e: Exception) {
			var errorResponse = JSONObject()
			errorResponse.put("message", e.message)
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorResponse)
		}
	}
}
