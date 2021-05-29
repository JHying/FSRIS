package tw.ym.fshra.crawler;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import tw.ym.fshra.dao.SubsClassDao;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author H-yin on 2019.
 *         <p>
 *         抽取網頁內容
 */
@Component
public class IntroPageProcessor implements PageProcessor {

    //測試網域 環毒中心 (設定 request cookie 時需要)
//    private static final String URL_domain = "nehrc.nhri.org.tw";

    @Resource
    private SubsClassDao subsClassDao;

    //爬蟲訪問參數
    private Site site = Site.me()
//            .setDomain(URL_domain)//域名(設定 request cookie 時需要)
            .setSleepTime(5000)//休息時間
            .setRetryTimes(30)//重試時間
            .setCharset("utf-8")//網頁編碼
            .setTimeOut(3000)//逾時時間
            //仿照瀏覽器進入
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");

    //爬蟲過程
    @Override
    public void process(Page page) {

        if (page.getStatusCode() == HttpStatus.SC_NOT_FOUND
                || page.getHtml().xpath("//title/text()").toString().contains("404")) {
            //如果網頁為 404 不存在，或 title 節點包含 404
            //將 skip 設為 true，不進行後續及 pipeline 處理
            page.setSkip(true);
        } else {
            //獲取物質簡介
            String intro;
            if (page.getUrl().toString().contains("fda.gov.tw")) {
                //爬自食藥署
                intro = page.getHtml().xpath("//div[@id='ContentPlaceHolder1_PageContentBox_PnlCms']").toString();
            } else {
                //國家環境毒物中心
                String point = page.getHtml().xpath("//div[@id='content']/p[1]").toString();
                if (point.contains("本文重點")) {
                    point = point + page.getHtml().xpath("//div[@id='content']/p[2]").toString();
                } else {
                    point = page.getHtml().xpath("//div[@id='content']/p[2]").toString() +
                            page.getHtml().xpath("//div[@id='content']/p[3]").toString();
                }
                intro = page.getHtml().xpath("//div[@id='content']/ul[1]").toString() + point;
            }

            page.putField("subsUrl", page.getUrl().toString());
            page.putField("subsIntro", intro);//包到 field 給 pipeline 處理

            //新增後續的url等待抓取
            List<String> subsList = subsClassDao.selectAllUrlExceptFirst(subsClassDao.selectFirstUrl().getSubsid());
            page.addTargetRequests(subsList);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}