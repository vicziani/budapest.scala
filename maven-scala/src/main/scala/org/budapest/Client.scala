package org.budapest

import play.api.libs.json._
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.client.methods.HttpGet

import org.budapest.model.Timeline
import org.budapest.serialization.json._

class Client {

	def getUrl(url: String): String = {
		val httpClient = HttpClientBuilder.create().build()
		val httpResponse = httpClient.execute(new HttpGet(url))
		httpResponse.setHeader("Content-Type", "text/html; charset=UTF-8")
		val entity = httpResponse.getEntity
		val content = if (entity != null) {
			val inputStream = entity.getContent()
			val input = io.Source.fromInputStream(inputStream, "UTF-8").getLines.mkString
			inputStream.close
			return input
		} else ""
		httpClient.close()
		return content
	}

	def getTimeline(url: String): List[Timeline] = {
		val content = getUrl(url)
		if (content != "") {
			val result = Json.parse(content)
			(result \ "meta" \ "success").as[Boolean] match {
				case true => (result \ "data" \ "items").as[List[Timeline]]
				case _ => throw new Exception("Error in timeline")
			}
		} else List()
	}
}
