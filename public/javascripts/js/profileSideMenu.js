$(document).ready(function(){
$('.js-searchinput')
  .popup({
    on: 'focus',
    inline: true,
    position: 'right center'
  });


$('.card .header.task').click(function() {
  $(".task-modal").modal('setting', 'transition', 'fade up').modal('setting', 'autofocus', false).modal("show");
});


$('.card .header.gemba').click(function() {
  $(".gemba-modal").modal('setting', 'transition', 'fade up').modal('setting', 'autofocus', false).modal("show");
});

//top top menu item selecteion then active remove very important

$('.ui .menu .item').on('click', function() {
   $('.ui .menu .item').removeClass('active');
   $(this).addClass('active');
});


 // ths clear code works <div class="ui clear button">
$('.ui.clear.button').on('click', function(){
  $('.ui.form').form('clear');
});

 var $sidebar = $('#sideBarMenuProfile');
  var $sidebarTrigger = $('#togglesidemenu');

$sidebarTrigger.click(function(){
$sidebar.sidebar().sidebar('toggle').sidebar('attach events', '#close', 'hide');
  });

  $('.ui.accordion')
      .accordion()
      .accordion({exclusive:true})
    ;


 $('.ui .dropdown').dropdown();

//for pop in side http://jsfiddle.net/L1mc8prw/
 $('#btnAdmin').popup({
    movePopup: false,
    
    popup: '#btnAdminPopup',
    inline: true,
    hoverable: true,
    position: 'right center',
    delay: {
        show: 300,
        hide: 800
    }
});

 $('#sb_notification_trigger').transition('set looping').transition('bounce','2000ms');

 $('sb_notification_trigger').click().transition('remove looping');


//window resfresh on screen changes
//$(window).resize(function() {
//window.location.href = window.location.href;
//});

$('#').sidebar({
  context: $('.context'),
  closable:true,
  transition:'overlay',
  dimPage:true
}).sidebar('attach events', '.item_one');

//this is for planet side bar menu ..gud looking on main.scala.html

$('.ui.popup')
.popup()
;

$('#shift').dropdown({transition:'drop',on:'hover'}).dropdown('refresh');

$('.ui.dropdown')
.dropdown()
;



$('.sidebar').first()
.sidebar({
  context: $('.context'),
  closable:false,
  dimPage:false
}).sidebar('attach events', '.toggle.button') 
.sidebar('setting', 'transition', 'push');



$('.message .close').on('click', function() {
    $(this).closest('.message').fadeOut();
});


$('.context.example .ui.sidebar')
  .sidebar({
    context: $('.context.example .bottom.segment'),
    transition: 'push'
  })
  .sidebar('attach events', '.context.example .top.attached.menu .item')
;

$('#responsivemobilesidebar').sidebar({
    context: $('.pushable.segment'),
    transition: 'overlay'
}).sidebar('attach events', '#hamburger-link');

});