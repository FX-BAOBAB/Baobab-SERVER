package delivery.common.config.kafka;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import db.domain.receiving.ReceivingEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@Slf4j
public class KafkaConsumer {

    private static final String GOODS_TOPIC = "receiving";

    private static final String GROUP_ID = "goods.group.v1";

    private final ObjectMapper objectMapper;

    public KafkaConsumer() {
        this.objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()) // JavaTimeModule 등록
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @KafkaListener(topics = GOODS_TOPIC, groupId = GROUP_ID)
    public void recordListener(String jsonMessage) {
        try {
            ReceivingEntity receivingEntity = objectMapper.readValue(jsonMessage, ReceivingEntity.class);
            log.info("Received data to kafka : {}", receivingEntity);
        } catch (Exception e) {
            log.error("Record Listener Error Message : {}", jsonMessage, e);
        }
    }

}
