var pos;
var map;
var geocoder;
// var infowindow = new google.maps.InfoWindow();
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
  if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
            pos = {
              lat: position.coords.latitude,
              lng: position.coords.longitude
            };
            map.setCenter(pos);
            lat = position.coords.latitude;
            lng = position.coords.longitude;
            x.innerHTML="Latitude: " + position.coords.latitude + 
    "<br>Longitude: " + position.coords.longitude;
          }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
            
          });
        } 
    else {
      // Browser doesn't support Geolocation
      handleLocationError(false, infoWindow, map.getCenter());
    }
}
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
infoWindow.setPosition(pos);
infoWindow.setContent(browserHasGeolocation ?
                      'Error: The Geolocation service failed.' :
                      'Error: Your browser doesn\'t support geolocation.');
}

function codeLatLng(){
    var latlng = new google.maps.LatLng(pos.lat, pos.lng);
    geocoder = new google.maps.Geocoder();
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

     