package tw.ym.fshra.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tw.ym.fshra.pojo.*;
import tw.ym.fshra.service.FoodRiskService;
import tw.ym.fshra.service.FoodService;

import javax.annotation.Resource;
import java.util.Map;


/**
 * RESTful API
 * For 食品建議攝食之食品分類及相關毒化物資訊
 */
@RestController
@ResponseBody
public class FoodController {

    @Resource
    private FoodService foodService;

    @Resource
    private FoodRiskService foodRiskService;

    @ApiOperation(value = "取得食品第一層分類")
    @GetMapping(value = "/initFirstFood", produces = "application/json;charset=UTF-8")
    public Map<String, String> getMainFoodClass() {
        return foodService.getMainFoodClass();
    }

    @ApiOperation(value = "取得食品第二層分類")
    @GetMapping(value = {"/initSecondFood/{id}", "/initSecondFood"},
            produces = "application/json;charset=UTF-8")
    public Map<String, String> getSecondFoodClass(@PathVariable(value = "id", required = false)
                                                          String id) {
        return foodService.getSecondFoodClass(id);
    }

    @ApiOperation(value = "取得食品第三層分類")
    @GetMapping(value = {"/initThirdFood/{id}", "/initThirdFood"},
            produces = "application/json;charset=UTF-8")
    public Map<String, String> getThirdFoodClass(@PathVariable(value = "id", required = false)
                                                         String id) {
        return foodService.getThirdFoodClass(id);
    }

    @ApiOperation(value = "取得食品單位資訊")
    @GetMapping(value = {"/initFoodUnit/{id}", "/initFoodUnit"},
            produces = "application/json;charset=UTF-8")
    public FoodUnitRsp getFoodUnit(@PathVariable(value = "id", required = false)
                                           String id) {
        return foodService.getFoodUnitDesc(id);
    }

    @ApiOperation(value = "取得食品相關毒化物資訊")
    @PostMapping(value = "/Recommend", produces = "application/json;charset=UTF-8")
    public FoodSubsRsp getFoodSubs(@RequestBody FoodSubsReq fsReq) {
        return foodService.getFoodSubsRsp(fsReq.getFoodid(), fsReq.getBodyWeight());
    }

    @ApiOperation(value = "取得食品毒化物風險資訊")
    @PostMapping(value = "/Recommend/Risk", produces = "application/json;charset=UTF-8")
    public FoodRiskRsp getFoodSubsRisk(@RequestBody FoodRiskReq frReq) {
        return foodRiskService.getFoodRisk(frReq);
    }

}
