package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.AgeGroup;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author H-yin on 2020.
 */
@Repository
@Transactional
public class AgeGroupDao extends BaseDAO<AgeGroup> {

    public AgeGroup getSpecificGroup(Integer age, String genderCode) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<AgeGroup> cq = criteriaBuilder.createQuery(AgeGroup.class);
        Root<AgeGroup> root = cq.from(AgeGroup.class);

        return getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("ageMax"), age),
                        criteriaBuilder.lessThanOrEqualTo(root.get("ageMin"), age),
                        criteriaBuilder.equal(root.get("gender"), genderCode)
                        )
                )
        ).getSingleResult();
    }

}
