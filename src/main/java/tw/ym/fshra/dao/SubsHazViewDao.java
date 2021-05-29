package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.SubsHazView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author H-yin on 2020.
 */
@Repository
@Transactional
public class SubsHazViewDao extends BaseDAO<SubsHazView> {

    public List<SubsHazView> getHazardData(String subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsHazView> cq = criteriaBuilder.createQuery(SubsHazView.class);
        Root<SubsHazView> root = cq.from(SubsHazView.class);

        List<SubsHazView> substances = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("subsid"), subsid),
                        criteriaBuilder.equal(root.get("smainId"), subsid)
                )))
                .getResultList();
        return substances.isEmpty() ? null : substances;
    }

    public List<SubsHazView> getHazardById(List<String> subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsHazView> cq = criteriaBuilder.createQuery(SubsHazView.class);
        Root<SubsHazView> root = cq.from(SubsHazView.class);

        Path<String> path = root.get("subsid");
        CriteriaBuilder.In<String> inCriteria = criteriaBuilder.in(path);
        for (String id : subsid) {
            inCriteria.value(id);
        }
        List<SubsHazView> subsHazards = getSession().createQuery(cq.select(root)
                .where(inCriteria)).getResultList();

        return subsHazards.isEmpty() ? null : subsHazards;
    }


}
