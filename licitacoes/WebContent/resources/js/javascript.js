$( document ).ready(function() {
	$("#breadcrumbs ul").empty();
	$("#breadcrumbs ul").append('<li><a href="docs/home.html">Home</a><span class="divider">></span></li>');
	
	if($ehLista){			
		$("#breadcrumbs ul").append('<li>$response.name</li>');
		document.title = "$response.name - Dados Abertos";	
	}else{
		$("#breadcrumbs ul").append('<li>$response.name</li>');
		document.title = "$response.name - Dados Abertos";	
	}
});

$(document).ready(function(){
  $("#siteaction-contraste2").hide();	
  $("#siteaction-contraste a").click(function(){
    $("body").addClass("fundoPreto");
    $("#siteaction-contraste").hide();
	$("#siteaction-contraste2").show();
  });
 $("#siteaction-contraste2 a").click(function(){
	    $("body").removeClass("fundoPreto");
	    $("#siteaction-contraste2").hide();
		$("#siteaction-contraste").show();
	});
});