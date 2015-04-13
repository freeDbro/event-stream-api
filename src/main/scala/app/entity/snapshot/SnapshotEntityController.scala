package app.entity.snapshot

import app.action.AppAction.{Script, noAction}
import app.infrastructure.FrameworkResponse
import app.model.Codecs._
import app.model.EntityId
import app.parseTime
import argonaut.integrate.unfiltered.JsonResponse
import unfiltered.response.{BadRequest, Created, ResponseString}

object SnapshotEntityController {

  def snapshot(id: EntityId, time: String): Script[FrameworkResponse] = {
    val error = BadRequest ~> ResponseString("Time must be in ISO8601 format")
    parseTime(time).fold(noAction[FrameworkResponse](error))(dateTime =>
      SnapshotEntityService.snapshot(id, dateTime) map (snapshot => Created ~> JsonResponse(snapshot)))
  }

}
