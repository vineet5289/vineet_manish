    var shiftid = 0;

    

    
$(function () {
    
    var validationObj = {
        userName:{
                identifier:'userName',
                rules:[
                {
                  type:'empty',
                  prompt:'PLEASE ENTER USERNAME'
                }]
        },
        
        
        password: {
                     identifier  : 'password',
                      rules: [
                      {
                          type: 'empty',
                          prompt: 'PLEASE ENTER THE PASSWORD'
                      }]
                       
          },
                        
         confirmPassword: {
                        identifier  : 'confirmPassword',
                        rules: [
                         {
                               type   : 'match[password]',
                               prompt : 'PLEASE ENTER THE SAME VALUE IN BOTH FIELDS'
                          }   ]
             },
        
        
       
        instituteName: {
                identifier: 'instituteName',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER SCHOOL NAME'
                }]
            },
         instituteRegistrationId: {
                identifier: 'instituteRegistrationId',
                optional:true,
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER YOUR SCHOOL REGISTRATION ID'
                }]
            },
         instituteAddressLine1: {
                identifier: 'instituteAddressLine1',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER YOUR SCHOOL ADDRESS'
                }]
            },
            instituteAddressLine2: {
                identifier: 'instituteAddressLine2',
                optional:true,
                rules: [{
                    
                    type: 'empty',
                    prompt: 'PLEASE ENTER YOUR SCHOOL ADDRESS'
                }]
            },
             instituteCity: {
                identifier: 'instituteCity',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER CITY'
                }]
            },
            
            instituteState: {
                identifier: 'instituteState',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE SELECT STATE'
                }]
            },
            
             instituteCountry: {
                identifier: 'instituteCountry',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER COUNTRY NAME'
                }]
            },
             institutePinCode: {
                identifier: 'institutePinCode',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER PINCODE'
                },
                {
                  type:'number',
                  prompt:'SIX DIGIT NUMBERS [0....9] ONLY'
                },
                {
                    type:'exactLength[6]',
                    prompt: 'PINCODE SHOULD BE OF  LENGTH SIX ONLY'
                }
                ]
            },
            
         contactPersonName: {
                identifier: 'contactPersonName',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER YOUR NAME'
                }]
            },

            groupOfInstitute: {
                identifier: 'groupOfInstitute',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE SELECT TYPE OF INSTITUTE'
                }]
            },

             noOfInstitute: {
                identifier: 'noOfInstitute',
                
                 rules: [
                 {
                    type: 'empty',
                    prompt: 'PLEASE ENTER NUMBER OF INSTITUTE'
                },
                {
                    type: 'number',
                    prompt: 'PLEASE ENTER VALID NUMBER'
                }]
            },
            
            instituteEmail: {
                identifier: 'instituteEmail',
                rules: [
                 {
                    type: 'empty',
                    prompt: 'PLEASE ENTER SCHOOL EMAIL ID'
                },
                {
                    type: 'email',
                    prompt: 'EMAIL ID IS NOT CORRECTt'
                }
                ]
            },
        institutePhoneNumber: {
                identifier: 'institutePhoneNumber',
                rules: [
                {
                    type:'empty',
                    prompt: 'PLEASE ENTER MOBILE NUMBER'
                },
                {
                  type:'number',
                  prompt:'NUMBERS ALLOWED ONLY'
                },
                {
                    type:'exactLength[10]',
                    prompt: 'MOBILE NUMBER SHOULD BE OF  LENGTH 10'
                }
                
                ]
                 
            },

            instituteOfficeNumber: {
                identifier: 'instituteOfficeNumber',
                optional:true,
                rules: [
                {
                    type:'empty',
                    prompt: 'PLEASE ENTER MOBILE NUMBER'
                },
                {
                  type:'number',
                  prompt:'NUMBERS ALLOWED ONLY'
                },
                {
                    type:'exactLength[10]',
                    prompt: 'MOBILE NUMBER SHOULD BE OF  LENGTH 10'
                }
                
                ]
                 
            },
        query: {
                identifier: 'query',
                optional:true,
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER YOUR QUERY'
                }]
            },
            referenceKey:{
                 identifier:'referenceKey',
                 rules:[{
                      type:'empty',
                      prompt:'PLEASE ENTER REFERENCE KEY'
                 }]
            },
            
            otp:{
                 identifier:'otp',
                 rules:[{
                      type:'empty',
                      prompt:'PLEASE ENTER OTP GENERATED'
                 }]
            },
            emailID:{
                 identifier:'emailID',
                 rules:[{
                      type:'empty',
                      prompt:'PLEASE ENTER E-MAIL ID'
                 },
                 {
                    type: 'email',
                    prompt: 'EMAIL ID IS NOT CORRECTt'
                }]
            },

            groupName:{
              identifier:'groupName',
              rules:[{
                type:'empty',
                prompt:'GROUP NAME CANNOT BE LEFT EMPTY'
              }]
            },
            groupDescription:{
              identifier:'groupDescription',
              rules:[{
                type:'empty',
                prompt:'ENTER SOME DESCRIPTION OF GROUP'
              }]
            },

             roleName:{
              identifier:'roleName',
              rules:[{
                type:'empty',
                prompt:'ROLE NAME CANNOT BE LEFT EMPTY'
              }]
            },
            roleDescription:{
              identifier:'roleDescription',
              rules:[{
                type:'empty',
                prompt:'ENTER SOME DESCRIPTION OF ROLE'
              }]
            }
            
            
    };
    
    $('.state').dropdown();
    $('HomePage-DropDown').dropdown();
    $('.ui.dropdown').dropdown();
    
    //resetform
      
    // New School Request Form
        $('#New-schoolRequest-submit').on('click',function(){
           $('#newSchoolRegistrationForm').form(validationObj,{
              on:'change',
             inline:true,
             onSuccess:function(){
              $('#newSchoolRegistrationForm').submit(function(e){

                e.preventDefault();
                 return false;
              });
             }

           });

        });

        //add new group Form
        $('#addNewGroupSubmitbtn').on('click',function(){
          $('#addNewGroupForm').form(validationObj,{
            on:'change',
            inline:true,
            onSucess:function(){
              $('#addNewGroupForm').submit(function(e){
                e.preventDefault();
                return false;
              });
            }
          })
        });

        // add new role form
        $('#addNewRoleSubmitbtn').on('click',function(){
          $('#addNewRoleForm').form(validationObj,{
            on:'change',
            inline:true,
            onSucess:function(){
              $('#addNewRoleForm').submit(function(e){
                e.preventDefault();
                return false;
              });
            }
          })
        });

       

      
     $('.ui.dropdown').dropdown();  
    
    
    // For Main login form
    $('#login-submit').on('click',function(){
    $('#loginform').form(validationObj, {
        inline : true,
        on     : 'blur',
        transition: 'fade down'
        
    
    });
    });
    
    
    
    
    // For adding new Class
    
    $('#addNewClass').click(function(){
    $('#addSchool_modal').modal('show');
    });
    
    //After sending OTP modal comes
    
    $('#completeSchoolRegistration').click(function(){
    $('#OTP-modal').modal('show');
    
    });  
    
    //Register school Modal Yes or No
    
    $('#RegisterSchoolButton').click(function(){
     $('#RegisterSchoolModal').modal('show');
    });  
    
    $('#cancel-OTP-Send').click(function(){
        $('#RegisterSchoolModal').modal('show');
        $('#OTP-modal').modal('hide');      
    }); 
    
    //OTP modal submit
 $('#sendSchoolRegistrationOTPForm').on('click',function(){
           $('#completeSchoolRegistrationOTPForm').form(validationObj,{
              on:'change',
             inline:true,
             onSuccess:function(){
              $('#completeSchoolRegistrationOTPForm').submit(function(e){

                e.preventDefault();
                 return false;
              });
             }

           });

        });


