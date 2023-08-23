package zerobase.reservation.dto;

import lombok.*;
import zerobase.reservation.dao.Member;
import zerobase.reservation.dao.Store;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDto {
    private Long storeId;
    private Long memberId;
    private String name;
    private String location;
    private String description;
    
    public static Store toStoreEntity(Member member, StoreDto storeDto) {

        return new Store().builder()
                .member(member)
                .name(storeDto.getName())
                .location(storeDto.getLocation())
                .description(storeDto.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
