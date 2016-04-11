var pos;
var map;
var geocoder ;
var infowindow;
var marker;
var directionsService;
var directionsDisplay;
function initialize() {
  var mapOptions = {
   center: new google.maps.LatLng(42.730787,-73.682488),
   zoom: 6,
   mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  
  map = new google.maps.Map(document.getElementById("googleMap"),
            mapOptions);
  geocoder =  new google.maps.Geocoder();

  if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
            pos = {
              lat: position.coords.latitude,
              lng: position.coords.longitude
            };
            map.setCenter(pos);
            lat = position.coords.latitude;
            lng = position.coords.longitude;

          }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
            
          });
        } 
  else {
    // Browser doesn't support Geolocation
    handleLocationError(false, infoWindow, map.getCenter());
  }
  //set all markers visible
  // var markers = [];//some array;
  // var bounds = new google.maps.LatLngBounds();
  // for(i=0;i<markers.length;i++) {
  //    bounds.extend(markers[i].getPosition());
  // }
  // map.setCenter(bounds.getCenter());
  // console.log(bounds[0], markers);
  // map.fitBounds(bounds);

  directionsService = new google.maps.DirectionsService;
  directionsDisplay = new google.maps.DirectionsRenderer;
  directionsDisplay.setMap(map);
  document.getElementById('submit').addEventListener('click', function(){
  	calculateAndDisplayRoute(directionsService, directionsDisplay);
  });
  
}
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
	infoWindow.setPosition(pos);
	infoWindow.setContent(browserHasGeolocation ?
	                      'Error: The Geolocation service failed.' :
                      		'Error: Your browser doesn\'t support geolocation.');
}

function codeLatLng(){
    var latlng = new google.maps.LatLng(pos.lat, pos.lng);
    geocoder.geocode({'latLng': latlng}, function(results, status)
    {
        if (status == google.maps.GeocoderStatus.OK)
        {
            if (results[0])
            {
                map.setZoom(11);
                marker = new google.maps.Marker(
                {
                    position: latlng,
                    map: map
                });
                infowindow = new google.maps.InfoWindow({map: map});
                infowindow.setContent("Address: "+ results[0].formatted_address);
                infowindow.open(map, marker);

            }
            else
            {
                alert("No results found");
            }
        }
        else
        {
            alert("Geocoder failed due to: " + status);
        }
    });
}

// function address(){
//   var a1 = document.getElementById("loc1").value;
//   var a2 = document.getElementById("loc2").value;
//   var a3 = document.getElementById("loc3").value;
//   var a4 = document.getElementById("loc4").value;
//   var a5 = document.getElementById("loc5").value;
//   var addresses = [a1, a2, a3, a4, a5];
//   for (i = 0; i < addresses.length; i++){
//     geocoder.geocode({'address': addresses[i]}, function(results, status){
//       if (status == google.maps.GeocoderStatus.OK)
//         if (addresses[i] !== ""){
//           {
//             if (results[0])
//               {
//                   map.setZoom(11);
//                   marker = new google.maps.Marker(
//                   {
//                       position: results[0].geometry.location,
//                       map: map
//                   });
//                   infowindow = new google.maps.InfoWindow({map: map});
//                   infowindow.setContent("Address: "+ results[0].formatted_address);
//                   infowindow.open(map, marker);

//               }
//               else
//               {
//                   alert("No results found");
//               }
//           }
//         }
//           else
//           {
//               alert("Geocoder failed due to: " + status);
//           }
//       });
//   }
// }

function calculateAndDisplayRoute(checkboxArray){
	var waypts = [];
	//var checkboxArray = [ "Coney Island", "Brooklyn Botanic Garden", "Central Park", "Harlem"];
	for (var i = 1; i < checkboxArray.length - 1; i++){
		waypts.push({
			location: checkboxArray[i],
			stopover:true
		});
	}
	
	window.alert(checkboxArray);
	
	directionsService.route({
		origin: checkboxArray[0],
		destination: checkboxArray[checkboxArray.length-1],
		waypoints: waypts,
		// optimizeWaypoints: true,
		travelMode: google.maps.TravelMode.DRIVING
	}, function(response, status){
		if(status === google.maps.DirectionsStatus.OK){
			directionsDisplay.setDirections(response);
			var route = response.routes[0];
			console.log(route);
			//var summaryPanel = document.getElementById('directions-panel');
            //summaryPanel.innerHTML = '';
            // for (var i = 0; i < route.legs.length; i++) {
            //   var routeSegment = i + 1;
            //   summaryPanel.innerHTML += '<b>Route Segment: ' + routeSegment +
            //       '</b><br>';
            //   summaryPanel.innerHTML += route.legs[i].start_address + ' to ';
            //   summaryPanel.innerHTML += route.legs[i].end_address + '<br>';
            //   summaryPanel.innerHTML += route.legs[i].distance.text + '<br><br>';
            // }
		}
		else{
			window.alert('Directions request failed due to ' + status);
		}
	});
}

$(document).on("click", "#myajax", function(event) {
	
//		//Java Script
//		var xhttp = new XMLHttpRequest();
//		xhttp.onreadystatechange = function() {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
//			if (xhttp.readyState == 4 && xhttp.status == 200) {
//				$("#directions").text(xhttp.responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
//			    window.alert(xhttp.responseText)
//			}
//		};
	
//		xhttp.open("POST", "LocationServlet", true);
//		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//		xhttp.send("loc1=Seattle&loc2=Miami"); 
	
	//JQuery
	
	var param = {
        loc1: document.getElementsByName("loc1")[0].value,
        loc2: document.getElementsByName("loc2")[0].value,
        loc3: document.getElementsByName("loc3")[0].value,
        loc4: document.getElementsByName("loc4")[0].value,
        loc5: document.getElementsByName("loc5")[0].value,
    };
	
	console.log(param);
	
	$.ajax({
	    type: 'GET', // it's easier to read GET request parameters
	    url: 'LocationServlet',
	    dataType: 'JSON',
	    data: param,
	    success: function(responseText) {
	        
	        console.log("response is " + responseText);

		    $("#directions").text(responseText);
		    var arr = $.map(responseText, function(el) { return el; })
            calculateAndDisplayRoute(responseText);
	    },
	    error: function(data) {
	        window.alert('fail');
	    }
	});
	
	
//	$.post("LocationServlet", $.param(param), function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
//		window.alert(responseText);
//	    console.log("response is " + responseText);
//        $("#directions").text(responseText[0]);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
//    });
	
	event.preventDefault();
});




// $.get('myservlet', function(data)){
// 	data = "HI";
// 	$('#data').innerHTML(data);
// });
