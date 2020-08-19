package upload.example.archivador.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.InputStream
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVFormat
import upload.example.archivador.entities.Student
import org.apache.commons.csv.CSVRecord
import org.springframework.web.multipart.MultipartFile
import kotlin.reflect.KMutableProperty

class CSVHelper {

	companion object Factory {

		var TYPE = "text/csv"

		fun hasCSVFormat(file: MultipartFile): Boolean {

			if (!TYPE.equals(file.getContentType())) {
				return false;
			}

			return true;
		}

		fun convertToRecords(inputStream: InputStream, hasHeader: Boolean): List<CSVRecord> {
			try {
				var fileReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))

				val shouldRemoveHeader =
					if (hasHeader) CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
					else {
						CSVFormat.DEFAULT
					}

				var csvParser =
					CSVParser(
						fileReader,
						shouldRemoveHeader
					)

				var csvRecords = csvParser.getRecords()

				return csvRecords

			} catch (e: Exception) {
				println(e.message)
				throw e
			}

		}

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

	}


}