package upload.example.archivador.services

import org.springframework.stereotype.Service
import upload.example.archivador.utils.CSVHelper
import org.springframework.web.multipart.MultipartFile
import org.apache.commons.csv.CSVRecord

@Service
class UploadService {

	fun convertToRecords(file: MultipartFile, hasHeader: Boolean): List<CSVRecord> {

		try {
			if (CSVHelper.hasCSVFormat(file)) {
				return CSVHelper.convertToRecords(file.getInputStream(), hasHeader)
			}
			throw Exception()
		} catch (e: Exception) {
			throw e
		}

	}

}