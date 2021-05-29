import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.ym.fshra.FsrisApplication;
import tw.ym.fshra.crawler.IntroPageJob;
import tw.ym.fshra.scheduled.RiskRankJob;
import tw.ym.fshra.service.RiskRankService;

/**
 * @author H-yin on 2020.
 */
@RunWith(SpringRunner.class)//指定使用的單元測試類: Spring-Test (SpringJUnit4ClassRunner 的新名字)
@SpringBootTest(classes = FsrisApplication.class)//指定springBoot的啟動類，模擬啟動環境
public class CrawlerTest {

    @Test
    public void spiderTest() {
        IntroPageJob introPageJob = new IntroPageJob();
        introPageJob.spiderJob();
    }

    @Test
    public void culRiskTest() {
        RiskRankJob riskRankJob = new RiskRankJob();
        riskRankJob.culRiskRank();
    }

    @Test
    public void getRiskRank() {
        RiskRankService riskRankService = new RiskRankService();
        System.out.println(riskRankService.getRiskRankRsp("A"));
    }
}
