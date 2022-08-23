package org.metrichosu.mcollect

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.lambda.runtime.events.S3Event
import com.amazonaws.services.s3.AmazonS3Client


class S3MetricCollector {
    fun handle(event: S3Event) {
        val s3Client = AmazonS3Client(DefaultAWSCredentialsProviderChain())
        val s3Key = event.records[0].s3.`object`.key
        val bucketName = event.records[0].s3.bucket.name


        println(s3Key)
        println(bucketName)
//        val s3Object = s3Client.getObject(GetObjectRequest(bucket, s3Key))
//        s3Object.use{ s3->
//
//        }
//        println(event)
//        println(bucket)
//        println("Hello World!")
    }
}

//suspend fun getObjectBytes(bucketName: String, keyName: String, path: String) {
//
//    val request = GetObjectRequest {
//        key = keyName
//        bucket = bucketName
//    }
//
//    S3Client { region = "us-east-1" }.use { s3 ->
//        s3.getObject(request) { resp ->
//            val myFile = File(path)
//            resp.body?.writeToFile(myFile)
//            println("Successfully read $keyName from $bucketName")
//        }
//    }
//}