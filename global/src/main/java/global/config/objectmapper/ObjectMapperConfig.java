package global.config.objectmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  Object Mapper 설정
 */
@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(){

        ObjectMapper objectMapper = new ObjectMapper();

        // Jdk 8 이후 Module 포함하기
        objectMapper.registerModule(new Jdk8Module());

        // local data 사용
        objectMapper.registerModule(new JavaTimeModule());

        // Spring Boot에서는 좀 더 유연한 Schema를 보장하기 위해서
        // ObjectMapper의 설정 중으로 FAIL_ON_UNKNOWN_PROPERTIES의 옵션을 false 처리하여
        // deserialize시 알지 못하는 property가 오더라도 실패하지 않도록 처리
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        // 빈 beans 에러 무시하기
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);


        // 날짜 관련 직렬화
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 스네이크 케이스
        objectMapper.setPropertyNamingStrategy(new SnakeCaseStrategy());

        return objectMapper;

    }

}
