const app= angular.module("admin-app",[]);
app.controller("product-ctrl",function($scope,$http){
	$scope.form = {
		categoryID:1,
		name : 'roboost',
		status : true,
		number : 0,
		price: 200000,
		describe : 'abcxyz',
		createDate : new Date()
	};
	var data = new FormData();

	$scope.create = function (){
		var item= JSON.stringify(angular.copy($scope.form));
		data.append("product",item);
		$http.post('http://localhost:8080/api/product/create',data,{
			transformRequest : angular.identity,
			headers: {'Content-Type':undefined}
		}).then(resp =>{
			alert("Thêm mới sản phẩm thành công");
		}).catch(error=>{
			alert("lỗi thêm mới sản phẩm!");
			console.log("Error",error);
		});
	}

	$scope.imageChanged1 = function (files){
		data.append("photo1",files[0])
	}
	$scope.imageChanged2 = function (files){
		data.append("photo2",files[0])
	}
	$scope.imageChanged3 = function (files){
		data.append("photo3",files[0])
	}
	$scope.imageChanged4 = function (files){
		data.append("photo4",files[0])
	}
})
