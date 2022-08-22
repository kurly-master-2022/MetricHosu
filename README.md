# MetricHosu
MetricHosu - AWS SAM &amp; ElasticBeanstalk Infrastructure

# Infrastructure

## Metric Collectors
> `:metrichosu:metrichosu-collectors` 모듈

메트릭 수집기는 두 개의 람다 함수를 사용합니다. 메트릭을 읽는 곳이 외부 소스이냐, S3이냐에 따라 클래스를 분화했습니다.
- ExternMetricCollector
- S3MetricCollector

왜냐하면 두 가지 입력 유형을 동시에 처리하는 핸들러 작성이 자바에서 난해하기 때문입니다.

두 람다 자원은 **AWS SAM CLI**을 사용해 개발자가 곧바로 배포할 수 있습니다.

## REST API
> `:metrichosu:metrichosu-restapi` 모듈

메트릭 워크플로를 관리할 수 있는 엔드포인트를 노출합니다. 

REST API는 **Elastic Beanstalk** 서비스의 웹 서버 티어로 배치하여 관리 부담을 줄입니다.

REST API 역시 SAM CLI를 통해 Elastic Beanstalk으로 소스 번들 형태로 업로드됩니다.
따라서 개발자가 곧바로 배포를 수행할 수 있습니다.

## DynamoDB - MetricHosu Table

어드민 웹 서비스와 MetricHosu가 데이터베이스를 공유해야 하는지는 검토가 필요합니다.
두 서비스가 따로 자료를 영속하는 것이 적절하므로 전용 DynamoDB 테이블을 우선 배치했습니다.

DynamoDB 테이블 역시 AWS SAM 템플릿에 정의된 설정으로 구성됩니다.

## S3 - MetricHosu Bucket

S3MetricCollector가 메트릭을 읽어들이는 원본 버킷입니다. 

둘 사이의 관계는 AWS SAM 템플릿에 정의되어 있습니다.
따라서 S3 버킷 아이템 추가시 S3MetricCollector 람다가 반응합니다.

# Deployment 절차

## metrichosu-collectors 스택 배포
1. 루트 프로젝트 폴더에서 `make collectors` 명령어
2. 2가 안될 경우 1회에 한해 `metrichosu-collectors` 폴더> `sam build & sam deploy --guided` 진행

## metrichosu-restapi 스택 배포
1. 루트 프로젝트> `build.gradle`> `version` 설정 (또는, gradle 태스크에 `-Pversion`을 주입하기)
2. 루트 프로젝트 폴더에서 `make restapi` 명령어
3. 2가 안될 경우 1회에 한해 `metrichosu-restapi` 폴더> `sam build & sam deploy --guided` 진행

# Prerequsites

## AWS CLI, AWS SAM CLI 설치
- [AWS CLI 설치](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
- [AWS SAM CLI 설치](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)

## AWS CLI 프로필 설정
`kurly`라는 이름의 프로필을 만들고 유저의 AWS 액세스 키와 시크릿 키를 등록해 주세요.

`make auth` 명령어로 진행 가능합니다.

<img width="697" alt="스크린샷 2022-08-19 오후 1 19 04" src="https://user-images.githubusercontent.com/15683098/185542689-f9d0c6b2-344f-40bc-b463-6a552c70b70b.png">

(끝)