main: info all

info:
	$(info 모든 스택을 빌드하고 CloudFormation에 배포하겠습니다.)
	$(info 개별 빌드&배포도 가능합니다: 1. make collectors 2. make alarms 3. make restapi)

all: collectors-build alarms-build restapi-build collectors alarms restapi

collectors: collectors-build
	cd metrichosu-collectors; sam deploy --profile kurly --stack-name metrichosu-collectors
collectors-build:
	cd metrichosu-collectors; ./gradlew clean -x test build; sam build --profile kurly

alarms: alarms-build
	cd metrichosu-alarms; sam deploy --profile kurly --stack-name metrichosu-alarms
alarms-build:
	cd metrichosu-alarms; ./gradlew clean -x test build; sam build --profile kurly

restapi: restapi-build
	cd metrichosu-restapi; sam deploy --profile kurly --stack-name metrichosu-restapi
restapi-build:
	cd metrichosu-restapi; ./gradlew clean -x test build; sam build --profile kurly

auth:
	aws configure --profile kurly
	cat ~/.aws/config
	cat ~/.aws/credentials
