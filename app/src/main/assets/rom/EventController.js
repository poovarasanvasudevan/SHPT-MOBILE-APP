'use strict';
angular.module('pmpApp.Eventcontroller', 
    [
        'ngMaterial', 
        'ngMessages', 
        'ui.bootstrap', 
        function () {}
    ]
)

.controller('eventListController',['$scope', '$rootScope', '$mdDialog', 'EventFactory', 'LocalData', 'DateFactory', function($scope, $rootScope, $mdDialog, EventFactory, LocalData, DateFactory) {
    $scope.showEventList = true;
    $rootScope.myBanner=true;
    $scope.eventRecords = [];
    $scope.itemsPerPageSizeList = LocalData.getPageSizeList();
    $scope.eventSearchFields = LocalData.getEventSearchFields();

    $scope.eventJson = {};
    $scope.totalcount = 0;
    $scope.eventJson.pagesize = 5;
    $scope.eventJson.searchfield = "";
    $scope.eventJson.searchtext = "";
    $scope.eventJson.datefrom = "";
    $scope.eventJson.dateto = "";
	
    getResultsPage(1);

    $scope.pagination = {
        current: 1
    };

    $scope.pageChanged = function(newPage) {
        getResultsPage(newPage);
    };

    function getResultsPage(pageNumber) {
        //$scope.eventRecords = [];
        $scope.eventJson.pageindex = pageNumber;
        
        if(($scope.eventJson.searchfield == "" || $scope.eventJson.searchfield == undefined) && ($scope.eventJson.datefrom == "" || $scope.eventJson.datefrom == undefined ) && ($scope.eventJson.dateto == "" || $scope.eventJson.dateto == undefined) ){
            $scope.eventPaginationJson = {};
            $scope.eventPaginationJson.pageindex = $scope.eventJson.pageindex;
            $scope.eventPaginationJson.pagesize = $scope.eventJson.pagesize;
            EventFactory.geteventlist($scope.eventPaginationJson)
                .then(
                    function(data) {				   
                        $scope.eventRecords = data.eventlist;
                        $scope.totalcount = 0;
                        $scope.totalcount = data.totalcount;
                        $rootScope.alertMessage('Successfully loaded all events data.');
                    },
                    function(errResponse){
                       $rootScope.alertMessage(errResponse);
                    }
                 );
        }else{
            EventFactory.search($scope.eventJson)
                .then(
                    function(data) {
                        $scope.eventRecords = data.eventlist;
                        $scope.totalcount = 0;
                        $scope.totalcount = data.totalcount;
                        $rootScope.alertMessage('Successfully loaded all events data.');                        
                    },
                    function(errResponse){
                        $rootScope.alertMessage(errResponse);
                    }
                );
        }

    }

    $scope.sort = function(keyname){
        $scope.sortKey = keyname;   //set the sortKey to the param passed
        $scope.reverse = !$scope.reverse; //if true make it false and vice versa
        $scope.eventJson.sortby = keyname;
        $scope.eventJson.sortdirection = $scope.reverse == true ? 1 : 0;
        getResultsPage(1);
    };

    $scope.search = function(){
        getResultsPage(1);
    };
	
    $scope.resetFields = function(){
        $scope.eventJson.searchfield = "";
        $scope.eventJson.searchtext = "";
        $scope.startDate = null;
        $scope.endDate = null;
        $scope.eventJson.datefrom = "";
        $scope.eventJson.dateto = "";
        //$scope.eventJson.sortdirection = "0";
        getResultsPage(1);
    };
	
    $scope.formatFromDate = function(date){
        $scope.eventJson.datefrom = DateFactory.dateToString(date);
    };
	
    $scope.formatEndDate = function(date){
        $scope.eventJson.dateto = DateFactory.dateToString(date);
    };
	
    $scope.getEventDetails = function(eventId){
    	window.location.href = $rootScope.basePath+"eventDetails?id="+eventId;
    };

    $scope.showAlert = function(ev, eventId) {
        // Appending dialog to document.body to cover sidenav in docs app
        var confirm = $mdDialog.confirm()
            .title('Are you sure you want to update this event('+eventId+')?')
            .targetEvent(ev)
            .ok('Yes')
            .cancel('No');

        $mdDialog.show(confirm).then(function() {
            /*var seqIdData = "\"participantIds\":[{\"seqId\":\""+seqId+"\"}]";
            var jsonData = "{\"eventId\": \""+$scope.eventId+"\","+seqIdData+"}";
            ParticipantFactory.deleteParticipant(jsonData)
                .then(
                    function(data) {
                        loadParticipantList();
                        var message = { type: 'success', msg: 'Successfully deleted participant.' };
                        $rootScope.alerts.push(message);
                    },
                    function(errResponse){
                        var message = { type: 'danger', msg: 'Error while deleting participant.' };
                        $rootScope.alerts.push(message);
                     }
            );*/
        });
    };    
    
}])

