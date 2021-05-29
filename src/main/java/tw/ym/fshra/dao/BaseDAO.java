package tw.ym.fshra.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.ym.fshra.util.GenericsUtil;
import tw.ym.fshra.util.Log;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO 常用功能
 *
 * @author H-yin
 */
@Repository
@Transactional
@SuppressWarnings("unchecked")
public class BaseDAO<T> {

    @Resource
    protected SessionFactory sessionFactory;

    private Class<T> entityClass;//準備接收繼承類別

    public BaseDAO() {
//        //目的：得到實際類別參數
//        //getClass()得到當前運行之對象
//        //getGenericSuperclass()得到當前父類別之參數化類型
//        //一般使用ParameterizedType
//        ParameterizedType genericSuperclass = (ParameterizedType) this
//                .getClass().getGenericSuperclass();
//        //得到實際繼承類別
//        Type[] types = genericSuperclass.getActualTypeArguments();
//        this.entityClass = (Class<T>) types[0];
        //以上會拋 Class cannot be cast to ParameterizedType
        //故調用以下 util 協助轉換
        this.entityClass = GenericsUtil.getSuperClassGenricType(this.getClass());
    }

    /**
     * 獲得當前可用的 session
     *
     * @return session
     */
    public Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        if (session == null) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    /**
     * 新增一筆資料
     *
     * @param bean bean object
     */
    public void saveBean(T bean) {
        try {
            getSession().save(bean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(">> [" + bean.getClass().getName() + "] BaseDAO save exception: " + e.getMessage());
        }
    }

    /**
     * 修改一筆資料
     *
     * @param bean bean object
     */
    public void updateBean(T bean) {
        try {
            getSession().update(bean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(">> [" + bean.getClass().getName() + "] BaseDAO update exception: " + e.getMessage());
        }
    }

    /**
     * 新增或修改一筆資料
     *
     * @param bean bean object
     */
    public void saveOrUpdateBean(T bean) {
        try {
            getSession().saveOrUpdate(bean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(">> [" + bean.getClass().getName() + "] BaseDAO save exception: " + e.getMessage());
        }
    }


    /**
     * 刪除一筆資料
     *
     * @param bean data
     */
    public void deleteBean(T bean) {
        try {
            getSession().delete(bean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(">> [" + bean.getClass().getName() + "] BaseDAO delete exception: " + e.getMessage());
        }
    }

    /**
     * 刪除所有資料
     */
    public void deleteBeanAll() {
        try {
            String sql = String.format("TRUNCATE TABLE %s", entityClass.getSimpleName());
            getSession().createSQLQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(">> [" + entityClass.getName() + "] BaseDAO deleteAll exception: " + e.getMessage());
        }
    }

    /**
     * @param id PK
     */
    public T selectBean(String id) {
        T data = null;
        try {
            data = getSession().get(entityClass, id);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(">> [" + entityClass.getName() + "] BaseDAO select exception: " + e.getMessage());
        }
        return data;
    }

    public List<T> selectBeans(String[] ids) {
        return getSession().createQuery("FROM "
                + entityClass.getSimpleName() +
                " WHERE id IN (:ids)")
                .setParameterList("ids", ids)
                .list();
    }

    /**
     * 查詢所有資料
     */
    public List<T> selectAllBean() {
        List<T> data = null;
        try {
            data = getSession().createQuery(
                    "FROM " + entityClass.getSimpleName())
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(">> [" + entityClass.getName() + "] BaseDAO select exception: " + e.getMessage());
        }
        return data;
    }

    /**
     * 查詢第一筆資料
     */
    public T selectFirst() {
        List<T> data = new ArrayList<>();
        try {
            data = getSession().createQuery(
                    "FROM " + entityClass.getSimpleName())
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(">> [" + entityClass.getName() + "] BaseDAO select exception: " + e.getMessage());
        }
        return data.get(0);
    }

    /**
     * 產生流水號編號
     *
     * @param MsgNoName 流水號編號屬性名稱
     * @return 流水號編號
     */
    public Long createMsgNo(String MsgNoName) {
        try {
            Session sess = getSession();
            // 建立 CriteriaBuilder 實例
            CriteriaBuilder criteriaBuilder = sess.getCriteriaBuilder();

            // Create a query object by creating an instance of the CriteriaQuery interface
            CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);

            // Set the query Root by calling the from() method on the CriteriaQuery object
            // to define a range variable in FROM clause
            Root<?> root = query.from(entityClass);

            Long msgNo = 6000000000L;
            Long maxNO = sess.createQuery(query.select(criteriaBuilder.max(root.get(MsgNoName)))
                    .where(criteriaBuilder.ge(root.get(MsgNoName), msgNo))).uniqueResult();

            msgNo = (maxNO != null ? maxNO : msgNo);
            ++msgNo;
            return msgNo;
        } catch (Exception e) {
            Log.error(">> [" + entityClass.getName() + "] BaseDAO getMsgNo exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param sql SQL
     * @return List (bean)
     */
    public List<T> selectBySQL(String sql) {
        return getSession().createSQLQuery(sql)
                .addEntity(entityClass).list();
    }

    /**
     * @param sql SQL
     * @return List (bean)
     */
    public List<Object[]> selectColBySQL(String sql) {
        // 建立 SQLQuery
        try {
            return (List<Object[]>) getSession().createSQLQuery(sql).list();
        } catch (Exception e) {
            e.printStackTrace();
            Log.error(">> [" + entityClass.getName() + "] BaseDAO selectColBySQL exception: " + e.getMessage());
            return null;
        }
    }

    public void callSP(String SPname) {
        try {
            ProcedureCall pc = getSession().createStoredProcedureCall(SPname);
            pc.getOutputs();
        } catch (Exception e) {
            Log.error(">> [" + entityClass.getName() + "] BaseDAO callSP exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}