//testpage  javascript file
$(function(){
  	/*Accordion*/
  	$('.accordion').accordion({
	    selector: {
	      trigger: '.title .icon'
	    }
  	});

  	/*Checkbox*/
  	$('.test.checkbox').checkbox('attach events', '.toggle.button');
	$('.test.checkbox').checkbox('attach events', '.check.button', 'check');
	$('.test.checkbox').checkbox('attach events', '.uncheck.button', 'uncheck');

	/*Dimmer*/
  	$('.dimmerContainer .image').dimmer({
    	on: 'hover'
  	});

  	/*DropDown*/
	$('.default .ui.dropdown').dropdown();

	$('.multipleSelection .ui.dropdown').dropdown({
		maxSelections: 4
	});

	/*Embed*/
	$('.ui.embed').embed();


	/*Menu*/
	$('.menu .item').click(function(){
		$('.item').removeClass('active');
		$(this).addClass('active');
	});

	/*Modal*/
	$('.test.modal').modal('attach events', '.test.button', 'show');

	$('.test2.basic.modal').modal('attach events', '.test2.button', 'show');

	$('.test3.fullscreen.modal').modal('attach events', '.test3.button', 'show');

	$('.test4.small.modal').modal('attach events', '.test4.button', 'show');

	$('.test5.large.modal').modal('attach events', '.test5.button', 'show');

	$('.coupled.modal').modal(
	{
		allowMultiple: false
	});

	$('.test7.first.modal').modal('attach events', '.test7.button', 'show');

	$('.test8.second.modal').modal('attach events', '.test7.first.modal .button', 'show');

	$('.test9.fullscreen.modal').modal({
		closable  : false,
	    onDeny    : function(){
	      window.alert('Wait not yet!');
	      return false;
	    },
	    onApprove : function() {
	      window.alert('Approved!');
	    }
	}).modal('attach events', '.test9.button', 'show');

	$('.test10.large.modal').modal({
		inverted: true
		//blurring: true
	}).modal('attach events', '.test10.button', 'show');

	$('.modalTransition.selection.dropdown').dropdown({
	    onChange: function(value) {
		$('.test11.modal').modal('setting', 'transition', value).modal('show');
		}
  	});

  	/*Nag*/
	$('.showNag').click(function(){
		$('.cookie.nag').nag('show');
	});

	$('.clearNag').click(function(){
		$('.cookie.nag').nag('clear');
	});

	$('.cookie.nag').nag({
		key      : 'accepts-cookies',
		value    : true
	});

	/*Popup*/
	$('.popupMenu .browseMenu').popup({
		inline   : true,
		hoverable: true,
		position : 'bottom left',
		delay: {
		  show: 300,
		  hide: 800
		}
	});

	$('.custom.button').popup({
	    popup : $('.custom.popup'),
	    on    : 'click'
  	});

	$('.popupInput').popup({
    	on: 'focus'
  	});

  	$('.test12.button').popup({
		position : 'right center',
		target   : '.test12.image',
		title    : 'My favorite dog',
		content  : 'My favorite dog would like other dogs as much as themselves'
	});

	$('.inline.icon').popup({
		inline: true
	});

	$('.positioningPopup i').popup({
		hoverable: true
	});

	$('.offsetPopup i').popup({
		hoverable: true
	});

	/*Progress*/
	$('.ui.progress').progress({
		total: 3
	});

	$('.ui.increment').click(function(){
		$(this).parent().parent().parent().find('.ui.progress').progress('increment');
	});

	$('.ui.decrement').click(function(){
		$(this).parent().parent().parent().find('.ui.progress').progress('decrement');
	});

	$('#uploadProgress').progress({
		label: 'ratio',
	 	text: {
	      active  : 'Adding {value} of {total} Files',
	      success : '{total} Files Uploaded!',
	      ratio   : '{value} / {total}',
	    }
	});

	/*Ratings*/
	$('.ratingSegment .ui.rating').rating('setting', 'clearable', true);

	/*Search*/	
	$('.ui.search').search({
		type          : 'category',
		minCharacters : 3,
		apiSettings   : {
		  onResponse: function(githubResponse) {
		    var
		      response = {
		        results : {}
		      }
		    ;
		    // translate github api response to work with search
		    $.each(githubResponse.items, function(index, item) {
		      var
		        language   = item.language || 'Unknown',
		        maxResults = 8
		      ;
		      if(index >= maxResults) {
		        return false;
		      }
		      // create new language category
		      if(response.results[language] === undefined) {
		        response.results[language] = {
		          name    : language,
		          results : []
		        };
		      }
		      // add result to category
		      response.results[language].results.push({
		        title       : item.name,
		        description : item.description,
		        url         : item.html_url
		      });
		    });
		    return response;
		  },
		  url: 'https://api.github.com/search/repositories?q={query}'
		}
	});

	$('.localSearch .ui.search input').api({
	    action       : 'search',
	    stateContext : '.ui.input'
  	});

  	var content = [
		{ title: 'Andorra' },
		{ title: 'United Arab Emirates' },
		{ title: 'Afghanistan' },
		{ title: 'Antigua' },
		{ title: 'Anguilla' },
		{ title: 'Albania' },
		{ title: 'Armenia' },
		{ title: 'Netherlands Antilles' },
		{ title: 'Angola' },
		{ title: 'Argentina' },
		{ title: 'American Samoa' },
		{ title: 'Austria' },
		{ title: 'Australia' },
		{ title: 'Aruba' },
		{ title: 'Aland Islands' },
		{ title: 'Azerbaijan' },
		{ title: 'Bosnia' },
		{ title: 'Barbados' },
		{ title: 'Bangladesh' },
		{ title: 'Belgium' },
		{ title: 'Burkina Faso' },
		{ title: 'Bulgaria' },
		{ title: 'Bahrain' },
		{ title: 'Burundi' }
	];

	/*Shape*/
	$('.shapeSegment .ui.button[data-shape=auto]').click(function(){
		$('.shapeSegment .ui.button[data-shape]').removeClass('active');
		$(this).addClass('active');
		$('.shape').addClass('auto').removeClass('square').removeClass('irregular');
	});

	$('.shapeSegment .ui.button[data-shape=square]').click(function(){
		$('.shapeSegment .ui.button[data-shape]').removeClass('active');
		$(this).addClass('active');
		$('.shape').addClass('square').removeClass('auto').removeClass('irregular');
	});

	$('.shapeSegment .ui.button[data-shape=irregular]').click(function(){
		$('.shapeSegment .ui.button[data-shape]').removeClass('active');
		$(this).addClass('active');
		$('.shape').addClass('irregular').removeClass('auto').removeClass('square');
	});

	$('.shapeSegment .ui.button[data-direction=left]').click(function(){
		$('.shape').shape('flip left');
	});

	$('.shapeSegment .ui.button[data-direction=up]').click(function(){
		$('.shape').shape('flip up');
	});

	$('.shapeSegment .ui.button[data-direction=down]').click(function(){
		$('.shape').shape('flip down');
	});

	$('.shapeSegment .ui.button[data-direction=right]').click(function(){
		$('.shape').shape('flip right');
	});

	$('.shapeSegment .ui.button[data-direction=over]').click(function(){
		$('.shape').shape('flip over');
	});

	$('.shapeSegment .ui.button[data-direction=back]').click(function(){
		$('.shape').shape('flip back');
	});

	/*Sidebar*/
	$('#toc').sidebar({
	  dimPage          : true,
	  transition       : 'overlay',
	  mobileTransition : 'uncover'
	});

	$('#toc').sidebar('attach events', '.launch.item');

	$('.sidebarSegment .ui.sidebar').sidebar({
	    context: $('.sidebarSegment .bottom.segment'),
	    dimPage: true,
	    transition: 'overlay'
	}).sidebar('attach events', '.sidebarSegment .demo .first.item');

	$('.sidebarSegment .ui.sidebar').sidebar({
	    context: $('.sidebarSegment .bottom.segment'),
	    dimPage: true,
	    transition: 'push'
	}).sidebar('attach events', '.sidebarSegment .demo .second.item');

	$('.sidebarSegment .ui.sidebar').sidebar({
	    context: $('.sidebarSegment .bottom.segment'),
	    dimPage: true,
	    transition: 'scale down'
	}).sidebar('attach events', '.sidebarSegment .demo .third.item');

	$('.sidebarSegment .ui.sidebar').sidebar({
	    context: $('.sidebarSegment .bottom.segment'),
	    dimPage: true,
	    transition: 'uncover'
	}).sidebar('attach events', '.sidebarSegment .demo .fourth.item');

	/*Sticky*/
	$('.stickySegment .ui.sticky').sticky();

	$('.transitionSegment .ui.sticky').sticky({
		context: '.transitionSegment'
	});

	/*Tab*/
	$('#firstColumn .menu .item').tab({
		context: $('#firstColumn')
	});
	$('#secondColumn .menu .item').tab({
		context: $('#secondColumn')
	});
	
	/*Transition*/

	$('.scale').click(function(){
		$('.autumn.leaf').transition('scale');
	});

	$('.fade').click(function(){
		$('.autumn.leaf').transition('fade');
	});

	$('.fadeUp').click(function(){
		$('.autumn.leaf').transition('fade up');
	});

	$('.fadeDown').click(function(){
		$('.autumn.leaf').transition('fade down');
	});

	$('.fadeLeft').click(function(){
		$('.autumn.leaf').transition('fade left');
	});

	$('.fadeRight').click(function(){
		$('.autumn.leaf').transition('fade right');
	});

	$('.horizontalFlip').click(function(){
		$('.autumn.leaf').transition('horizontal flip');
	});

	$('.verticalFlip').click(function(){
		$('.autumn.leaf').transition('vertical flip');
	});

	$('.drop').click(function(){
		$('.autumn.leaf').transition('drop');
	});

	$('.flyLeft').click(function(){
		$('.autumn.leaf').transition('fly left');
	});

	$('.flyRight').click(function(){
		$('.autumn.leaf').transition('fly right');
	});

	$('.flyUp').click(function(){
		$('.autumn.leaf').transition('fly up');
	});

	$('.flyDown').click(function(){
		$('.autumn.leaf').transition('fly down');
	});

	$('.browse').click(function(){
		$('.autumn.leaf').transition('browse');
	});

	$('.browseRight').click(function(){
		$('.autumn.leaf').transition('browse right');
	});

	$('.swingLeft').click(function(){
		$('.autumn.leaf').transition('swing left');
	});

	$('.swingRight').click(function(){
		$('.autumn.leaf').transition('swing right');
	});

	$('.swingUp').click(function(){
		$('.autumn.leaf').transition('swing up');
	});

	$('.swingDown').click(function(){
		$('.autumn.leaf').transition('swing down');
	});

	$('.slideUp').click(function(){
		$('.autumn.leaf').transition('slide up');
	});

	$('.slideDown').click(function(){
		$('.autumn.leaf').transition('slide down');
	});

	$('.slideLeft').click(function(){
		$('.autumn.leaf').transition('slide left');
	});

	$('.slideRight').click(function(){
		$('.autumn.leaf').transition('slide right');
	});

	$('.jiggle').click(function(){
		$('.autumn.leaf').transition('jiggle');
	});

	$('.flash').click(function(){
		$('.autumn.leaf').transition('flash');
	});

	$('.shake').click(function(){
		$('.autumn.leaf').transition('shake');
	});

	$('.pulse').click(function(){
		$('.autumn.leaf').transition('pulse');
	});

	$('.tada').click(function(){
		$('.autumn.leaf').transition('tada');
	});

	$('.bounce').click(function(){
		$('.autumn.leaf').transition('bounce');
	});

});



