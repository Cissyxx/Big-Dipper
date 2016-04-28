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

    <!-- style sheets and Font Imports *******************************************************************************-->
    <link rel="stylesheet" href="./libs/bootstrap.min.css">
    <link rel="stylesheet" href="./stylesheets/homepage.css">

    <script src="./libs/jquery-1.11.3.min.js"></script>
</head>
<body>
    <div class = "page-header">
    	<img src="images/logo.jpg" class = "pull_left">
        <h1>Big Dipper</h1>
    </div>

    <div class = "row">
        <div class="col-xs-4">
           <div class = "description">
                <h3> Enter your current location in the first entry below, then enter up to four more locations that you want to visit: </h3>
            </div>
            <form name="loginForm">
                <div class="form-group">
                    <label for="exampleInputName2">Current Location </label>
                    <button type = "button" class = "btn btn-info" onclick = "codeLatLng()">Current Location</button>
                </div>
                <label class="checkInfo"> 
                    Check this box to set your current location as starting point: 
                    <input class="btn btn-default" type = "checkbox" id = "myCheck">
                </label>
                <div class="form-group">
                    <label for="exampleInputName2">Location 1.</label>
                    <input type="text" class="form-control" id="loc1" placeholder="Central Park New York" name="loc">
                </div>
                <div class="form-group">
                    <label for="exampleInputName2">Location 2. </label>
                    <input type="text" class="form-control" id="loc2" placeholder="High Line" name="loc">
                </div>
                <div class="form-group">
                    <label for="exampleInputName2">Location 3. </label>
                    <input type="text" class="form-control" id="loc3" placeholder="Botanic Garden" name="loc">
                </div>
                <div class="form-group">
                    <label for="exampleInputName2">Location 4. </label>
                    <input type="text" class="form-control" id="loc4" placeholder="Time Square" name="loc">
                </div>
                <div class="form-group">
                    <label for="exampleInputName2">Location 5.</label>
                    <input type="text" class="form-control" id="loc5" placeholder="Yankee Stadium" name="loc">
                </div>
                <button type="button" class="btn btn-default" id = 'myajax' value="Blob">Submit</button>
                
            </form>
           <div id = "directions-panel">
                <h3 id = "directions"> Itinerary </h3>
                <a class="btn btn-default" id="mailTo">Email This Itinerary</a>
           </div>
        </div>

        <div class="col-xs-8">
            <div id="googleMap"></div>
        </div>

    </div>

</body>

<script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyALZvDqmZKte0ru1-fekJQE9ekCgovQcYw&signed_in=true&callback=initialize">
</script>
<script type = "text/javascript" src="map.js"></script>
<!-- <script type="text/javascript" src="../setup_files/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="../setup_files/bootstrap.min.js"></script> -->
<!-- <script type="text/javascript" src="./libs/jquery-1.11.3.min.js"></script>  -->
<script type="text/javascript" src="./libs/bootstrap.min.js"></script>
</html>