package com.bakigoal.springdatajpademo.config;

import com.hazelcast.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "hazelcast")
public class HazelcastConfig {

    @Bean
    public Config hazelCastConfig(){
        return new Config()
                .setInstanceName("hazelcast-instance");
    }
}
