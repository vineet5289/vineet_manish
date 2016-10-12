$(function(){
	
   
   var emails=[];




    
$(".permissionBtn > button").on("click", function(e){
     //$(this).addClass('disabled');
     e.preventDefault();
    var id = $(this).attr("data-permission-id");
   
    var alreadyInCart = false;
    for(var i = 0; i < emails.length; i++){
       
        if(emails[i].id === id ){
            alreadyInCart = true;
            cart[i].qty = cart[i].qty + 1;
        }
    }
    
    if(alreadyInCart === false){
        addToCart(
            $(this).attr("data-permission-id"),
            $(this).attr("data-permission-name"),
            1
        );
    }
  
});



function addToCart(itemID,itemName,qty){
    //cart.push({name: itemName, price: itemPrice, id: itemID, qty: 1});
    

     
    emails.push({id:itemID});
    
   
	$('.permissionForm .dropdown .menu').append('<div class="item" data-value="'+itemID+'">'+itemName+'</div>');
    $('.permissionForm .dropdown select').append('<option value="'+itemID+'" selected="">'+itemName+'</option>');
    $('.permissionForm .dropdown').dropdown({allowAdditions:true});
    
    $('.permissionForm .dropdown').dropdown({onRemove:function(val){
        var str="btn-".concat(val);
        $().removeClass('disabled');
    }});
}



}); 