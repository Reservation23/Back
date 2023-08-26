# ReservationSystem
프로젝트명 : 매장 예약 시스템

프로젝트 기능 소개 : 

위 프로젝트를 하는 이유에 대해서 다른 레포지토리 참고해서 하기

---

## 📺 화면 설계서(기획)
Figma : 
https://www.figma.com/file/BCAqdHXbMbGmMQcFsSdtDy/%EB%A7%A4%EC%9E%A5%EC%98%88%EC%95%BD-%EC%84%9C%EB%B9%84%EC%8A%A4?type=design&node-id=0-1&mode=design&t=IvoTxGUzLz0WExzf-0

---

# 📑 API 명세서

## ✅ 1. 사용자 관련 API
### 1) 사용자 생성
<details>
<summary>요청경로 / 파라미터 / 결과</summary>
요쳥경로 : [POST] http://localhost:8080/member

파라미터

~~~
{
    "username" : "jiho",
    "password" : "dodlal1234",
    "memberStatus" : "PARTNER"
}
~~~

결과
~~~
{
    "id": 33,
    "username": "jisu",
    "password": "dodlal1234",
    "memberStatus": "PARTNER"
}
~~~
</details>

### 2) 간단 로그인 

<details>
<summary>요청경로 / 파라미터 / 결과</summary>
요쳥경로 : [POST] http://localhost:8080/login

파라미터

~~~
{
    "username" : "jisu",
    "password" : "dodlal12345"
}
~~~

결과
- 성공시
~~~
{
    "username": "jisu",
    "password": null,
    "memberStatus": "PARTNER"
}
~~~
- 실패시 
~~~
{
    로그인 정보가 올바르지 않습니다.
}
~~~
</details>

## ✅ 2. 매장 관련 API
### 1) 매장등록
<details>
<summary>요청경로 / 파라미터 / 결과</summary>

<img width="932" alt="스크린샷 2023-08-21 오후 12 20 24" src="https://github.com/JisuPark-dev/ReservationSystem/assets/122674412/21506602-819e-4437-8ced-48f005bbb242">

요쳥경로 : [POST] http://localhost:8080/store

파라미터
~~~
{
    "memberId" : 2,
    "name" : "testStore5",
    "location" : "seoul",
    "description" : "test description2"
}
~~~
결과

~~~ 
{
    "storeId": 39,
    "memberId": 2,
    "name": "testStore5",
    "location": "seoul",
    "description": "test description2"
}
~~~

</details>


### 2) 매장 리스트 조회
<details>
<summary>요청경로 / 결과</summary>

<img width="651" alt="스크린샷 2023-08-21 오후 12 52 13" src="https://github.com/JisuPark-dev/ReservationSystem/assets/122674412/353c6807-7d1c-49d3-b5fc-3b78600ce085">



요쳥경로 : [GET] http://localhost:8080/stores?page={currentPage}&size={pageSize}&sort=id,desc

최신 매장부터 조회

결과
~~~
{
    "totalItems": 4,
    "data": [
        {
            "storeId": 9,
            "memberId": 1,
            "name": "testStore4",
            "location": "seoul",
            "description": "test description3"
        },
        {
            "storeId": 5,
            "memberId": 1,
            "name": "testStore3",
            "location": "seoul",
            "description": "test description3"
        }
    ],
    "totalPages": 2,
    "currentPage": 0
}
~~~

</details>


### 3) 매장 상세정보 조회
<details>
<summary>요청경로 / 결과</summary>

<img width="680" alt="스크린샷 2023-08-21 오후 1 03 25" src="https://github.com/JisuPark-dev/ReservationSystem/assets/122674412/75f641eb-9f8c-4ea2-96c1-6a212fdc723d">


요쳥경로 : [GET] http://localhost:8080/store/15

결과
~~~
{
    "count": 1,
    "data": {
        "storeId": 15,
        "memberId": 2,
        "name": "testStore4",
        "location": "seoul",
        "description": "test description2"
    }
}
~~~

