var pos;
var map;
var geocoder ;
var infowindow;
var marker;

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

  var directionsService = new google.maps.DirectionsService;
  var directionsDisplay = new google.maps.DirectionsRenderer;
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
var a1 = document.getElementById("loc1").value;
var a2 = document.getElementById("loc2").value;
var a3 = document.getElementById("loc3").value;
var a4 = document.getElementById("loc4").value;
var a5 = document.getElementById("loc5").value;
var listofLocations = {loc1: a1, loc2: a2, loc3: a3, loc4: a4, loc5: a5};
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

function calculateAndDisplayRoute(directionsService, directionsDisplay){
	var waypts = [];
	var checkboxArray = [ "Coney Island", "Brooklyn Botanic Garden", "Central Park", "Harlem"];
	for (var i = 1; i < checkboxArray.length - 1; i++){
		waypts.push({
			location: checkboxArray[i],
			stopover:true
		});
	}
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
			var summaryPanel = document.getElementById('directions-panel');
            summaryPanel.innerHTML = '';
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

// $.get('myservlet', function(data)){
// 	data = "HI";
// 	$('#data').innerHTML(data);
// });
