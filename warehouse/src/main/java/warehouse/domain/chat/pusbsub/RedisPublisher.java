package warehouse.domain.chat.pusbsub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import db.domain.chat.message.ChatMessageEntity;

/**
 * 채팅방에 입장하여 메시지를 작성하면 해당 메시지를 Redis Topic 에 발행하는 기능을 수행한다.
 * 이 서비스를 통해 메시지를 발행하면 대기하고 있던 redis 구독 서비스가 메시지를 처리한다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessageEntity message){
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

}
