package com.Hayati.Reservation.des.Hotels.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // // @Override
    // // public void addCorsMappings(CorsRegistry registry) {
    // //     registry.addMapping("/**")
    // //             .allowedOrigins("http://localhost:56289")  // Remplacez par l'URL correcte de votre front-end
    // //             .allowedMethods("*")
    // //             .allowedHeaders("*")
    // //             .allowCredentials(true);
    // // }

    //  @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     // Exposez les fichiers du dossier C:/Pfe/Reservation_hotels/hotel_photos
    //     registry.addResourceHandler("/hotel_photos/**")
    //             .addResourceLocations("file:///C:/Pfe/Reservation_hotels/hotel_photos/");
    // }
 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Expose the "hotel_photos" directory so it is publicly accessible via a URL
        registry.addResourceHandler("/hotel_photos/**")
                .addResourceLocations("file:///C:/Pfe/Reservation_hotels/hotel_photos/")
                .setCachePeriod(3600);  // Optional: Cache for one hour

        // Expose the "chambre_photos" directory so it is publicly accessible via a URL
        registry.addResourceHandler("/chambre_photos/**")
                .addResourceLocations("file:///C:/Pfe/Reservation_hotels/chambre_photos/")
                .setCachePeriod(3600); 
                
                registry.addResourceHandler("/client_photos/**")
                .addResourceLocations("file:///C:/Pfe/Reservation_client/client_photos/")
                .setCachePeriod(3600); 
                registry.addResourceHandler("/subscribe_photos/**")
                .addResourceLocations("file:///C:/Pfe/Reservation_client/subscribe_photos/")
                .setCachePeriod(3600);   
                registry.addResourceHandler("/suite_photos/**")
                .addResourceLocations("file:///C:/Pfe/Reservation_hotels/suite_photos/")
                .setCachePeriod(3600);  }
                
    
}

