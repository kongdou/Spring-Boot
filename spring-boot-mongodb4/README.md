# boot-mongodb4
Spring Boot 2 &amp; mongodb 4.x

# Getting Started

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)

### Mongodb启用3.4新特性

进入客户端mongo shell 命令行输入如下代码 
*	db.adminCommand( { setFeatureCompatibilityVersion: "3.4" } )
*	返回
*	{ "ok" : 1 }
*	即可使用3.4新特性
	
使用collation本地化排序
