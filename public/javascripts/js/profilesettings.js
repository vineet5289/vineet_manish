// For Profile Page Tab and Menu
$(document).ready(function(){
	
	 $('.ui .item').on('click', function() {
        $('.ui .item').removeClass('active');
        $(this).addClass('active');
    });

	  $('.profile.menu .item').tab({history:false});

	  $('.ui.dropdown').dropdown();

	  $("#fileUpload").on("click", function() {
   $('#new-file-upload').click();
});
	  /* below two codes are foe toggle in kitchen sink and donot close if clicked enywhere*/
  var menu = $('#kitchensinksidebar').sidebar({
    context: $('#ctx'),
    transition: 'push',
    dimPage:false,
    closable:false

  });
  $('#menuToggle').click(function(e) {

    menu.sidebar('toggle');
    e.preventDefault();
  }); 


   /*$( '#kitchensinksidebar')
        .sidebar({
            transition:'push',
            context:$('#ctx'),
            dimPage:false
        })
        .sidebar('attach events', '#menuToggle')
        .sidebar('attach events', '#menuToggle', 'hide')
    ; */


	});