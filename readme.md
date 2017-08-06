这是一个学习JDBC过程中做的一些基础知识的总结，总体上有很多不足，但抱着记录所学的想法，我还是决定将它认真的做一下。  
其中分为6个包：  
**[com.jdbc.common](https://github.com/StephenHuge/MyJDBCReWrite/tree/master/src/com/jdbc/common)：存储一些基础知识**  
　　其中主要的类是 [CommonJDBC.java](https://github.com/StephenHuge/MyJDBCReWrite/blob/master/src/com/jdbc/common/CommonJDBC.java) 和 [JDBCDao.java](https://github.com/StephenHuge/MyJDBCReWrite/blob/master/src/com/jdbc/common/JDBCDao.java)  ，包中其余的类都是一些POJO类和测试类。 

**[com.jdbc.extensions](https://github.com/StephenHuge/MyJDBCReWrite/tree/master/src/com/jdbc/extensions)： 存储一些初基础之外需要掌握的知识**  
　　其中只有一个类[CommonUpdateBlob.java](https://github.com/StephenHuge/MyJDBCReWrite/blob/master/src/com/jdbc/extensions/CommonUpdateBlob.java)，用来读写数据库（这里使用的是MySQL）中的Blob类型，也就是二进制大对象类型文件（一般存储长文本或者图片）。  
**[com.jdbc.transaction](https://github.com/StephenHuge/MyJDBCReWrite/tree/master/src/com/jdbc/transaction)： 存储JDBC中关于数据库事务的知识**  
　　其中只有一个类[CommonTransaction.java](https://github.com/StephenHuge/MyJDBCReWrite/tree/master/src/com/jdbc/transaction/CommonTransaction.java)，用来简单的定义了一下JDBC中的事务。  
**[com.jdbc.connectionpool](https://github.com/StephenHuge/MyJDBCReWrite/tree/master/src/com/jdbc/connectionpool)： 存储JDBC中关于数据库连接池的知识**  
　　其中主要的类是[CommonConnectionPool.java](https://github.com/StephenHuge/MyJDBCReWrite/blob/master/src/com/jdbc/connectionpool/CommonConnectionPool.java)，其中提到了两种常见的数据库连接池实现：DBCP数据库连接池和C3P0数据库连接池。  
　　包内还有一个它的测试类[TestConnectionPool.java](https://github.com/StephenHuge/MyJDBCReWrite/blob/master/src/com/jdbc/connectionpool/TestConnectionPool.java)。

**[com.jdbc.dbutilstest](https://github.com/StephenHuge/MyJDBCReWrite/tree/master/src/com/jdbc/dbutilstest)： 存储对Apache开发的一个工具类DBUtils的简单测试**  
　　包内只有一个类[CommonDBUtilsTest.java](https://github.com/StephenHuge/MyJDBCReWrite/blob/master/src/com/jdbc/connectionpool/CommonDBUtilsTest.java)，其中用到了`QueryRunner` 类和`ResultSetHandle` 接口的几个实现类。

**[com.jdbc.mytools](https://github.com/StephenHuge/MyJDBCReWrite/tree/master/src/com/jdbc/mytools)： 工具包，存储自己抽取出来的一些工具代码**  
　　工具类只有一个[MyJDBCTools.java](https://github.com/StephenHuge/MyJDBCReWrite/blob/master/src/com/jdbc/mytools/MyJDBCTools.java)，里面有将这些包中的一些知识抽取出来成为了工具方法，详细参见工具包的readme.md文件。

顺序是按照学习先后顺序，这只是一个简陋的不成形的代码的集合，仅作为自己知识的记录。  

在数据库中创建的`schema` 为：`myjdbcrewrite` ，其中有两个`table` ：`user` 和`singer` 。  
table `user` :  

<table>
        <tr>
            <th>id</th>
            <th>user</th>
            <th>password</th>
        </tr>
        <tr>
            <th>1</th>
            <th>李雷</th>
            <th>hanmeimei</th>
        </tr>
        <tr>
            <th>2</th>
            <th>韩梅梅</th>
            <th>lihua</th>
        </tr>
        <tr>
            <th>　</th>
            <th>　</th>
            <th>　</th>
        </tr>
</table>

table `singer` :  


<table>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>bestsong</th>
			<th>avatar</th>
        </tr>
        <tr>
            <th>1</th>
            <th>Jay Chou</th>
            <th>七里香</th>
            <th>　</th>
        </tr>
        <tr>
            <th>2</th>
            <th>林俊杰</th>
            <th>美人鱼</th>
            <th>　</th>
        </tr>
        <tr>
            <th>　</th>
            <th>　</th>
            <th>　</th>
            <th>　</th>
        </tr>
    </table>



