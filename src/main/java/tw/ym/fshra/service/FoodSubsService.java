package tw.ym.fshra.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import tw.ym.fshra.bean.*;
import tw.ym.fshra.dao.*;
import tw.ym.fshra.pojo.FoodConcContent;
import tw.ym.fshra.pojo.FoodSubsDetail;
import tw.ym.fshra.pojo.FoodSubsList;
import tw.ym.fshra.util.DataProcess;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author H-yin on 2020.
 */
@Service
public class FoodSubsService {

    @Resource
    private PassStoredDao passStoredDao;

    @Resource
    private ConcStoredDao concStoredDao;

    @Resource
    private MrlViewDao mrlViewDao;

    @Resource
    private SubsClassDao subsClassDao;

    public List<FoodSubsList> getFoodSubsList(Map<String, String> subsClasses, String foodid) {
        List<FoodSubsList> foodSubsLists = new ArrayList<>();
        if (subsClasses != null) {
            for (Map.Entry<String, String> entrySet : subsClasses.entrySet()) {
                FoodSubsList foodSubsList = new FoodSubsList();
                foodSubsList.setSubsClassName(entrySet.getValue());
                foodSubsList.setFoodSubsDetailList(this.getFoodSubsDetailList(entrySet.getKey(), foodid));
                foodSubsList.setFoodConcList(this.getFoodConcList(entrySet.getKey(), foodid));
                foodSubsLists.add(foodSubsList);
            }
        }
        return foodSubsLists;
    }

    /**
     * 取得該食品所有相關濃度明細
     *
     * @param foodid 食品編號
     * @return FoodConcContent list
     */
    public List<FoodConcContent> getFoodConcList(String smainId, String foodid) {
        List<PassStored> conclistViewList = passStoredDao.getConcDetail(smainId, foodid);
        List<FoodConcContent> foodConcContentList = new ArrayList<>();
        for (PassStored fv : conclistViewList) {
            FoodConcContent foodConcContent = new FoodConcContent();
            foodConcContent.setConcid(fv.getConcid());
            foodConcContent.setSubsName(fv.getSNameCh() + " (" + fv.getSName() + ")");
            foodConcContent.setSubsConc((fv.getDtConc() < fv.getMdl() || fv.getDtConc() == 0) ?
                    "未檢出" : fv.getDtConc().toString());
            foodConcContent.setConcRef(fv.getConcRef());
            foodConcContent.setConcDTtime(fv.getDtTime());

            foodConcContentList.add(foodConcContent);
        }
        return foodConcContentList.isEmpty() ? null : foodConcContentList;
    }

    /**
     * 取得該食品所有相關物質明細
     *
     * @param foodid 食品編號
     * @return FoodSubsDetail list
     */
    public List<FoodSubsDetail> getFoodSubsDetailList(String smainId, String foodid) {
        List<ConcStored> foodViewList = concStoredDao.getConcData(foodid, smainId);
        List<FoodSubsDetail> fsContentList = new ArrayList<>();
        List<String> subsidStr = new ArrayList<>();
        for (ConcStored cv : foodViewList) {
            subsidStr.add(cv.getSubsid());
        }
        List<MrlView> mrlViews = mrlViewDao.getMrlData(foodid, subsidStr);
        for (ConcStored fv : foodViewList) {
            //找限量
            MrlView mrlView = (MrlView) CollectionUtils.find(mrlViews,
                    obj -> ((MrlView) obj).getSubsid().equals(fv.getSubsid())
                            && ((MrlView) obj).getFoodid().equals(fv.getFoodid()));

            FoodSubsDetail foodSubsDetail = new FoodSubsDetail();
            foodSubsDetail.setSubsName(fv.getSNameCh() + " (" + fv.getSName() + ")");
            foodSubsDetail.setSubsConc((fv.getConcMax() < fv.getMinMdl() || fv.getConcMax() == 0) ?
                    "未檢出" : fv.getConcMax().toString());
            foodSubsDetail.setConcCount(fv.getConcCount());
            foodSubsDetail.setConcDate(fv.getConcEdate()
                    + " ~ " + fv.getConcLdate());
            foodSubsDetail.setSubsMrl(mrlView == null ?
                    "從缺" : mrlView.getMrl().toString());
            foodSubsDetail.setSubsMrlRef(mrlView == null ?
                    "尚無限量資料" : mrlView.getMrlRef());
            foodSubsDetail.setMrlUpdatedTime(mrlView == null ?
                    new Date() : mrlView.getMrlUpdated());
            foodSubsDetail.setSubsid(fv.getSubsid());
            foodSubsDetail.setSubsIntro(fv.getIntro() == null ?
                    "<h2>抱歉，資料尚在建立或維護...</h2>" : fv.getIntro());
            foodSubsDetail.setSubsIntroRef(fv.getIntroRef());

            fsContentList.add(foodSubsDetail);
        }

        return fsContentList;
    }

    /**
     * 取得該食品所有相關物質主分類
     *
     * @param foodid 食品編號
     * @return subsList
     */
    public Map<String, String> getFoodSubsClass(String foodid) {
        List<String> sMainList = concStoredDao.getRelateSMainIdByFood(foodid);
        if (!sMainList.isEmpty()) {
            //查物質分類之最大類
            List<SubsClass> scList = subsClassDao.getSubsById(sMainList);
            return DataProcess.listToLinkedHashMap(scList,
                    SubsClass::getSmainId, SubsClass::getSnameCh);
        } else {
            return null;
        }
    }

}
