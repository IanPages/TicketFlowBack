package com.back.ticketflow.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dtnprgkqq",
                "api_key", "813224463518994",
                "api_secret", "o_jWECZk4Rx7ppjs3G_ilwTqXZI"
        ));
    }
}
