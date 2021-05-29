package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.FoodFail;
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
public class FoodFailDao extends BaseDAO<FoodFail> {

    public List<FoodFail> selectBySubs(String smainId) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<FoodFail> cq = criteriaBuilder.createQuery(FoodFail.class);
        Root<FoodFail> root = cq.from(FoodFail.class);

        return getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("subsid"), smainId),
                        criteriaBuilder.equal(root.get("smainId"), smainId)
                )))
                .getResultList();
    }

}
