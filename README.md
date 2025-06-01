# NJUSE2024 软件工程与计算Ⅰ 课程大作业

在已有的基础上，结合课程学习和JVM文档阅读，基于Java实现符合JVM规范的类加载器和字节码执行引擎，包含类文件解析、动态加载、链接初始化、指令执行等核心模块。

## 核心模块
1. Java CommandLine：预热，使用Commons CLI自定义/解析命令行参数，处理参数绑定和错误校验 
2. ClassFileReader（类文件读取器）：实现类路径搜索机制，支持目录路径(dir/subdir)、JAR/ZIP归档文件、通配符路径(dir/*)和复合路径(dirA;dirB/*.jar)
3. ClassFileParser（类文件解析）​：解析Java类文件（.class）的二进制结构，处理字节流和数据结构转换。
4. ClassLoader（类加载器）：实现双亲委托加载机制，处理类依赖关系。
5. Instructions：构建运行时栈帧，生成字节码指令。

