![img.png](img.png)

한 프로젝트에 실습코드와 연습문제를 한번에 넣는 구조로 구성함

이점 : 동일한 환경을 공유할 수 있음



3장

뷰 이름을 반환할 땐 '/'로 시작하면 안된다 
ex)
GetMapping("/signup")
public String newMemberForm(){
return "signup";
}

     @GetMapping("/sign-up")
    public String newMemberForm(){
        return "sign-up"; //이 부분이다.
    }

    @PostMapping("/sign-up")
    public String saveMember(MemberForm form){

이런식으로 사용하면 된다.

또한 resources/templates 에 해당 머스타츠 파일이 있어야 웹 페이지에 접속할 수 있다.


세미나_0213
프론트서버, 백엔드서버, DB 이 세가지가 주어졌을 때
각각 서버의 문제를 200번대, 400번대, 500번대로 오류 메세지를 클라이언트 측에 넘겨서 문제를 찾을 수 있다.
이때 각 오류 번호들은 프론트와 백엔드 등 여러 팀간의 약속으로 정할 수 있다.

2만개의 get 요청을 보낼 필요 없이, 각 페이지가 10개의 게시글을 나타낸다고 하면
10개의 get요청을 보내면 된다. 이때 get요청은 각 게시글의 고유ID를 의미할 수 있다.

무결성이 검증되지 않은 데이터를 사용하면 SQLInjection이 가능하다. 
그래서 서버와 백엔드 간에 프로토콜을 파악하기 어렵게 만들어야 한다.




<hr>
4장

Slf4j : Simple Logging Facase for Java (로깅기능)

<hr>
5장

No default constructor for entity -> 해당 엔티티에 기본 생성자가 없어서 에러가 났음
<hr>
7장

뷰 페이지에서 변수를 사용할 떄는 중괄호를 두 개({{}})를 쓰지만 컨트롤러에서 URL변수를 사용할 때는 하나({})만 쓴다.
<hr>
<h2>8장 <데이터 삭제하기></h2>

@GetMapping("/articles/{id}/delete")<br>
public String delete(){<br>
log.info("삭제 요청이 들어왔습니다!");<br>
//1. 삭제할 대상 가져오기<br>
//2. 대상 엔티티 삭제하기<br>
//3. 결과 페이지로 리다이렉트하기<br>
return null;<br>
}<br>

위 순서대로 한다.
<hr>

**한 번 쓰고 사라지는 휘발성 데이터를 등록하는 법**<br>
형식 : 객체명.addFlashAttribute(넘겨_주려는_키_문자열, 넘겨_주려는_값_객체);<br><br>
<hr>

**데이터를 삭제할 때 사용하는 SQL문**<br>
형식 : DELETE [FROM] 테이블명 WHERE 조건; []:생략 가능<br><br>
<hr>

**삭제 순서 정리**<br>
1. 클라이언트가 삭제 요청을 함
2. 컨트롤러에서 @GetMapping으로 article/{id}/delete를 받음
3. 이때 id는 @PathVariable을 사용하여 외부 매개변수 사용을 가능케 함
4. 리파지터리에서 해당 id로("findById(id)"이용) DB에 있는 삭제 대상인 값을 찾음 //이때 findById()메서드는 Spring Data JPA의 JpaRepository 인터페이스에서 제공되는 메서드임
5. 리파지터리에 있는 delete() 메서드로 데이터를 삭제함(이때 자동으로 SQL문이 실행됨)
6. 삭제 작업이 끝나면 "/articles"로 리다이렉트함
7. "heder.mustache"에 있는 msg창을 띄움
8. 이때 리다이렉트되는 시점에 휘발성 데이터를 등록하기 위해 RedirectAttribytes 객체의 addFlashAttribute() 메서드를 이용함.
<br><br>
<hr>

**연습문제를 풀때 DB에 초기값을 넣기 위해선 data.sql에 SQL문을 넣어야함**
스프링 부트는 data.sql파일만 자동으로 인식함
<hr>

**쿼리(query)란**<br>
DB에 정보를 요청하는 구문
<br>
<hr>
<h3>9장 <CRUD와 SQL쿼리 종합></h3>

**JDBC URL 고정하기**<br>
#DB URL 설정<br>
#유니크 URL 생성하지 않기<br>
spring.datasource.generate-unique-name=false<br>
#고정 URL 설정하기<br>
spring.datasource.url=jdbc:h2:mem:testdb<br>
<hr>

**생성 테이블 명 설정하기**<br>
엔티티 클래스에 @Table어노테이션을 사용하여 @Table(name = "ORDERS")를 선언하면 클래스 명과 상관없이 이름이 설정된다<br>
<br>
<hr>
<h3>10장 </h3><br>

**PUT**:기존 데이터를 전부 새 내용으로 변경. 만약 기존데이터가 없으면 새로 생성<br>
**PATCH**:기존 데이터 중에서 일부만 새 내용으로 변경<br>

**HTTP 상태 코드**<br>
1xx(정보) : 요청이 수신돼 처리중<br>
2xx(성공) : 요청이 정삭정으로 처리됨<br>
3xx(리다이렉션 메시지) : 요청을 완료하려면 추가 행동 필요<br>
4xx(클라이언트 요청 오류) : 클라이언트의 요청이 잘못돼 서버가 요청을 수행할 수 없음<br>
5xx(서버 응답 오류) : 서버 내부에 에러가 발생해 클라이언트 요청에 대해 적절히 수행하지 못했음<br>
<hr>

**포스트맨에서 PATCH로 데이터 수정하기**<br>
https://jsonplaceholder.typicode.com/posts/1 처럼 1번 게시물을 URL로 설정한 뒤<br>
```json
{
    "title": "abcdf",
    "body" : "123456"
}
```
전송 방식을 PATCH로 변경하고 SEND를 누르면<br>
```json
{
    "userId": 1,
    "id": 1,
    "title": "abcdf",
    "body": "123456"
}
```
응답이 200으로 뜨고 1번 게시물의 데이터가 변경된다<br>
<hr>

**포스트맨에서 DELETE로 데이터 삭제하기**<br>
https://jsonplaceholder.typicode.com/posts/1 처럼 1번 게시물을 URL로 설정한 뒤<br>
전송 방식을 DELETE로 변경하고 SEND를 누르면<br>
응답이 200으로 뜨고 1번 게시물이 삭제된다<br>
<hr>

**연습문제**
**1. 오늘 할 일 생성**<br>
![img_3.png](img_3.png)<br>

**2. 오늘 할 일 조회**<br>
![img_5.png](img_5.png)<br>

**3. 오늘 할 일 수정**<br>
![img_4.png](img_4.png)<br>

**4. 오늘 할 일 삭제**<br>
![img_6.png](img_6.png)<br>
어차피 id는 고유하므로 users/10 지정 필요없이 todos/200으로 삭제 가능하다<br>

<hr>

<h3>11장</h3><br>
**응답으로 오는 상태 코드**<br>
200 : 요청 성공<br>
201 : 데이터 생성을 완료함<br>
404 : 요청한 정보를 찾을 수 없음<br>
500 : 서버에 오류 발생<br>

<br>

**REST 컨트롤러와 일반 컨트롤러의 차이점**<br>
REST 컨트롤러 : JSON이나 텍스트 같은 데이터를 반환<br>
일반 컨트롤러 : 뷰 페이지를 반환<br><br>
<hr>

**전체 게시글 조회하기**<br>

1. 클래스 내부에 articleRepository를 선언, 스프링 부트가 제공하므로 @Autowired 어노테이션을 붙여 의존성 주입<br>
```java
@Autowired
    private ArticleRepository articleRepository;
```

2. @GetMapping으로 "api/articles" 주소로 오는 URL 요청을 받음. 메서드 수행 결과로 Article 묶음을 반환하므로 반환형이 List<Article>인 index()라는 메서드를 정의.
```java
// GET
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }
```
<hr>

**단일 게시글 조회하기**<br>

1. 조회하려는 게시글의 id에 따라 URL 요청이 바뀌어야 하므로 @GetMapping의 URL을 "/api/articles/{id}"로 수정
2. 메서드 수행 결과로 단일 Article을 반환함. 메서드 이름 구분을 위해 show()로 수정. return문은 DB에서 id로 검색해 얻은 엔티티를 가져오도록 수정하고, 만약 해당 엔티티가 없으면 null을 반환하도록 함.
3. DB에서 id로 검색하기 위해 show()메서드의 매개변수로 id를 가져와야 함. 이때 id를 요청 URL에서 가져오기 때문에 @PathVarialble을 붙임

```java
// POST
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }
```
<hr>

**POST 구현하기 (데이터 생성요청 보내기)**<br>

1. @PostMapping 으로 "/api/articles" 주소로 오는 URL 요청을 받는다
2. 반환형이 Article 인 create()라는 메서드를 정의하고 수정할 데이터를 dto 매개변수로 받아옴. 받아 온 dto를 DB에서 활용할 수 있도록 엔티티로 변환해 article변수에 넣고, articleRepository를 통해 DB에 저장 후 반환한다.
```java
// POST
@PostMapping("/api/articles")
public Article create(@RequestBody ArticleForm dto) {
    Article article = dto.toEntity();
    return articleRepository.save(article);
}
```
@RequestBody 를 써야 JSON의 BODY에 실어 보내는 데이터를 create() 메서드의 매개변수로 받아올 수 있다.<br>
<hr>

**PATCH 구현하기 (데이터 수정하기)**<br>
1. @PatchMapping 으로 "/api/articles/{id}" 주소로 오는 URL 요청을 받음.
2. 반환형이 Article인 update()라는 메서드를 정의하고 매개변수로 요청 URL의 id와 요청 메시지의 본문 데이터를 받아옴.

메서드의 본문은 다음 네 부분으로 나누어 작성
1. DTO -> 엔티티 변환
2. 타깃 조회
3. 잘못된 요청 처리
4. 업데이트 및 정상 응답(200)

* ResponseEntity는 REST 컨트롤러의 반환형, 즉 REST API의 응답을 위해 사용하는 클래스
* REST API 요청응ㄹ 받아 응답할 때 이 클래스에 HTTP 상태 코드, 헤더, 본문을 실어 보낼 수 있음
* HttpsStatus는 HTTP 상태 코드를 관리하는 클래스로, 다양한 Enum 타입과 관련한 메서드를 가짐 (형식 : 열거형이름.상수)

**전체 코드**
```java
// PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        //1. DTO -> 엔티티 변환
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString()); //로그 찍기
        //2. 타깃 조회
        Article target = articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
        if(target == null || id != article.getId()){
            // 400, 잘목된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //4. 업데이트 및 정상 응답(200)
        target.patch(article); // 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target); // 수정 내용 DB에 최종 저장
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
```

* URL의 번호와 JSON의 id 번호가 일치하면 수정 성공
![img_7.png](img_7.png)


**DELETE 구현하기**<br>
1. @DeleteMapping으로 URL 요청을 받는다
2. delete() 메서드를 정의하고 URL의 id를 매개변수로 받아 온다
3. DB에서 대상 엔티티를 조회한다
4. 대상 엔티티가 없을시 생기는 잘못된 요청을 처리한다
5. 대상 엔티티가 있으면 삭제하고 정상 응답(200)을 반환한다

* return문에 body(null) 대신 build()를 작성해도 되는 이유<br>
: build 메서드는 HTTP 응답의 body가 없는 ResponseEntity객체를 생성하기 때문이다<br>

<h3>연습문제</h3><br>
1. 전체 데이터 조회
![img_8.png](img_8.png)
2. 특정 데이터 조회
![img_9.png](img_9.png)
3. POST 구현 (데이터 생성 요청)
![img_10.png](img_10.png)
4. PATCH 구현 (데이터 수정) +) 기존 데이터에 붙임
![img_11.png](img_11.png)
5. DELETE 구현
![img_12.png](img_12.png)
![img_13.png](img_13.png)


<h2>12장 <서비스 계츨과 트랜잭션></h2><br>

* 서비스란?
: 컨트롤러와 리파지터리 사이에 위치하는 계층으로, 서버의 핵심 기능(비즈니스 로직)을 처리하는 순서를 총괄한다

* 트렌잭션이란?
: 모두 성공해야 하는 일련의 과정. 쪼갤 수 없는 업무 처리의 최소 단위

* 롤백이란?
: 트랜잭션이 실패로 돌아갈 경우 진행 초기 단계로 돌리는 것

* 컨트롤러의 역할 : 클라이언트 요청받기
* 서비스의 역할 : 리파지터리에 데이터 가져오도록 명령하기
* 컨트롤러의 역할 : 클라이언트에 응답하기








