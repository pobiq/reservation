package com.zerobase.reservation.controller;

import com.zerobase.reservation.dto.StoreDto;
import com.zerobase.reservation.entity.StoreEntity;
import com.zerobase.reservation.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/store")
@AllArgsConstructor
public class StoreController {

    private final StoreService storeService;


    /**
     * 매장 추가
     * @param storeDto 매장명, 매장위치, 매장설명 dto
     */
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> createStore(@RequestBody StoreDto storeDto) {
        StoreDto store = this.storeService.createStore(storeDto);
        return ResponseEntity.ok(store);
    }

    /**
     * 매장 수정
     * @param storeDto 매장명, 매장위치, 매장설명 dto
     */
    @PutMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> modifyStore(@RequestBody StoreDto storeDto) {
        StoreDto store = this.storeService.modifyStore(storeDto);
        return ResponseEntity.ok(store);
    }

    /**
     * 매장 삭제
     * @param name 매장명
     */
    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> DeleteStore(@PathVariable String name) {
        String storeName = this.storeService.deleteStore(name);
        return ResponseEntity.ok(storeName);
    }

    /**
     * 매장 목록 조회(가나다 순)
     * @return
     */
    @GetMapping("orderbyname")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> selectStoreOrderByName() {
        List<StoreDto> list = this.storeService.selectStoreOrderByName();
        return ResponseEntity.ok(list);
    }

    /**
     * 매장 상세 정보 조회
     * @param name 매장명
     * @return
     */
    @GetMapping("/{name}/detail")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> selectStoreDetail(@PathVariable String name) {
        StoreDto storeDto = this.storeService.selectStoreDetail(name);
        return ResponseEntity.ok(storeDto);
    }

}
