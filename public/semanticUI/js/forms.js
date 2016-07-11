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
                    prompt: 'Please enter a value'
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
			 schoolAddress: {
                identifier: 'schoolAddress',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter your School Address'
                }]
            },
			 principalName: {
                identifier: 'principalName',
                rules: [{
                    type: 'empty',
                    prompt: 'Please enter your principal Name'
                }]
            },
			contact: {
                identifier: 'contact',
                rules: [{
                    type: 'minLength[8]',
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
		onSuccess: function(){
			$('#newSchoolRequestPost').modal('show');
		}
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
    
    
});
