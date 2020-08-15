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


@RestController
class UploadResource(private val uploadService: UploadService) {

	@PostMapping("/upload")
	fun uploadFile(
		@RequestParam("file") file: MultipartFile, @RequestParam(
			name = "hasHeader",
			defaultValue = "true"
		) hasHeader: Boolean
	): ResponseEntity<*> {

		try {
			var students: ArrayList<Student> = ArrayList<Student>()
			var csvRecords = uploadService.convertToRecords(file, hasHeader)
			for (record: CSVRecord in csvRecords) {
				var student = Student(
					record.get(0).toLong(), record.get(1), record.get(2)
				)
				students.add(student)
			}
			return ResponseEntity.status(HttpStatus.OK).body(students)
		} catch (e: Exception) {
			var errorResponse = JSONObject()
			errorResponse.put("message", e.message)
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorResponse)
		}
	}
}
