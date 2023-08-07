# INU-phonebook-Android

### __Commit Message Convention__
작성된 형식에 따라 Commit Message를 작성

#### type
- feat : 새로운 기능 추가
- fix : 오류 수정
- docs : 문서 수정
- refactor : 코드 리팩토링
- design : 사용자 UI 변경
- gradle : gradle 환경 수정

---

### __Issue__

#### 해야 할 일
- 서버와 통신할 Retrofit 구성 (Retrofit Flow 확인)
- Splash 화면 지정 시 동영상 uri를 필요로 하기 때문에 서버에 동영상을 올려서 받아오는 방식(현 : 영상을 resource파일에 넣어서 직접 링크 / 서버에서 받아오는 방식과 어떤 것이 더 유용할지?)
- ~~전화번호에 전화 앱으로 이동하는 링크 (뒤로 가기 시 바탕화면이 아닌 기존 앱으로 돌아오는 방법 찾기)~~
- ~~이메일 앱과 연결하는 방법~~
- UI figma 맞춰서 수정

#### issue
- Local DB에 "기본" category를 defualt로 지정해야 하지만 앱을 처음 시작하는 것이 아니면 중복 실행이 일어나지 않도록 해야함
- EditCategory 화면에서 category를 삭제할 경우 삭제된 category의 상태를 다음 카테고리가 받아서 중복되는 오류 해결해야 함. [임시방편 : 일일이 삭제했는지 확인하고 삭제했다면 초기화 시키는 방식]
- 들어있지 않은 카테고리 또한 그냥 빈 것으로 표현할지?
- UI와 데이터 동기화

---
