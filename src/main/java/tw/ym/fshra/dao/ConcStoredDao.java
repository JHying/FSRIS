package tw.ym.fshra.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.ConcStored;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author H-yin on 2020.
 */
@Repository
@Transactional
public class ConcStoredDao extends BaseDAO<ConcStored> {

    public List<ConcStored> getConcData(String foodid, String subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<ConcStored> cq = criteriaBuilder.createQuery(ConcStored.class);
        Root<ConcStored> root = cq.from(ConcStored.class);

        return getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.or(
                                criteriaBuilder.equal(root.get("subsid"), subsid),
                                criteriaBuilder.equal(root.get("smainId"), subsid)
                        ),
                        criteriaBuilder.equal(root.get("foodid"), foodid),
                        criteriaBuilder.notEqual(root.get("concMean"), 0)
                )).distinct(true)
        ).getResultList();
    }

    //    @Cacheable("myCache")
    public List<ConcStored> getConcData(String subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<ConcStored> cq = criteriaBuilder.createQuery(ConcStored.class);
        Root<ConcStored> root = cq.from(ConcStored.class);

        return getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("subsid"), subsid),
                        criteriaBuilder.equal(root.get("smainId"), subsid)
                )).distinct(true)
        ).getResultList();
    }

    //    @Cacheable("myCache")
    public List<ConcStored> getFailData(String subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<ConcStored> cq = criteriaBuilder.createQuery(ConcStored.class);
        Root<ConcStored> root = cq.from(ConcStored.class);

        return getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.or(
                                criteriaBuilder.equal(root.get("subsid"), subsid),
                                criteriaBuilder.equal(root.get("smainId"), subsid)
                        ),
                        criteriaBuilder.notEqual(root.get("failRate"), 0)
                )).distinct(true)
        ).getResultList();
    }

    public List<ConcStored> getRelateConcByFood(String foodid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<ConcStored> cq = criteriaBuilder.createQuery(ConcStored.class);
        Root<ConcStored> root = cq.from(ConcStored.class);

        return getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        //濃度為0不列入考量
                        criteriaBuilder.equal(root.get("foodid"), foodid),
                        criteriaBuilder.notEqual(root.get("concMean"), 0)
                        )
                )).getResultList();
    }

    public List<String> getRelateSMainIdByFood(String foodid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<String> cq = criteriaBuilder.createQuery(String.class);
        Root<ConcStored> root = cq.from(ConcStored.class);

        return getSession().createQuery(
                cq.select(root.get("smainId"))
                        .where(criteriaBuilder.and(
                                //濃度為0不列入考量
                                criteriaBuilder.equal(root.get("foodid"), foodid),
                                criteriaBuilder.notEqual(root.get("concMean"), 0)
                                )
                        ).distinct(true)
        ).getResultList();
    }

}
