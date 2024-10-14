package com.Hayati.Reservation.des.Hotels.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")  // Remplacez par l'origine de votre application Flutter Web
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}


// @Override
// public void addCorsMappings(CorsRegistry registry) {
//     registry.addMapping("/api/**") // Ajustez cette ligne pour correspondre à vos endpoints
//             .allowedOrigins("http://localhost:56852") // Remplacez par l'origine de votre application Flutter
//             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//             .allowedHeaders("*")
//             .allowCredentials(false); // Si vous avez besoin d'autoriser les cookies ou sessions
// }
    











// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

//         @Override
//         public void addCorsMappings(CorsRegistry registry) {
//             registry.addMapping("/**")
//                     .allowedOriginPatterns("http:// 192.168.100.115:58758") // Ensure this matches your Flutter web URL
//                     .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                     .allowedHeaders("*")
//                     .allowCredentials(false); // Enable this if you are using cookies or authentication tokens
//         }
    
    
// }
