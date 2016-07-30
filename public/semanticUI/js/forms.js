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
			identifier : 'password',
			rules: [
			{
				type   : 'empty',
				prompt : 'Please enter a password'
			}
			
			]
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
                rules: [{
                    optional:true,
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
            
		 contactPersonName: {
                identifier: 'contactPersonName',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter your Name'
                }]
            },
		schoolMobileNumber: {
                identifier: 'schoolMobileNumber',
                rules: [{
                    type: 'minLength[10]',
                    prompt: 'Please enter your Contact Number'
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
		inline: false,
		//onSuccess: function(){
			//$('#newSchoolRequestPost').modal('show');
		//}
	});
	
	// For Main login form
	$('#loginform').form(validationObj, {
		inline: false,
	
	});
	
	// For adding new Class
	
	$('#addNewClass').click(function(){
    $('#addSchool_modal').modal('show');
    });
    
    //After sending OTP modal comes
    
    $('#completeSchoolRegistration').click(function(){
    $('#OTP-modal').modal('show');
    
    });  
    
    $('#RegisterSchoolButton').click(function(){
     $('#RegisterSchoolModal').modal('show');
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
   
   
  