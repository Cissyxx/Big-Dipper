<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Big Dipper Homepage</title>

    	<meta charset="UTF-8">

    	<!-- Needed for mobile friendly site ****************************************************************************-->
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<meta name="viewport" content="initial-scale=1.0, user-scalable=no">


    	<!-- Stylesheets and Font Imports *******************************************************************************--> 
    	<link rel="stylesheet" href="./libs/bootstrap.min.css"> 
    	<!-- <link rel="stylesheet" href="./WEB-INF/lib/websiteCSS.css"> -->
    	<link rel="stylesheet" href="./stylesheets/homepage.css"> 
    	
    	<script src="./libs/jquery-1.11.3.min.js"></script>
		
		<script type="text/javascript">
$(document).on("submit", "#loginForm", function(event) {
    var $form = $(this);

    $.post($form.attr("action"), $form.serialize(), function(response) {
    	 $("#directions").text(response);
    });

    event.preventDefault(); // Important! Prevents submitting the form.
});
</script>
    	
</head>
<body>
<div class = "page-header">
	<h1>Big Dipper </h1>
	</div>

	<div class = "row">
		<div class="col-xs-4">
			<div class = "description">
				<h3> Enter your current location in the first entry below as the starting point, then enter up to four more locations that you want to visit: </h3>
				<!-- <p> <button type="button" class="btn btn-info" onclick = "codeLatLng()">Current Location</button></p> -->
			</div>
			<form name="loginForm">
				<div class="form-group">
					<label for="exampleInputName2">Current Location.</label>
					<input type="text" class="form-control" id="loc1" placeholder="15th Street Troy NY 12180" name="loc1">
				</div>
				<div class="form-group">
					<label for="exampleInputName2">Location 2. </label>
					<input type="text" class="form-control" id="loc2" placeholder="High Line" name="loc2">
				</div>
				<div class="form-group">
					<label for="exampleInputName2">Location 3. </label>
					<input type="text" class="form-control" id="loc3" placeholder="Botanic Garden" name="loc3">
				</div>
				<div class="form-group">
					<label for="exampleInputName2">Location 4. </label>
					<input type="text" class="form-control" id="loc4" placeholder="Time Square" name="loc4">
				</div>
					<div class="form-group">
					<label for="exampleInputName2">Location 5.</label>
					<input type="text" class="form-control" id="loc5" placeholder="Yankee Stadium" name="loc5">
				</div>
				<button type="button" class="btn btn-default" id = 'submit' value="Send">Submit</button>
				<button type="button" class="btn btn-default" id = 'myajax' value="Blob">Test Ajax</button>
				<input type="submit" value="Login">
				<!-- <button type="button" class="btn btn-default" onclick= "address()">Submit</button> -->
			</form>
			<div id = "directions-panel">
				<h3 id = "directions"> Itinerary </h3>
			</div>
		</div>
		
		<div class="col-xs-8">
			<div id="googleMap"></div>
		</div>
		 
	</div>

</body>

<script>
// 	function myFunction(){
		
// // 		//Java Script
// // 		var xhttp = new XMLHttpRequest();
// // 		xhttp.onreadystatechange = function() {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
// // 			if (xhttp.readyState == 4 && xhttp.status == 200) {
// // 				$("#directions").text(xhttp.responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
// // 			    window.alert(xhttp.responseText)
// // 			}
// // 		};
		
// // 		xhttp.open("POST", "LocationServlet", true);
// // 		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
// // 		xhttp.send("loc1=Seattle&loc2=Miami"); 
		
// 		//JQuery
		
// 		var param = {
// 	        loc1: document.getElementsByName("loc1")[0].value,
// 	        loc2: document.getElementsByName("loc2")[0].value,
// 	        loc3: document.getElementsByName("loc3")[0].value,
// 	        loc4: document.getElementsByName("loc4")[0].value,
// 	        loc5: document.getElementsByName("loc5")[0].value,
// 	    };
		
// 		console.log(param);
		
// // 		$.ajax({
// // 		    type: "POST",
// // 		    url: "LocationServlet",
// // 		    contentType: "application/json", // NOT dataType!
// // 		    data: JSON.stringify(param),
// // 		    success: function(response) {
// // 		    	window.alert(responseText);
// // 			    console.log("response is " + responseText);
// // 	            $("#directions").text(responseText[0]);
// // 		    }
// // 		});
		
		
// 		$.post("LocationServlet", $.param(param), function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
// 			window.alert(responseText);
// 		    console.log("response is " + responseText);
//             $("#directions").text(responseText[0]);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
//         });
// 	};
</script>

<script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyALZvDqmZKte0ru1-fekJQE9ekCgovQcYw&signed_in=true&callback=initialize">
</script>
 <script type = "text/javascript" src="map.js"></script>	
<!-- <script type="text/javascript" src="../setup_files/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../setup_files/bootstrap.min.js"></script> -->
<!-- <script type="text/javascript" src="./libs/jquery-1.11.3.min.js"></script>  -->
<script type="text/javascript" src="./libs/bootstrap.min.js"></script>
</html>