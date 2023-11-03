package com.example.toy.repository;

import com.example.toy.dto.ChattingRoomDto;
import com.example.toy.entity.ChattingContent;
import com.example.toy.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
    @Query(value = "select * from chatting_room where post_num=:postNum and buyer_user=:userPhone", nativeQuery = true)
    ChattingRoom findChattingRoom(@Param("postNum") Long postNum, @Param("userPhone") String phoneNum);

    List<ChattingRoom> findByBuyerUser(String phoneNum);
    @Query(value = "select new com.example.toy.dto.ChattingRoomDto(r.roomId,r.sellerUser,r.buyerUser, c.sendTime, c.contents, m.nickName, m.profile)\n" +
            "from ChattingRoom r, ChattingContent c, Member m\n" +
            "where r.roomId = c.roomId \n" +
            "and r.sellerUser = m.phone \n" +
            "and c.roomId in (select roomId from ChattingRoom where buyerUser = :buyer_user)\n" +
            "and c.sendTime = (select max(sendTime) from ChattingContent where roomId = r.roomId )\n" +
            "order by c.sendTime desc\n"
    )
    List<ChattingRoomDto> getRoomList(@Param("buyer_user") String phoneNum);
}
