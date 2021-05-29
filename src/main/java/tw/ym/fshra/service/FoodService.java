package tw.ym.fshra.service;

import org.springframework.stereotype.Service;
import tw.ym.fshra.bean.FoodClass;
import tw.ym.fshra.dao.FoodClassDao;
import tw.ym.fshra.pojo.FoodSubsRsp;
import tw.ym.fshra.pojo.FoodUnitRsp;
import tw.ym.fshra.util.DataProcess;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author H-yin on 2020.
 */
@Service
public class FoodService {

    @Resource
    private FoodClassDao foodClassDao;

    @Resource
    private FoodSubsService foodSubsService;

    @Resource
    private FoodRiskService foodRiskService;

    /**
     * 取得食品主分類
     *
     * @return 主分類 Map('foodid', 'fNameCh')
     */
    public Map<String, String> getMainFoodClass() {
        List<FoodClass> classList = foodClassDao.selectMainClass();
        return DataProcess.listToLinkedHashMap(classList,
                FoodClass::getFoodid, FoodClass::getFnameCh);
    }

    /**
     * 取得食品第二層分類
     *
     * @param foodid 食品編號
     * @return 主分類 Map('foodid', 'fNameCh')
     */
    public Map<String, String> getSecondFoodClass(String foodid) {
        List<FoodClass> classList;
        if (foodid == null || foodid.equals("")) {
            classList = foodClassDao.selectSecondClass();
        } else {
            classList = foodClassDao.selectSecondClass(foodid);
        }
        return DataProcess.listToLinkedHashMap(classList,
                FoodClass::getFoodid, FoodClass::getFnameCh);
    }

    /**
     * 取得食品第三層分類
     *
     * @param foodid 食品編號
     * @return 主分類 Map('foodid', 'fNameCh')
     */
    public Map<String, String> getThirdFoodClass(String foodid) {
        List<FoodClass> classList;
        if (foodid == null || foodid.equals("")) {
            classList = foodClassDao.selectThirdClass();
        } else {
            classList = foodClassDao.selectThirdClass(foodid);
        }
        return DataProcess.listToLinkedHashMap(classList,
                FoodClass::getFoodid, FoodClass::getFnameCh);
    }

    /**
     * 取得食品第三層分類
     *
     * @param foodid 食品編號
     * @return 主分類 Map('foodid', 'fNameCh')
     */
    public FoodUnitRsp getFoodUnitDesc(String foodid) {
        FoodUnitRsp foodUnitRsp = new FoodUnitRsp();
        if (foodid == null || foodid.equals("")) {
            foodUnitRsp.setUnitDesc("克");
        } else {
            foodUnitRsp.setUnitDesc(foodClassDao.selectBean(foodid).getUnitDesc());
        }
        return foodUnitRsp;
    }

    /**
     * 取得食品內含毒化物之相關資訊
     *
     * @param foodid 食品編號
     * @return FoodSubsRsp
     */
    public FoodSubsRsp getFoodSubsRsp(String foodid, Double bodyWeight) {
        FoodSubsRsp foodSubsRsp = new FoodSubsRsp();
        Map<String, String> subsClasses = foodSubsService.getFoodSubsClass(foodid);
        foodSubsRsp.setFoodIntakeCommmend(foodRiskService.getFoodIntakeCommmend(foodid, bodyWeight));
        foodSubsRsp.setSubsClasses(subsClasses);
        foodSubsRsp.setFoodSubsList(foodSubsService.getFoodSubsList(subsClasses, foodid));
        return foodSubsRsp;
    }

}
