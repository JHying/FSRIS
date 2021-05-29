package tw.ym.fshra.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.bean.SubsClass;
import tw.ym.fshra.util.DataProcess;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

/**
 * @author H-yin on 2020.
 */
@Repository
@Transactional
public class SubsClassDao extends BaseDAO<SubsClass> {

    public List<SubsClass> selectMainClass() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsClass> cq = builder.createQuery(SubsClass.class);
        Root<SubsClass> root = cq.from(SubsClass.class);

        //subsid=cmainId 代表主分類
        return getSession().createQuery(cq.select(root)
                .where(builder.equal(root.get("subsid"), root.get("smainId"))))
                .getResultList();
    }

    public List<SubsClass> selectSecondClass(String mySubsId) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsClass> cq = builder.createQuery(SubsClass.class);
        Root<SubsClass> root = cq.from(SubsClass.class);

        //cmainId=mySubsId and (subsid=cbranchId and subsid!=cmainId)
        List<SubsClass> subsClass = getSession().createQuery(cq.select(root)
                .where(builder.and(
                        builder.equal(root.get("smainId"), mySubsId),
                        builder.and(
                                builder.equal(root.get("subsid"), root.get("sbranchId")),
                                builder.notEqual(root.get("subsid"), root.get("smainId")
                                ))
                )))
                .getResultList();

        return subsClass;
    }

    public List<SubsClass> selectSecondClass() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsClass> cq = builder.createQuery(SubsClass.class);
        Root<SubsClass> root = cq.from(SubsClass.class);

        //cmainId=mySubsId and (subsid=cbranchId and subsid!=cmainId)
        List<SubsClass> subsClass = getSession().createQuery(cq.select(root)
                .where(builder.and(
                        builder.equal(root.get("subsid"), root.get("sbranchId")),
                        builder.notEqual(root.get("subsid"), root.get("smainId")
                        )))).getResultList();

        return subsClass;
    }

    public List<SubsClass> selectThirdClass(String mySubsId) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsClass> cq = builder.createQuery(SubsClass.class);
        Root<SubsClass> root = cq.from(SubsClass.class);

        //subsid!=cbranchId and cbranchId=mySubsId
        List<SubsClass> subsClass = getSession().createQuery(cq.select(root)
                .where(builder.and(
                        builder.notEqual(root.get("subsid"), root.get("sbranchId")),
                        builder.equal(root.get("sbranchId"), mySubsId)
                )))
                .getResultList();

        return subsClass;
    }

    public List<SubsClass> selectThirdClass() {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsClass> cq = builder.createQuery(SubsClass.class);
        Root<SubsClass> root = cq.from(SubsClass.class);

        //subsid!=cbranchId and cbranchId=mySubsId
        List<SubsClass> subsClass = getSession().createQuery(cq.select(root)
                .where(builder.notEqual(root.get("subsid"), root.get("sbranchId"))
                )).getResultList();

        return subsClass;
    }

    public List<SubsClass> selectUnderSubs(String smainId) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsClass> cq = builder.createQuery(SubsClass.class);
        Root<SubsClass> root = cq.from(SubsClass.class);

        //subsid!=smainId && smainId=帶入主類
        List<SubsClass> subsClass = getSession().createQuery(cq.select(root)
                .where(builder.and(
                        builder.notEqual(root.get("subsid"), root.get("smainId")),
                        builder.equal(root.get("smainId"), smainId)
                )))
                .getResultList();

        return subsClass.isEmpty() ? null : subsClass;
    }

    public SubsClass selectFirstUrl() {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsClass> cq = criteriaBuilder.createQuery(SubsClass.class);
        Root<SubsClass> root = cq.from(SubsClass.class);

        List<SubsClass> urls = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.isNotNull(root.get("introRef"))))
                .getResultList();

        return urls.isEmpty() ? null : urls.get(0);
    }

    public List<String> selectAllUrlExceptFirst(String firstSubsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<String> cq = criteriaBuilder.createQuery(String.class);
        Root<SubsClass> root = cq.from(SubsClass.class);

        List<String> urls = getSession().createQuery(cq.select(root.get("introRef"))
                .where(criteriaBuilder.notEqual(root.get("subsid"), firstSubsid)))
                .getResultList();

        //去除空值及重複元素
        urls.removeAll(Collections.singletonList(""));
        List<String> urlsUniq = DataProcess.getUniqList(urls);

        return urlsUniq.isEmpty() ? null : urls;
    }

    public List<SubsClass> selectByIntroRef(String introRef) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsClass> cq = criteriaBuilder.createQuery(SubsClass.class);
        Root<SubsClass> root = cq.from(SubsClass.class);

        List<SubsClass> substances = getSession().createQuery(cq.select(root)
                .where(criteriaBuilder.equal(root.get("introRef"), introRef)))
                .getResultList();

        return substances.isEmpty() ? null : substances;
    }

    public List<SubsClass> getSubsById(List<String> subsid) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<SubsClass> cq = criteriaBuilder.createQuery(SubsClass.class);
        Root<SubsClass> root = cq.from(SubsClass.class);

        Path<String> path = root.get("subsid");
        CriteriaBuilder.In<String> inCriteria = criteriaBuilder.in(path);
        for (String id : subsid) {
            inCriteria.value(id);
        }

        List<SubsClass> subsClasses = getSession().createQuery(cq.select(root)
                .where(inCriteria)).getResultList();

        return subsClasses.isEmpty() ? null : subsClasses;
    }

}
