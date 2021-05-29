package tw.ym.fshra.crawler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tw.ym.fshra.dao.SubsClassDao;
import tw.ym.fshra.util.Log;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author H-yin on 2019.
 *         <p>
 *         爬蟲排程
 */
@Service
public class IntroPageJob {

    //默認使用名稱注入，若無匹配名稱，則注入類
    @Resource
    private IntroPageProcessor introPageProcessor;

    @Resource
    private IntroPagePipeline introPagePipeline;

    @Resource
    private SubsClassDao subsClassDao;

    //啓動項目時就開啓
    //使用排程，設定於 application.properties
    @PostConstruct
    @Scheduled(cron = "${webmagic.intro.job.cron}")
    public void spiderJob() {

        //從第一筆物質資料開始爬
        String url = subsClassDao.selectFirstUrl().getIntroRef();

        Log.info(">> 爬蟲開始.");
        Log.info("--- 初始 URL：" + url);

        long startTime, endTime;
        startTime = System.currentTimeMillis();
        this.runSpider(url, introPageProcessor, introPagePipeline);
        endTime = System.currentTimeMillis();

        Log.info("--- 爬蟲結束. (" + ((endTime - startTime) / 1000) + " sec)");
    }

    private void runSpider(String url, PageProcessor processor, Pipeline pipeline) {
        try {
            //呼叫 processor 執行爬蟲
            Spider spider = Spider.create(processor);
            spider.addUrl(url);
            // 呼叫 pipeline 將資料寫入DB
            spider.addPipeline(pipeline);
            spider.setExitWhenComplete(true);//抓取結束就退出,sleep後不繼續抓取.
            //單執行緒
//            spider.run();
            //多執行緒
            spider.thread(5);
            spider.start();
        } catch (Exception e) {
            Log.info("--- runSpider 發生問題 (詳見 error.log)");
            Log.error(">> runSpider error: " + e.getMessage());
//            System.out.println(e.getMessage());
        }
    }

}
