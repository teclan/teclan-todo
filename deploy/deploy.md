# 部署说明

## 前端部署

将 `src\main`下的`webapp` 整个目录复制到 `${path/to/nginx}`,目录结构如下:

``` 
E:\Apps\nginx-1.17.2>dir
 驱动器 E 中的卷没有标签。
 卷的序列号是 0006-A4CA

 E:\Apps\nginx-1.17.2 的目录

2019/07/27  09:40    <DIR>          .
2019/07/27  09:40    <DIR>          ..
2019/07/23  20:08    <DIR>          conf
2019/07/23  20:08    <DIR>          contrib
2019/07/23  20:09    <DIR>          docs
2019/07/23  20:08    <DIR>          html
2019/07/27  09:59    <DIR>          logs
2019/07/23  19:15         3,697,664 nginx.exe
2019/07/27  09:41    <DIR>          temp
2019/07/27  09:40    <DIR>          webapp      # 这里是复制过来的前端代码
               1 个文件      3,697,664 字节
               9 个目录 82,275,295,232 可用字节

```


然后确认 nginx 的配置，打开文件 `${path/to/nginx}/conf/nginx.conf`


``` 


#user  nobody;

# worker进程个数
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid文件（master进程ID的pid文件存放路径）的路径
#pid        logs/nginx.pid;


events {

    #仅对指定的客户端输出debug级别的日志： 语法：debug_connection[IP|CIDR]
    #debug_connection 10.224.66.14;  #或是debug_connection 10.224.57.0/24
    #，仅仅以上IP地址的请求才会输出debug级别的日志，其他请求仍然沿用error_log中配置的日志级别。
    #注意：在使用debug_connection前，需确保在执行configure时已经加入了--with-debug参数，否则不会生效
   worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;
	

    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;
        
        
        # location 匹配是有顺序的，当多个配置都匹配时，使用第一个配置，配置时请注意顺序
        
        # teclan-spring-mvc  是后端的连接标识，即所有的后端请求格式 http://host:port/teclan-spring-mvc/xxxxx
        # 具体使用是根据实际情况调整
        
        location /teclan-spring-mvc {
            root   webapp;
            index  index.html index.htm;
						proxy_pass http://localhost:8080/teclan-spring-mvc; # 负载均衡指向的发布服务tomcat
						proxy_redirect default;
						proxy_connect_timeout 5; #跟代理服务器连接的超时时间，必须留意这个time out时间不能超过75秒，当一台服务器当掉时，过10秒转发到另外一台服务器。
						proxy_set_header X-Real-IP $remote_addr;
        }
		
		
		location / {
            root   webapp;
            index  index.html index.htm;
		}

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}

```

## 后端配置

后端为`Maven`管理的普通`WEB`项目，打成`war`文件，可使用`tomcat`或`jetty`等
容易部署。

在项目下执行以下命令即可生成war包 

``` 
mvn package
```


## nginx 常用命令

``` 
    nginx -s stop       快速停止nginx
    
    nginx -s quit       优雅停止nginx，有连接时会等连接请求完成再杀死worker进程  

    nginx -s reload     优雅重启，并重新载入配置文件nginx.conf

    nginx -s reopen     重新打开日志文件，一般用于切割日志

    nginx -v            查看版本  

    nginx -t            检查nginx的配置文件

    nginx -h            查看帮助信息

 　 nginx -V            详细版本信息，包括编译参数 

   nginx  -c            filename  指定配置文件

```