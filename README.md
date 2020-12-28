# 项目： teclan-todo

## 环境
- JDK8+
- Maven3.6.1+

进入项目根路径后，执行以下命令，以安装 [`teclan-jwt`](https://github.com/teclan/teclan-jwt)  和 [`teclean-flyway`](https://github.com/teclan/teclan-flyway) 依赖

``` 
git clone https://github.com/teclan/teclan-jwt
cd teclan-jwt
mvn install -D maven.test.skip=true

git clone https://github.com/teclan/teclan-flyway
cd teclan-jwt
mvn install -D maven.test.skip=true
```

## 运行
在项目根目录下执行 

```
mvn tomcat:run  
```
启动完成后访问地址 ` http://localhost:8080/teclan-todo`