</details>


### 4) 사용자별 본인 매장 조회

<details>
<summary>요청경로 / 파라미터 / 결과</summary>

요청경로 : [GET] http://localhost:8080/store/member/1?page={currentPage}&size={pageSize}

파라미터

결과
~~~
{
    "totalItems": 4,
    "data": [
        {
            "storeId": 3,
            "memberId": 1,
            "name": "testStore",
            "location": "seoul",
            "description": "test description"
        },
        {
            "storeId": 4,
            "memberId": 1,
            "name": "testStore2",
            "location": "seoul",
            "description": "test description2"
        }
    ],
    "totalPages": 2,
    "currentPage": 0
}
~~~

</details>

### 5) 회원이 소유하고 있는 매장의 모든 예약 정보 조회

<details>
<summary>요청경로 / 파라미터 / 결과</summary>

요청경로 : [GET] http://localhost:8080/members/1/owned-stores/reservations?page={currentPage}&size={pageSize}

파라미터

결과
~~~
{
    "totalItems": 2,
    "data": [
        {
            "storeName": "testStore2",
            "reservationMemberName": "jisu",
            "createdAt": "2023-08-26T12:19:45.269286",
            "reservationAt": "2023-08-18T14:30:00"
        }
    ],
    "totalPages": 2,
    "currentPage": 1
}
~~~

</details>

### 6) 매장 정보 수정
<details>
<summary>요청경로 / 파라미터 / 결과</summary>

<img width="680" alt="스크린샷 2023-08-21 오후 1 04 04" src="https://github.com/JisuPark-dev/ReservationSystem/assets/122674412/91bc250c-4dc3-4a9a-baf7-4ef02ddd999f">

요청경로 : [PUT] http://localhost:8080/store/13

파라미터 
~~~
{
    "name" : "update Test Name",
    "location" : "update Test location",
    "description" : "update test store"
}
~~~

결과
~~~
{
    "storeId": 13,
    "memberId": 12,
    "name": "update Test Name",
    "location": "update Test location",
    "description": "update test store"
}
~~~

</details>


### 7) 매장 정보 삭제(등록 해제)
<details>
<summary>요청경로 / 결과</summary>

요쳥경로 : [DELETE] http://localhost:8080/store/9

결과 : 삭제기능 구현
</details>

## ✅ 3. 예약 관련 API
### 1) 예약 생성
<details>
<summary>요청경로 / 파라미터 / 결과</summary>

요청경로 : [POST] http://localhost:8080/reservation
파라미터
~~~
{
  "memberId" : 19,
  "storeId" : 15,
  "reservationStatus" : "REQUESTED",
  "time" : "2023-08-18T14:30:00"
}
~~~

결과
~~~
{
    "memberId": 19,
    "storeId": 15,
    "reservationId": 40,
    "time": "2023-08-18T14:30:00",
    "reservationStatus": "REQUESTED"
}
~~~
</details>

### 2) 사용자별 예약 목록 조회
<details>
<summary>요청경로 / 결과</summary>

요청경로 : [GET] http://localhost:8080/reservations/member/2?page={currentPage}&size={pageSize}

결과
~~~
{
    "totalItems": 2,
    "data": [
        {
            "memberId": 2,
            "storeId": 3,
            "reservationId": 6,
            "time": "2023-08-18T14:30:00",
            "reservationStatus": "CONFIRMED"
        },
        {
            "memberId": 2,
            "storeId": 4,
            "reservationId": 7,
            "time": "2023-08-18T14:30:00",
            "reservationStatus": "REQUESTED"
        }
    ],
    "totalPages": 1,
    "currentPage": 0
}
~~~

</details>

### 3) 상점별 예약 목록 조회
<details>
<summary>요청경로 / 결과</summary>

요청경로 : [GET] http://localhost:8080/reservations/store/3?page={currentPage}&size={pageSize}

