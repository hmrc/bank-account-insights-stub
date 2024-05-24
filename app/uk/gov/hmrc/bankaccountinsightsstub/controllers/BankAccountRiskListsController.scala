/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.bankaccountinsightsstub.controllers

import play.api.libs.json._
import play.api.mvc.{Action, BaseController, ControllerComponents}
import uk.gov.hmrc.bankaccountinsightsstub.model.BankAccountDetails
import uk.gov.hmrc.bankaccountinsightsstub.model.BankAccountDetails.implicits._

import javax.inject.Inject
import scala.concurrent.Future

class BankAccountRiskListsController @Inject() (val controllerComponents: ControllerComponents) extends BaseController {

  private val watchList = Seq(
    "393358,13902323",
    "393358,13902325",
    "543199,28337647",
    "456539,27060974",
    "035814,50493734",
    "731959,09912180",
    "703997,46719898",
    "898436,37686211",
    "579848,83410937",
    "810538,27852155",
    "594814,41824257",
    "785382,33029759",
    "808330,93087622",
    "228840,70776135",
    "569821,65892385",
    "031018,11559125",
    "771343,90345198",
    "737721,56244798",
    "788480,67783615",
    "881692,50225300"
  )

  def isBankAccountOnRejectList: Action[JsValue] = Action.async(parse.json) { req =>
    val bankAccountOnRejectList = Json.fromJson[BankAccountDetails](req.body)
    Future.successful(
      bankAccountOnRejectList.fold(
        _ => BadRequest("""{"message": "malformed request payload}"""),
        valid =>
          if (watchList.contains(s"${valid.sortCode},${valid.accountNumber}")) {
            Ok("""{"result": true}""")
          } else {
            Ok("""{"result": false}""")
          }
      )
    )
  }
}
