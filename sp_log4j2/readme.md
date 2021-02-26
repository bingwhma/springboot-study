WebFlux 的几个特征
1、异步非阻塞
2、响应式(reactive)函数编程
3、不再拘束于Servlet容器

Spring MVC is built on the Servlet API and uses a synchronous blocking I/O architecture whth a one-request-per-thread model.

Spring WebFlux is a non-blocking web framework built from the ground up to take advantage of multi-core, next-generation processors and handle massive numbers of concurrent connections.

WebFlux 并不能使接口的响应时间缩短，它仅仅能够提升吞吐量和伸缩性。

WebFlux 主要还是应用在异步非阻塞编程模型，而 Spring MVC 是同步阻塞的


Mono：返回 0 或 1 个元素，即单个对象。
Flux：返回 N 个元素，即 List 列表对象。