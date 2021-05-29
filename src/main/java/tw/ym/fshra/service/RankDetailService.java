package tw.ym.fshra.service;

import org.springframework.stereotype.Service;
import tw.ym.fshra.bean.FoodFail;
import tw.ym.fshra.bean.PassStored;
import tw.ym.fshra.bean.RiskScore;
import tw.ym.fshra.dao.PassStoredDao;
import tw.ym.fshra.pojo.FoodFailConc;
import tw.ym.fshra.pojo.FoodFailDetail;
import tw.ym.fshra.pojo.RiskScoreDetail;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author H-yin on 2020.
 */
@Service
public class RankDetailService {

    @Resource
    private PassStoredDao passStoredDao;

    public RiskScoreDetail getScoreDetail(RiskScore rs) {
        return RiskScoreDetail.builder()
                .subsName(rs.getSubsName())
                .subsNameCh(rs.getSubsNameCh())
                .scoreA(rs.getScoreA())//取得該物質危害性質分數
                .scoreB(rs.getScoreB())// 取得ADI或TDI
                .scoreC(rs.getScoreC())//取得攝取量分數
                .scoreD(rs.getScoreD())//取得法規分數
                .total(rs.getTotal())
                .build();
    }

    public FoodFailDetail getFailDetail(FoodFail ff) {
        return FoodFailDetail.builder()
                .foodNameCh(ff.getFoodNameCh())
                .subsName(ff.getSubsName())
                .subsNameCh(ff.getSubsNameCh())
                .failRate(ff.getFailRate())
                .foodConcList(this.getConcDetail(ff.getFoodid(), ff.getSubsid()))
                .build();
    }

    private List<FoodFailConc> getConcDetail(String foodid, String subsid) {
        List<FoodFailConc> foodConcs = new ArrayList<>();
        List<PassStored> pvListByFoodSubs = passStoredDao.getConcDetail(subsid, foodid);
        for (PassStored pv : pvListByFoodSubs) {
            FoodFailConc foodPassConc = FoodFailConc.builder()
                    .concid(pv.getConcid())
                    .dtConc((pv.getDtConc() < pv.getMdl() || pv.getDtConc() == 0) ?
                            "未檢出" : pv.getDtConc().toString())
                    .dtTime(pv.getDtTime())
                    .mrl(pv.getMrl() == null ? "未規範" : pv.getMrl().toString())
                    .concRef(pv.getConcRef() == null ? "未規範" : pv.getConcRef())
                    .mrlRef(pv.getMrlRef())
                    .pass(pv.getPass() != null && pv.getPass().equals("Y") ? "合格" : "不合格").build();
            foodConcs.add(foodPassConc);
        }
        return foodConcs;
    }

}
