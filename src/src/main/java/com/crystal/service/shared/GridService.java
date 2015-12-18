package com.crystal.service.shared;

import com.crystal.model.shared.GridResult;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

@Repository
public class GridService<T> {

    @PersistenceContext(unitName = "punit")
    private EntityManager entityManager;


    public GridResult<T> toGrid(Class<T> type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(type);
        Root<T> r = q.from(type);

//        Path<String> namePath = r.get("name");
//        Path<String> userTypeClassTypeDisplayName =
//                r.get("userType").get("classType").get("displayName");
//        Path<String> userTypeModel = r.get("userType").get("model");
//        List<Predicate> predicates = new ArrayList<>();

//        for(String word : words) {
//            Expression<String> wordLiteral = cb.literal(word);
//            predicates.add(
//                    cb.or(
//                            cb.like(cb.lower(namePath), cb.lower(wordLiteral)),
//                            cb.like(cb.lower(userTypeClassTypeDisplayName),
//                                    cb.lower(wordLiteral)),
//                            cb.like(cb.lower(userTypeModel), cb.lower(wordLiteral))
//                    )
//            );
//        }
//        q.select(doc).where(
//                cb.and(predicates.toArray(new Predicate[predicates.size()]))
//        );

        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest requestA = ra.getRequest();

        GridResult<T> result = new GridResult<>();

        result.setTotal(10L);
        result.setRows(entityManager.createQuery(q).getResultList());

        return result;
    }
}
