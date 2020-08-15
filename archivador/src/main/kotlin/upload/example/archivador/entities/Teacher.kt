package upload.example.archivador.entities

import lombok.Getter
import lombok.Setter
import lombok.NoArgsConstructor

@Getter
@Setter
@NoArgsConstructor
class Teacher(
	var id: Long = -1,
	var firstName: String = "",
	var lastName: String = ""
) {
}