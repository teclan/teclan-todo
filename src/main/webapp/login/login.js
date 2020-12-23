


function register(){
  $.globalMessenger().post({
     message: "未开放!",
      hideAfter: 3,
      type: 'info'
  });

};


function login(id,password){

     var result;

     var handleSuccess = function(data){
            if(data !== undefined) {
                    try {

                       if(data.code==200){

                       var callBack = function(configs){

                            for(var i in configs){
                               var item = configs[i];
                               var key = item.key;

                               var value = data.data[key];

                               localStorage.setItem(key,value);

                               console.log('缓存头请求头信息，key='+key+',value='+value);

                            }
                        }

                         readJsonConfig(ROOT_URL+"/resource/json/header.json",callBack);

                        showMessage(data.message);

                        window.location.href=ROOT_URL+"/resource/home/home.html";

                        }else{
                           showMessage(data.message);
                        }


                    } catch(e) {
                        alert("error!"+e);
                        return false;
                    }
           }
      };

 	 var handleFailure = function(o){
 	 };

    var json = '{"account":"'+id+'","password":"'+password+'"}';

     $.ajaxSettings.async = false;

 	sync('POST',BASE_URL+'/login.do',json,handleSuccess,handleFailure);

};


function logout(){

     var handleSuccess = function(data){
     };

 	 var handleFailure = function(o){
 	 };

    var json = '{"account":"'+localStorage.getItem("USER")+'"}';
 	sync('POST',BASE_URL+'/logout.do',json,handleSuccess,handleFailure);

};