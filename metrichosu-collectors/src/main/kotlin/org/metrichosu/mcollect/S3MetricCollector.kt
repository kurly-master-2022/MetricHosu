package org.metrichosu.mcollect

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.lambda.runtime.events.S3Event
import com.amazonaws.services.s3.AmazonS3Client
import org.metrichosu.mcollect.parser.s3.getS3Parser


class S3MetricCollector {
    // {회사명}_{조건}.csv 라는 형식으로 가격이 저장된다고 가정
    fun handle(event: S3Event) {
        val s3Client = AmazonS3Client(DefaultAWSCredentialsProviderChain())
        val s3Key = event.records[0].s3.`object`.key
        val bucketName = event.records[0].s3.bucket.name


        println("event records size : ${event.records.size}")
        event.records.forEach{
            val key = it.s3.`object`.key
            val bucketName = it.s3.bucket.name
            val inputStream = s3Client.getObject(bucketName, key).objectContent
            val rowList = String(inputStream.readAllBytes()).split("\n")
            println(rowList)
            val (company, condition, _) = key.split("_", ".")
            println("company $company, condition $condition")
//            val parser = getS3Parser(condition)
//            rowList.forEach 반복문으로 파서 데이터 조회작업 실행

        }
        println(s3Key)
        println(bucketName)

    }
}
