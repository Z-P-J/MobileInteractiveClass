# 移动互动课堂MobileInteractiveClassroom

----------------------------------
## 统一使用IDEA进行开发
----------------------------------

# 一、如何将该项目导入IDEA
## 方法一：
### 在IDEA中依次点击File->New->Project from Version Control->Git ，然后输入https://github.com/Z-P-J/MobileInteractiveClass.git ，设置好工程目录，然后点击Clone。

## 方法二(需要电脑已安装git)：
### 在控制台输入git clone https://github.com/Z-P-J/MobileInteractiveClass.git D:/MobileInteractiveClass/ 将项目克隆到D盘MobileInteractiveClass目录下(也可以自己设置clone路径)，然后在IDEA中依次点击File->Open打开clone的工程路径。

# 二、导入项目到IDEA后
### 1. 修改文件src/com/interactive/classroom/utils/DBHelper.java中的数据库名字，驱动名，用户名以及密码
### 2. 将sql文件夹下的sql文件导入到自己的数据库(xm05.sql或xm05_5.0.sql选其一，低版本的mysql选mysql5.0.sql)
### 3. 注意web/WEB-INF/lib路径下的jar包是否已导入(根据mysql版本选择导入mysql-connector-java-5.0.4-bin.jar或mysql-connector-java-8.0.12.jar)
### 4. 可能还需要配置jdk版本和tomcat的lib目录等

# 三、使用IDEA进行多人开发同一个项目 https://blog.csdn.net/qq_27093465/article/details/76080878