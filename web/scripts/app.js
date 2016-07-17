$( document ).ready(function() {
  $('#loginContainer').show();
  $('#respMessage').hide();
  $('#compileContainer').hide();

  $('#login-form-link').click(function(e) {
      $('#respMessage').hide();
      $("#login-form").delay(100).fadeIn(100);
      $("#register-form").fadeOut(100);
      $('#register-form-link').removeClass('active');
      $(this).addClass('active');
      e.preventDefault();
  });

  $('#register-form-link').click(function(e) {
      $('#respMessage').hide();
      $("#register-form").delay(100).fadeIn(100);
      $("#login-form").fadeOut(100);
      $('#login-form-link').removeClass('active');
      $(this).addClass('active');
      e.preventDefault();
  });

  $('#headerContainer').click(function(e){
    $('#login-form-link').click();
  });
});


var jCompiler = angular.module("jCompiler", []);
var baseURL = 'http://localhost:8080/jcompiler';
jCompiler.controller("LoginController", function ($scope, $http) {

    $scope.login = function () {
        // construct Json object.
        var dataObj = {
            "loginName":$scope.loginName,
            "password":$scope.password
        }

        var config = {
            headers : {
                'Content-Type': 'application/json;charset=utf-8;'
            }
        }
        //TODO: externalize base url.
        var res = $http.post(baseURL + '/security/login', dataObj, config);
        res.success(function(data, status, headers, config) {
            $('#respMessage').addClass('panel panel-success');
            $('#respMessage').show();
            $('#compileContainer').show();
            $('#LoginContainer').hide();
            $scope.respMessage = data.message;
        });

        res.error(function(data, status, headers, config) {
            $('#respMessage').addClass('panel panel-danger');
            $('#respMessage').show();
            $scope.respMessage = data.message;
        });
        // Making the fields empty
        $scope.loginName='';
        $scope.password='';
    };

    $scope.register = function () {
       // use $.param jQuery function to serialize data from JSON
        var dataObj = {
            "loginName":$scope.regLoginName,
            "password":$scope.regPassword,
            "email":$scope.regEmail
        }

        var config = {
            headers : {
                'Content-Type': 'application/json;charset=utf-8;'
            }
        }

        var res = $http.post(baseURL + '/security/register', dataObj, config);
        res.success(function(data, status, headers, config) {
            $('#respMessage').addClass('panel panel-success');
            $('#respMessage').show();
            $('#login-form-link').click();
            $scope.respMessage = data.message;
        });
        res.error(function(data, status, headers, config) {
            //alert( "failure message: " + JSON.stringify({data: data}));
           $('#respMessage').addClass('panel panel-danger');
           $('#respMessage').show();
           $scope.respMessage = data.message;
        });
        // Making the fields empty
        //
        $scope.regLoginName='';
        $scope.regPassword='';
        $scope.regEmail='';
    };
});

jCompiler.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

jCompiler.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function($scope, file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        var resp = $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
        resp.success(function(data, status, headers, config) {
            $scope.output = data.message;
            alert(data.message);
        });
        resp.error(function(data, status, headers, config) {
            $scope.output = data.message;
            alert(data.message);
        });
    }
}]);

jCompiler.controller('CompileController', ['$scope', 'fileUpload', function($scope, fileUpload){

    $scope.uploadFile = function(){
        var file = $scope.myFile;
        console.log('file is ' );
        console.dir(file);
        var uploadUrl = baseURL + '/compile';
        fileUpload.uploadFileToUrl($scope, file, uploadUrl);
    };

}]);


