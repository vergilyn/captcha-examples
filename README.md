# captcha-examples
验证码的形式

## 1. 
阿里：https://help.aliyun.com/knowledge_list/43120.html?spm=a2c4g.11186623.6.615.tVIWBz  
网易：http://dun.163.com/trial/jigsaw  
极验：http://www.geetest.com;  SDK: https://github.com/GeeTeam/gt3-java-sdk  

## 2.部分乱七八糟的问题
1) 未找到"滑动验证"的源代码实现！  

2) spring boot如何指定所有的controller请求路径url（不管GET/POST, 不管是返回页面/数据）必须是"*.html"?  
<font color="red">（未解决）</font> DispatcherServlet会导致无法请求到js、css等资源。  
http://412887952-qq-com.iteye.com/blog/2315107  
http://412887952-qq-com.iteye.com/blog/2398639

https://segmentfault.com/q/1010000009496167  

```
    // 这会导致js、css等资源文件无法获取; 感觉等价于：application.properties -> server.servlet-path=*.html
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        registration.getUrlMappings().clear();
        registration.addUrlMappings("*.html");
        return registration;
    }
    
    // extends WebMvcConfigurerAdapter 即使重写addResourceHandlers也无法获取资源文件
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
```

3) IDEA + Spring boot 热部署。（devtools参考pom.xml配置）  
解决：因为IDEA默认自动编译关闭了，所以需要自己执行"build project，ctrl + F9"  