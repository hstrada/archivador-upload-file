package upload.example.archivador.config

import springfox.documentation.swagger2.annotations.EnableSwagger2
import org.springframework.context.annotation.Configuration
import springfox.documentation.spring.web.plugins.Docket
import org.springframework.context.annotation.Bean
import springfox.documentation.spi.DocumentationType
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.PathSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.Contact
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableSwagger2
open class SwaggerConfiguration {
	
	@Bean
	open fun api(): Docket {
		return Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage("upload.example.archivador"))
			.paths(PathSelectors.any())
			.build()
	}

}