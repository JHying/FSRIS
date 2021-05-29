package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.FoodCode;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author H-yin on 2019.
 */
@Repository
@Transactional
public class FoodCodeDao extends BaseDAO<FoodCode> {

    public List<FoodCode> selectByFoodid(String foodid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<FoodCode> cq = criteriaBuilder.createQuery(FoodCode.class);
        Root<FoodCode> root = cq.from(FoodCode.class);

        List<FoodCode> fcList = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.equal(root.get("foodid"), foodid)))
                .getResultList();

        return fcList.isEmpty() ? null : fcList;
    }

    public List<FoodCode> selectByFoodSubsid(String foodid, String subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<FoodCode> cq = criteriaBuilder.createQuery(FoodCode.class);
        Root<FoodCode> root = cq.from(FoodCode.class);

        List<FoodCode> fcList = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("foodid"), foodid),
                        criteriaBuilder.equal(root.get("subsid"), subsid)
                )).orderBy(criteriaBuilder.desc(root.get("dtConc"))))
                .getResultList();

        return fcList.isEmpty() ? null : fcList;
    }

}
