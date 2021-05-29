package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.RiskScore;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author H-yin on 2020.
 */
@Repository
@Transactional
public class RiskScoreDao extends BaseDAO<RiskScore> {

    public List<RiskScore> selectBySubs(String smainId) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<RiskScore> cq = criteriaBuilder.createQuery(RiskScore.class);
        Root<RiskScore> root = cq.from(RiskScore.class);

        return getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("subsid"), smainId),
                        criteriaBuilder.equal(root.get("smainId"), smainId)
                )))
                .getResultList();
    }

}
