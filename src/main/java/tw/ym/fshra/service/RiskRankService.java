package tw.ym.fshra.service;

import org.springframework.stereotype.Service;
import tw.ym.fshra.bean.*;
import tw.ym.fshra.dao.*;
import tw.ym.fshra.pojo.FoodFailDetail;
import tw.ym.fshra.pojo.RiskRankRsp;
import tw.ym.fshra.pojo.RiskScoreDetail;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author H-yin on 2020.
 */
@Service
public class RiskRankService {

    @Resource
    private FoodFailDao foodFailDao;

    @Resource
    private RiskScoreDao riskScoreDao;

    @Resource
    private RankDetailService rankDetailService;

    public RiskRankRsp getRiskRankRsp(String smainId) {
        return RiskRankRsp.builder()
                .riskScoreList(this.getRiskScoreList(smainId))
                .foodFailList(this.getFoodFailList(smainId))
                .build();
    }

    private List<RiskScoreDetail> getRiskScoreList(String smainId) {
        //蒐集底下毒化物
        List<RiskScore> riskScores = riskScoreDao.selectBySubs(smainId);
        List<RiskScoreDetail> scoreDetails = new ArrayList<>();
        if (riskScores != null && !riskScores.isEmpty()) {
            for (RiskScore rs : riskScores) {
                scoreDetails.add(rankDetailService.getScoreDetail(rs));
            }
            //依總分由高至低排序
            scoreDetails.sort((o1, o2) -> o2.getTotal() - o1.getTotal());
        }
        return scoreDetails;
    }

    private List<FoodFailDetail> getFoodFailList(String smainId) {
        //蒐集底下有超標者
        List<FoodFail> foodFails = foodFailDao.selectBySubs(smainId);
        List<FoodFailDetail> failDetails = new ArrayList<>();
        if (foodFails != null && !foodFails.isEmpty()) {
            for (FoodFail ff : foodFails) {
                failDetails.add(rankDetailService.getFailDetail(ff));
            }
            //依總分由高至低排序
//            failDetails.sort((o1, o2) -> o2.getFailRate().intValue() - o1.getFailRate().intValue());
            failDetails.sort(Comparator.comparingDouble(FoodFailDetail::getFailRate).reversed());
        }
        return failDetails;
    }

}
