package com.zerobase.reservation.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity(name="store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreEntity {
    // 매장 아이디(숫자)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 매장명
    @Column(unique = true)
    private String name;

    // 매장 위치
    private String location;

    // 매장 설명
    private String explanation;

}
