//package com.unioncoding.config;
//
//import org.apache.catalina.Context;
//import org.apache.catalina.connector.Connector;
//import org.apache.tomcat.util.descriptor.web.SecurityCollection;
//import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * https相关配置
// * Created by 吴晓冬 on 2017/9/10.
// */
//@Configuration
//public class HttpsConfig
//{
//    @Bean
//    public EmbeddedServletContainerFactory servletContainerFactory()
//    {
//        TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory = new TomcatEmbeddedServletContainerFactory()
//        {
//            @Override
//            protected void postProcessContext(Context context)
//            {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//
//        tomcatEmbeddedServletContainerFactory.addAdditionalTomcatConnectors(httpConnector());
//        return tomcatEmbeddedServletContainerFactory;
//    }
//
//    @Bean
//    public Connector httpConnector()
//    {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//
//        return connector;
//    }
//}