//test2 javascript file

    /*! DataTables Semantic integration
 */

/**
 * DataTables integration for Semantic UI. This requiresSemantic UI and
 * DataTables 1.10 or newer.
 *
 * This file sets the defaults and adds options to DataTables to style its
 * controls using Bootstrap. See http://datatables.net/manual/styling/
 * for further information.
 */
(function(window, document, undefined){

var factory = function( $, DataTable ) {
"use strict";

/* Set the defaults for DataTables initialisation */
$.extend( true, DataTable.defaults, {
	dom:
		"<'left aligned eight wide column'l><'right aligned eight wide column'f>" +
		"<'sixteen wide column'tr>" +
		"<'left aligned four wide column'i><'right aligned twelve wide column'p>",
	renderer: 'semantic'
} );

$.extend( DataTable.ext.pager, {
		full_numbers_icon: DataTable.ext.pager.full_numbers
});

/* Default class modification */
$.extend( DataTable.ext.classes, {
	sWrapper:      "ui grid dataTables_wrapper ",
	sFilterInput:  "",
	sLengthSelect: ""
} );

/* Bootstrap paging button renderer */
DataTable.ext.renderer.pageButton.semantic = function ( settings, host, idx, buttons, page, pages ) {
	var api     = new DataTable.Api( settings );
	var classes = settings.oClasses;
	var lang    = settings.oLanguage.oPaginate;
	var btnDisplay, btnClass, btnIcon, counter=0;
    var addIcons = (( !api.init().pagingType ? '' : api.init().pagingType.toLowerCase() ).indexOf('icon') !== -1 );
	
	var attach = function( container, buttons ) {
		var i, ien, node, button;
		var clickHandler = function ( e ) {
			e.preventDefault();
			if ( !$(e.currentTarget).hasClass('disabled') ) {
				api.page( e.data.action ).draw( 'page' );
			}
		};

		for ( i=0, ien=buttons.length ; i<ien ; i++ ) {
			button = buttons[i];

			if ( $.isArray( button ) ) {
				attach( container, button );
			}
			else {
				btnDisplay = '';
				btnClass = '';
                btnIcon = '';
				switch ( button ) {
					case 'ellipsis':
					    btnDisplay = ( addIcons  ? '<i class="mini ellipsis horizontal icon"></i>' : '&hellip;');
						btnClass = 'disabled';
						break;

					case 'first':
					    btnIcon = ( addIcons ? '<i class="angle single left icon"></i>' : '');
						btnDisplay = btnIcon + lang.sFirst;
						btnClass = button + (page > 0 ?
							'' : ' disabled');
						break;

					case 'previous':
					    btnIcon = ( addIcons ? '<i class="angle double left icon"></i>' : '');
						btnDisplay = btnIcon + lang.sPrevious;
						btnClass = button + (page > 0 ?
							'' : ' disabled');
						break;

					case 'next':
                        btnIcon = ( addIcons ? '<i class="angle double right icon"></i>' : '');
						btnDisplay = lang.sNext + btnIcon;
						btnClass = button + (page < pages-1 ?
							'' : ' disabled');
						break;

					case 'last':
                        btnIcon = ( addIcons ? '<i class="angle single right icon"></i>' : '');
						btnDisplay = lang.sLast + btnIcon;
						btnClass = button + (page < pages-1 ?
							'' : ' disabled');
						break;

					default:
						btnDisplay = button + 1;
						btnClass = page === button ?
							'active' : '';
						break;
				}
                
			
				
				if ( btnDisplay ) {
					node = $('<a>', {
							'class': classes.sPageButton+' '+btnClass+' item ',
							'id': idx === 0 && typeof button === 'string' ?
								settings.sTableId +'_'+ button :
								null
						} ).html( btnDisplay ).appendTo( container );

					settings.oApi._fnBindAction(
						node, {action: button}, clickHandler
					);

					counter++;
				}
			}
		}
	};

    
				 
	// IE9 throws an 'unknown error' if document.activeElement is used
	// inside an iframe or frame. 
	var activeEl;

	try {
		// Because this approach is destroying and recreating the paging
		// elements, focus is lost on the select button which is bad for
		// accessibility. So we want to restore focus once the draw has
		// completed
		activeEl = $(host).find(document.activeElement).data('dt-idx');
	}
	catch (e) {}

	attach(
		$(host).empty().html('<div class="ui stackable small pagination menu"/>').children('div'),
		buttons
	);

	if ( activeEl ) {
		$(host).find( '[data-dt-idx='+activeEl+']' ).focus();
	}
};

}; // /factory


// Define as an AMD module if possible
if ( typeof define === 'function' && define.amd ) {
	define( ['jquery', 'datatables'], factory );
}
else if ( typeof exports === 'object' ) {
    // Node/CommonJS
    factory( require('jquery'), require('datatables') );
}
else if ( jQuery ) {
	// Otherwise simply initialise as normal, stopping multiple evaluation
	factory( jQuery, jQuery.fn.dataTable );
}


})(window, document);


