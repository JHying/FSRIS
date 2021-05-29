package tw.ym.fshra.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tw.ym.fshra.pojo.RiskRankReq;
import tw.ym.fshra.pojo.RiskRankRsp;
import tw.ym.fshra.service.RiskRankService;

import javax.annotation.Resource;


/**
 * RESTful API
 * For 食品建議攝食量之風險
 */
@RestController
@ResponseBody
public class RiskRankController {

    @Resource
    private RiskRankService riskRankService;

    @ApiOperation(value = "取得風險分級資訊")
    @PostMapping(value = "/RiskRank", produces = "application/json;charset=UTF-8")
    public RiskRankRsp getRiskRank(@RequestBody RiskRankReq riskRankReq) {
        return riskRankService.getRiskRankRsp(riskRankReq.getSmainId());
    }

}
