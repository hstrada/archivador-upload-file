package upload.example.archivador.entities

import lombok.Getter
import lombok.Setter
import lombok.NoArgsConstructor
import lombok.AllArgsConstructor
import lombok.Data


@Getter
@Setter
class Student(
	var id: Long = -1,
	var name: String = "",
	var link: String = ""
) {
}