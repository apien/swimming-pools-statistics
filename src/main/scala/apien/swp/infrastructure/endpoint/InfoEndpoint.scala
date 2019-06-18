package apien.swp.infrastructure.endpoint

import apien.swp.BuildInfo
import cats.Applicative
import cats.effect.Sync
import io.circe.ObjectEncoder
import io.circe.generic.semiauto._
import org.http4s._
import org.http4s.circe.jsonEncoderOf
import org.http4s.dsl.Http4sDsl
class InfoEndpoint[F[_]: Sync] extends Http4sDsl[F] {

  def getInfo: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "info" => Ok(InfoApiDto(BuildInfo.version))
  }
}

object InfoEndpoint {}

case class InfoApiDto(version: String)

object InfoApiDto {
  implicit val encoder: ObjectEncoder[InfoApiDto] = deriveEncoder[InfoApiDto]

  implicit def infoApiDtoEntityEncoder[F[_]: Applicative]: EntityEncoder[F, InfoApiDto] = jsonEncoderOf[F, InfoApiDto]
}
