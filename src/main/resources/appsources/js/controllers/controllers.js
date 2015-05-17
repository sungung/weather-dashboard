angular
    .module('RDash')
    .controller('ObservationListCtrl', ['$scope', '$http', ObservationListCtrl])
    .controller('ForecastCtrl', ['$scope', '$http', ForecastCtrl])

function ObservationListCtrl($scope, $http) {

    $scope.config = {
        title: 'Wind Speed',
        tooltips: true,
        labels: false,
        mouseover: function() {},
        mouseout: function() {},
        click: function() {},
        legend: {
            display: true,
            position: 'right'
        }
    };

    $http.get('api/observation').success(function(data, status, header, config){
        $scope.observations = data;
    });

}

function ForecastCtrl($scope, $http) {
    $http.get('api/forecast').success(function(data, status, header, config){
        $scope.forecast = data;
    })
}