$(document).ready(function() {
    

    
	 var dtable = $('#examplesearch').DataTable({
     "pagingType": "full_numbers_icon",
	order: [ 1, 'desc' ],
	

	 });

//test3 javascript file
$('.user')
  .popup({
    inline   : true,
    hoverable: true,
    position : 'top right',
    delay: {
      show: 100,
      hide: 100
    }
  })
;


$('.ui.dropdown')
  .dropdown()
;


$('.add-button,.agenda-item').click(function(){
  $('.activity-modal').modal('show');
});

$('.list .master.checkbox')
  .checkbox({
    // check all children
    onChecked: function() {
      var
        $childCheckbox  = $(this).closest('.checkbox').siblings('.list').find('.checkbox')
      ;
      $childCheckbox.checkbox('check');
    },
    // uncheck all children
    onUnchecked: function() {
      var
        $childCheckbox  = $(this).closest('.checkbox').siblings('.list').find('.checkbox')
      ;
      $childCheckbox.checkbox('uncheck');
    }
  })
;


$('.show-all').click(function(){
  $("#activity-filter").dropdown('clear');
});

$(".save-button").click(function(){
  $('.save-modal').modal('show');
  var URL='http://codepen.io/voltron2112/debug/JdQMWa';
  setTimeout(function(){ window.location = URL; }, 1500 );  
});



// for message sending buttons
$("#msgSendAll").click(function(){
  $("#sendbutton").append("<input type='hidden' name='sendToAll' value='sendAll'/>");
   
   alert("btn1");
  });
  $("#msgSendStudent").click(function(){
   alert("send To selected Student");
  });
  $("#msgSendTeachers").click(function(){
   alert("send To selected teachers");
  });



    
});

