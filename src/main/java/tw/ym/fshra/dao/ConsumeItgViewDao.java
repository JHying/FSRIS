package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.ConsumeItgView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

/**
 * @author H-yin on 2020.
 */
@Repository
@Transactional
public class ConsumeItgViewDao extends BaseDAO<ConsumeItgView> {

    public ConsumeItgView getConsume(String foodid, String genderCode) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<ConsumeItgView> cq = criteriaBuilder.createQuery(ConsumeItgView.class);
        Root<ConsumeItgView> root = cq.from(ConsumeItgView.class);

        return getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("foodid"), foodid),
                        criteriaBuilder.equal(root.get("gender"), genderCode)
                ))
        ).getSingleResult();
    }

    public List<ConsumeItgView> getWholeConsume(Set<String> foodid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<ConsumeItgView> cq = criteriaBuilder.createQuery(ConsumeItgView.class);
        Root<ConsumeItgView> root = cq.from(ConsumeItgView.class);

        Path<String> path = root.get("foodid");
        CriteriaBuilder.In<String> inCriteria = criteriaBuilder.in(path);
        for (String id : foodid) {
            inCriteria.value(id);
        }

        List<ConsumeItgView> consumeItgViews = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("gender"), "W"),
                        inCriteria
                ))).getResultList();

        return consumeItgViews.isEmpty() ? null : consumeItgViews;
    }

}
