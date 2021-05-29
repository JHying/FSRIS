import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tw.ym.fshra.FsrisApplication;
import tw.ym.fshra.bean.*;
import tw.ym.fshra.dao.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author H-yin on 2020.
 */
@RunWith(SpringRunner.class)//指定使用的單元測試類: Spring-Test (SpringJUnit4ClassRunner 的新名字)
@SpringBootTest(classes = FsrisApplication.class)//指定springBoot的啟動類，模擬啟動環境
public class JpaTest {

    @Resource
    private PassStoredDao passStoredDao;

    @Resource
    private SubsClassDao subsClassDao;

    @Resource
    private ConcStoredDao concStoredDao;

    @Resource
    private SubsHazardDao subsHazardDao;

    @Resource
    private ConsumeItgViewDao consumeItgViewDao;

    @Resource
    private BaseDAO baseDAO;

    @Test
    public void passViewTest1() {
        List<PassStored> passStoredList = passStoredDao.getPassDetail("A-7", "B-1-2");
        System.out.println("*****" + passStoredList.toString());
    }

    @Test
    public void subclassTest() {
        List<String> sMainList = concStoredDao.getRelateSMainIdByFood("B-1-2");
        List<SubsClass> passViewList = subsClassDao.getSubsById(sMainList);
        System.out.println("*****" + passViewList.toString());
    }

    @Test
    public void hazardTest() {
        List<ConcStored> foodViewList = concStoredDao.getRelateConcByFood("B-1-2");
        Map<String, Integer> subsidMap = new HashMap<>();
        for (int i = 0; i < foodViewList.size(); i++) {
            subsidMap.put(foodViewList.get(i).getSubsid(), i);
        }
        List<SubsHazard> subsHazards = subsHazardDao.getHazardById(new ArrayList<>(subsidMap.keySet()));
        System.out.println("*****" + subsHazards.toString());
    }

    @Test
    public void consumeTest() {
        List<ConcStored> foodViewList = concStoredDao.getConcData("A");
        Set<String> foodidStr = new HashSet<>();
        for (ConcStored cv : foodViewList) {
            foodidStr.add(cv.getFoodid());
        }
        //取得食品相關攝食量
        List<ConsumeItgView> consumeItgViews = consumeItgViewDao.getWholeConsume(foodidStr);
        for (ConcStored fv : foodViewList) {
            System.out.println("*****" + fv.getFoodid());
            ConsumeItgView csByfood = (ConsumeItgView) CollectionUtils.find(consumeItgViews,
                    obj -> ((ConsumeItgView) obj).getFoodid().equals(fv.getFoodid()));
            System.out.println("*****" + csByfood.toString());
        }
//        System.out.println("*****" + csByfood.toString());
    }

    @Test
    public void concTest() {
        List<ConcStored> foodViewList = concStoredDao.getConcData("B");
        System.out.println("*****" + foodViewList.toString());
    }

    @Test
    public void concTest2() {
        List<ConcStored> foodViewList = concStoredDao.getConcData("B-3-1", "B");
        System.out.println("*****" + foodViewList.toString());
    }

    @Test
    public void cacheTest(){
        List<PassStored> passStoredList = passStoredDao.getConcDetail("B");
        System.out.println("*****" + passStoredList.toString());
        List<PassStored> passStoredList2 = passStoredDao.getConcDetail("B");
        System.out.println("*****" + passStoredList2.toString());
    }

    @Test
    public void spTest(){
        baseDAO.callSP("conc_proc");
    }

}
