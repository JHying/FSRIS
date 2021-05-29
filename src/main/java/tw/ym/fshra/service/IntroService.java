package tw.ym.fshra.service;

import org.springframework.stereotype.Service;
import tw.ym.fshra.bean.SubsClass;
import tw.ym.fshra.dao.SubsClassDao;
import tw.ym.fshra.pojo.IntroRsp;
import tw.ym.fshra.util.DataProcess;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author H-yin on 2020.
 */
@Service
public class IntroService {

    @Resource
    private SubsClassDao subsClassDao;

    /**
     * 取得毒化物主分類
     *
     * @return 主分類 Map('subsId', 'cNameCh')
     */
    public Map<String, String> getMainSubsClass() {
        List<SubsClass> classList = subsClassDao.selectMainClass();
        return DataProcess.listToLinkedHashMap(classList,
                SubsClass::getSubsid, SubsClass::getSnameCh);
    }

    /**
     * 取得毒化物第二層分類
     *
     * @param subsid 物質編號
     * @return 主分類 Map('subsId', 'cNameCh')
     */
    public Map<String, String> getSecondSubsClass(String subsid) {
        List<SubsClass> classList;
        if (subsid == null || subsid.equals("")) {
            classList = subsClassDao.selectSecondClass();
        } else {
            classList = subsClassDao.selectSecondClass(subsid);
        }
        return DataProcess.listToLinkedHashMap(classList,
                SubsClass::getSubsid,
                obj -> new StringBuilder().append(obj.getSname()).append("-").append(obj.getSnameCh())
                        .toString());
    }

    /**
     * 取得毒化物第三層分類
     *
     * @param subsid 物質編號
     * @return 主分類 Map('subsId', 'cNameCh')
     */
    public Map<String, String> getThirdSubsClass(String subsid) {
        List<SubsClass> classList;
        if (subsid == null || subsid.equals("")) {
            classList = subsClassDao.selectThirdClass();
        } else {
            classList = subsClassDao.selectThirdClass(subsid);
        }
        return DataProcess.listToLinkedHashMap(classList,
                SubsClass::getSubsid,
                obj -> new StringBuilder().append(obj.getSname()).append("-").append(obj.getSnameCh())
                        .toString());
    }

    /**
     * 取得毒化物簡介
     *
     * @param subsid 物質編號
     * @return IntroRsp
     */
    public IntroRsp getIntro(String subsid) {
        SubsClass substances = subsClassDao.selectBean(subsid);
        IntroRsp introRsp = new IntroRsp();

        if (substances != null) {
            introRsp = IntroRsp.builder()
                    .subsName(substances.getSname())
                    .subsNameCh(substances.getSnameCh() + " (" + substances.getSname() + ")")
                    .casrn(substances.getCasrn() == null ? "" : substances.getCasrn())
                    .intro(substances.getIntro() == null
                            || substances.getIntro().equals("")
                            ? "<h1>抱歉，資料尚未建立或尚在維護...</h1>" : substances.getIntro())
                    .introRef(substances.getIntroRef() == null ? "" : substances.getIntroRef())
                    .updateTime(substances.getIntroUpdated()).build();
        }

        return introRsp;
    }

}
