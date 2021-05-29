package tw.ym.fshra.service;

import org.springframework.stereotype.Service;
import tw.ym.fshra.bean.*;
import tw.ym.fshra.dao.*;
import tw.ym.fshra.pojo.CommendRsp;
import tw.ym.fshra.pojo.FoodRiskReq;
import tw.ym.fshra.pojo.FoodRiskRsp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author H-yin on 2020.
 */
@Service
public class FoodRiskService {

    @Resource
    private SubsHazViewDao subsHazViewDao;

    @Resource
    private ConcStoredDao concStoredDao;

    @Resource
    private ConsumeViewDao consumeViewDao;

    @Resource
    private ConsumeItgViewDao consumeItgViewDao;

    @Resource
    private MrlViewDao mrlViewDao;

    public CommendRsp getFoodIntakeCommmend(String foodid, Double bodyWeight) {
        List<ConcStored> foodViewList = concStoredDao.getRelateConcByFood(foodid);
        Double minFoodIntake = 9999999999.0;//先假設最小值為一極大值，以便覆蓋
        Double foodIntake;
        CommendRsp itc = CommendRsp.builder().build();
        if(!foodViewList.isEmpty()) {
            Map<String, Integer> subsidMap = new HashMap<>();
            for (int i = 0; i < foodViewList.size(); i++) {
                subsidMap.put(foodViewList.get(i).getSubsid(), i);
            }
            List<SubsHazView> subsHazards = subsHazViewDao.getHazardById(new ArrayList<>(subsidMap.keySet()));
            Double atdi;
            Double concMean;
            if (subsHazards != null && !subsHazards.isEmpty()) {
                for (SubsHazView sh : subsHazards) {
                    atdi = this.getATDIRfD(sh);
                    concMean = foodViewList.get(subsidMap.get(sh.getSubsid())).getConcMean();
                    foodIntake = this.culIntakeCommend(atdi, concMean, bodyWeight);// kg/day
                    if (foodIntake != 0.0 && foodIntake < minFoodIntake) {
                        //等於 0 代表沒有 RfD 或 conc=0，不列入考量
                        minFoodIntake = foodIntake;
                        itc.setSubsid(sh.getSubsid());
                        itc.setSubsName(sh.getSnameCh() + " (" + sh.getSname() + ")");
                    }
                }
            }
            itc.setIntakeCommmend(minFoodIntake == 9999999999.0 ? 0.0 : minFoodIntake);
        }
        return itc; // g/day
    }

    public FoodRiskRsp getFoodRisk(FoodRiskReq frReq) {
        MrlView mrl = mrlViewDao.getMrlData(frReq.getFoodid(), frReq.getSubsid());
        SubsHazView sh = subsHazViewDao.getHazardData(frReq.getSubsid()).get(0);
        ConcStored cv = concStoredDao.getConcData(frReq.getFoodid(), frReq.getSubsid()).get(0);
        Double RfD = this.getATDIRfD(sh);
        String RfDref = this.getATDIRfDRef(sh);
        Double conc = cv.getConcMean();
        Double consume;
        if (RfD != 0.0) {//不等於0代表有RfD可以計算風險
            if (frReq.getConsume() == null && frReq.getAge() == null) {
                //攝食量、年齡皆未輸入，查該性別全體國人食品攝食表
                ConsumeItgView csv = consumeItgViewDao.getConsume(
                        frReq.getFoodid(), frReq.getGender());
                //使用平均值
                consume = csv.getCsMean();
            } else if (frReq.getConsume() == null) {
                //攝食量未輸入，查該年齡層國人食品攝食表
                ConsumeView csv = consumeViewDao.getSpecificConsume(
                        frReq.getFoodid(), frReq.getAge(), frReq.getGender());
                consume = csv.getCsMean();
            } else {
                //都有輸入，以使用者輸入攝食量為攝食量
                consume = frReq.getConsume();
            }
            return FoodRiskRsp.builder()
                    .subsATDIRfD(RfD)
                    .subsATDIRfDRef(RfDref)
                    .mrl(mrl == null ? null : mrl.getMrl())
                    .consumeMean(consume)
                    .concMean(conc)
                    .intakeCommmend((conc == null || conc == 0) ? null :
                            this.culIntakeCommend(RfD, conc, frReq.getBodyWeight()))
                    .personalRisk(this.culPercentADI(conc, RfD, consume, frReq.getBodyWeight()))
                    .regulationRisk(mrl == null ? null :
                            this.culPercentADI(mrl.getMrl(), RfD, consume, frReq.getBodyWeight()))
                    .build();
        } else {
            return FoodRiskRsp.builder().build();
        }
    }

    public Double getATDIRfD(SubsHazView subsHazard) {
        if (subsHazard != null) {
            if (subsHazard.getAdi() != null) {
                return subsHazard.getAdi();
            } else if (subsHazard.getTdi() != null) {
                return subsHazard.getTdi();
            } else if (subsHazard.getOralRfD() != null) {
                return subsHazard.getOralRfD();
            } else {
                return 0.0;// mg/kg-day
            }
        } else {
            return 0.0;
        }
    }

    public String getATDIRfDRef(SubsHazView subsHazard) {
        if (subsHazard != null) {
            if (subsHazard.getAdiRef() != null && !subsHazard.getAdiRef().equals("")) {
                return subsHazard.getAdiRef() + " (ADI)";
            } else if (subsHazard.getTdiRef() != null && !subsHazard.getTdiRef().equals("")) {
                return subsHazard.getTdiRef() + " (TDI)";
            } else if (subsHazard.getOralRfDref() != null && !subsHazard.getOralRfDref().equals("")) {
                return subsHazard.getOralRfDref() + " (RfD)";
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    private Double culIntakeCommend(Double RfD, Double conc, Double bodyWeight) {
        if (conc != 0.0) {
            return ((RfD * bodyWeight) / conc) * 1000;// (mg/day)/(mg/kg) * 1000 g/kg
        } else {
            return 0.0;
        }
    }

    private Double culMOE(Double conc, Double pod, Double consume) {
        Double exposure = conc * consume;
        if (pod != null && pod != 0.0 && exposure != 0.0) {
            return pod / exposure;
        } else {
            return 0.0;
        }
    }

    private Double culPercentADI(Double conc, Double RfD, Double consume, Double bodyWeight) {
        return ((conc * (consume / 1000)) / (RfD * bodyWeight)) * 100;
    }

}
