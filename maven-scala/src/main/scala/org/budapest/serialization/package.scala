package org.budapest.serialization

import org.budapest.model.Timeline
import play.api.libs.json._

package object json {

	class TimelineFormat extends Format[Timeline] {
		def reads(json: JsValue): JsResult[Timeline] = {
			json match {
				case _: JsObject =>
					JsSuccess(Timeline(
						id = (json \ "id").as[Long],
						permalink = (json \ "permalink").as[String],
						headline = (json \ "headline").as[String]))
				case _ =>
					JsError("Invalid Timeline")
			}
		}

		def writes(item: Timeline): JsObject = 
			Json.obj(
				"id" -> item.id,
				"permalink" -> item.permalink,
				"headline" -> item.headline)
	}

	implicit val timelineFormat = new TimelineFormat
}