//popup data in input field on school registration and other forms

 $('input')
      .popup({
        on: 'focus',
      });    
    
    
    // captcha  code on otp form
    captchaCode();

   //Flash message boiunce
 


// closing message
$('.message .close').on('click', function() {
    $(this).closest('.message').fadeOut();
});



 


  // this is for selkecting number nu,ber of shift of institute runs in more than 1 shift toggle transition

  $(".ui.checkbox.shift").checkbox({
    "onChecked": function() {
        document.getElementById("toggleShift").style.display = "";
    },
    "onUnchecked": function() {

      var container = document.getElementById("shiftContainer");
      while (container.hasChildNodes()) {
             container.removeChild(container.lastChild);
           }

           $('.ui.dropdown.shift').dropdown('refresh'); 
      
      document.getElementById("toggleShift").style.display = "none";
    }
  });


// stepwise form on institute mandatory Info



    // for validation in step form new approach
  $('#next').on('click',function(e){
    e.preventDefault();
  
  $('.ui.form.one').form( { 
     fields : {
       "instituteBoard": {
        "identifier": "instituteBoard",
        "rules": [{
          "type": "empty",
          "prompt": "Please selet Board"
        }]
      },
      "instituteType": {
        "identifier": "instituteType",
        "rules": [{
          "type": "empty",
          "prompt": "Please selet type"
        }]
      },

                     "instituteOfficeWeekStartDay": {
        "identifier": "instituteOfficeWeekStartDay",
        "rules": [{
          "type": "empty",
          "prompt": "Please Select start day of week"
        }]
      },
      "instituteOfficeWeekEndDay": {
        "identifier": "instituteOfficeWeekEndDay",
        "rules": [{
          "type": "empty",
          "prompt": "Please selet end day of week"
        }]
      },
      "instituteClassFrom": {
        "identifier": "instituteClassFrom",
        "rules": [{
          "type": "empty",
          "prompt": "Please selet start class"
        }]
      },
       "instituteClassTo": {
        "identifier": "instituteClassTo",
        "rules": [{
          "type": "empty",
          "prompt": "Please selet end class"
        }]
      },
      "instituteOfficeStartTime": {
        "identifier": "instituteOfficeStartTime",
        "rules": [{
          "type": "empty",
          "prompt": "Please Select start time"
        }]
      },
      "instituteOfficeEndTime": {
        "identifier": "instituteOfficeEndTime",
        "rules": [{
          "type": "empty",
          "prompt": "Please selet end  time"
        }]
      },
      "instituteFinancialStartDate": {
        "identifier": "instituteFinancialStartDate",
        "rules": [{
          "type": "empty",
          "prompt": "Please selet start date"
        }]
      },
       "instituteFinancialEndDate": {
        "identifier": "instituteFinancialEndDate",
        "rules": [{
          "type": "empty",
          "prompt": "Please selet end class"
        }]
      }
      
     },
     inline : true,
     onSuccess:function(e){
      e.preventDefault();
      $('.ui.form.two').form().show();
       $(this).form().hide();
      return false;
     }
     
  });

});

    $('#back').on('click',function(e){
       e.preventDefault();
       $('.ui.form.one').form().show();
       $('.ui.form.two').form().hide();
    });

    $('#submitMandInfobtn').on('click',function(e){
      e.preventDefault();
    $('.ui.form.two').form({
        fields : {
           "shiftAdd": {
        "identifier": "shiftAdd",
        "rules": [{
          "type": "checked",
          "prompt": "You must agree to the terms and conditions"
        }]
      },
       "shiftName": {
        "identifier": "shiftName",
        "rules": [{
          "type": "empty",
          "prompt": "Please Enter Shift Name"
        }]
      },
      "shiftAttendenceType": {
        "identifier": "shiftAttendenceType",
        "rules": [{
          "type": "empty",
          "prompt": "Please select attendance type"
        }]
      },
      "shiftClassStartTime": {
        "identifier": "shiftClassStartTime",
        "rules": [{
          "type": "empty",
          "prompt": "Please selet start time"
        }]
      },
       "shiftClassEndTime": {
        "identifier": "shiftClassEndTime",
        "rules": [{
          "type": "empty",
          "prompt": "Please selet end class"
        }]
      },
       "shiftStartClassFrom": {
        "identifier": "shiftStartClassFrom",
        "rules": [{
          "type": "empty",
          "prompt": "Please select class from"
        }]
      },
       "shiftEndClassTo": {
        "identifier": "shiftEndClassTo",
        "rules": [{
          "type": "empty",
          "prompt": "Please select class to"
        }]
      },
      "shiftWeekStartDay": {
        "identifier": "shiftWeekStartDay",
        "rules": [{
          "type": "empty",
          "prompt": "Please select start week"
        }]
      },
      "shiftWeekEndDay": {
        "identifier": "shiftWeekEndDay",
        "rules": [{
          "type": "empty",
          "prompt": "Please select end week"
        }]
      }
        },
        inline:true,
        onSuccess:function(e){
           e.preventDefault();
          $('#MandatoryUpdateForm').submit();
          return false;
        }
      });
    });

    // this is the code for dynamically adding shifts 

     
   

    // up to here dynamically adding shifts


    
});


