package tw.ym.fshra.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tw.ym.fshra.bean.*;
import tw.ym.fshra.dao.*;
import tw.ym.fshra.service.GradeService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author H-yin on 2020.
 */
@Service
public class RiskRankJob {

    @Resource
    private SubsHazViewDao subsHazViewDao;

    @Resource
    private PassStoredDao passStoredDao;

    @Resource
    private SubsClassDao subsClassDao;

    @Resource
    private GradeService gradeService;

    @Resource
    private ConcStoredDao concStoredDao;

    @Resource
    private ConsumeItgViewDao consumeItgViewDao;

    @Resource
    private RiskScoreDao riskScoreDao;

    @Resource
    private FoodFailDao foodFailDao;

    @PostConstruct
    @Scheduled(cron = "${risk.rank.job.cron}")
    public void culRiskRank() {
        List<SubsClass> scList = subsClassDao.selectMainClass();
        if (scList != null && !scList.isEmpty()) {
            for (SubsClass sc : scList) {
                if (sc.getAtdiRule() == null && sc.getLegalRule() == null
                        && sc.getConsumeRule() == null && sc.getBanned() == null) {
                    //無法排序，只做超標率
                    this.culFoodFailList(sc.getSmainId());
                } else {
                    this.culRiskScoreList(sc.getSmainId());
                    this.culFoodFailList(sc.getSmainId());
                }
            }
        }
    }

    private void culRiskScoreList(String smainId) {
        //蒐集底下毒化物
        List<SubsHazView> subsHazViewList = subsHazViewDao.getHazardData(smainId);
        List<RiskScore> scoreDetails = new ArrayList<>();
        if (subsHazViewList != null) {
            //蒐集底下濃度資料
            List<ConcStored> foodViewList = concStoredDao.getConcData(smainId);
            //蒐集底下濃度明細
            List<PassStored> pvList = passStoredDao.getConcDetail(smainId);

            Set<String> foodidStr = new HashSet<>();
            for (ConcStored cv : foodViewList) {
                foodidStr.add(cv.getFoodid());
            }
            //取得食品相關攝食量
            List<ConsumeItgView> consumeItgViews = consumeItgViewDao.getWholeConsume(foodidStr);

            for (SubsHazView subsHaz : subsHazViewList) {
                scoreDetails.add(gradeService.getScoreDetail(subsHaz, foodViewList,
                        pvList, consumeItgViews));
            }
            //依總分由高至低排序
            scoreDetails.sort((o1, o2) -> o2.getTotal() - o1.getTotal());
            //寫入資料庫
            RiskScore riskScore;
            for (int i = 0; i < scoreDetails.size(); i++) {
                riskScore = scoreDetails.get(i);
                riskScore.setId(scoreDetails.get(i).getSmainId() + (i + 1));
                riskScore.setRank(i + 1);
                riskScoreDao.saveOrUpdateBean(riskScore);
            }
        }
    }

    private void culFoodFailList(String smainId) {
        //蒐集底下有超標者
        List<ConcStored> foodViewList = concStoredDao.getFailData(smainId);
        List<FoodFail> failDetails = new ArrayList<>();
        if (foodViewList != null && !foodViewList.isEmpty()) {
            for (ConcStored fv : foodViewList) {
                failDetails.add(gradeService.getFailDetail(fv));
            }
            //依超標率由高至低排序
//            failDetails.sort((o1, o2) -> o2.getFailRate().intValue() - o1.getFailRate().intValue());
            failDetails.sort(Comparator.comparingDouble(FoodFail::getFailRate).reversed());
            //寫入資料庫
            FoodFail foodFail;
            for (int i = 0; i < failDetails.size(); i++) {
                foodFail = failDetails.get(i);
                foodFail.setId(failDetails.get(i).getSmainId() + (i + 1));
                foodFail.setRank(i + 1);
                foodFailDao.saveOrUpdateBean(foodFail);
            }
        }
    }

}
