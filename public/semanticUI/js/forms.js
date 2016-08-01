$(function () {
    
    var validationObj = {
        userName:{
                identifier:'userName',
                rules:[
                {
                  type:'empty',
                  prompt:'Please enter UserName'
                }]
        },
        
        
        password: {
                     identifier  : 'password',
                      rules: [
                      {
                          type: 'empty',
                          prompt: 'Please enter the password'
                      }]
                       
          },
                        
         confirmPassword: {
                        identifier  : 'confirmPassword',
                        rules: [
                         {
                               type   : 'match[password]',
                               prompt : 'Please enter the same value in both fields'
                          }   ]
             },
        
        
       
		schoolName: {
                identifier: 'schoolName',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter School Name'
                }]
            },
	     schoolRegistrationId: {
                identifier: 'schoolRegistrationId',
                optional:true,
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter your School Registration Id'
                }]
            },
		 schoolAddressLine1: {
                identifier: 'schoolAddressLine1',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter your School Address'
                }]
            },
            schoolAddressLine2: {
                identifier: 'schoolAddressLine2',
                optional:true,
                rules: [{
                    
                    type: 'empty',
                    prompt: 'Please enter your School Address'
                }]
            },
             city: {
                identifier: 'city',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter City'
                }]
            },
            
            state: {
                identifier: 'state',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter State'
                }]
            },
            
             country: {
                identifier: 'country',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter City'
                }]
            },
             pincode: {
                identifier: 'pincode',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter pincode'
                }]
            },
            
		 contactPersonName: {
                identifier: 'contactPersonName',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter your Name'
                }]
            },
            
            schoolEmail: {
                identifier: 'schoolEmail',
                rules: [{
                    type: 'email',
                    prompt: 'email Id Format is not correct'
                },
                {
                    type: 'empty',
                    prompt: 'Please enter School email Id'
                }]
            },
		schoolMobileNumber: {
                identifier: 'schoolMobileNumber',
                rules: [{
                    type:'exactLength[10]',
                    prompt: 'Mobile Number Shoud be of 10 Length'
                },
                {
                    type:'empty',
                    prompt: 'Please Enter proper Mobile Number'
                }]
                 
            },
		query: {
                identifier: 'query',
                optional:true,
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter your Query'
                }]
            }
	};
    

	// New School Request Form

	   $('#newSchoolRegistrationForm').form(validationObj, {
		on:'change',
		inline:false
	     
		
	});
	
	
	// For Main login form
	$('#loginform').form(validationObj, {
		inline: false,
		
	
	}).submit(function(event){
	  event.preventDefault();
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
    
    //OTP modal submit
    $('#completeSchoolRegistrationOTPForm').form(validationObj, {
        on:'blur',
		inline:true
		
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
   
   
  