// For Profile Page Tab and Menu
$(document).ready(function(){
	
	 $('.ui .item').on('click', function() {
        $('.ui .item').removeClass('active');
        $(this).addClass('active');
    });

	  $('.profile.menu .item').tab({history:false});

	  $('.ui.dropdown').dropdown();
});