function shiftbox_original(){

  /*var shift=parseInt(document.getElementById("shiftvalue").value);
    
   for(var i=2;i<=shift;i++){
  
    var button=$('#shiftContainer div:first')
        .clone();
       
        button.appendTo($('#shiftContainer'));
      }*/

      var shift=parseInt(document.getElementById("shiftvalue").value);
      
      for(var i=2;i<=shift;i++){
      var button = $('#shiftContainer').clone(true);

        shiftid++;
        
        button.find('input').val('');
        button.removeAttr('id');
        button.insertBefore('.newShiftContainer');
        button.attr('id', 'newShiftContainer' + shiftid);
      } 
      
      
}


function shiftbox(){

  $('.dropdown').dropdown();
  var container = document.getElementById("shiftContainer");
 var shift=parseInt(document.getElementById("shiftvalue").value);
 //while (container.hasChildNodes()) {
   //             container.removeChild(container.lastChild);
     //       }
      
      for(var i=0;i<shift;i++){

            var shiftName = "shifts[" + i + "].shiftName";
            var shiftAttendenceType = "shifts[" + i + "].shiftAttendenceType";
            var shiftClassStartTime = "shifts[" + i + "].shiftClassStartTime";
            var shiftClassEndTime = "shifts[" + i + "].shiftClassEndTime";
            var shiftStartClassFrom = "shifts[" + i + "].shiftStartClassFrom";
            var shiftEndClassTo = "shifts[" + i + "].shiftEndClassTo";
            var shiftWeekStartDay = "shifts[" + i + "].shiftWeekStartDay";
            var shiftWeekEndDay = "shifts[" + i + "].shiftWeekEndDay";



             var htmlshift = [
'<div class="ui segment">',
'                     <div class="two fields">',
'                        <div class="field">',
'                           <label>Shift Name</label>',
'                           <input name='+shiftName+' type="text" value=" " placeholder="Enter Shift Name">',
'                        </div>',
'                        <div class="field">',
'                           <label>Shift Attendance Type </label>',
'                           <div class="ui fluid selection dropdown">',
'                              <div class="text">Select</div>',
'                              <i class="dropdown icon"></i>',
'                              <input name='+shiftAttendenceType+' type="hidden">',
'                              <div class="menu">',
'                                 @for(value<- attendenceType){ ',
'                                 <div class="item" data-value="@value">@value</div>',
'                                 }',
'                              </div>',
'                           </div>',
'                        </div>',
'                     </div>',
'                     <div class="two fields">',
'                        <div class="field">',
'                           <label>Shift Start Time</label>',
'                           <div class="ui calendar timeSelect">',
'                              <div class="ui input left icon">',
'                                 <i class="time icon"></i>',
'                                 <input name='+shiftClassStartTime+' type="text" value="" placeholder="School Start Time">',
'                              </div>',
'                           </div>',
'                        </div>',
'                        <div class="field">',
'                           <label>Shift End Time</label>',
'                           <div class="ui calendar timeSelect">',
'                              <div class="ui input left icon">',
'                                 <i class="time icon"></i>',
'                                 <input name='+shiftClassEndTime+' type="text" value="" placeholder="School End Time">',
'                              </div>',
'                           </div>',
'                        </div>',
'                     </div>',
'                     <div class="two fields">',
'                        <div class="field">',
'                           <label>Class From</label>',
'                           <div class="ui fluid selection dropdown">',
'                              <div class="text">Select</div>',
'                              <i class="dropdown icon"></i>',
'                              <input name='+shiftStartClassFrom+' type="hidden">',
'                              <div class="menu">',
'                                 @for(value<- classes){ ',
'                                 <div class="item" data-value="@value">@value</div>',
'                                 }',
'                              </div>',
'                           </div>',
'                        </div>',
'                        <div class="field">',
'                           <label>Class To</label>',
'                           <div class="ui fluid selection dropdown">',
'                              <div class="text">Select</div>',
'                              <i class="dropdown icon"></i>',
'                              <input name='+shiftEndClassTo+' type="hidden">',
'                              <div class="menu">',
'                                 @for(value<- classes){ ',
'                                 <div class="item" data-value="@value">@value</div>',
'                                 }',
'                              </div>',
'                           </div>',
'                        </div>',
'                     </div>',
'                     <div class="two fields">',
'                        <div class="field">',
'                           <label>Shift Week Starts on</label>',
'                           <div class="ui fluid selection dropdown">',
'                              <div class="text">Select</div>',
'                              <i class="dropdown icon"></i>',
'                              <input name='+shiftWeekStartDay+' type="hidden">',
'                              <div class="menu">',
'                                 @for(value<- weekDay){ ',
'                                 <div class="item" data-value="@value">@value</div>',
'                                 }',
'                              </div>',
'                           </div>',
'                        </div>',
'                        <div class="field">',
'                           <label>Shift Week Ends on</label>',
'                           <div class="ui fluid selection dropdown">',
'                              <div class="text">Select</div>',
'                              <i class="dropdown icon"></i>',
'                              <input name='+shiftWeekEndDay+' type="hidden">',
'                              <div class="menu">',
'                                 @for(value<- weekDay){ ',
'                                 <div class="item" data-value="@value">@value</div>',
'                                 }',
'                              </div>',
'                           </div>',
'                        </div>',
'                     </div>',
'                    </div> '
].join('');

    






      //var html = $(html);
      $(htmlshift).appendTo(container).each(function(){ $('.ui.dropdown').dropdown(); });
      //container.appendChild(html);
      //container.appendChild(document.createElement("br"));
      //container.innerHTML=htmlshift;
      }
   

}














function captchaCode() {
        var Numb1, Numb2, Numb3, Numb4, Code;     
        Numb1 = (Math.ceil(Math.random() * 10)-1).toString();
        Numb2 = (Math.ceil(Math.random() * 10)-1).toString();
        Numb3 = (Math.ceil(Math.random() * 10)-1).toString();
        Numb4 = (Math.ceil(Math.random() * 10)-1).toString();
  
        Code = Numb1 + Numb2 + Numb3 + Numb4;
        $("#captcha span").remove();
        $("#captcha input").remove();
        $("#captcha").append("<span id='code'>" + Code+"  " + "</span><input type='button' onclick='captchaCode();'>");
   }
   
   function resetForm(){
    $('#completeSchoolRegistrationOTPForm').trigger('reset');
   }




   
   








    
