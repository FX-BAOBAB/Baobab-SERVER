package db.domain.chat.room;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity,Long> {

    Optional<ChatRoomEntity> findFirstByUsedGoodsIdAndUserId(Long usedGoodsId, Long userId);

    List<ChatRoomEntity> findAll();

    void deleteById(Long id);

    Optional<ChatRoomEntity> findFirstById(Long id);

    List<ChatRoomEntity> findByUsedGoodsIdIn(List<Long> usedGoodsId);

    List<ChatRoomEntity> findByUserId(Long userid);

}
