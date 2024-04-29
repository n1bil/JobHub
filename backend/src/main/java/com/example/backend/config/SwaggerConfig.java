package com.example.backend.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Track Job Application APIs",
                description = "REST APIs for managing tasks and job tracking in the Track Job Application",
                version = "v1.0",
                contact = @Contact(
                        name = "nabil",
                        email = "nabil.nadzor@example.com",
                        url = "https://github.com/n1bil"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Track Job Application Documentation",
                url = "https://frontend-one-beige.vercel.app/"
        )
)
//@SecurityScheme(
//        name = "Bearer Authentication",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer",
//        extensions = {
//                @Extension(
//                        name = "cookie",
//                        properties = {
//                                @ExtensionProperty(
//                                        name = "name",
//                                        value = "token"
//                                ),
//                                @ExtensionProperty(
//                                        name = "in",
//                                        value = "cookie"
//                                )
//                        }
//                )
//        }
//)
public class SwaggerConfig {
}
