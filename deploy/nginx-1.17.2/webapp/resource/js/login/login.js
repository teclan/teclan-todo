


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

                        showMessage(data.message);

                        //window.open(BASE_URL+"/resource/home/home.html");
                        window.location.href=BASE_URL+"/resource/home/home.html";

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

    var json = '{"id":"'+id+'","password":"'+password+'"}';

 	async('POST',BASE_URL+'/user/login.do',json,handleSuccess,handleFailure);

};