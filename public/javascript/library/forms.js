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




    
    
    
    // captcha  code on otp form
    captchaCode();




// adding shift box on update
 // shiftbox();




    
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



 var shift=parseInt(document.getElementById("shiftvalue").value);
      
      for(var i=0;i<shift;i++){
        var shiftName = "shifts[" + i + "].shiftName";
            var shiftAttendenceType = "shifts[" + i + "].shiftAttendenceType";
            var shiftClassStartTime = "shifts[" + i + "].shiftClassStartTime";
            var shiftClassEndTime = "shifts[" + i + "].shiftClassEndTime";
            var shiftStartClassFrom = "shifts[" + i + "].shiftStartClassFrom";
             var shiftEndClassTo = "shifts[" + i + "].shiftEndClassTo";
             var shiftWeekStartDay = "shifts[" + i + "].shiftWeekStartDay";
             var shiftWeekEndDay = "shifts[" + i + "].shiftWeekEndDay";

      var html = $(newShiftContainer);
      $("#shiftContainer").append(html);
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
   








    