.controller('eventDetailsController', ['$scope', 'DateFactory', '$location', '$rootScope', 'EventFactory', function($scope, DateFactory, $location, $rootScope, EventFactory) {
    $rootScope.myBanner=true;
    $scope.eventFormData = {};
    $scope.eventId = $location.search().id;
    $rootScope.eventId = $scope.eventId;
    $scope.eventFormData.programStartDate = new Date();
    $scope.eventFormData.programEndDate = null;
    
    $scope.channels = [];
    //$scope.channels = LocalData.getConnectList();
    EventFactory.getChannelList()
        .then(
            function(data) {
                $scope.channels = data;
            },
            function(errResponse){
                $rootScope.alertMessage('Error while fetching channel list data.');
            }
       );
    
    $scope.open1 = function() {
        $scope.popup1.opened = true;
    };    
    $scope.open2 = function() {
        $scope.popup2.opened = true;
    };
    $scope.popup1 = {
        opened: false
    };
    $scope.popup2 = {
        opened: false
    };
    $scope.myDate = new Date();
    $scope.minDate = new Date( $scope.myDate.getFullYear()-100, $scope.myDate.getMonth(), $scope.myDate.getDate() );
    $scope.maxDate = new Date( $scope.myDate.getFullYear(),     $scope.myDate.getMonth(), $scope.myDate.getDate() );

    $scope.showAlert = function(value){
        if(value)
            alert('Please enter all the mandatory(*) fields.');
    }
    var geteventdetails = function(eventId){
        var EventFactoryObj = EventFactory;
        if(eventId.length>10){
            EventFactoryObj.fetcheventdetails(eventId)
                .then(
                    function(data) {
                        if(data.hasOwnProperty('errors')){
                            $location.path('/myEvents');
                            $rootScope.alertMessage(data.errors.eventId);
                        }else{
                            data.programStartDate = DateFactory.stringToDate(data.hasOwnProperty('programStartDate') == true ? data.programStartDate : null );
                            data.programEndDate = DateFactory.stringToDate(data.hasOwnProperty('programEndDate') == true ? data.programEndDate : null );
                            
                            $scope.eventFormData = data;
                            $scope.eventId = data.eventId;
                            $rootScope.eventId = data.eventId;
                            $scope.showParticipantButton = true;
                        }
                    },
                    function(errResponse){
                        $location.path('/myEvents');
                        $rootScope.alertMessage('Error while fetching event data.');
                    }
               );
        }else{
            EventFactoryObj.geteventdetails(eventId)
                .then(
                    function(data) {
                        if(data.hasOwnProperty('errors')){
                            $location.path('/myEvents');
                            $rootScope.alertMessage(data.errors.eventId);
                        }else{
                            data.programStartDate = DateFactory.stringToDate(data.hasOwnProperty('programStartDate') == true ? data.programStartDate : null );
                            data.programEndDate = DateFactory.stringToDate(data.hasOwnProperty('programEndDate') == true ? data.programEndDate : null );
                            
                            $scope.eventFormData = data;
                            $rootScope.eventId = data.eventId;
                            $scope.eventId = data.eventId;
                            $scope.showParticipantButton = true;
                        }
                    },
                    function(errResponse){
                        $location.path('/myEvents');
                        $rootScope.alertMessage('Error while fetching event data.');
                    }
                );
        }
    };
	
    geteventdetails($scope.eventId);

    $scope.updateEvent = function(){
        //$scope.eventFormData.eventId = $scope.eventId;
        $scope.eventFormData.programStartDate = DateFactory.dateToString($scope.eventFormData.programStartDate);
        $scope.eventFormData.programEndDate = DateFactory.dateToString($scope.eventFormData.programEndDate);
        EventFactory.createOrUpdate($scope.eventFormData)
            .then(
                function(data) {
                    $scope.errorMessages = {};
                    if(data.hasOwnProperty('errors') && data.status=="Failed"){
                        data.programStartDate = DateFactory.stringToDate(data.hasOwnProperty('programStartDate') == true ? data.programStartDate : null );
                        data.programEndDate = DateFactory.stringToDate(data.hasOwnProperty('programEndDate') == true ? data.programEndDate : null );
                        $scope.eventFormData = data;
                        $scope.errorMessages = data.errors;
                        $rootScope.alertMessage('Failed: Due to invalid data.');
                    }else{
                        data.programStartDate = DateFactory.stringToDate(data.hasOwnProperty('programStartDate') == true ? data.programStartDate : null );
                        data.programEndDate = DateFactory.stringToDate(data.hasOwnProperty('programEndDate') == true ? data.programEndDate : null );
                        $scope.eventFormData = data;
                        //geteventdetails($scope.eventId);
                        $rootScope.alertMessage('Successfully updated event data.');                        
                    }
                },
                function(errResponse){
                    $rootScope.alertMessage('Error while updating event data.');
                }
            );
    };
    $scope.backToEvents = function(){
    	window.location.href = $rootScope.basePath+"myEvents";
    };
    
    $scope.decisionMaker = false;
    $scope.setDecisionMakerDetails = function(){
        if($scope.decisionMaker == true){
            $scope.eventFormData.organizationDecisionMakerName = "";
            $scope.eventFormData.organizationDecisionMakerEmail = "";
            $scope.eventFormData.organizationDecisionMakerPhoneNo = "";
            $scope.decisionMaker = false;
        }else{
            $scope.eventFormData.organizationDecisionMakerName = $scope.eventFormData.organizationContactName;
            $scope.eventFormData.organizationDecisionMakerEmail = $scope.eventFormData.organizationContactEmail;
            $scope.eventFormData.organizationDecisionMakerPhoneNo = $scope.eventFormData.organizationContactMobile;
            $scope.decisionMaker = true;
        }
    };
    
    $scope.$on('gmPlacesAutocomplete::placeChanged', function(){
        var address_components = $scope.eventFormData.eventPlace.getPlace().address_components;
        var i = 0;
        var place = "", city = "", state = "";
        for(i = 0; i < address_components.length; i++ ){			
            /*if(address_components[i].types[0] == "sublocality_level_2"){
                place = address_components[i].long_name;
            }else if(address_components[i].types[0] == "sublocality_level_1" && place != ""){
                place = address_components[i].long_name;
            }else */
            if(address_components[i].types[0] === "locality"){
                city = address_components[i].long_name;
            }else if(address_components[i].types[0] == "administrative_area_level_2" && city == ""){
                city = address_components[i].long_name;
            }else if(address_components[i].types[0] === "administrative_area_level_1"){
                state = address_components[i].long_name;
            }else if(address_components[i].types[0] === "country"){
                $scope.eventFormData.eventCountry = address_components[i].long_name;
            }
        }
        $scope.eventFormData.eventCity = city;
        $scope.eventFormData.eventState = state;
        $scope.eventFormData.organizationCity = city;
        /*if(place == ""){
            if(city != "")
                    place = city;
            else if(state != "")
                    place = state;
            else
                place = $scope.eventFormData.eventCountry;
        }*/
        /*var n = s.indexOf('?');
        s = s.substring(0, n != -1 ? n : s.length);*/
        $scope.eventFormData.eventPlace = address_components[0].long_name;
        $scope.$apply();
    });
}])

