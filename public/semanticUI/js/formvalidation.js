$(function(){
$('form').validate({
		// Validation rules
		rules: {
			email: {
				email: true,
				required: true
			}
		},
		// Error message
		messages: {
			name: {
				required: 'Please type in your name',
				minlength: 'At least six letters'
			},
			email: 'E-mail format is incorrect',
			telephone: 'Please enter the phone',
			agree: 'Please agree to the treaty',
			textarea: {
				required: 'Enter Profile',
				minlength: 'Content can not be less than 10 characters',
				maxlength: 'greater than 20'
			},
			select: 'please choose'
		},
		// Error element tag
		errorElement: 'span',
		// Display an error when executing
		showErrors: function(errorMap, errorList) {
			this.defaultShowErrors();
			$(errorList).each(function(index, err){
				$(err.element).parent('.ui.input').addClass('right labeled').children('span').addClass('ui basic label red error-label');
				if ($(err.element).attr('type') == 'checkbox') {
					$(err.element).siblings('span').css({'float': 'right', 'margin-left': '10px', 'color': '#db2828', 'font-weight': '300'}).siblings('label').css({'float': 'left'});
				}
				if ($(err.element).prop("tagName") == 'TEXTAREA' || $(err.element).prop("tagName") == 'SELECT') {
					$(err.element).siblings('span').addClass('ui error message').css({'display': 'block'}).show();
				}
			});
		},
		// After verifying the successful implementation
		success: function(obj){
			$(obj).parent().removeClass('right labeled');
			$(obj).remove();
		},
		// When submitting trigger
		submitHandler: function(form) {
			//alert('submit');
			form.submit();
		}
	});  

});

