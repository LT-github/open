package com.lt.lxc.configuration;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class TaskExecutePoolConfig  {

	@Bean("resultCalculate")
	public ThreadPoolTaskExecutor defaultThreadPool() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//核心线程数目
		executor.setCorePoolSize(8);
		//指定最大线程数
		executor.setMaxPoolSize(10);
		//队列中最大的数目
		executor.setQueueCapacity(16);
		//线程名称前缀
		executor.setThreadNamePrefix("defaultThreadPool_");
		//rejection-policy：当pool已经达到max size的时候，如何处理新任务
		//CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
		//对拒绝task的处理策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		//线程空闲后的最大存活时间
		executor.setKeepAliveSeconds(5);
		//加载
		executor.initialize();
		return executor;
	}
}

