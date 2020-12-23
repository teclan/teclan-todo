var currentPage=1;
var totals=0;
var totalPages=0;
var isFirst=false;
var isLast=false;


function query(currentPage){

     var result;

     var handleSuccess = function(response){
            if(response !== undefined) {
                    try {

                       if(response.code==200){

                            // 内容相关
                            var data=eval(response.data);

                            var tbody = document.getElementById('tbody');

                            var tableContent='';
                            $(data).each(function (index){
                                var val=data[index];
                                var tr='<tr class="active text-center"> ';
                                tr+='<td> '+val.id+' </td>';
                                tr+='<td> '+val.name+' </td>';
                                tr+='<td> '+val.phone+' </td>';
                                tr+='<td> '+val.id_card+' </td>';
                                tr+='<td class="active text-left" style="width: 200px;"> '
                                +'<button class="btn btn-default" type="button" data="'+val.id+'" onclick="del(this)">删除</button> '
                                +'<button class="btn btn-default" type="button" data="'+val.id+'" onclick="edit(this)">编辑</button> '
                                +' </td>';

                                tr+='</tr>';

                                tableContent+=tr;
                            });
                            tbody.innerHTML = tableContent;

                           showMessage(response.message);

                            var pageInfo=eval(response.pageInfo);
                            flushPageInfo(pageInfo);
                       }else{
                            showMessage(response.message);
                       }

                    } catch(e) {
                        alert("error!"+e);
                        return false;
                    }
           }
      };

 	 var handleFailure = function(o){
 	 };

   var json = '{"currentPage":'+currentPage+',"pageSize":'+PAGE_SIZE+'}';
   async('POST',BASE_URL+'/user/page.do',json,handleSuccess,handleFailure);

};

function del(val){
     commonDel(val,'/user/delete.do');
};

function edit(val){
    getEditPage(val,"/resource/user/edit.html");
};

function add(){
    getPage("/resource/user/add.html");
};

// 用于详细页面的设值
function setDataForDetail(data){
  $("#id").attr("value",data.id);
  $("#id").attr("readonly","true");
  $("#name").attr("value",data.name);
  $("#phone").attr("value",data.phone);
  $("#id_card").attr("value",data.id_card);
}

function getById(){
    getDetail('/user/get.do');
}

function doUpdate(json){
     commonUpdate('/user/update.do',json);
};

function doAdd(json){
     commonAdd('/user/add.do',json);
};

