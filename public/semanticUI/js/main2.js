$(document).ready(function(){

$('#newSchoolRegistrationForm').form({
        inline:true,
               
        fields: {
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
		}
        
});

});