//---------------------------------------------------------------------------

 $(document).ready(function() {
    var text_max = 200;
    $('#textarea_feedback').html(text_max + ' characters remaining');

    $('#textarea').keyup(function() {
        var text_length = $('#textarea').val().length;
        var text_remaining = text_max - text_length;

        $('#textarea_feedback').html(text_remaining + ' characters remaining');
    });
	
	$("#check").on("click", function () {

    var chk = document.getElementById('check').checked;

    if (chk) {
        var arr = document.getElementsByTagName("input");
        for (var i in arr) {
            if (arr[i].name == 'check') arr[i].checked = true;
        }
    } else {
        var arr = document.getElementsByTagName("input");
        for (var i in arr) {
            if (arr[i].name == 'check') arr[i].checked = false;
        }
    }
});
});

(function( $ ) {
    
    $.fn.checked = function(value) {
        
        if(value === true || value === false) {
            // Set the value of the checkbox
            $(this).each(function(){ this.checked = value; });
            
        } else if(value === undefined || value === 'toggle') {
            
            // Toggle the checkbox
            $(this).each(function(){ this.checked = !this.checked; });
        }
        
    };
    
})( jQuery );

$(function() {
    
    $('#check').click(function() {
        $(':checkbox').checked(true);
    });
    
    $('#uncheck').click(function() {
        $(':checkbox').checked(false);
    });
    
    $('#toggle').click(function() {
        $(':checkbox').checked('toggle');
    });
    
    
});
