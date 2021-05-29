package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.FoodClass;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author H-yin on 2020.
 */
@Repository
@Transactional
public class FoodClassDao extends BaseDAO<FoodClass> {

    public List<FoodClass> selectMainClass() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<FoodClass> cq = builder.createQuery(FoodClass.class);
        Root<FoodClass> root = cq.from(FoodClass.class);

        //foodid=fmainId 代表主分類
        List<FoodClass> foodClass = getSession().createQuery(cq.select(root)
                .where(builder.equal(root.get("foodid"), root.get("fmainId"))))
                .getResultList();

        return foodClass.isEmpty() ? null : foodClass;
    }

    public List<FoodClass> selectSecondClass(String myFoodid) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<FoodClass> cq = builder.createQuery(FoodClass.class);
        Root<FoodClass> root = cq.from(FoodClass.class);

        //fmainId=myFoodid and (foodid=fbranchId and foodid!=fmainId)
        List<FoodClass> foodClass = getSession().createQuery(cq.select(root)
                .where(builder.and(
                        builder.equal(root.get("fmainId"), myFoodid),
                        builder.and(
                                builder.equal(root.get("foodid"), root.get("fbranchId")),
                                builder.notEqual(root.get("foodid"), root.get("fmainId")
                                ))
                )))
                .getResultList();

        return foodClass.isEmpty() ? null : foodClass;
    }

    public List<FoodClass> selectSecondClass() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<FoodClass> cq = builder.createQuery(FoodClass.class);
        Root<FoodClass> root = cq.from(FoodClass.class);

        //fmainId=myFoodid and (foodid=fbranchId and foodid!=fmainId)
        List<FoodClass> foodClass = getSession().createQuery(cq.select(root)
                .where(builder.and(
                        builder.equal(root.get("foodid"), root.get("fbranchId")),
                        builder.notEqual(root.get("foodid"), root.get("fmainId")
                        )))).getResultList();

        return foodClass.isEmpty() ? null : foodClass;
    }

    public List<FoodClass> selectThirdClass(String myFoodid) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<FoodClass> cq = builder.createQuery(FoodClass.class);
        Root<FoodClass> root = cq.from(FoodClass.class);

        //foodid!=fbranchId and fbranchId=myFoodid
        List<FoodClass> foodClass = getSession().createQuery(cq.select(root)
                .where(builder.and(
                        builder.notEqual(root.get("foodid"), root.get("fbranchId")),
                        builder.equal(root.get("fbranchId"), myFoodid)
                )))
                .getResultList();

        return foodClass.isEmpty() ? null : foodClass;
    }

    public List<FoodClass> selectThirdClass() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<FoodClass> cq = builder.createQuery(FoodClass.class);
        Root<FoodClass> root = cq.from(FoodClass.class);

        //foodid!=fbranchId and fbranchId=myFoodid
        List<FoodClass> foodClass = getSession().createQuery(cq.select(root)
                .where(
                        builder.notEqual(root.get("foodid"), root.get("fbranchId"))
                )).getResultList();

        return foodClass.isEmpty() ? null : foodClass;
    }

}
