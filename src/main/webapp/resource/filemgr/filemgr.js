var currentPage=1;
var totals=0;
var totalPages=0;
var isFirst=false;
var isLast=false;

function del(val) {

    if(!confirm("此操作会将服务器上对应的文件从磁盘上删除，确认操作吗？")){
        return;
    }
    commonDel(val,'/filemgr/delete.do');
}


function query(currentPage){

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
                                tr+='<td hidden="true"> '+val.id+' </td>';
                                tr+='<td> '+val.filename+' </td>';
                                tr+='<td hidden="true"> '+val.filename+' </td>';
                                tr+='<td> '+val.size+' </td>';
                                tr+='<td> '+val.created_at+' </td>';
                                tr+='<td> '+val.updated_at+' </td>';
                                tr+='<td> '+val.owner_dispay+' </td>';
                                tr+='<td> '+val.authorize_display+' </td>';
                                tr+='<td class="active text-left" style="width: 200px;"> '
                                    +'<button class="btn btn-default" type="button" data="'+val.id+'" onclick="del(this)">删除</button> '
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

   var json = '{"currentPage":'+currentPage+',"pageSize":'+PAGE_SIZE+',"oderBy":"created_at","sort":"desc"}';
   async('POST',BASE_URL+'/filemgr/query.do',json,handleSuccess,handleFailure);

};


// 下拉选项设值使用
function selected(val){
 var data=$(val).attr('data');
 $("#parent_id").attr("value",data);
  $("#parent_title").attr("value",val.text);
 //$("#parent_title").attr("value",$(val).attr('text'));
}
