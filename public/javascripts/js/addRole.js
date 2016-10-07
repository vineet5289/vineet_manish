$(function(){
	var cart = [];
    var j=0;
   var emails=[];

//updateCart();
$("$permissionBtn").click(function(e){
    e.preventDefault();
    for(var i=0;i < emails.length ;i++){

    }
});

    
$(".permissionBtn > button").on("click", function(e){
     e.preventDefault();
    var id = $(this).attr("data-permission-index");
   
    var alreadyInCart = false;
    for(var i = 0; i < emails.length; i++){
        alert(emails[i].id + " " + id);
        if(emails[i].id === id ){
            alreadyInCart = true;
            cart[i].qty = cart[i].qty + 1;
        }
    }
    if(alreadyInCart === false){
        addToCart(
            $(this).attr("data-permission-id"),
            $(this).attr("data-permission-index"),
            $(this).attr("data-permission-name"),
            1
        );
    }
    updateCart();
    //console.log(cart);
});

/*$(".JS-view").on("click", function(){
    listCart()
});

function listCart(){
    var table = $("<table>"+"</table>");
    $(".view-cart").html(table);
    table.html(
      "<tr>"+
        "<td>Product</td>"+
        "<td>Price</td>"+
        "<td>QTY</td>"+
        "<td>Total</td>"+
      "</tr>"
    );
    for(var i = 0; i < cart.length; i++){
        table.append(
          "<tr>"+
            "<td>"+cart[i].name+"</td>"+
            "<td>"+cart[i].price+"</td>"+
            "<td>"+cart[i].qty+"</td>"+
            "<td>"+(cart[i].price*cart[i].qty)+"</td>"+
          "</tr>"
        );



    }
}*/

function addToCart(itemID,itemName,itemPrice,qty){
    cart.push({name: itemName, price: itemPrice, id: itemID, qty: 1});
    

     //emails = $('.permissionForm .dropdown').dropdown('get value');
    emails.push({id:itemName});
    //alert(emails);
	
    $('.permissionForm .dropdown select').append('<option value="'+itemName+'" selected="">'+itemPrice+'</option>');
    $('.permissionForm .dropdown .menu').append('<input type="hidden" name="permissions['+j+']" value="'+itemName+'"><div class="item" data-value="'+itemName+'">'+itemPrice+'</div>');
    
    //$('.permissionForm .dropdown').dropdown('set value', emails);
	$('.permissionForm .dropdown').dropdown('set selected', itemPrice);
    $('.permissionForm .dropdown').dropdown();
    $(".permissionForm .dropdown .menu").dropdown("refresh");
    
    j++;
    
}


function updateCart(){
    var cartItems = 0;
    var cartTotal = 0;
    for(var i = 0; i < cart.length; i++){
        var price = parseInt(cart[i].price) * parseInt(cart[i].qty);
        var items = parseInt(cart[i].qty);
        cartItems = cartItems + items;
        cartTotal = cartTotal + price;
    }

  
    if(cartItems === 0){
        return updateMessage("No permissionForm added Yet.");
    } else if(cartItems === 1){
        return updateMessage("Cart: 1 item - $"+cartTotal.toFixed(2));
    } else{
        return updateMessage("Added: "+cartItems+" Permissions - $"+cartTotal.toFixed(2));
    }
    function updateMessage(message){
        var cartCtn = $(".cart");
        cartCtn.html(message);
    }
}
}); 