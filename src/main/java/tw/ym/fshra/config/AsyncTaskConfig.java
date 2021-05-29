package tw.ym.fshra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 異步處理相關設定 (spring bean)<br>
 * 避免第三方執行因排隊造成卡頓
 */
@Configuration
@EnableAsync
public class AsyncTaskConfig {
	@Bean
	public TaskExecutor getTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setMaxPoolSize(10);	// 最大執行數
		taskExecutor.setCorePoolSize(5);	// 最小執行數
		taskExecutor.setQueueCapacity(20);	// 等待佇列
		taskExecutor.initialize();
		return taskExecutor;
	}
}
