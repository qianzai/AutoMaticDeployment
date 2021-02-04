### AutoMaticDeployment---自动部署

<hr/>

#### 项目简介
**使用Github的webhooks进行代码的自动化部署**

本项目是个人最近搞的一个小工具，自己最近在用hexo部署个人博客（地址：http://www.gongsir.club/blog）  
部署完成之后，ui感觉还不错，于是自己就写了几篇文章发布上去，但是就遇到一个问题：  
我每次写完博客，然后`hexo g -d`之后，代码会自动提交到github上面（https://github.com/gongsir0630/blog.git）  
这时，通过https://gongsir0630.github.io/blog 自然可以访问到，但是因为速度慢，我将网站部署在自己  
的服务器上，使用nginx部署静态项目，这就意味着我每次需要去服务器重新从github拉取最新代码，以此更  
新网站。那这样太繁琐了，我不想每次都去手动更新代码，于是借助github（gitee也提供了）的webhooks，   
搭建了一个自动部署：  
webhooks可以在每次收到push之后向一个指定的url发送一个post请求，此项目正是基于此产生的，通过post  
请求触发服务器执行一个脚本（或者执行linux命令），自动从github拉取代码，并更新nginx，这样就可以实  
现网站自动更新。

#### 技术栈
- Java编程
- SpringBoot

#### 使用说明
1、克隆代码到本地或者服务器，修改端口号，编译打包：  
编辑src/main/resources下的application.properties文件，修改端口号，然后使用maven命令编译打包
```shell
mvn clean install -Dmaven.test.skip=true
```

2、后台运行项目：  
```shell
nohup java -jar AutomaticDeployment.jar > AutomaticDeployment.out 2>&1 &
```

3、访问http://{your_website}:{port}/hello,显示“hello”表示部署成功

4、配置webhooks（以github为例）:  
在项目的settings页面，点击左侧webhook选项，点击new新建webhooks，填写url，并在url拼接需要执行  
的shell脚本的位置：
![mark](http://cdn.gongsir.club/blog/20200402/it9QRShppxXu.png?imageslim)

配置url：http://{your_website}:{port}/linux/exec?cmd={cmd}&secret={secret}  

参数说明：

其中cmd表示需要执行的shell脚本的位置：/root/xxx/update.sh：
```shell
echo "========== 开始执行home.sh脚本 =========="
echo "进入blog所在目录"
cd /usr/local/nginx/html/blog
## 拉取最新代码
echo "从github拉取最新代码"
git pull
## 重启nginx
echo "重启nginx"
../../sbin/nginx -s reload
## 打印提示语句
echo "========== 网站更新完成 =========="
```

secret表示自定义密码，这里需要和代码一致（默认：gongsir0630），以此验证用户身份，  
如需修改，请编辑src/main/java/club.gongsir.linux.controller.DemoController中exec方法的secret字符串：
![mark](http://cdn.gongsir.club/blog/20200402/3cHCd4NlLvvN.png?imageslim)

5、保存webhooks配置即可，这样当仓库的代码更新之后，就会自动发post请求以触发shell脚本的执行。

6、执行成功返回：  
![mark](http://cdn.gongsir.club/blog/20200402/5q8TSaYGcooC.png?imageslim)

#### 后期更新
使用github的secret签名完成用户身份验证

#### 联系我
个人主页（含联系方式）：http://www.gongsir.club