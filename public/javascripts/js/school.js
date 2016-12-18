//addRole.scala.html js file addRolePermissions.scala.html
$(document).ready(function(){
$('#addPeriodBtn').click(function(e){
    
        $('#time-table tr').append($("<td>"));
        $('#time-table thead tr>td:last').html("Period");
        $('#time-table tbody tr').each(function(){$(this).children('td:last').append($('<input type="checkbox">'))});
    
});

   var id = 0;
      jQuery("#addrow").click(function() {
        id++;           
        var row = jQuery('.samplerow tr').clone(true);
        row.find("input:text").val("");
        row.attr('id',id); 
        row.appendTo('#dynamicTable1');        
        return false;
    });        
        
  $('.remove').on("click", function() {
  $(this).parents("tr").remove();
});

$('.ui.dropdown').dropdown();

$('.disableRoleBtn').click(function(e){
   $(this).closest('tr').remove();
});

$('.disableGroupBtn').click(function(e){
   $(this).closest('tr').remove();
});

 $('.addNewRoleBtn').click(function(){
     $('#addRoleModal').modal('show');
    });

 /*$('#permissionBtn').click(function(e){
	var permissionvalue=$(this).closest('tr').children('td.permissionlistrow').text();
	//var permissionvalue=$(".permissionlistrow",$(this).parent().parent()).val();
	alert(permissionvalue);
	e.preventDefault();
});*/



});

    function addSection(index) {
    var textsection = document.getElementById("section");
    var sectionList = document.getElementById("sectionList");
    var sectionName = document.getElementById("sectionname");
    
    var option = document.createElement("option");
    var otext = document.createElement("text");
    
    sectionName.text = textsection.value;
    sectionName.value = textsection.value;
    
       option.text = textsection.value;
    option.value = textsection.value;
    
    try {
        sectionList.add(option, null); //Standard 
        
    }catch(error) {
        sectionList.add(option); // IE only
    }
    
    
    
}


function addPermission(index){
    
       var permissionvalue=$(this).closest('tr').children('td .permissionlistrow[index]').text();
       //var permissionvalue=$(".permissionlistrow[index]",$(this).parent().parent()).val();
        //var permission = $('.permissonlistform').form('get value', 'permissionlistrow[index]');
    
    alert(permissionvalue); 

    /*var permissions = $('.Permissions .permissiondropdown').dropdown('get value');
    permissions.push(permission);
    
    $('.Permissions .dropdown select').append('<option value="'+permission+'" selected="">'+permission+'</option>');
    $('.Permissions .dropdown .menu').append('<div class="item" data-value="'+permission+'">'+permission+'</div>');
    
    $('.Permissions .dropdown').dropdown('set value', permissions);
    $('.Permissions .dropdown').dropdown('set selected', permission);
    $('.Permissions .dropdown').dropdown();
    
    $('.permissiondropdown.dropdown').dropdown();*/

    
	
}






