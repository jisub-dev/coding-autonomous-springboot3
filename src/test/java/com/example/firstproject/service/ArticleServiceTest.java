package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test; // Test 패키지 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*; // 앞으로 사용할 수 있는 패키지 임포트

@SpringBootTest // 해당 클래스를 스프링 부트와 연동해 통합 테스트를 수행하겠다고 선언 -> 테스트 코드에서 스프링 부트가 관리하는 다양한 객체를 주입받을 수 있음
class ArticleServiceTest {

    @Autowired
    ArticleService articleService; // articleService 객체 주입
    @Test // 해당 메서드가 테스트 코드임을 선언
    void index() {
        // 1. 예상 데이터
        Article a = new Article(1L, "가가가가", "1111"); // 예상 데이터 객체로 저장
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<>(Arrays.asList(a, b, c)); // a, b, c 합치기
        // 2. 실제 데이터
        List<Article> articles = articleService.index(); // 모든 게시글을 조회 요청하고 그 결과로 반환되는 게시글의 묶음을 받아옴
        // 3. 비교 및 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    @Transactional
    void show_성공_존재하는_id_입력() {
        // 1. 예상 데이터
        Long id = 1L;
        Article expacted = new Article(id, "가가가가", "1111");
        // 2. 실제 데이터
        Article article = articleService.show(id); // 실제 데이터 저장
        // 3. 비교 및 검증
        assertEquals(expacted.toString(), article.toString()); // 비교
    }
    @Test
    @Transactional
    void show_실패_존재하지_않는_id_입력() {
        // 1. 예상 데이터
        Long id = -1L;
        Article expacted = null;
        // 2. 실제 데이터
        Article article = articleService.show(id); // 실제 데이터 저장
        // 3. 비교 및 검증
        assertEquals(expacted, article); // 실제 데이터, 예상 데이터의 값 null은 toString() 메서드를 호출할 수 없음
    }

    @Test
    @Transactional
    void create_성공_title과_content만_있는_dto_입력() {
        // 1. 예상 데이터
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);
        // 2. 실제 데이터
        Article article = articleService.create(dto);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {
        // 1. 예상 데이터
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expacted = null;
        // 2. 실제 데이터
        Article article = articleService.create(dto);
        // 3. 비교 및 검증
        assertEquals(expacted, article); // 어차피 null 이므로 .toString() 메서드는 사용하지 않는다
    }


    @Test
    @Transactional
    void updat_성공_존재하는_id와_title_content가_있는_dto_입력() {
        // 1. 예상 데이터
        Long id = 1L;
        String title = "가나다라";
        String content = "1234";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expacted = new Article(id, title, content); // 예상 값을 직접 지정
        // 2. 실제 데이터
        Article article = articleService.update(id, dto);
        // 3. 비교 및 검증
        assertEquals(expacted.toString(), article.toString());
    }
    @Test
    @Transactional
    void update_성공_존재하는_id와_title만_있는_dto_입력() {
        // 1. 예상 데이터
        Long id = 1L;
        String title = "AAAA";
        String content = null;
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expacted = new Article(1L, "AAAA", "1111"); // 예상 값을 직접 지정
        // 2. 실제 데이터
        Article article = articleService.update(id, dto);
        // 3. 비교 및 검증
        assertEquals(expacted.toString(), article.toString());
    }
    @Test
    @Transactional
    void update_실패_존재하지_않는_id의_dto_입력() {
        // 1. 예상 데이터
        Long id = -1L;
        String title = "가나다라";
        String content = "1234";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expacted = null; // 예상 값을 직접 지정
        // 2. 실제 데이터
        Article article = articleService.update(id, dto);
        // 3. 비교 및 검증
        assertEquals(expacted, article); // article 이 먼저 null 이 되기 때문에 .getTitle() 메서드 사용 불가능
    }


    @Test
    @Transactional
    void delete_성공_존재하는_id_입력() {
        // 1. 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        // 2. 실제 데이터
        Article article = articleService.delete(id);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void delete_실패_존재하지_않는_id_입력() {
        // 1. 예상 데이터
        Long id = -1L;
        Article expected = null;
        // 2. 실제 데이터
        Article article = articleService.delete(id);
        // 3. 비교 및 검증
        assertEquals(expected, article);
    }
}