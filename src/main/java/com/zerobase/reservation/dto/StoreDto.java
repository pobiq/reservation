package com.zerobase.reservation.dto;

import com.zerobase.reservation.entity.StoreEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDto {
    private String name;

    private String location;

    private String explanation;

    public static StoreDto fromEntity(StoreEntity storeEntity) {
        return StoreDto.builder()
                .name(storeEntity.getName())
                .location(storeEntity.getLocation())
                .explanation(storeEntity.getExplanation())
                .build();
    }
}
