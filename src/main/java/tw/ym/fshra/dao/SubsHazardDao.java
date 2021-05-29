package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.SubsHazard;

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
public class SubsHazardDao extends BaseDAO<SubsHazard> {

    public List<SubsHazard> getHazardById(List<String> subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsHazard> cq = criteriaBuilder.createQuery(SubsHazard.class);
        Root<SubsHazard> root = cq.from(SubsHazard.class);

        Path<String> path = root.get("subsid");
        CriteriaBuilder.In<String> inCriteria = criteriaBuilder.in(path);
        for (String id : subsid) {
            inCriteria.value(id);
        }

        List<SubsHazard> subsHazards = getSession().createQuery(cq.select(root)
                .where(inCriteria)).getResultList();

        return subsHazards.isEmpty() ? null : subsHazards;
    }

}
