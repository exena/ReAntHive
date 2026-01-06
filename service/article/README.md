# 게시판 서비스
### 글 목록
<img width="1920" height="953" alt="image" src="https://github.com/user-attachments/assets/3a44f705-d768-4fa7-ab80-6bac28551e8e" />

### 글 작성
<img width="1920" height="953" alt="image" src="https://github.com/user-attachments/assets/f172420e-d6ab-4041-b6bd-e517c57ae54e" />


## 제작과정
<img width="1698" height="1101" alt="image" src="https://github.com/user-attachments/assets/f20e74ab-82be-4eb2-bf65-683bae7a8aaf" />

- 백엔드는 Java Spring Boot 와 MariaDB를 사용했습니다.
- 프론트엔드는 Thymeleaf와 Bootstrap을 사용했습니다.
- 배포 서버는 AWS EC2를 사용했고 데이터베이스는 AWS RDS를 사용했습니다.


## 기능 구현
- [x]  회원가입, 로그인 기능 (Spring Security 6 사용)
- [x]  업로드 버튼을 누르면 글과 글 내부의 모든 사진 업로드
- [x]  작성글 삭제시 맵핑되었던 사진들도 서버에서 삭제
- [x]  블로그 포스트 썸네일 이미지 기능
- [x]  이미지 업로드 및 다운로드시 파일명 유지


## 디렉토리 구조
```
anthive
  ├─ AnthiveApplication.java
  ├─ domain
  │  ├─ package-info.java
  │  ├─ shared
  │  │  └─ Email.java
  │  ├─ post
  │  │  ├─ Post.java
  │  │  ├─ PostNotFoundException.java
  │  │  └─ PublishBlogpostFormRequest.java
  │  └─ member
  │     ├─ DuplicateEmailException.java
  │     ├─ Member.java
  │     ├─ MemberRegisterRequest.java
  │     └─ Role.java
  ├─ application
  │  ├─ post
  │  │  ├─ PostService.java
  │  │  ├─ required
  │  │  │  └─ PostRepository.java
  │  │  └─ provided
  │  │     ├─ PostFinder.java
  │  │     ├─ PostModify.java
  │  │     └─ PostPermission.java
  │  └─ member
  │     ├─ MemberModifyService.java
  │     ├─ MemberQueryService.java
  │     ├─ required
  │     │  └─ MemberRepository.java
  │     └─ provided
  │        ├─ EmailSender.java
  │        ├─ MemberFinder.java
  │        └─ MemberRegister.java
  └─ adapter
     ├─ webapi
     │  ├─ post
     │  │  ├─ PostApi.java
     │  │  ├─ PostPage.java
     │  │  └─ dto
     │  │     └─ GetBlogpostFormResponse.java
     │  └─ member
     │     ├─ MemberApi.java
     │     ├─ MemberPage.java
     │     └─ dto
     │        └─ MemberRegisterResponse.java
     ├─ security
     │  ├─ AccountContext.java
     │  ├─ CustomUserDetailsService.java
     │  └─ config
     │     └─ WebSecurityConfig.java
     └─ integration
        └─ DummyEmailSender.java
```


## 테스트 커버리지
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/efd0dcb0-f037-41fa-b993-1106ee906b91" />
- 컨트롤러 단을 제외하면 테스트 코드 커버리지 100%
