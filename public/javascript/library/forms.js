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
        
        
       
        schoolName: {
                identifier: 'schoolName',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER SCHOOL NAME'
                }]
            },
         schoolRegistrationId: {
                identifier: 'schoolRegistrationId',
                optional:true,
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER YOUR SCHOOL REGISTRATION ID'
                }]
            },
         schoolAddressLine1: {
                identifier: 'schoolAddressLine1',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER YOUR SCHOOL ADDRESS'
                }]
            },
            schoolAddressLine2: {
                identifier: 'schoolAddressLine2',
                optional:true,
                rules: [{
                    
                    type: 'empty',
                    prompt: 'PLEASE ENTER YOUR SCHOOL ADDRESS'
                }]
            },
             city: {
                identifier: 'city',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER CITY'
                }]
            },
            
            state: {
                identifier: 'state',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE SELECT STATE'
                }]
            },
            
             country: {
                identifier: 'country',
                rules: [{
                    type: 'empty',
                    prompt: 'PLEASE ENTER COUNTRY NAME'
                }]
            },
             pincode: {
                identifier: 'pincode',
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
            
            schoolEmail: {
                identifier: 'schoolEmail',
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
        schoolMobileNumber: {
                identifier: 'schoolMobileNumber',
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
    
});












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
   
  