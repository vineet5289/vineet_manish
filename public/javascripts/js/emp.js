// addEmployee.scala.html
$(document).ready(function(){
$("#addEmpCode").checkbox({
        "onChecked": function() {
            $("#empCodeInput").addClass("disabled");
           

        },
        "onUnchecked": function() {
            
            $("#empCodeInput").removeClass("disabled");
        }
    });	

 $("#delete-employee").on('click',function(){
 	$('#delete-confirm').modal('show');
 });
});
