package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    public List<Coffee> index() {return coffeeRepository.findAll();}

    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }
    public Coffee create(CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        // coffee 가 신규로 작성되었다면 body에 id가 있을 필요가 없기 때문에 getId()메서드로 예외처리
        if(coffee.getId() != null){
            return null;
        }
        return coffeeRepository.save(coffee);
    }
    public Coffee update(Long id, CoffeeDto coffeeDto) {
        //1. DTO -> 엔티티 변환
        Coffee coffee = coffeeDto.toEntity();
        log.info("id = {}, coffee = {}", id, coffee.toString());
        //2. 타깃 조회
        Coffee target = coffeeRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
        if(target == null || id != coffee.getId()) {
            log.info("잘못된 요청! id = {}, coffee = {}", id, coffee.toString());
            return null;
        }
        //4. 업데이트 및 정상처리(200)
        target.patch(coffee);
        return coffeeRepository.save(target);
    }
    public Coffee delete(Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if(target == null) {
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }


}