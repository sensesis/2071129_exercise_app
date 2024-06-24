운동일지 만들기 미니 프로젝트


개발 배경 및 동기
---------------
요즘은 자기개발의 일환으로 몸 관리가 유행하고 있다. 필자도 자기개발의 일환으로 운동을 하고 있다. 

운동을 할 때 식단 관리도 물론 중요하지만, 점진적 과부하, 즉 운동의 강도를 점차 높여가며 근육을 성장시키는 것이 매우 중요하다. 

필자 역시 운동을 하면서 메모장을 켜고, 타이머를 설정하며, 세트 수, 무게, 쉬는 시간 등을 일일이 기록하는 것이 번거롭다고 느꼈다. 

이러한 불편함을 해소하고자 운동일지를 개발하게 되었다. 이 앱을 통해 사용자는 운동 세션을 보다 쉽게 관리하고, 체계적으로 기록하여 점진적 과부하 원칙을 효과적으로 적용할 수 있다. 

이를 통해 보다 효율적으로 운동 목표를 달성할 수 있도록 돕고자 한다.


개발 목표
------------
외부 데이터베이스(MariaDB)와 도메인 주소를 통해 PHP코드를 사용

데이터 베이스를 JSON 형태로 받아 데이터베이스를 구축

이후 구축된 데이터베이스를 Android Studio를 통해 개발하는 것이 목표

또한, 레이아웃을 동적으로 사용하여 실무에서 사용하는 앱처럼 구현 하는 것이 목표 

어플리케이션 구조
------------------------

![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/3e7038df-7d1f-42a7-8434-2bde6f991409)

 ![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/91769c61-175d-4543-bac4-456397e355dc)

Cafe24사이트를 통해 도메인서버, 내부 데이터베이스(MariaDB) 사용 


데이터베이스 구축
------------------------
![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/e8466063-ded1-4244-b5bc-5fc1928de866)

![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/f271c2a6-191d-404a-b969-bf3f81d5132c)

운동일지 데이터베이스 구축


코드 리뷰 및 구현
---------------------------------

![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/3a883bd7-af07-4d94-8233-e8d7f1d47157)

![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/d5aa16d9-15c5-44b5-bb41-bfe3dc113687)

![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/f47a0aa9-79ad-4e91-acf2-582a2066f7bc)

-------------------

![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/9b2f6885-b67d-4666-8e79-6172b650fc18)

메인화면



1. Musclegroup.xml
   
다양한 UI 요소를 제약 조건으로 배치 목적으로 ConstraintLayout을 사용

Toolbar는 앱의 상단에 위치

주요 근육과 소근육 섹션으로 구분된 TextView와 이미지들이 중앙에 정렬되어 있음.

이를 통해 각 근육 그룹을 시각적으로 분리하고 사용자가 쉽게 식별할 수 있게 구현함


1. MusclegroupsActivity.java
   
사용자가 주요 근육 그룹을 선택할 수 있는 UI를 구성.

ConstraintLayout을 사용하여 TextView와 ImageView를 적절히 배치

각 근육 그룹에 대해 클릭 이벤트를 설정하여 세부 정보를 제공하도록 구현 

이를 통해 사용자가 직관적으로 근육 그룹을 탐색하고 필요한 정보를 얻을 수 있게 함

-------------------

![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/9147b9a5-3626-4976-bc1b-e9d5c54822e0)

해당 대, 소근육 이미지 클릭 시 나타나는 근육별 파트 근육


2. Submusles.xml
   
ConstraintLayout을 사용하여 세부 근육 그룹을 표시하는 레이아웃을 구성

각 세부 근육 그룹을 RecyclerView로 배치하여 목록 형식으로 보여줌.

2. SubMusclesActivity.java
   
세부 근육 그룹 보여주는 기능을 구현

세부 근육 그룹을 보여주는 활동(Activity)으로, RecyclerView와 SubMusclesAdapter를 사용하여 데이터를 표시 

각 항목을 클릭하면 세부 정보를 보여줌 


2. SubmuslesAdapter.java
   
RecyclerView 어댑터,

세부 근육 그룹 데이터를 뷰 홀더에 바인딩하여 표시

각 항목의 클릭 이벤트를 처리하여 사용자가 선택한 항목에 대한 세부 정보를 제공

-------------------

![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/a74d058e-81b7-47fc-836a-4a87343a6aeb)

부위 별 파트 근육 이미지를 클릭 시 해당 부위의 운동 이미지가 나옴.

해당 운동 리스트는 데이터베이스 값 추가를 통해 동적으로 추가할 수 있음


3. Exercises.xml
   
Submuscle 코드와 동일, 세부 근육의 운동리스트를 보여줌.


3. ExercisesActivity.java
   
SubmuscleActivity 코드와 동일


3. ExercisesAdapter.java
   
SubmuscleAdapter 코드와 동일

-------------------

![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/d60a6c22-5b2e-498b-88aa-8e003c0adc84)

세부 근육 운동 이미지 클릭시 전 데이터베이스를 통해 운동 기록했던 리스트가 나옴. 


4. ExerciseList.xml
   
ListView 사용

해당 리스트 값은 ExerciseUi.xml을 받아옴 .


4. ExerciseListActivity.java
   
ExercisesActivity의 코드와 동일



4. ExerciseListAdapter.java
   
리스트의의 인자값을 받아 ListView를 출력해주는 역할

-------------------

![image](https://github.com/sensesis/2071129_exercise_app/assets/39579919/bc6d2568-8fb8-4f39-b8c5-38cbf8d70bbe)


해당 메모일지. 최대 5개의 운동 기입할 수 있는 리스트 박스 생성 가능. 무게, 시간, 횟수 기입 가능.

타이머를 성정 후, 타이머가 종료되면, 쉬는 시간을 자동으로 기입 + 체크박스 설정. 

모두 기입 후 저장하면 해당 인자값을 데이터베이스에 적재시켜 운동리스트에 표시



5. activity_main.xml
   
해당 필요한 값들을 모아둔 메모장 리스트.


5. MainActivity.java
   
운동일지 메모 후 저장 버튼을 누르면 해당 인자값이 데이터베이스에 저장











