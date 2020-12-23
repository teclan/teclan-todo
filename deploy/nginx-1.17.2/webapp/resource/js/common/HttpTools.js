function async(method,url,suc,fai){
	var result="";
	$.ajax({
        type: method,
        url:url,
        async:true,

        success: function (res) {
		   result =  suc(res);
       },

       failure: function (res) {
    	   result =  fai(res);
       }

    });

    return result;

};


function async(method,url,json,suc,fai){
	var result="";
	$.ajax({
        type: method,
        contentType: "application/json;charset=utf-8",
        url:url,
        data:json,
        async:true,

        success: function (res) {
		   result =  suc(res);
       },

       failure: function (res) {
    	   result =  fai(res);
       }

    });

    return result;

};



function sync(method,url,suc,fai){
	var result="";
	$.ajax({
        type: method,
        url:url,
        async:false,

        success: function (res) {
		   result =  suc(res);
       },

       failure: function (res) {
    	   result =  fai(res);
       }

    });

   return result;
};

function sync(method,url,json,suc,fai){
	var result="";
	$.ajax({
        type: method,
        contentType: "application/json;charset=utf-8",
        url:url,
        data:json,
        async:false,

        success: function (res) {
		   result =  suc(res);
       },

       failure: function (res) {
    	   result =  fai(res);
       }

    });

    return result;

};
