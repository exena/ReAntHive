# 게시판 서비스
<img width="1920" height="1080" alt="301190802-0d04118f-0021-44fe-aebb-2ce835b06703" src="https://github.com/user-attachments/assets/5de20ed3-d621-4445-bab0-6070367305a1" />

## 제작과정
- 백엔드는 Java Spring Boot 와 MariaDB를 사용했습니다.
- 프론트엔드는 Thymeleaf와 Bootstrap을 사용했습니다.
- 로컬에서 작동 확인 후 서버에 올리는 식으로 작업했습니다.
- 서버는 AWS EC2를 사용했고 데이터베이스는 AWS RDS를 사용했습니다.

## 기능
- 회원가입, 로그인 기능 (Spring Security 6 사용)
- 업로드 버튼을 누르면 글과 글 내부의 모든 사진 업로드
- 작성글 삭제시 맵핑되었던 사진들도 서버에서 삭제
- 블로그 포스트 썸네일 이미지 기능
- 이미지 업로드 및 다운로드시 파일명 유지
