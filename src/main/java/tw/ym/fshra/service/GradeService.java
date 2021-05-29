package tw.ym.fshra.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import tw.ym.fshra.bean.*;
import tw.ym.fshra.util.DataProcess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author H-yin on 2020.
 */
@Service
public class GradeService {

    public FoodFail getFailDetail(ConcStored fv) {
        return FoodFail.builder()
                .foodNameCh(fv.getFnameCh())
                .subsName(fv.getSName())
                .subsNameCh(fv.getSNameCh())
                .failRate(fv.getFailRate())
                .foodid(fv.getFoodid())
                .subsid(fv.getSubsid())
                .smainId(fv.getSmainId())
                .build();
    }

    public RiskScore getScoreDetail(SubsHazView subsHaz, List<ConcStored> foodViewList,
                                    List<PassStored> pvList, List<ConsumeItgView> consumeItgViews) {
        RiskScore scoreDetail = RiskScore.builder()
                .subsName(subsHaz.getSname())
                .subsNameCh(subsHaz.getSnameCh())
                .scoreA(subsHaz.getScoreMax())//取得該物質危害性質分數
                .scoreB(this.getATDIScore(subsHaz))// 取得ADI或TDI
                .scoreC(this.getConsumeScore(subsHaz, foodViewList, consumeItgViews))//取得攝取量分數
                .scoreD(this.getLegalScore(subsHaz, pvList))//取得法規分數
                .subsid(subsHaz.getSubsid())
                .smainId(subsHaz.getSmainId())
                .build();
        //(A+B)xCxD 計算總分
        int total = (scoreDetail.getScoreA() + scoreDetail.getScoreB())
                * scoreDetail.getScoreC() * scoreDetail.getScoreD();
        scoreDetail.setTotal(total);//總分
        return scoreDetail;
    }

    private Double getATDIRfD(SubsHazView subsHaz) {
        if (subsHaz.getOralRfD() != null) return subsHaz.getOralRfD();
        else if (subsHaz.getAdi() != null) return subsHaz.getAdi();
        else if (subsHaz.getTdi() != null) {
            return subsHaz.getTdi();
        } else {
            return 0.0;// mg/kg-day
        }
    }

    private int getATDIScore(SubsHazView subsHaz) {
        //比對分數
        //取得評分標準
        List<Double> bgradelist = this.getATDIGrading(subsHaz);
        Double atdiRfD = this.getATDIRfD(subsHaz);
        //將計算之分數加入其中並排序
        if (atdiRfD != 0.0) {
            // !=0 代表有 ATDI
            bgradelist.add(atdiRfD);
            bgradelist.sort(Collections.reverseOrder());//值愈高分數愈低 - desc
            //該分數之索引+1代表落點區間 = 分數
            return bgradelist.indexOf(atdiRfD) + 1;
        } else {
            //沒有ATDI為0分
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    private int getConsumeScore(SubsHazView subsHaz, List<ConcStored> foodViewList,
                                List<ConsumeItgView> consumeItgViews) {
        //取得與毒化物相關的食品
        List<ConcStored> fvListBySubs = new ArrayList<>();
        fvListBySubs.addAll(CollectionUtils.select(foodViewList,
                obj -> ((ConcStored) obj).getSubsid().equals(subsHaz.getSubsid())));

        Double subsIntake = 0.0;//攝入量總和
        ConsumeItgView csByfood;
        for (ConcStored fv : fvListBySubs) {
            csByfood = (ConsumeItgView) CollectionUtils.find(consumeItgViews,
                    obj -> ((ConsumeItgView) obj).getFoodid().equals(fv.getFoodid()));
            //C(平均濃度值) x IR(平均攝食量) = 從該食品攝入之物質量 (mg/kg * g/day) * 1E-6 kg/mg
            //每個物質量相加作為物質攝入量
            subsIntake = subsIntake + (fv.getConcMean() * csByfood.getCsMean() * 1E-6);
        }
        //取得攝入量評分標準
        List<Double> cgradelist = this.getConsumeGrading(subsHaz);
        //將計算之分數加入其中並排序
        cgradelist.add(subsIntake);
        Collections.sort(cgradelist);
        //該分數之索引+1代表落點區間 = 分數
        return cgradelist.indexOf(subsIntake) + 1;
    }

    @SuppressWarnings("unchecked")
    private int getLegalScore(SubsHazView subsHaz, List<PassStored> pvList) {
        //從subsclass-banned看是否被禁止 - 分數=B
        String score;
        if (subsHaz.getBanned().equals("Y")) {
            //被禁止 - 分數=B
            score = "B";
        } else {
            //PassStored 裡 mrl 出現 null - 代表出現在未受規範者
            List<PassStored> pvListBySubsMrl = new ArrayList<>();
            pvListBySubsMrl.addAll(CollectionUtils.select(pvList,
                    obj -> ((PassStored) obj).getSubsid().equals(subsHaz.getSubsid())
                            && ((PassStored) obj).getMrl() == null));

            if (!pvListBySubsMrl.isEmpty()) {
                //有未受規範者=N
                score = "N";
            } else {
                //PassStored 裡的 pass 有沒有 N - 合不合格
                pvListBySubsMrl = new ArrayList<>();
                pvListBySubsMrl.addAll(CollectionUtils.select(pvList,
                        obj -> ((PassStored) obj).getSubsid().equals(subsHaz.getSubsid())
                                && ((PassStored) obj).getPass().equals("N")));
                if (pvListBySubsMrl.isEmpty()) {
                    //合格=P
                    score = "P";
                } else {
                    score = "F";//合格=F
                }
            }
        }
        //比較評分標準，索引+1 = 分數
        return this.getLegalGrading(subsHaz).indexOf(score) + 1;
    }

    private List<Double> getATDIGrading(SubsHazView subsHaz) {
        //從subsClass取得評分標準
        String[] cRuleStr = subsHaz.getAtdiRule().split(",");
        return DataProcess.strToDoubleList(cRuleStr);
    }

    private List<Double> getConsumeGrading(SubsHazView subsHaz) {
        //從subsClass取得評分標準
        String[] cRuleStr = subsHaz.getConsumeRule().split(",");
        return DataProcess.strToDoubleList(cRuleStr);
    }

    private List<String> getLegalGrading(SubsHazView subsHaz) {
        //從subsClass取得評分標準
        String[] cRuleStr = subsHaz.getLegalRule().split(",");
        return new ArrayList<>(Arrays.asList(cRuleStr));
    }

}
