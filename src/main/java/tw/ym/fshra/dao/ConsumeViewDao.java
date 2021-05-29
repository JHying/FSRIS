package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.ConsumeView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author H-yin on 2020.
 */
@Repository
@Transactional
public class ConsumeViewDao extends BaseDAO<ConsumeView> {

    public ConsumeView getSpecificConsume(String foodid, Integer age, String genderCode) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<ConsumeView> cq = criteriaBuilder.createQuery(ConsumeView.class);
        Root<ConsumeView> root = cq.from(ConsumeView.class);

        return getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("foodid"), foodid),
                        criteriaBuilder.greaterThanOrEqualTo(root.get("ageMax"), age),
                        criteriaBuilder.lessThanOrEqualTo(root.get("ageMin"), age),
                        criteriaBuilder.equal(root.get("gender"), genderCode)
                )).orderBy(criteriaBuilder.asc(root.get("agid")))//女性可能有一般&育齡(取第一筆:一般即可)
        ).getResultList().get(0);
    }

}
