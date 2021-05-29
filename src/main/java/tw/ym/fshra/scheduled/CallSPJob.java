package tw.ym.fshra.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tw.ym.fshra.dao.BaseDAO;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author H-yin on 2020.
 */
@Service
public class CallSPJob {

    @Resource
    private BaseDAO baseDAO;

    @PostConstruct
    @Scheduled(cron = "${pass.conc.updated.cron}")
    public void callStoredProcedure() {
        baseDAO.callSP("conc_proc");
        baseDAO.callSP("pass_proc");
    }

}
