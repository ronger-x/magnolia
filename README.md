# Magnolia
> Magnolia —— 木兰,又名迎春花,春天的信使

## 环境搭建
有以下两种方式安装 rabbitmq

- 使用 chocolatey 安装(官方推荐)

- 使用安装程序安装

### 使用 chocolatey安装

- 安装 chocolatey

```PowerShell
`Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('`[`https://chocolatey.org/install.ps1`](https://chocolatey.org/install.ps1)`'))`
```

- 安装成功后执行 `choco install rabbitmq`

### 使用安装程序安装

#### 注意事项

- 每台机器上只能存在一个版本的 Erlang

- 需要使用管理员权限进行安装

- 安装路径不能包含中文及特殊字符

#### 安装 Erlang

- [下载地址](https://www.rabbitmq.com/which-erlang.html)

- 添加环境变量 `ERLANG_HOME` `C:\Program Files\erl{version}`

- 在环境变量 `Path` 内添加 `%ERLANG_HOME%\bin\erl.exe`

#### 安装 rabbitmq

- [下载地址](https://github.com/rabbitmq/rabbitmq-server/releases)

- 将程序安装至 `C:\Program Files\RabbitMQ Server` 文件夹下

### 命令行工具

`Rabbitmq_server-{version}\sbin`目录中有一些脚本，你可以运行这些脚本命令来控制 RabbitMQ 服务器。

RabbitMQ服务器可以作为应用程序或服务器（两者不能同时）运行

- rabbitmq-server.bat作为应用程序启动代理

- rabbitmq-service.bat管理服务并启动代理

- rabbitmqctl.bat管理运行中的代理

#### 配置环境变量

- 添加环境变量 `RABBITMQ_SERVER` `C:\Program Files\RabbitMQ Server\rabbitmq_server-{version}`

- 在环境变量 `Path `内添加 `%RABBITMQ_SERVER%\sbin`

现在你可以从任何（管理员）命令提示符运行`rabbitmq`命令

#### 将 RabbitMQ Server 作为应用程序运行

使用 `sbin `目录下的 `rabbitmq-server.bat` 启动

#### 定制 RabbitMQ 环境变量

你可以使用默认配置运行服务。你也可以 [自定义 RabbitMQ 环境](https://www.rabbitmq.com/configure.html#customise-windows-environment) 或者 [编辑配置](https://www.rabbitmq.com/configure.html#configuration-file)。

> 修改环境后需要重启 rabbitMQ 服务

#### 启动代理作为应用程序

运行以下命令,在后台启动一个节点（不附加到命令提示符）。

```PowerShell
rabbitmq-server.bat -detached
```

> 更多详细信息请查看 [Installing on Windows Manually](https://www.rabbitmq.com/install-windows-manual.html#overview)

#### 使用扩展插件

运行以下命令,启用管理插件

```PowerShell
rabbitmq-plugins enable rabbitmq_management
```

> 更多扩展插件信息请查看 [Plugins ](https://www.rabbitmq.com/plugins.html)

### 添加用户

使用 `rabbitmqctl add_user username password `  命令添加用户

```PowerShell
rabbitmqctl add_user weixin weixin001
```

### 配置用户角色

使用 `rabbitmqctl set_user_tags username user_tag` 配置用户角色

```PowerShell
rabbitmqctl set_user_tags weixin none
```

用户角色:

- 超级管理员(administrator)

可登陆管理控制台，可查看所有的信息，并且可以对用户，策略(policy)进行操作。

- 监控者(monitoring)

可登陆管理控制台，同时可以查看rabbitmq节点的相关信息(进程数，内存使用情况，磁盘使用情况等)

- 策略制定者(policymaker)

可登陆管理控制台, 同时可以对policy进行管理。但无法查看节点的相关信息(上图红框标识的部分)。

- 普通管理者(management)

仅可登陆管理控制台，无法看到节点信息，也无法对策略进行管理。

- 其他(none)

无法登陆管理控制台，通常就是普通的生产者和消费者。

### 设置访问权限

设置 weixin 用户的权限，指定允许访问的vhost以及write/read

```PowerShell
rabbitmqctl set_permissions -p "/" weixin ".*" ".*" ".*" 
```

### 一些问题

#### windows server 服务器启动服务后里面自动停止,查看日志提示 `Could not find handle.exe, please install from sysinternals` 问题

解决办法:

- [下载 handle.exe](https://docs.microsoft.com/zh-cn/sysinternals/downloads/handle)

- 下载成功后,解压到对应目录

- 在环境变量 `Path` 连接解压后文件所在的目录

#### 重新安装 rabbitMQ 后,启动服务后里面自动停止问题

卸载 rabbitMQ 后,删除 `C:\Users\{username}\AppData\Roaming\RabbitMQ` 目录后再安装

## 项目开发

### 目录结构
- config: `rabbitMQ` 配置信息目录
- constant: 消息队列名称常量目录
- entity: 传输数据实体类目录
- receiver: 消息消费(消息接收)类目录
- sender: 消息生产(消息发送)类目录
- web: 对外接口目录

### 注意事项
1. 构建消息服务时需修改配置文件`application.yml`中以下配置
    - spring.rabbitmq.username
    - spring.rabbitmq.password
2. 在 `RabbitQueueConstan.java` 中添加你创建和需要接收的消息队列名称
3. 在 `receiver` 目录下创建自己的`消息消费(接收)类`
4. 在 `sender` 目录下创建自己的`消息生产(发送)类`
5. 在 `CommonRabbitConfig.java` 中注册你创建的消息队列
