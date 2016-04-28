var pos,
    current = false,
    map,
    geocoder,
    infowindow,
    marker,
    directionsService,
    directionsDisplay,
    defaultDirection = '<h3 id = "directions"> Itinerary </h3><a class="btn btn-default" id="mailTo">Email This Itinerary</a>';

/**
 * initialize the web page and handle event registration
 */
function initialize() {
    var mapOptions = {
        center: new google.maps.LatLng(42.730787,-73.682488),
        zoom: 6,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    // setup google map api
    map = new google.maps.Map(document.getElementById("googleMap"), mapOptions);
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

    directionsService = new google.maps.DirectionsService;
    directionsDisplay = new google.maps.DirectionsRenderer;
    directionsDisplay.setMap(map);

}

/**
 * handles location error
 * @param browserHasGeolocation
 * @param infoWindow
 * @param pos
 */
function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setContent(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
}

/**
 * get latitude and longitude
 */
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
//                infowindow = new google.maps.InfoWindow({map: map});
//                infowindow.setContent("Address: "+ results[0].formatted_address);
//                infowindow.open(map, marker);
                map.setCenter(pos);

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

/**
 * organize destination order in a more readable format
 * @param checkboxArray
 * @returns {String}
 */
function organizeDestination(checkboxArray)
{
    var i = 0,
        destinationOrder = "The destination travel order is as below:";
    while (i < checkboxArray.length) {
        destinationOrder += "\nStop " + (i+1) + ": " + checkboxArray[i];
        i++;
    }
    return destinationOrder;
}


/**
 * display the route on map section
 * @param checkboxArray
 */
function calculateAndDisplayRoute(checkboxArray){
    var waypts = [];
    for (var i = 1; i < checkboxArray.length - 1; i++){
        waypts.push({
            location: checkboxArray[i],
            stopover:true
        });
    }

    console.log(checkboxArray);
    window.alert(organizeDestination(checkboxArray));

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
        }
        else{
            window.alert('Directions request failed due to ' + status);
        }
    });
}

/**
 * whenever the mail to button is clicked, open default email app with
 * auto-filled subject and body
 */
$(document).on("click", "#mailTo", mailToHandler);

/**
 * whenever the submit button is clicked, send corresponding request to back end
 */
$(document).on("click", "#myajax", function(event) {
	var x, y, checkbox;
	if ((document.getElementById("myCheck")).checked) {
		x = pos.lat.toString();
		y = pos.lng.toString();
		console.log('checked');
	}
	else {
		x = '';
		y = '';
		console.log('checked false');
	}

    var param = {
        loc1: document.getElementsByName("loc")[0].value,
        loc2: document.getElementsByName("loc")[1].value,
        loc3: document.getElementsByName("loc")[2].value,
        loc4: document.getElementsByName("loc")[3].value,
        loc5: document.getElementsByName("loc")[4].value,
        lat: x,
        lng: y
	};

    console.log(param);

    $.ajax({
        type: 'GET', // it's easier to read GET request parameters
        url: 'LocationServlet',
        dataType: 'JSON',
        data: param,
        success: function(responseText) {
            console.log("response: " + responseText);

            // add direction detail to bottom left corner
            var div = document.getElementById('directions-panel');
            div.innerHTML = defaultDirection + organizeDirection(responseText[1]);

            var arr = $.map(responseText[0], function(el) { return el; })
            calculateAndDisplayRoute(responseText[0]);
        },
        error: function(jqXHR, textStatus, errorThrown) {
        	console.log("response(Error): " + jqXHR.responseText);
            window.alert(jqXHR.responseText);
        }
    });

    event.preventDefault();
});

/**
 * organize direction text from back end to be more readable
 * @param responseText
 * @returns {String}
 */
function organizeDirection(responseText)
{
    var allDirection = '';
    var i = 0;
    for (; i < responseText.length; i++)
    {
        var eachStep = '<p class="direction-detail">' + responseText[i].replace('<div', '<div class="direction-warning"') + '</p>';
        allDirection = allDirection + eachStep;
    }
    return allDirection;
}

// handles mail to event
function mailToHandler(event) {
    var loc = ["", "", "", "", ""];
    loc[0] = "loc1=%22"+document.getElementById("loc1").value.replace(" ", "%20")+"%22";
    loc[1] = "loc2=%22"+document.getElementById("loc2").value.replace(" ", "%20")+"%22";
    loc[2] = "loc3=%22"+document.getElementById("loc3").value.replace(" ", "%20")+"%22";
    loc[3] = "loc4=%22"+document.getElementById("loc4").value.replace(" ", "%20")+"%22";
    loc[4] = "loc5=%22"+document.getElementById("loc5").value.replace(" ", "%20")+"%22";

    // create mailto link
    prefex = "mailto:&subject=My%20Itinerary&body=The%20link%20is:%20";
    endvar = loc.join("%26");
    httpAddress = document.location.origin+document.location.pathname+"%3F"+endvar;
    document.location.href = prefex + httpAddress;

    event.preventDefault();
}

/**
 * Reads an address with location information
 * And auto-fill the input tags with corresponding information
 */
$(document).ready(function(event) {
    var searchContent = window.location.search;
    if (searchContent != '') {
        locations = searchContent.split('&');
        $('#loc1').val(locations[0].split('%22')[1].replace(/%20/g, ' '));
        $('#loc2').val(locations[1].split('%22')[1].replace(/%20/g, ' '));
        $('#loc3').val(locations[2].split('%22')[1].replace(/%20/g, ' '));
        $('#loc4').val(locations[3].split('%22')[1].replace(/%20/g, ' '));
        $('#loc5').val(locations[4].split('%22')[1].replace(/%20/g, ' '));

    }
    // change the website address to homepage
    window.history.pushState('', 'Big Dipper Homepage', '/Big-Dipper/');

    // deactivate get location button for 10 seconds
    $('.btn-info').attr('disabled', true);
    setTimeout(function(){
        $('.btn-info').removeAttr('disabled');
    }, 10000)

    event.preventDefault();
})
