// 后端服务根路径
var BASE_URL="/teclan-spring-mvc";

// 前端根路径,前后端分离请设置为空，否则与 BASE_URL 同值
var ROOT_URL=BASE_URL;

//  全局分页大小
var PAGE_SIZE=5;

// 用于编辑页面，缓存被选中行的标识
var SELECT_ID=-1;

$._messengerDefaults = {
           extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top messenger-on-center'
};

// 版权
var  COPY_RIGHT = "©2019 Teclan 广西xxxx公司";

// 全局页脚信息，一般含版权，联系方式等
var GLOBAL_FOOTER_HTML ='<footer id="default_footer" style="text-align:center">'+COPY_RIGHT+'</footer>';

// 全局的分页代码
var GLOBAL_PAGINATION_HTML='<nav aria-label="..."> '
                +'   <ul class="pager"> '
                +'         <li><a href="#" id="first" onclick="getFirst()">首页</a></li> '
                +'         <li><a href="#" id="previous" onclick="getPrevious()">上一页</a></li> '
                +'         <li><a href="#" id="info"></a></li> '
                +'         <li><a href="#" id="next" onclick="getNext()">下一页</a></li> '
                +'         <li><a href="#" id="last" onclick="getLast()">末页</a></li> '
                +'    </ul>'
                +' </nav>';
