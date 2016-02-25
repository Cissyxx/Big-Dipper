
function initialize() {
  var mapOptions = {
   center: new google.maps.LatLng(51.508742,-0.120850),
   zoom: 6,
   mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  var map = new google.maps.Map(document.getElementById("googleMap"),
            mapOptions);
}
google.maps.event.addDomListener(window, 'load', initialize);
// google.maps.event.addDomListener(window, "resize", function() {
// var center = map.getCenter();
// google.maps.event.trigger(map, "resize");
// map.setCenter(center); 
// });