package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.MrlView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author H-yin on 2019.
 */
@Repository
@Transactional
public class MrlViewDao extends BaseDAO<MrlView> {

    public List<String> getRestrictedFood(String subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<String> cq = criteriaBuilder.createQuery(String.class);
        Root<MrlView> root = cq.from(MrlView.class);

        return getSession().createQuery(cq.select(root.get("foodid"))
                .where(criteriaBuilder.equal(root.get("subsid"), subsid)
                ).distinct(true)
                .orderBy(criteriaBuilder.desc(root.get("foodid")
                ))).getResultList();
    }

    public MrlView getMrlData(String foodid, String subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<MrlView> cq = criteriaBuilder.createQuery(MrlView.class);
        Root<MrlView> root = cq.from(MrlView.class);

        List<MrlView> mrlViewList = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("foodid"), foodid),
                        criteriaBuilder.equal(root.get("subsid"), subsid)
                ))).getResultList();

        return mrlViewList.isEmpty() ? null : mrlViewList.get(0);
    }

    public List<MrlView> getMrlData(String foodid, List<String> subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<MrlView> cq = criteriaBuilder.createQuery(MrlView.class);
        Root<MrlView> root = cq.from(MrlView.class);

        Path<String> path = root.get("subsid");
        CriteriaBuilder.In<String> inCriteria = criteriaBuilder.in(path);
        for (String id : subsid) {
            inCriteria.value(id);
        }

        List<MrlView> mrlViewList = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("foodid"), foodid),
                        inCriteria
                ))).getResultList();

        return mrlViewList.isEmpty() ? null : mrlViewList;
    }

}
