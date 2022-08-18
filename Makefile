main:
	./gradlew clean -x test build
	sam build --profile kurly
	sam deploy --profile kurly
