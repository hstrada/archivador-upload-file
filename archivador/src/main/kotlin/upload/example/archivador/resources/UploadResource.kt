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
import kotlin.reflect.KMutableProperty


@RestController
class UploadResource(private val uploadService: UploadService) {

	fun createObjectFromRecord(
		ownerClassName: String,
		fieldNameWithValues: HashMap<String, String> = HashMap<String, String>()
	): Any {
		val kClass = Class.forName(ownerClassName).kotlin

		val instance = kClass.objectInstance ?: kClass.java.newInstance()

		for (keyValue in fieldNameWithValues) {
			val member = kClass.members
				.filterIsInstance<KMutableProperty<*>>()
				.filter { it.name == keyValue.key }
				.firstOrNull()

			var memberType: Any = member?.returnType.toString().split(".")[1]
			if (memberType == "String") {
				member?.setter?.call(instance, keyValue.value)
			} else {
				var memberValue = keyValue.value

				var result: Any = when (memberType) {
					"Int" -> memberValue.toInt()
					"Long" -> memberValue.toLong()
					"Double" -> memberValue.toDouble()
					"Float" -> memberValue.toFloat()
					else -> println("Unknown Type")
				}

				member?.setter?.call(instance, result)
			}

		}

		return instance
	}

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
			var items: ArrayList<Any> = ArrayList<Any>()
			var csvRecords = uploadService.convertToRecords(file, hasHeader)

			for (record: CSVRecord in csvRecords) {
				var recordMap: HashMap<String, String> = HashMap<String, String>()
				recordMap.put("id", record.get(0))
				recordMap.put("firstName", record.get(1))
				recordMap.put("lastName", record.get(2))
				var item =
					createObjectFromRecord("upload.example.archivador.entities.${entity}", recordMap)
				items.add(item)
			}

			return ResponseEntity.status(HttpStatus.OK).body(items)
		} catch (e: Exception) {
			var errorResponse = JSONObject()
			errorResponse.put("message", e.message)
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorResponse)
		}
	}
}
