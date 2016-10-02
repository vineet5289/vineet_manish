//addRole.scala.html js file
$(document).ready(function(){
$('.ui.dropdown').dropdown();

$('.disableRoleBtn').click(function(e){
   $(this).closest('tr').remove()
})

 $('.addNewRoleBtn').click(function(){
     $('#addRoleModal').modal('show');
    });

});






