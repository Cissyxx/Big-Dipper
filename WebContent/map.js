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
    
    document.getElementById('mailTo').addEventListener('click', function() {
        mailToWithAddress();
        /*var loc = ["", "", "", "", ""];
        console.log(loc);
        loc[0] = "loc1=%22"+document.getElementsByName("loc")[0].value.replace(" ", "%20")+"%22";
        loc[1] = "loc2=%22"+document.getElementsByName("loc")[1].value.replace(" ", "%20")+"%22";
        loc[2] = "loc3=%22"+document.getElementsByName("loc")[2].value.replace(" ", "%20")+"%22";
        loc[3] = "loc4=%22"+document.getElementsByName("loc")[3].value.replace(" ", "%20")+"%22";
        loc[4] = "loc5=%22"+document.getElementsByName("loc")[4].value.replace(" ", "%20")+"%22";
        console.log("location is " + loc);
        //this.click(function() {
            prefex = "mailto:&subject=My%20Itinerary&body=The%20link%20is:";
            endvar = loc.join("%26");
            httpAddress = document.location.origin+document.location.pathname+"LocationServlet%3F"+endvar;
            document.location.href = prefex + httpAddress;

        //});
        event.preventDefault();*/
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


function calculateAndDisplayRoute(checkboxArray){
    var waypts = [];
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
        }
        else{
            window.alert('Directions request failed due to ' + status);
        }
    });
}

$(document).on("click", "#myajax", function(event) {

    var param = {
        loc1: document.getElementsByName("loc")[0].value,
        loc2: document.getElementsByName("loc")[1].value,
        loc3: document.getElementsByName("loc")[2].value,
        loc4: document.getElementsByName("loc")[3].value,
        loc5: document.getElementsByName("loc")[4].value,
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

    event.preventDefault();
});

//$(document).ready ( function () {
//    $(document).on ("click", "#mailto", function () {
//$("#mailto").click(function(event) {
function mailToWithAddress(event) {
    var loc = ["", "", "", "", ""];
    console.log(loc);
    loc[0] = "loc1=%22"+document.getElementsByName("loc")[0].value.replace(" ", "%20")+"%22";
    loc[1] = "loc2=%22"+document.getElementsByName("loc")[1].value.replace(" ", "%20")+"%22";
    loc[2] = "loc3=%22"+document.getElementsByName("loc")[2].value.replace(" ", "%20")+"%22";
    loc[3] = "loc4=%22"+document.getElementsByName("loc")[3].value.replace(" ", "%20")+"%22";
    loc[4] = "loc5=%22"+document.getElementsByName("loc")[4].value.replace(" ", "%20")+"%22";
    console.log("location is " + loc);
    //this.click(function() {
        prefex = "mailto:&subject=My%20Itinerary&body=The%20link%20is:";
        endvar = loc.join("%26");
        httpAddress = document.location.origin+document.location.pathname+"LocationServlet%3F"+endvar;
        document.location.href = prefex + httpAddress;

    //});
    event.preventDefault();
}
//})


//href="mailto:&amp;subject=My%20Itinerary&amp;body=The%20link%20is%20below:http://localhost:8080/Big-Dipper/LocationServlet%3Floc1=%22NYC%22%26loc2=%22Miami,%20FL%22%26loc3=%22Troy,%20NY%22"



/*
$(document).on("click", "#mailto", function(event) {
    var loc1, loc2, loc3, loc4, loc5;
    loc1 = document.getElementsByName("loc")[0].value,
    loc2 = document.getElementsByName("loc")[1].value,
    loc3 = document.getElementsByName("loc")[2].value,
    loc4 = document.getElementsByName("loc")[3].value,
    loc5 = document.getElementsByName("loc")[4].value,
    $("#emailLnk").click(function()
    {
        document.location.href = 'mailto:&subject="My%20Itinerary&body="My%20itinerary%20is%20as%20link%20:%20http://localhost:8080/Big-Dipper/LocationServlet?loc1="'+loc1+'"&loc2="'+loc2+'"&loc3="'+loc3+'"&loc4="'+loc4+'"&loc5="'+loc5+'"';
    });
    event.preventDefault();
});*/
