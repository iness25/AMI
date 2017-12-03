var app = angular.module('angularjs-starter', []);

					app.controller('MainCtrl', function($scope,$http) {
						$scope.reclamation= [];
						$http({
							header: 'content-type:application/json',
							method: 'GET',
							url: 'https://busfinder.000webhostapp.com/Reclamations.php',
						}).then(function(response){
							
							$scope.reclamation = response.data.Reclamation;
															

						});

					$scope.submit = function () {
						console.log("hello")
					}
				
				
				
			})
			
			