결과 
~~~
{
    "totalItems": 1,
    "data": [
        {
            "memberId": 2,
            "storeId": 3,
            "reservationId": 6,
            "time": "2023-08-18T14:30:00",
            "reservationStatus": "CONFIRMED"
        }
    ],
    "totalPages": 1,
    "currentPage": 0
}
~~~

</details>

### 4) 예약 확정
<details>
<summary>요청경로 / 결과</summary>

요청경로 : [PUT] http://localhost:8080/reservation/cancel?reservationId=21

결과 
~~~
{
    "memberId": 19,
    "storeId": 15,
    "reservationId": 40,
    "time": "2023-08-18T14:30:00",
    "reservationStatus": "CONFIRMED"
}
~~~
"reservationStatus": "CONFIRMED"으로 변경됨.

</details>

### 5) 예약 취소
<details>
<summary>요청경로 / 결과</summary>

요청경로 : [PUT] http://localhost:8080/reservation/cancel?reservationId=21

결과 
~~~
{
    "memberId": 19,
    "storeId": 15,
    "reservationId": 40,
    "time": "2023-08-18T14:30:00",
    "reservationStatus": "CANCELED"
}
~~~

"reservationStatus": "CANCELED"으로 변경됨.

</details>

### 6) 확정되었지만, 리뷰가 작성되지 않은 예약 조회
<details>
<summary>요청경로 / 결과</summary>

요청경로 : [GET] http://localhost:8080/reservations/without_review/member/2?page={currentPage}&size={pageSize}

결과
~~~
{
    "totalItems": 0,
    "data": [],
    "totalPages": 0,
    "currentPage": 0
}
~~~

</details>

## ✅ 4. 리뷰 관련 API
### 1) 리뷰 등록
<details>
<summary>요청경로 / 파라미터 / 결과</summary>

요쳥경로 : [POST] http://localhost:8080/review

파라미터 
~~~
{
    "memberId" : 19,
    "storeId" : 15,
    "reservationId" : 51,
    "content" : "review test5"
}
~~~

결과
~~~
{
    "memberId": 48,
    "storeId": 39,
    "reservationId": 51,
    "reviewId": 52,
    "content": "review test!!@@"
}
~~~
</details>

### 2) 사용자별 작성한 리뷰 조회
<details>
<summary>요청경로 / 결과</summary>

요쳥경로 : [GET] http://localhost:8080/reviews/member/2?page={currentPage}&size={pageSize}

결과
~~~
{
    "totalItems": 1,
    "data": [
        {
            "memberId": 2,
            "storeId": 3,
            "reservationId": 6,
            "reviewId": 8,
            "content": "review test"
        }
    ],
    "totalPages": 1,
    "currentPage": 0
}
~~~

</details>

### 3) 매장별 작성된 리뷰 조회
<details>
<summary>요청경로 / 결과</summary>

요쳥경로 : [GET] http://localhost:8080/reviews/store/3?page={currentPage}&size={pageSize}

결과
~~~
{
    "totalItems": 1,
    "data": [
        {
            "memberId": 2,
            "storeId": 3,
            "reservationId": 6,
            "reviewId": 8,
            "content": "review test"
        }
    ],
    "totalPages": 1,
    "currentPage": 0
}
~~~
</details>

### 4) 리뷰 수정
<details>
<summary>요청경로 / 파라미터 / 결과</summary>

요쳥경로 : [PUT] http://localhost:8080/review/52

파라미터
~~~
{
    "content": "review content is updated!!"
}
~~~

결과
~~~
{
    "memberId": 48,
    "storeId": 39,
    "reservationId": 51,
    "reviewId": 52,
    "content": "review content is updated!!"
}
~~~
</details>

### 5) 리뷰 삭제
<details>
<summary>요청경로 / 결과</summary>

요쳥경로 : [DELETE] http://localhost:8080/review/50

결과
- 입력값이 있는 데이터에 대해서만 수정 진행
</details>

