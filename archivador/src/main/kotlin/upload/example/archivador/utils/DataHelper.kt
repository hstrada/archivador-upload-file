package upload.example.archivador.utils

import java.net.URL
import sun.misc.Launcher
import java.io.File

class DataHelper {
	companion object Factory {
		fun retrieveEntities(pckgname: String): ArrayList<String> {

						// Translate the package name into an absolute path
			var name = pckgname
			if (!name.startsWith("/")) {
				name = "/$name"
			}
			name = name.replace('.', '/')

			// Get a File object for the package
			val url: URL = Launcher::class.java.getResource(name)
			val directory = File(url.getFile())

			var entities: ArrayList<String> = ArrayList<String>()
			
			if (directory.exists()) {
				// Get the list of the files contained in the package
				directory.walk()
					.filter { f -> f.isFile() && f.name.contains('$') == false && f.name.endsWith(".class") }
					.forEach {
						val fullyQualifiedClassName = pckgname +
								it.canonicalPath.removePrefix(directory.canonicalPath)
									.dropLast(6) // remove .class
									.replace('/', '.')
						try {
							entities.add(fullyQualifiedClassName)
						} catch (cnfex: ClassNotFoundException) {
							System.err.println(cnfex)
							throw cnfex
						} catch (iex: InstantiationException) {
							// We try to instantiate an interface
							// or an object that does not have a
							// default constructor
							throw iex
						} catch (iaex: IllegalAccessException) {
							// The class is not public
							throw iaex
						}
					}
			}
			return entities
		}
	}
}