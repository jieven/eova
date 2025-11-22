<h2 align="center" style="text-align:center;">
    <img src="https://foruda.gitee.com/avatar/1763477966560547021/8195707_eova_1763477966.png!avatar200" width="128" />
    <br />
    EovaMeta v4.0.0
</h2>
<h3 align="center">
<a href="https://eova.cn/">最简单的低代码开发平台</a>
</h3>
<h4 align="center">12年持续迭代 · 2025全新重构 · AI+元数据 · 极简依赖</h4>


<p align="center">
	<a target="_blank" href="https://gitee.com/eova/eova/stargazers"><img src="https://gitee.com/eova/eova/badge/star.svg?theme=gvp" alt="Gitee GVP" ></a>
	<a target="_blank" href="https://central.sonatype.com/search?q=cn.eova"><img src="https://img.shields.io/maven-central/v/cn.eova/eova-core" alt="Maven Central Version" ></a>
	<a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html"><img src="https://img.shields.io/badge/JDK-8-green.svg" alt="jdk-8" /></a>
</p>

### 核心优势

| 优势 | 传统开发痛点 | EovaMeta | 用户价值 |
|:---:|------------|------------|------------|
| 轻 | 依赖大量第三方库 | 轻量级依赖 | 维护成本低 |
| 快 | 学不动，学到吐，技术更新快 | 学习成本降低70%，即学即用 | 学习成本低 |
| 稳 | 无法穿越软件生命周期 | 12年持续迭代打磨 | 踩过的坑比你写的代码还长 |
| 省 | 纯手工施肥 | 使用无人机施肥 | 一个人顶一个组 |

### 技术选型说明

| 技术点 | 说明                                                         |
| ------ | ------------------------------------------------------------ |
| Java8  | 经典稳定版本，覆盖广，受众广。                               |
| MySQL8 | 核心库选择最流行的MySql8.0，业务库支持任意类型的数据库（需适配数据源）。 |
| JFinal | 极简的后端解决方案，直接跳过庞大且复杂的Spring生态，弯道超车。 |
| Vue3   | 前端主流大三方案中，简单又好上手的。                         |
| 其他   | 无其他技术依赖。                                             |

### 核心技术介绍

核心技术自研情况，往往反馈了一个公司的技术能力，也突出了产品在上层应用，到底层框架的控制、优化及定制能力。

| 技术方案 | 是否自研 | 说明                                                         |
| -------- | -------- | ------------------------------------------------------------ |
| JFinal   | 国产开源 | 从2012使用至今，源码级掌握。                                 |
| EovaPlus | 自研     | 基于AI大模型实现的智能辅助编程套件。                         |
| MetaData | 自研     | 元数据驱动，实现业务与技术解耦，支持可视化配置与动态扩展，适配复杂场景需求。 |
| Template | 自研     | 支持开发者自定义模版，降低重复开发成本，确保代码质量一致性，加速企业级应用构建。 |
| Hook     | 自研     | 保障平台灵活性与稳定性，实现无侵入式扩展，适配个性化业务需求。 |
| Render   | 自研     | 提升前端开发灵活性，实现无侵入式扩展，适配个性化UI展示需求。 |
| EovaUI   | 自研     | 基于Vue3为EovaMeta定制的UI解决方案，无需受限于第三方。       |



## 快速部署

### 第一步：环境确认

```java
// Java环境确认 必须是1.8.x
C:\Users\Jieven>java -version
java version "1.8.0_202"

// Maven环境确认 3.6.x +
C:\Users\Jieven>mvn -v
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
```

### 第二步：下载项目

```
git clone https://gitee.com/eova/eova.git
```

### 第三步：编译安装

```
mvn clean install -Dmaven.test.skip=true
// Maven配置阿里云镜像仓库
// 默认推荐使用IDEA 2024, Eclipse需自行探索.
```

### 第四步：数据库创建与配置

```
// 创建数据库(Mysql8)
/demo/sql/demo.sql
/demo/sql/eova_meta.sql

// 修改JDBC配置
/demo/src/main/resources/eova/dev.txt

// 修改eova数据源DB账号密码
eova.user=root
eova.pwd=xxxx

// 修改main数据源DB账号密码
main.user=root
main.pwd=xxxx
```

### 第五步：运行Demo

`src/main/java/cn/eova/meta/RunEovaMeta.java`

### 第六步：使用现代浏览器访问

http://127.0.0.1:9090/

### BUG反馈与交流

<img src="https://static.eova.cn/doc/20251114145355798.png" alt="image-20251114145355741" style="zoom:50%;" />