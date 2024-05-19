# myai

这个项目是直接用Spring AI生成的sample,在此基础上做了修改。目的是学习Spring AI提供的相关功能。

2024年五一期间，学习langchain，langflow，又看了了一个很好的项目https://vectorvein.com/ 于是基于该项目开源的前后端代码进行学习研究以加深对相关知识的理解应用。

该项目的源码地址为https://github.com/AndersonBY/vector-vein 在阅读和学习的过程中，对项目整洁的代码风格和逻辑非常钦佩。

于是利用该项目现有的前端代码，实现Spring AI版本的后端代码，纯为本人学习使用。

## Prerequisites

前端项目可到上面所说的源码地址下载vector-vein的前端代码并根据文档描述启动。

后端需要安装mysql，可以执行doc/schema/mysql/flow.sql创建数据库

## 部署与运行

```
./mvnw spring-boot:run
```

## 访问

可以访问下项目的健康检查地址

```shell 
curl 'localhost:8080/i/health'
```