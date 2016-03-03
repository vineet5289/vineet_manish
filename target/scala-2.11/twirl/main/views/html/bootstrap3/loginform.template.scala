
package views.html.bootstrap3

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object loginform_Scope0 {
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

class loginform extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template3[play.api.mvc.Call,Field,Field,play.twirl.api.HtmlFormat.Appendable] {

  /*************************
 Simple Login template.
 
 Takes a reverse route, the email field, and the password field from a form. 
 If form validation fails, then the fields will have Twitter Bootstrap error highlighting.
 Clients should set the "error" key in flash scope to provide feedback on errors. 
**************************/
  def apply/*9.2*/(reverseRoute: play.api.mvc.Call, emailField: Field, passwordField: Field):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*9.76*/("""

"""),format.raw/*11.1*/("""<div class="row">
  <div class="col-sm-4 col-sm-offset-4">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title">Please log in</h3>
      </div>
      <div class="panel-body">
        """),_display_(/*18.10*/helper/*18.16*/.form(reverseRoute, 'role -> "form")/*18.52*/ {_display_(Seq[Any](format.raw/*18.54*/("""
          """),format.raw/*19.11*/("""<fieldset>
            <div class="form-group """),_display_(/*20.37*/if(emailField.hasErrors)/*20.61*/ {_display_(Seq[Any](format.raw/*20.63*/("""has-error""")))}),format.raw/*20.73*/("""" >
              <input id=""""),_display_(/*21.27*/emailField/*21.37*/.id),format.raw/*21.40*/("""" name=""""),_display_(/*21.49*/emailField/*21.59*/.name),format.raw/*21.64*/("""" value=""""),_display_(/*21.74*/emailField/*21.84*/.value.getOrElse("")),format.raw/*21.104*/("""" class="form-control" placeholder="E-mail" type="text">
            </div>
            <div class="form-group """),_display_(/*23.37*/if(passwordField.hasErrors)/*23.64*/ {_display_(Seq[Any](format.raw/*23.66*/("""has-error""")))}),format.raw/*23.76*/("""" >
              <input id=""""),_display_(/*24.27*/passwordField/*24.40*/.id),format.raw/*24.43*/("""" name=""""),_display_(/*24.52*/passwordField/*24.65*/.name),format.raw/*24.70*/("""" value=""""),_display_(/*24.80*/passwordField/*24.93*/.value.getOrElse("")),format.raw/*24.113*/("""" class="form-control" placeholder="Password" type="password">
            </div>
            <input class="btn btn-lg btn-success btn-block" type="submit" value="Login">
          </fieldset>
        """)))}),format.raw/*28.10*/("""
         """),_display_(/*29.11*/if(flash.containsKey("error"))/*29.41*/ {_display_(Seq[Any](format.raw/*29.43*/("""
           """),format.raw/*30.12*/("""<div id="error-message" class="text-danger">
             """),_display_(/*31.15*/flash/*31.20*/.get("error")),format.raw/*31.33*/("""
           """),format.raw/*32.12*/("""</div>
         """)))}),format.raw/*33.11*/("""
      """),format.raw/*34.7*/("""</div>
    </div>
  </div>
</div>
"""))
      }
    }
  }

  def render(reverseRoute:play.api.mvc.Call,emailField:Field,passwordField:Field): play.twirl.api.HtmlFormat.Appendable = apply(reverseRoute,emailField,passwordField)

  def f:((play.api.mvc.Call,Field,Field) => play.twirl.api.HtmlFormat.Appendable) = (reverseRoute,emailField,passwordField) => apply(reverseRoute,emailField,passwordField)

  def ref: this.type = this

}


}

/*************************
 Simple Login template.
 
 Takes a reverse route, the email field, and the password field from a form. 
 If form validation fails, then the fields will have Twitter Bootstrap error highlighting.
 Clients should set the "error" key in flash scope to provide feedback on errors. 
**************************/
object loginform extends loginform_Scope0.loginform
              /*
                  -- GENERATED --
                  DATE: Fri Mar 04 01:19:01 IST 2016
                  SOURCE: /Users/vineet/sms/srp/app/views/bootstrap3/loginform.scala.html
                  HASH: f672933baed876adcd8768e49d4d2d47f4c07788
                  MATRIX: 1115->335|1284->409|1313->411|1575->646|1590->652|1635->688|1675->690|1714->701|1788->748|1821->772|1861->774|1902->784|1959->814|1978->824|2002->827|2038->836|2057->846|2083->851|2120->861|2139->871|2181->891|2320->1003|2356->1030|2396->1032|2437->1042|2494->1072|2516->1085|2540->1088|2576->1097|2598->1110|2624->1115|2661->1125|2683->1138|2725->1158|2958->1360|2996->1371|3035->1401|3075->1403|3115->1415|3201->1474|3215->1479|3249->1492|3289->1504|3337->1521|3371->1528
                  LINES: 33->9|38->9|40->11|47->18|47->18|47->18|47->18|48->19|49->20|49->20|49->20|49->20|50->21|50->21|50->21|50->21|50->21|50->21|50->21|50->21|50->21|52->23|52->23|52->23|52->23|53->24|53->24|53->24|53->24|53->24|53->24|53->24|53->24|53->24|57->28|58->29|58->29|58->29|59->30|60->31|60->31|60->31|61->32|62->33|63->34
                  -- GENERATED --
              */
          