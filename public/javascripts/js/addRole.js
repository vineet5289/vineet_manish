// addRolePermissions.scala.html amd addRoles.scala.htm
$(function(){

    //active role and inctive roles tab
    $('.rolesmenu .item').on('click', function() {
        $('.rolesmenu .item').removeClass('active');
        $(this).addClass('active');
    });  

    $('.rolesmenu.menu .item')
.tab({ history: false })
;

$('#disableroletable').DataTable();

// pagination on adding permissions addrolepermissions.scala
 //var pager = new Pager('permissiontable', 5); 
       // pager.init(); 
        //pager.showPageNav('pager', 'pageNavPosition'); 
        //pager.showPage(2);
   	
   // for adding permissions dnamicaaly list in addrolespermission.sca
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

function Pager(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;
    
    this.showRecords = function(from, to) {        
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)  
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    }
    
    this.showPage = function(pageNumber) {
        if (! this.inited) {
            alert("not inited");
            return;
        }

        var oldPageAnchor = document.getElementById('pg'+this.currentPage);
        oldPageAnchor.className = 'pg-normal';
        
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('pg'+this.currentPage);
        newPageAnchor.className = 'pg-selected';
        
        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    }   
    
    this.prev = function() {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    }
    
    this.next = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    }                        
    
    this.init = function() {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1); 
        this.pages = Math.ceil(records / itemsPerPage);
        this.inited = true;
    }

    this.showPageNav = function(pagerName, positionId) {
        if (! this.inited) {
            alert("not inited");
            return;
        }
        var element = document.getElementById(positionId);
        
        var pagerHtml = '<span onclick="' + pagerName + '.prev();" class="pg-normal"> &#171 Prev </span> | ';
        for (var page = 1; page <= this.pages; page++) 
            pagerHtml += '<span id="pg' + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> | ';
        pagerHtml += '<span onclick="'+pagerName+'.next();" class="pg-normal"> Next &#187;</span>';            
        
        element.innerHTML = pagerHtml;
    }
}

