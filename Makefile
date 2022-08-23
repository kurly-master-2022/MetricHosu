main: collectors alarms restapi

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
