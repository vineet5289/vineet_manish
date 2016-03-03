
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object Login_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._

class Login extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template4[String,Boolean,UserInfo,Form[views.forms.LoginForm],play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(page: String, isLoggedIn: Boolean, userInfo: UserInfo, loginForm: Form[views.forms.LoginForm]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
import bootstrap3._

Seq[Any](format.raw/*1.97*/("""

"""),format.raw/*4.1*/("""
"""),_display_(/*5.2*/Main(page, isLoggedIn, userInfo)/*5.34*/ {_display_(Seq[Any](format.raw/*5.36*/("""
   """),format.raw/*6.4*/("""<div class="container">
     """),_display_(/*7.7*/loginform(routes.SRPController.postLogin(), loginForm("username"), loginForm("password"))),format.raw/*7.96*/("""
   """),format.raw/*8.4*/("""</div>
""")))}),format.raw/*9.2*/("""
"""))
      }
    }
  }

  def render(page:String,isLoggedIn:Boolean,userInfo:UserInfo,loginForm:Form[views.forms.LoginForm]): play.twirl.api.HtmlFormat.Appendable = apply(page,isLoggedIn,userInfo,loginForm)

  def f:((String,Boolean,UserInfo,Form[views.forms.LoginForm]) => play.twirl.api.HtmlFormat.Appendable) = (page,isLoggedIn,userInfo,loginForm) => apply(page,isLoggedIn,userInfo,loginForm)

  def ref: this.type = this

}


}

/**/
object Login extends Login_Scope0.Login
              /*
                  -- GENERATED --
                  DATE: Fri Mar 04 01:23:41 IST 2016
                  SOURCE: /Users/vineet/sms/srp/app/views/Login.scala.html
                  HASH: 75149cbc46dea8ee0a384adb41d5982ff7664527
                  MATRIX: 790->1|999->96|1027->119|1054->121|1094->153|1133->155|1163->159|1218->189|1327->278|1357->282|1394->290
                  LINES: 27->1|32->1|34->4|35->5|35->5|35->5|36->6|37->7|37->7|38->8|39->9
                  -- GENERATED --
              */
          