/*****************************************************************
File: main.js
Author: Jake Oh
Description:
Here is the sequence of logic for the app
- Read localStorage Data
- Display previous Data on Maps
- load google map callback
- geolocation
- center map
- add U marker
- add U marker click listener -> remove, create new marker, add position plus timestamp to localStorage
- fetch json
- json to localStorage
- add markers
- add markers click listeners -> show infoWindow with time and date

Version: 0.0.1
Updated: Jan 25, 2017

*****************************************************************/

let locationarray = [];
var max = 30;
var map;
const KEYSTRING = "oh000024"




//Promise 
var _promise = function() {

    return new Promise(function(resolve, reject) {

        if (navigator.geolocation) {
            //document.getElementById("output").innerHTML = "";
            //code goes here to find position
            let to = 1000 * 30; //1000 times 30 = 30 seconds
            let max = 1000 * 60 * 60; //1000 * 60 * 60 = 1 hour
            var params = {
                enableHighAccuracy: false,
                timeout: to,
                maximumAge: max
            };
            //enableHighAccuracy means try to use GPS and drain the battery
            //for improved accuracy within a few meters.
            //maximum age is how long to cache the location info
            //timeout is how long to wait for the network to respond after the user says ok
            navigator.geolocation.getCurrentPosition(resolve, reject, params);

            //to continually check the position (in case it changes) use
            // navigator.geolocation.watchPosition( reportPosition, gpsError, params)
        } else {
            //browser does not support geolocation api
            alert("Sorry, but your browser does not support location based awesomeness.")
        }

    });
};

function readLocationData() {
    if (!localStorage.getItem(KEYSTRING)) {
        let locObj = {
            "lat": 34.1234,
            "lng": -100.0000,
            "timestamp": 1485405156131
        };
        locationarray.push(locObj);

        locObj = {
            "lat": 43.1234,
            "lng": -75.0000,
            "timestamp": 1485679817002
        };
        locationarray.push(locObj);
        locObj = {
            "lat": 49.1234,
            "lng": -80.0000,
            "timestamp": 1488474615003
        };
        locationarray.push(locObj);
        locObj = {
            "lat": 53.1234,
            "lng": -100.0000,
            "timestamp": 1489364513004
        }
        locationarray.push(locObj);
        localStorage.setItem(KEYSTRING, JSON.stringify(locationarray));
    } else {
        locationarray = JSON.parse(localStorage.getItem(KEYSTRING));
    }
}

function Mapia() {
    readLocationData(); 
    map = new google.maps.Map(document.getElementById("MyMap"), {
        center: {
            lat: 45.3496711,
            lng: -75.7569551
        },
        zoom: 3,
        mapTypeId: google.maps.MapTypeId.SATELLITE
            //mapTypedId:"roadmap"
    });
    //setTimeout(sam,3000);

    locationarray.forEach(function(element) {
        // statements
        // let lt = (Math.random() * 180) - 90;
        // let lg = (Math.random() * 360) - 180;
        let lt = element.lat;
        let lg = element.lng;

        //mal.setCenter(lat:lt,lng:lg);
        map.panTo({
            lat: lt,
            lng: lg
        });
        let marker = new google.maps.Marker({
            position: {
                lat: lt,
                lng: lg
            },
            map: map,
            title: element.timestamp.toString()
        });

        //marker.setMap(map);



down vote
This works fine. Checked in chrome browser:

var theDate = new Date(timeStamp_value * 1000);
dateString = theDate.toGMTString();
        marker.addListener('click', function() {
            let inforWindow = new google.maps.InfoWindow({
                position: this.position,
                map: map,
                content: element.timestamp.toString() //'<h3>MyMap</h3>'
            });
        });
    });
}


//Promise 
_promise()
    .then(function(position) {
        // 성공시
        console.log("Latitude: " + position.coords.latitude + ", Longitude: " + position.coords.longitude);
        map.panTo({
            lat: position.coords.latitude,
            lng: position.coords.longitude
        });
        let marker = new google.maps.Marker({
            position: {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            },
            map: map,
            label: 'U',
            animation: google.maps.Animation.DROP,
            title: position.timestamp.toString()
        });

        marker.addListener('click', function() {
            let inforWindow = new google.maps.InfoWindow({
                position: this.position,
                map: map,
                content: position.timestamp.toString()
            });
            marker.setLabel(null);
        });


    }).catch(function(error) {
        // 실패시 
        //console.error(error);
        console.log(error);
    });