package org.metrichosu.mcollect

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.lambda.runtime.events.S3Event
import com.amazonaws.services.s3.AmazonS3Client
import org.metrichosu.mcollect.adapter.CloudWatchApi
import org.metrichosu.mcollect.parser.s3.getS3Parser


class S3MetricCollector {
	// 파일명이 {조건}.csv 로 저장된다고 가정
	fun handle(event: S3Event) {
		val cloudWatchApi = CloudWatchApi()
		val s3Client = AmazonS3Client(DefaultAWSCredentialsProviderChain())
		val s3Key = event.records[0].s3.`object`.key
		val bucketName = event.records[0].s3.bucket.name

		event.records.forEach {
			val key = it.s3.`object`.key
			val bucketName = it.s3.bucket.name

			val (dateString, condition, _) = key.split("-", ".")

			val inputStream = s3Client.getObject(bucketName, key).objectContent
			val rowList = String(inputStream.readAllBytes()).split("\n")
			val parser = getS3Parser(condition)

			rowList.forEach { row ->
				parser.parseDataFromRow(condition, row, dateString)
						.let { metricValue ->
							cloudWatchApi.postToCloudWatch(metricValue)
							println("$metricValue 등록 완료")
						}
			}
		}
	}
}
