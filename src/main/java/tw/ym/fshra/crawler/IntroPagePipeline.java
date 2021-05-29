package tw.ym.fshra.crawler;

import org.springframework.stereotype.Component;
import tw.ym.fshra.bean.SubsClass;
import tw.ym.fshra.dao.BaseDAO;
import tw.ym.fshra.dao.SubsClassDao;
import tw.ym.fshra.util.Log;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author H-yin on 2019.
 *         <p>
 *         儲存抽取內容至資料庫
 */
@Component
public class IntroPagePipeline implements Pipeline {

    @Resource
    private BaseDAO baseDAO;

    @Resource
    private SubsClassDao subsClassDao;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<SubsClass> subslist = subsClassDao.selectByIntroRef(resultItems.get("subsUrl"));
        String intro = resultItems.get("subsIntro");

        //同一個網站可能不只介紹一種物質
        for (SubsClass subs : subslist) {
            if (intro == null || intro.equals("")) {
                subs.setIntroUpdated(new Date());
                baseDAO.updateBean(subs);
//                System.out.println("--- [" + subs.getSubsid() + ", " + subs.getIntroRef() + "] 此次爬蟲無內容.");
                Log.info("--- [" + subs.getSubsid() + ", " + subs.getIntroRef() + "] 此次爬蟲無內容.");
            } else {
                //更新介紹的內容
                subs.setIntro(intro);
                subs.setIntroUpdated(new Date());
                baseDAO.updateBean(subs);
                Log.info("--- [" + subs.getSubsid() + ", " + subs.getIntroRef() + "] 更新簡介及時間.");
            }
        }
    }

}
