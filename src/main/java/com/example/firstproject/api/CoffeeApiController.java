package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CoffeeApiController {
    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping("/api/coffees") // 전체 목록 읽기
    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }
    @GetMapping("/api/coffees/{id}") // 하나씩 읽기
    public Coffee getCoffeeById(@PathVariable Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }
    @PostMapping("/api/coffees")
    public Coffee addCoffee(@RequestBody CoffeeDto dto) {
        Coffee coffee =  dto.toEntity();
        return coffeeRepository.save(coffee);
    }
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> updateCoffee(@RequestBody CoffeeDto dto, @PathVariable Long id) {
        //1. DTO -> 엔티티 변환
        Coffee coffee = dto.toEntity();
        log.info("id = {}, coffee = {}", id, coffee.toString());
        //2. 타깃 조회
        Coffee target = coffeeRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
        if(target == null || id != coffee.getId()) {
            log.info("잘못된 요청! id = {}, coffee = {}", id, coffee.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //4. 업데이트 및 정상처리(200)
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> deleteCoffee(@PathVariable Long id) {
        Coffee coffee = coffeeRepository.findById(id).orElse(null);
        if(coffee == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        coffeeRepository.delete(coffee);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
