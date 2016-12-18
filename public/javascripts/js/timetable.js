$(document).ready(function(e){
	e.preventDefault();
	$('.ui.dropdown').dropdown();
 
 var id1 = 0;
      jQuery("#addNewPeriod").click(function() {
        id1++;           
        var row = jQuery('#clonetable tr').clone(true);
        
        row.attr('id',id1); 
        row.appendTo('#time-table');        
        return false;
    });        
        
  $('#removePeriod').on("click", function() {
  $(this).parents("tr").remove();
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



});

/*
Timing diffrence javacsript
  var start= "11:10 AM";
var end = "12:40 AM";

var stt = new Date("November 13, 2013 " + start);
//stt = stt.getTime();
//alert(stt);
var endt = new Date("November 13, 2013 " + end);
//endt = endt.getTime();

var diff = endt.getTime() - stt.getTime();

var hours = Math.floor(diff / (1000 * 60 * 60));
diff -= hours * (1000 * 60 * 60);

var mins = Math.floor(diff / (1000 * 60));
diff -= mins * (1000 * 60);
alert( hours + " hours : " + mins + " minutes : " );

if(start.split(" ")[1] === "PM"){
  start = convertTo24(start);
  alert(start);
}

if(end.split(" ")[1] === "PM"){
  end = convertTo24(end);
   alert(end);
}
var h1={};
var h2={};
starts = start.split(":").map(function(c, i, a){
  a[i] = parseInt(c);
  h1[i]=a[i];
  alert(a[i]);
  //alert(a);
});

ends = end.split(":").map(function(c, i, a){
  a[i] = parseInt(c);
  h2[i]=a[i];
  alert(a[i]);
 
})

alert(h1[0]+" "+h2[0]);
alert(h1[1]+" "+h2[1]);
if(h2[0]<h1[0]){
alert("invalid time");
}else if(h1[0]==h2[0] || h2[0]>h1[0]){
alert("same time or correct time");
}
alert(start);


function convertTo24(time){
  return (parseInt(time) + 12) + time.match(/\:\d+/)[0];
}
*/

/* combining value in to one

 function addNewListItem(){
var htmlSelect=document.getElementById('list');
var value1 = document.getElementById('Input1').value;
var value2 = document.getElementById('Input2').value;
var optionValue = value1 + " " + value2;
var optionDisplaytext = value1 + " " + value2;

var selectBoxOption = document.createElement("option");
selectBoxOption.value = optionValue.value;
selectBoxOption.text = optionDisplaytext.value;
htmlSelect.add(selectBoxOption, null);
alert("Option has been added successfully");
return true;







https://jsfiddle.net/osbL3fns/1/ for error on field take a look
*/