package warehouse.domain.kafka.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.domain.goods.GoodsEntity;
import db.domain.receiving.ReceivingEntity;
import global.annotation.Business;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Business
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerBusiness {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String GOODS_TOPIC = "receiving";

    public void sendGoodsToKafka(ReceivingEntity receivingEntity) {
        try {
            // json 문자열로 변환
            String jsonInString = objectMapper.writeValueAsString(receivingEntity);
            kafkaTemplate.send(GOODS_TOPIC, jsonInString);
            log.info("Success send to Kafka : {}", jsonInString);
        } catch (Exception e) {
            log.info("Error in send to kafka", e);
        }
    }

}
