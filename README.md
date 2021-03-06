## 기술 스택
- Spring Boot 2.6.2 (Java 11)
- Hibernate, JPA
- Gradle
- Thymeleaf (공부 중.. 🥲)  

<br></br>
  
## Tiny Web Application for Document Approval (문서 전자 결재 시스템)
#### 작디 작은 웹 어플리케이션 + 거기에 개인 Study를 곁들인...
  
> 문서를 작성하고, 결재자를 선택하여 결재를 받을 수 있습니다.
  
#### 💡 내가 결재자라면?

- 결재 승인
  - INBOX -> 결재 대상 문서 확인 -> (결재 문서 상세 확인) -> 결재 승인 -> 승인 의견 작성
- 결재 반려
  - INBOX -> 결재 대상 문서 확인 -> (결재 문서 상세 확인) -> 결재 반려 -> 반려 의견 작성
- 결재 상태 확인
  - ARCHIVE -> 결재 문서 상세 확인

#### 💡 내가 결재를 받으려면?

- 결재 상신
  - 문서 작성 -> 문서 등록 -> 결재자 지정
- 결재 상태 확인
  - OUTBOX -> 결재 문서 상세 확인

## 화면 명세

### 1. 홈 화면

<img src="https://user-images.githubusercontent.com/67534066/148018272-7f31d017-201b-4481-8b82-ed7945cd1c7c.png" width="500" height="450"/>

### 2. 메뉴구성

- 문서
  - 문서 작성
    - 결재받을 문서 생성
- 내 결재함
  - OUTBOX
    - 내가 생성한 문서 중 결재 진행 중인 문서 조회
  - INBOX
    - 내가 결재를 해야 할 문서 조회
      - 상세보기 : 문서 상세 내용 조회
      - 승인 : 결재 승인 -> 승인 의견 작성
      - 반려 : 결재 반려 -> 반려 의견 작성
  - ARCHIVE: 내가 관여한 문서 중 결재가 완료(승인 또는 거절)된 문서

### 3. 기능

- 로그인/로그아웃
  - email과 password를 통해 로그인할 수 있습니다.
  - 하단 logout 버튼을 눌러 로그아웃할 수 있습니다.
- 문서 생성
  - 사용자는 결재받을 문서를 생성할 수 있습니다.
  - 문서는 제목과 분류, 내용을 가집니다.
  - 문서 생성시 결재를 해주었으면 하는 사용자를 지정할 수 있습니다.
- 결재
  - 결재자는 한명 이상이 될 수 있습니다. 문서를 생성한 본인을 지정할 수도 있습니다.
  - 결재는 순서대로 진행됩니다. 두번째 결재자가 먼저 결재할 수는 없습니다.
  - 모든 결재자가 승인하면 문서가 승인됩니다. 한명이라도 거절하면 거절됩니다.
  - 문서 승인/거절시 의견을 추가할 수 있습니다.

### 회원 정보

| 회원이름 | id (email)        | password |
| -------- | ----------------- | -------- |
| MemberA  | MemberA@hello.com | hello    |
| MemberB  | MemberB@hello.com | hello    |
| MemberC  | MemberC@hello.com | hello    |
| MemberD  | MemberD@hello.com | hello    |
| MemberE  | MemberE@hello.com | hello    |
| MemberF  | MemberF@hello.com | hello    |
