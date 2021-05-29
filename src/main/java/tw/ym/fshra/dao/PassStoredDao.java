package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.PassStored;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author H-yin on 2020.
 */
@Repository
@Transactional
public class PassStoredDao extends BaseDAO<PassStored> {

    public List<PassStored> selectConcList(String foodid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<PassStored> cq = criteriaBuilder.createQuery(PassStored.class);
        Root<PassStored> root = cq.from(PassStored.class);

        List<PassStored> fvList = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.equal(root.get("foodid"), foodid)))
                .getResultList();

        return fvList.isEmpty() ? null : fvList;
    }

    //    @Cacheable("myCache")
    public List<PassStored> getConcDetail(String subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<PassStored> cq = criteriaBuilder.createQuery(PassStored.class);
        Root<PassStored> root = cq.from(PassStored.class);

        List<PassStored> passStoreds = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("subsid"), subsid),
                        criteriaBuilder.equal(root.get("smainId"), subsid))
                )).getResultList();

        return passStoreds.isEmpty() ? null : passStoreds;
    }

    public List<PassStored> getConcDetail(String subsid, String... foodid) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append("PASS_VIEW ");
        sb.append("WHERE (SUBSID=:subsid or SMAIN_ID=:subsid) ");
        sb.append("AND FOODID IN (:foodid) ");

        List query = getSession().createSQLQuery(sb.toString())
                .addEntity(PassStored.class)
                .setParameter("subsid", subsid)
                .setParameterList("foodid", foodid).list();

        return query.isEmpty() ? null : query;
    }

    public List<PassStored> getConcDetail(String subsid, String foodid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<PassStored> cq = criteriaBuilder.createQuery(PassStored.class);
        Root<PassStored> root = cq.from(PassStored.class);

        List<PassStored> passStoreds = getSession().createQuery(cq.select(root)
                        .where(criteriaBuilder.and(
                                criteriaBuilder.or(
                                        criteriaBuilder.equal(root.get("subsid"), subsid),
                                        criteriaBuilder.equal(root.get("smainId"), subsid)
                                ),
                                criteriaBuilder.equal(root.get("foodid"), foodid)
//                        ,criteriaBuilder.notEqual(root.get("dtConc"), 0)
                        )).orderBy(criteriaBuilder.desc(root.get("dtConc")))
        ).getResultList();

        return passStoreds.isEmpty() ? null : passStoreds;
    }

    public List<PassStored> getPassDetail(String subsid, String foodid) {
        //取得食品超標率明細 (未訂定標準--null者算食品超標)
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<PassStored> cq = criteriaBuilder.createQuery(PassStored.class);
        Root<PassStored> root = cq.from(PassStored.class);

        List<PassStored> passStoreds = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("subsid"), subsid),
                        criteriaBuilder.equal(root.get("foodid"), foodid)
//                        criteriaBuilder.isNotNull(root.get("pass"))
                ))).getResultList();

        return passStoreds.isEmpty() ? null : passStoreds;
    }

    public Boolean getFail(String subsid) {
        //是否有任何超標食品 (不算未訂定標準--null者：該類屬於違法添加)
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<PassStored> cq = criteriaBuilder.createQuery(PassStored.class);
        Root<PassStored> root = cq.from(PassStored.class);

        List<PassStored> substances = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("subsid"), subsid),
                        criteriaBuilder.equal(root.get("pass"), "N")
                ))).getResultList();

        return !substances.isEmpty();
    }

    public List<PassStored> presentInOthers(String subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<PassStored> cq = criteriaBuilder.createQuery(PassStored.class);
        Root<PassStored> root = cq.from(PassStored.class);

        List<PassStored> substances = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.or(
                                criteriaBuilder.equal(root.get("subsid"), subsid),
                                criteriaBuilder.equal(root.get("smainId"), subsid)
                        ),
                        criteriaBuilder.isNull(root.get("mrl"))
                ))).getResultList();

        return substances.isEmpty() ? null : substances;
    }

}
