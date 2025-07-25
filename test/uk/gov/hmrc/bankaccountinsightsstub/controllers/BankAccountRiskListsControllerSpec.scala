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

import org.apache.pekko.stream.Materializer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.bankaccountinsightsstub.model.BankAccountDetails
import uk.gov.hmrc.bankaccountinsightsstub.model.BankAccountDetails.implicits._

class BankAccountRiskListsControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  private val controller = new BankAccountRiskListsController(Helpers.stubControllerComponents())

  private val injector           = app.injector
  implicit val mat: Materializer = injector.instanceOf[Materializer]

  "POST /reject/nino" should {
    "return 400 for malformed request payload" in {
      val malformedRequest = FakeRequest("POST", "/reject/nino")
        .withBody(Json.parse("""{"invalidField": "value"}"""))
        .withHeaders(CONTENT_TYPE -> "application/json")

      val result           = controller.isBankAccountOnRejectList()(malformedRequest)
      status(result)          shouldBe Status.BAD_REQUEST
      contentAsString(result) shouldBe """{"message": "malformed request payload}"""
    }

    "return 200 with result true for bank account on reject list" in {
      val validRequest = FakeRequest("POST", "/reject/nino")
        .withBody(Json.toJson(BankAccountDetails("393358", "13902323")))
        .withHeaders(CONTENT_TYPE -> "application/json")

      val result       = controller.isBankAccountOnRejectList()(validRequest)
      status(result)          shouldBe Status.OK
      contentAsString(result) shouldBe """{"result": true}"""
    }

    "return 200 with result false for bank account not on reject list" in {
      val validRequest = FakeRequest("POST", "/reject/nino")
        .withBody(Json.toJson(BankAccountDetails("111111", "22222222")))
        .withHeaders(CONTENT_TYPE -> "application/json")

      val result       = controller.isBankAccountOnRejectList()(validRequest)
      status(result)          shouldBe Status.OK
      contentAsString(result) shouldBe """{"result": false}"""
    }
  }
}