.controller('createEventController', ['$scope', 'DateFactory', '$location', '$rootScope', 'EventFactory', 'LocalData', '$anchorScroll', function($scope, DateFactory, $location, $rootScope, EventFactory, LocalData, $anchorScroll) {
    
    $rootScope.myBanner=true;
    
    $scope.channels = [];
    //$scope.channels = LocalData.getConnectList();
    EventFactory.getChannelList()
        .then(
            function(data) {
                $scope.channels = data;
            },
            function(errResponse){
                $rootScope.alertMessage('Error while fetching channel list data.');
            }
       );
       
    $scope.eventFormData = {};
    $scope.eventFormData.eventPlace = null;
    $scope.eventFormData.programStartDate = new Date();
    $scope.eventFormData.programEndDate = null; //new Date();
    
    $scope.open1 = function() {
        $scope.popup1.opened = true;
    };    
    $scope.open2 = function() {
        $scope.popup2.opened = true;
    };
    $scope.popup1 = {
        opened: false
    };
    $scope.popup2 = {
        opened: false
    };
    
    $scope.options = {};
    $scope.options.minDate = $scope.eventFormData.programStartDate;
    $scope.setEventEndDate = function(){
        $scope.eventFormData.programEndDate = null;
        $scope.options.minDate = $scope.eventFormData.programStartDate;
    }
    $scope.myDate = new Date();
    $scope.minDate = new Date( $scope.myDate.getFullYear()-100, $scope.myDate.getMonth(), $scope.myDate.getDate() );
    $scope.maxDate = new Date( $scope.myDate.getFullYear(),     $scope.myDate.getMonth(), $scope.myDate.getDate() );

    $scope.showAlert = function(value){
        if(value)
            alert('Please enter all the mandatory(*) fields.');
    };    
    
    $scope.updateEvent = function(){
        $scope.eventFormData.programStartDate = DateFactory.dateToString($scope.eventFormData.programStartDate);        
        $scope.eventFormData.programEndDate = DateFactory.dateToString($scope.eventFormData.programEndDate);
        EventFactory.createOrUpdate($scope.eventFormData)
            .then(
                function(data) {
                    $scope.errorMessages = {};
                    if(data.hasOwnProperty('errors') && data.status == "Failed"){
                        $anchorScroll();
                        data.programStartDate = DateFactory.stringToDate(data.hasOwnProperty('programStartDate') == true ? data.programStartDate : null );
                        data.programEndDate = DateFactory.stringToDate(data.hasOwnProperty('programEndDate') == true ? data.programEndDate : null );
                        $scope.eventFormData = data;
                        $scope.errorMessages = data.errors;
                        $rootScope.alertMessage('Failed: Due to invalid data.');
                    }else{
                        $location.path('/eventDetails').search('id', data.eventId);
                        $rootScope.alertMessage('Successfully updated event data.');                        
                    }
                },
                function(errResponse){
                    $rootScope.alertMessage('Error while updating event data.');
                }
            );
    };

    $scope.backToEvents = function(){
    	window.location.href = $rootScope.basePath+"myEvents";
    };
    
    $scope.decisionMaker = false;
    $scope.setDecisionMakerDetails = function(){
        if($scope.decisionMaker == true){
            $scope.eventFormData.organizationDecisionMakerName = "";
            $scope.eventFormData.organizationDecisionMakerEmail = "";
            $scope.eventFormData.organizationDecisionMakerPhoneNo = "";
            $scope.decisionMaker = false;
        }else{
            $scope.eventFormData.organizationDecisionMakerName = $scope.eventFormData.organizationContactName;
            $scope.eventFormData.organizationDecisionMakerEmail = $scope.eventFormData.organizationContactEmail;
            $scope.eventFormData.organizationDecisionMakerPhoneNo = $scope.eventFormData.organizationContactMobile;
            $scope.decisionMaker = true;
        }
    };
    
    $scope.$on('gmPlacesAutocomplete::placeChanged', function(){
        var address_components = $scope.eventFormData.eventPlace.getPlace().address_components;
        var i = 0;
        var place = "", city = "", state = "";
        for(i = 0; i < address_components.length; i++ ){			
            /*if(address_components[i].types[0] == "sublocality_level_2"){
                place = address_components[i].long_name;
            }else if(address_components[i].types[0] == "sublocality_level_1" && place != ""){
                place = address_components[i].long_name;
            }else */
            if(address_components[i].types[0] === "locality"){
                city = address_components[i].long_name;
            }else if(address_components[i].types[0] == "administrative_area_level_2" && city == ""){
                city = address_components[i].long_name;
            }else if(address_components[i].types[0] === "administrative_area_level_1"){
                state = address_components[i].long_name;
            }else if(address_components[i].types[0] === "country"){
                $scope.eventFormData.eventCountry = address_components[i].long_name;
            }
        }
        $scope.eventFormData.eventCity = city;
        $scope.eventFormData.eventState = state;
        $scope.eventFormData.organizationCity = city;
        /*if(place == ""){
            if(city != "")
                    place = city;
            else if(state != "")
                    place = state;
            else
                place = $scope.eventFormData.eventCountry;
        }*/
        /*var n = s.indexOf('?');
        s = s.substring(0, n != -1 ? n : s.length);*/
        $scope.eventFormData.eventPlace = address_components[0].long_name;
        $scope.$apply();
    });
    
    $scope.getLocation = function(val) {
        /*return $http.get('http://profile.srcm.net:80/api/v2/cities/', {
            params: {
                name__icontains: val
            }
        }).then(function(response){
            return response.data.results;        
        });*/
    };
}]);