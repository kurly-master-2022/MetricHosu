# MetricHosu
MetricHosu - AWS SAM &amp; ElasticBeanstalk Infrastructure

# Infrastructure

## Metric Collectors
> `:metrichosu:metrichosu-collectors` 모듈

메트릭 수집기하는 두 개의 람다 함수입니다. 메트릭을 읽는 곳이 외부 소스이냐, S3이냐에 따라 클래스를 분화했습니다.
- ExternMetricCollector
- S3MetricCollector

그 이유는, 2가지 입력 유형을 동시에 다루는 핸들러 작성은 자바에서 난해하기 때문입니다.

두 람다 자원은 **AWS SAM CLI**을 사용해 개발자가 곧바로 배포할 수 있습니다.

## REST API
> `:metrichosu:metrichosu-restapi` 모듈

메트릭 워크플로를 관리할 수 있는 엔드포인트를 노출합니다. 

REST API는 **Elastic Beanstalk** 서비스의 웹 서버 티어로 배치하여 관리 부담을 줄입니다.
REST API 역시 AWS SAM CLI를 통해 Elastic Beanstalk에 소스 번들 형태로 업로드됩니다.
따라서 개발자가 곧바로 배포를 수행할 수 있습니다.

# Deployment 절차
1. 루트 프로젝트> `build.gradle`> `version` 설정
2. 루트 프로젝트 보이는 상태에서 `make` 명령어
3. AWS SAM의 Deploy가 끝나고 출력되는 엔드포인트 주소에 접속하여 잘 돌아가는지 확인.
   
(끝)