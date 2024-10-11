# JWT 강의 24.10.11
***
## 기본 의존성 추가
| Dependency             | Description                                                                                  |
|------------------------|----------------------------------------------------------------------------------------------|
| **Lombok**             | 자바에서 보일러플레이트 코드를 줄여주는 라이브러리. Getter, Setter, 생성자 등을 자동 생성.       |
| **Spring Web**          | 웹 애플리케이션을 만들기 위한 기본적인 의존성. RESTful 웹 서비스와 서블릿을 쉽게 구현.           |
| **Spring Security**     | 애플리케이션 보안을 위한 프레임워크. 인증, 권한 부여, 로그인 및 기타 보안 기능 제공.             |
| **Spring Data JPA**     | 자바 애플리케이션에서 JPA(Java Persistence API)를 쉽게 사용할 수 있도록 지원.                  |
| **MySQL Driver**        | 스프링 애플리케이션에서 MySQL 데이터베이스와 연결하기 위한 JDBC 드라이버.                      |
| **Spring DevTools**     | 개발 생산성 향상을 위해 제공되는 도구. 자동 재시작, 라이브 리로드, 캐시 비활성화 등의 기능 제공. |

#### JWT 관련 의존성 추가
    implementation 'io.jsonwebtoken:jjwt-api:0.12.3'
    implementation 'io.jsonwebtoken:jjwt-impl:0.12.3'
    implementation 'io.jsonwebtoken:jjwt-jackson:0.12.3'

***
