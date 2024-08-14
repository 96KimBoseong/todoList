
![리드미용 대빵 큰이미지](https://github.com/user-attachments/assets/fb70763d-dd12-42f1-8f2b-8b3b1c31d90b)

# 프로젝트 개요

todoList 애플리케이션은 개인 또는 팀의 작업 관리 및 일정을 효율적으로 관리할 수 있는 애플리케이션입니다. 

- 프로젝트 목표
    - JVM 언어 이해도 증진
    - Filter 와 JWT를 활용한 인증/ 인가
    - RestControllerAdvice / ExceptionHandler를 활용한 전역 예외처리
    - Validation 을 활용한 유효성 검사
    - 예외가 발생하면 로그 파일에 적재
    - 스프링 AOP를 이용하여 로그인 이력을 데이터베이스에 저장
 ---
 # DB modeling
 ![todoList](https://github.com/user-attachments/assets/486d0765-2e13-48d3-a808-07d77ecdfbbe)

 ---
 # API 설계

| 기능         | Method | Status | URL                          | Request                                          | Response                                                                                                                                                  |
|--------------|--------|--------|------------------------------|--------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| 회원가입     | Post   | 201    | /api/users/signup             | `{ nickname: String, username: String, password: String, role: User.Role }` | `{ id: Long, nickname: String, username: String }`                                                                                                      |
| 로그인       | Post   | 201    | /api/users/login              | `{ username: String, password: String }`        | Header: `{ Authorization: Jwt }` <br> Body: `{ id: Long, nickname: String, username: String, token: String }`                                                            |
| todo 생성    | Post   | 201    | /api/todos                    | Header: `{ Authorization: Jwt }` <br> Body: `{ title: String, content: String, password: String }` | `{ id: Long, title: String, content: String, writer: String, username: String, createAt: LocalDateTime, commentList: List }`                           |
| todo 수정    | Patch  | 200    | /api/todos/{todoId}           | Header: `{ Authorization: Jwt }` <br> Body: `{ title: String, content: String, password: String }` | `{ id: Long, title: String, content: String, writer: String, username: String, createAt: LocalDateTime, commentList: List }`                           |
| todo 삭제    | Delete | 204    | /api/todos/{todoId}           | Header: `{ Authorization: Jwt }` <br> Body: `{ password: String }`   |                                                                                                                                           |
| todo 단건 조회| Get    | 200    | /api/todos/{todoId}           |                              | `{ id: Long, title: String, content: String, writer: String, username: String, createAt: LocalDateTime, commentList: List }`                           |
| todo 리스트 조회| Get  | 200    | /api/todos                    |                                | `List { id: Long, title: String, content: String, writer: String, username: String, createAt: LocalDateTime, commentList: List }`                      |
| 댓글 생성    | Post   | 201    | /api/todos/{todoId}/comments  | Header: `{ Authorization: Jwt }` <br> Body: `{ content: String }` | `{ id: Long, content: String, writer: String, createAt: LocalDateTime, todoId: Long, username: String }`                                                |
| 댓글 수정    | Patch  | 200    | /api/todos/{todoId}/comment/{commentId} | Header: `{ Authorization: Jwt }` <br> Body: `{ content: String }` | `{ id: Long, content: String, writer: String, createAt: LocalDateTime, todoId: Long, username: String }`                                                |
| 댓글 삭제    | Delete | 204    | /api/todos/{todoId}/comment/{commentId} | Header: `{ Authorization: Jwt }`              |                                                                                                                                          |



---
# 요구사항
### todoList
- [ ]  생성 - 일정 작성
    - [ ]  `할 일 제목`, `할 일 내용`, `담당자`, `비밀번호`, `작성일` 을 저장할 수 있습니다.
        - [ ]  저장된 일정 정보를 반환 받아 확인할 수 있습니다.
- [ ]  조회 - 선택한 일정 조회(상세 페이지)
    - [ ]  선택한 일정의 정보를 조회할 수 있습니다.
    - [ ]  반환 받은 일정 정보에는 `할 일 제목`,`할 일 내용`, `작성일`, `작성자 이름` 정보가 들어있습니다.
- [ ]  조회 - 일정 목록 조회
    - [ ]  등록된 일정 전체를 조회할 수 있습니다.
    - [ ]  조회된 일정 목록은 `작성일` 기준 내림차순으로 정렬 되어있습니다.
- [ ]  수정 - 선택한 일정 수정
    - [ ]  선택한 일정의 `할 일 제목`, `할 일 내용`, `담당자`를 수정할 수 있습니다.
    - [ ]  서버에 일정 수정을 요청할 때 `비밀번호`를 함께 전달합니다.
        - [ ]  생성 시에, 입력한 비밀번호와 일치할 경우에만 수정할 수 있습니다.
    - [ ]  수정된 일정의 정보를 반환 받아 확인할 수 있습니다.
- [ ]  삭제 - 선택한 일정 삭제
    - [ ]  서버에 일정 삭제를 요청할 때 `비밀번호`를 함께 전달합니다.
        - [ ]  생성 시에, 입력한 비밀번호와 일치할 경우에만 수정할 수 있습니다.

--- 

- [ ]  [`HTTP 상태 코드(링크)`](https://developer.mozilla.org/ko/docs/Web/HTTP/Status)와 `에러 메시지`를 포함한 정보를 사용, 예외처리
    - 설명
        - 필요에 따라 사용자 정의 예외 클래스를 생성하여 예외 처리를 수행할 수 있습니다.
        - **`@ExceptionHandler`**를 활용하여 공통 예외 처리를 구현할 수도 있습니다.
        - 예외가 발생할 경우 적절한 HTTP 상태 코드와 함께 사용자에게 메시지를 전달하여 상황을 관리
    - 조건
        - [ ]  수정, 삭제 시 요청할 때 보내는 `비밀번호`가 일치하지 않을 때
        - [ ]  선택한 일정 정보가 이미 삭제되어 조회할 수 없을 때
        - [ ]  삭제하려는 일정 정보가 이미 삭제 상태일 때
- [ ]  `Swagger` 활용 / 파라미터 유효성 검사 / **`null 체크`** / **`특정 패턴`**에 대한 검증
    - 설명
        - Swagger
            - API 명세서를 직접 작성하는 대신 [**`Swagger(링크)`**](https://springdoc.org/#Introduction)를 활용하여 자동으로 생성할 수 있습니다.
            - 개발자는 코드와 함께 API 명세서를 업데이트하고 관리할 수 있어서 `개발 생산성`을 높일 수 있습니다.
            - Swagger UI를 통해 직관적인 인터페이스를 통해 API를 쉽게 이해하고 테스트할 수 있습니다.
        - 유효성 검사
            - 잘못된 입력이나 요청을 미리 방지할 수 있습니다.
            - 데이터의 `무결성을 보장`하고 애플리케이션의 예측 가능성을 높여줍니다.
            - Spring에서 제공하는 **`@Valid`** 어노테이션을 이용할 수 있습니다.
    - 조건
        - [ ]  Swagger
            - [ ]  Swagger UI를 통해 API 목록을 확인할 수 있다.
            - [ ]  Swagger UI를 통해 API 테스트를 할 수 있다.
        - [ ]  유효성 검사
            - [ ]  `할일 제목`은 최대 200자 이내로 제한, 필수값 처리
            - [ ]  `비밀번호`는 필수값 처리
            - [ ]  `담당자`는 이메일 형식을 갖도록 처리
             

---



- [ ]  일정과 댓글의 연관 관계
    - [ ]  일정에 댓글을 추가
    - [ ]  ERD에도 댓글 모델을 추가
    - [ ]  각 일정에 댓글을 작성할 수 있도록 관련 클래스를 추가하고 연관 관계를 설정
    - [ ]  매핑 관계를 설정 (1:1 or N:1 or N:M)
        
        
        | 댓글 필드 | 데이터 유형 |
        | --- | --- |
        | 아이디 (고유번호) | bigint |
        | 댓글 내용 | varchar |
        | 사용자 아이디 | varchar |
        | 일정 아이디 | bigint |
        | 작성일자 | timestamp |
- [ ]  댓글 등록
    - [ ]  댓글이 등록되었다면 client에게 반환합니다.
    - [ ]  선택한 일정이 DB에 저장되어 있어야 합니다.
    - [ ]  댓글을 식별하는 `고유번호`, `댓글 내용`, 댓글을 작성한 `사용자 아이디`, 댓글이 작성된 `일정 아이디`, `작성일자`를 저장할 수 있습니다.
    - [ ]  예외처리
        - [ ]  선택한 일정의 ID를 입력 받지 않은 경우
        - [ ]  댓글 내용이 비어 있는 경우
        - [ ]  일정이 DB에 저장되지 않은 경우
- [ ]  댓글 수정
    - [ ]  댓글이 수정되었다면 수정된 댓글을 반환합니다.
    - [ ]  `댓글 내용`만 수정 가능합니다.
    - [ ]  선택한 일정과 댓글이 DB에 저장되어 있어야 합니다.
    - [ ]  예외 처리
        - [ ]  선택한 일정이나 댓글의 ID를 입력 받지 않은 경우
        - [ ]  일정이나 댓글이 DB에 저장되지 않은 경우
        - [ ]  선택한 댓글의 사용자가 현재 사용자와 일치하지 않은 경우
- [ ]  댓글 삭제
    - [ ]  성공했다는 메시지와 상태 코드 반환하기
    - [ ]  선택한 일정과 댓글이 DB에 저장되어 있어야 합니다.
    - [ ]  예외 처리
        - [ ]  선택한 일정이나 댓글의 ID를 입력받지 않은 경우
        - [ ]  일정이나 댓글이 DB에 저장되지 않은 경우
        - [ ]  선택한 댓글의 사용자가 현재 사용자와 일치하지 않은 경우



---

- [ ]  JWT
    - [ ]  JWT를 이용한 인증/인가를 구현한다.
    - [ ]  위 CRUD 단계에서 인증/인가가 완료된 후에만 기능이 동작하도록 수정한다.
    - [ ]  조건
        - [ ]  Access Token 만료시간 60분
        - [ ]  Refresh Token 구현은 선택
    - [ ]  예외처리
        - [ ]  공통조건
            - [ ]  StatusCode : 400 / client에 반환
        - [ ]  토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 때
            - [ ]  에러 메세지 : “토큰이 유효하지 않습니다.”
        - [ ]  토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닐 때
            - [ ]  에러 메세지 : “작성자만 삭제/수정할 수 있습니다.”
        - [ ]  DB에 이미 존재하는 `username`으로 회원가입을 요청할 때
            - [ ]  에러 메세지 : “중복된 `username` 입니다.”
        - [ ]  로그인 시, 전달된 `username`과 `password` 중 맞지 않는 정보가 있을 때
            - [ ]  에러 메시지 : “회원을 찾을 수 없습니다.”
        - [ ]  StatusCode 나누기
            - [ ]  StatusCode를 다르게 정의하고 싶다면 참고하세요.
            - [ ]  [https://hongong.hanbit.co.kr/http-상태-코드-표-1xx-5xx-전체-요약-정리/](https://hongong.hanbit.co.kr/http-%EC%83%81%ED%83%9C-%EC%BD%94%EB%93%9C-%ED%91%9C-1xx-5xx-%EC%A0%84%EC%B2%B4-%EC%9A%94%EC%95%BD-%EC%A0%95%EB%A6%AC/)
- [ ]  회원가입
    - 사용자의 정보를 전달 받아 유저 정보를 저장한다.
        
        
        | 사용자 필드 | 데이터 유형 |
        | --- | --- |
        | 아이디 | bigint |
        | 별명 | varchar |
        | 사용자이름 (username) | varchar |
        | 비밀번호 (password) | varchar |
        | 권한 (일반, 어드민) | varchar |
        | 생성일 | timestamp |
    - [ ]  조건
        - [ ]  패스워드 암호화는 선택적으로 구현
        - [ ]  `username`은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성
    - [ ]  `password`는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성
    - [ ]  DB에 중복된 `username`이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환
- [ ]  로그인
    - `username`, `password` 정보를 client로부터 전달받아 토큰을 반환한다.
    - DB에서 `username`을 사용하여 저장된 회원의 유무를 확인한다.
        - 저장된 회원이 있다면 `password` 를 비교하여 로그인 성공 유무를 체크한다
    - [ ]  조건
        - [ ]  패스워드 복호화는 선택적으로 구현 (위 회원가입의 암호화와 동일)
        - [ ]  로그인 성공 시 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급
        - [ ]  발급한 토큰을 `Header`에 추가하고 성공했다는 메시지 및 상태코드와 함께 client에 반환
         
- [ ]  일정과 댓글 수정
    - 로그인 한 사용자에 한하여 일정과 댓글을 작성하고 수정할 수 있는 기능
    - ‘만료되지 않은 유효 토큰’인 경우에만 일정과 댓글 ‘생성’이 가능하도록 변경
    - 조회는 누구나 가능
    - 보안
        - 사용자 인증을 통해 접근을 제어하여 보안을 강화합니다.
        - 개인 로그를 적재하고 분석하면 모니터링도 가능합니다.
    - 사용자 경험
        - 로그인을 통해 개인을 식별하고 개인에 맞춘 서비스를 제공할 수 있습니다.
        - 이를 통해 사용자 경험을 향상시킬 수 있고 사용자 추적을 통해 맞춤 서비스를 개발
    - [ ]  조건
        - [ ]  로그인을 하지 않으면 기능을 사용할 수 없다.
        - [ ]  유효한 토큰인 경우에만 일정과 댓글을 작성할 수 있다.
        - [ ]  일정을 생성한 사용자와 동일한 `username`이면 수정할 수 있다.
        - [ ]  댓글을 작성한 사용자와 동일한 `username`이면 수정할 수 있다.
---
- [ ]  로깅
    - 예외가 발생하면 로그 파일에 적재가 되고 해당 로그 파일을 기반으로 디버깅을 할 수 있다.
        
        
        | 로그 기록 항목 | 데이터 형식 (예시) |
        | --- | --- |
        | 시간 | 2024-05-14 15:37:21 |
        | 로그 레벨 | TRACE, DEBUG, INFO, WARN, ERROR (5개 중 1개) |
        | 스레드 명 | http-nio-8080-exec-10 |
        | 클래스 명 | com.example.myapp.service.SpartaService |
        | 로그 메세지 | User validation failed: userId=12345 reason=Email not verified |
    - 예시 데이터
        
        ```xml
        2024-05-14 15:37:21 ERROR [http-nio-8080-exec-10] com.example.myapp.service.SpartaService - User validation failed: userId=12345 reason=Email not verified
        ```
        
    - 문제 진단 및 해결
        - 로그를 참고해서 개발자는 서버의 상태를 모니터링하고 예상하지 못하는 문제가 발생했을 때 원인을 파악하여 해결할 수 있습니다.
    - 성능 최적화
        - 로그를 참고해서 개발자는 서버에 작성해둔 로직의 성능을 분석하고 최적화 할 수 있습니다.
    - 보안 강화
        - 서버에서 발생하는 모든 중요한 작업을 기록하여 비정상적인 접근이나 보안 위협을 초기에 발견하고 대응할 수 있습니다. 또한 사후에 보안 사고 원인을 규명하고 재발 방지에 도움을 줍니다.
    - [ ]  조건
        - [ ]  로그 레벨 ERROR 수준으로 `.log` 확장자로 추출
- [ ]  스프링 AOP
    - 반복적이고 공통적인 작업을 분리하여 관리하기 위한 스프링 AOP를 사용
    - 관심사의 분리
        - 코드 중복을 줄이고 유지 보수를 편리하게 합니다.
        - 이로 인해 확장성 면에서 이점을 가질 수 있어 요구사항이 변경되더라도 효과적으로 처리
    - 로그인에 대한 모니터링을 하기 위해 로그인 과정 전반을 이력으로 남깁니다.
    - [ ]  조건
        - [ ]  스프링 AOP를 이용하여 로그인 이력을 데이터베이스에 저장합니다.
        - [ ]  로깅 단계 구현한 로그에도 기록이 되는지 확인합니다.
        - [ ]  ERD에도 로그인 이력 모델을 추가합니다.
        - [ ]  로그인 이력 테이블 입력
            - [ ]  로그인 기능 수행 전 - Before
            - [ ]  로그인 기능 성공 - AfterReturning
            - [ ]  로그인 기능 실패 - AfterThrowing
                
                
                | 로그인 이력 필드 | 데이터 유형 |
                | --- | --- |
                | 아이디 | bigint |
                | 사용자 이름 | varchar |
                | 수행 타입(로그인 수행 전, 로그인 성공, 로그인 실패) | varchar |
                | 최근 시간 | timestamp |

---

# 트러블슈팅

### 문제상황
- RestControllerAdvice에서 정의해 놓은 예외처리를 filter에서 사용했을 때 올바르게 적용 되지 않는 문제

![필터 메서드 익셉션 때우기 전](https://github.com/user-attachments/assets/a288f295-0666-4677-aa14-d353eab161ad)

### 원인 
- 정의 해놓은 예외처리는 Spring context에서만 핸들링 가능
- filter에서 올바르게 예외처리를 하려면 직접 구현 하여야함
![Untitled (3) (1)](https://github.com/user-attachments/assets/e869b5f4-51d3-4aaf-a87a-a7400254b454)

### 해결방안
- HttpServletResponse 를 개발자가 원하는 응답으로 바꾸는 메서드를 작성하여 메세지와 status 코드를 클라이언트에 전달하고 올바른 예외처리를 구현
![리뉴얼 ~ 상수받는 메서드 http서블릿어쩌고 상태코드](https://github.com/user-attachments/assets/8242966b-211d-4c32-bce5-be38c3e6ec0b)

- filter 클래스에서 exceptionHandler 메서드 구현 / 적용

![필터 개선 버전](https://github.com/user-attachments/assets/08cfcfaa-3c95-40ca-8537-46bd4d80654c)

- 401 error와 메세지를 클라이언트에 전달하는 모습

![실제 에러](https://github.com/user-attachments/assets/7d6ba70b-557a-4de0-a5d5-b1b9b72a2358)

---
# 기타
### error.log 파일 생성
![로그파일 적재](https://github.com/user-attachments/assets/9aeab169-99e5-494e-a5f2-8879b46ee1fa)

### Swagger API 명세
![스웨거 개선 버전](https://github.com/user-attachments/assets/4402ff12-23ed-408f-a856-e88f642d5b31)







---




