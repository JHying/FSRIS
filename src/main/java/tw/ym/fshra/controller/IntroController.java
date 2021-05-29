package tw.ym.fshra.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tw.ym.fshra.pojo.IntroReq;
import tw.ym.fshra.pojo.IntroRsp;
import tw.ym.fshra.service.IntroService;

import javax.annotation.Resource;
import java.util.Map;


/**
 * RESTful API
 * For 毒化物介紹頁
 */
@RestController
@ResponseBody
public class IntroController {

    @Resource
    private IntroService introService;

    @ApiOperation(value = "取得毒化物第一層分類", notes = "進入該頁時取得毒化物第一層分類")
    @GetMapping(value = "/initFirstSubs", produces = "application/json;charset=UTF-8")
    public Map<String, String> getMainSubsClass() {
        return introService.getMainSubsClass();
    }

    @ApiOperation(value = "取得毒化物第二層分類", notes = "根據第一層取得第二層分類")
    //等同 @RequestBody
    //@ApiImplicitParam(name = "Subsid", value = "分類編號", required = true, dataType = "String")
    @GetMapping(value = {"/initSecondSubs/{id}", "/initSecondSubs"},
            produces = "application/json;charset=UTF-8")
    public Map<String, String> getSecondSubsClass(@PathVariable(value = "id", required = false)
                                                          String id) {
        return introService.getSecondSubsClass(id);
    }

    @ApiOperation(value = "取得毒化物第三層分類", notes = "根據第二層取得第三層分類")
    @GetMapping(value = {"/initThirdSubs/{id}", "/initThirdSubs"},
            produces = "application/json;charset=UTF-8")
    public Map<String, String> getThirdSubsClass(@PathVariable(value = "id", required = false)
                                                         String id) {
        return introService.getThirdSubsClass(id);
    }

    @ApiOperation(value = "取得毒化物介紹", notes = "根據分類編號取得相關介紹")
    @PostMapping(value = "/Intro", produces = "application/json;charset=UTF-8")
    public IntroRsp getIntro(@RequestBody IntroReq introReq) {
        return introService.getIntro(introReq.getSubsid());
    }

}
