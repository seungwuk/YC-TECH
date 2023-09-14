# YC-TECH

Backend 직무 

요구사항 분석, api 스펙 정의, 인프라 구축 설계 운영, database 구축 설계 운영, backend 코드 구현, 보안 체계 도입, 내부 스크립트 도구 제작

소프트웨어 개발 프로세스

요구사항 분석→API 스펙 개발 → 테스트 & QA → 배포


API란

서버와 데이터베이스에 대한 출입구 역할

프로그램끼리의 통신 가능

모든 접속을 표준화

REST

구성 : 자원-URI, 행위 -HTTP METHOD, 표현

특징 :

-uniform interface : URI로 지정된 리소스에 대한 조작 통일, 한정적 인터페이스

-stateless : api 서버는 들어오는 요청만 처리, 서비스 자유도가 높고 서버에서 불필요한 정보 관리하지 않음

-cacheable : HTTP 웹표준 그대로 사용, 기존 인프라 그대로 활용

-self-descriptiveness : 자체 표현 구조

-client : 클라이언트와 서버에서 개발해야하는 내용 명확, 의존성 줄어듦

-계층형 구조 : 보안, 로드 밸런싱, 암호화 계층을 추가해 구조사으이 유연성을 둘 수 있음

Spring boot 

특징 :

제어 역전 : IOC 컨테이너 제공.. 객체의 생성과  관계설정(의존성)을 스프링에 위임 가능

의존성 주입 : 객체간의 관계 설정, 애플리케이션의 결합도를 낮추고 유연성과 테스트 용이성 향상

AOP지원 : 애플리케이션의 핵심 비즈니스 로직과 부가적인 기능(로깅, 트랜잭션 관리)을 분리, 모듈화 할수 있음.

웹 개발 지원 : MVC 아키텍처 지원

MVC

Model

Data와 애플리케이션이 무엇을 할 것인지 정의, 내부 비즈니스 로직 처리

컨트롤러 호출 시 db와 연동하여 입출력 데이터를 다룸

데이터 추출, 저장 삭제 업데이트 역할

View

사용자에게 보여주는 화면. 컨트롤러에게 받은 모델의 결과값을 화면으로 출력

Controller

model과 view사이를 이어주는 인터페이스. view에게 요청이 있으면 controller가 model 호출, model이 끝나면 다시 결과를 view에 전달

@Controller vs @RestController ?

@Service

Repository에서 얻어온 정보를 바탕으로 가공 후 controller에게 정보를 보냄

@Repository

db에 접근하는 클래스
