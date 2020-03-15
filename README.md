# spring-cloud-full
The full example of Java Application which be hosted and deployed in Spring Cloud environment

## Boot Application Launched Sequence
- eureka-server (服务注册中心)
- config-server (服务配置中心, native和github)
- gateway-service (网关服务zuul, 智能路由, 负载均衡)
- zipkin-service (Sluth, 查看请求在微服务系统里的链路关系)
- monitor-service (turbine, 熔断监控器)
- admin-service (Spring Boot Admin Server 控制台)
- uaa-service (OAuth2, 服务统一鉴权与授权, 生成token)
- user-service (业务模块 user, 通过feign调用uaa-service)
- blog-service (业务模块 blog, 通过feign调用user-service)
- log-service (通过RabbitMq接收业务日志, 然后持久化到mysql. 可以配置成ELK)