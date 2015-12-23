package com.crystal.service.shared;

import com.crystal.model.shared.GridResult;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GridService<T> {

    @PersistenceContext(unitName = "punit")
    private EntityManager entityManager;

    private Map<String, Object> filters;

    public GridResult<T> toGrid(Class<T> type, String fieldName, Object value){
        filters = new HashMap<>();
        filters.put(fieldName, value);
        return toGrid(type);
    }

    public GridResult<T> toGrid(Class<T> type, Map<String, Object> filters){
        this.filters = filters;
        return toGrid(type);
    }

    public GridResult<T> toGrid(Class<T> type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(type);
        Root<T> r = q.from(type);

        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        Map<String, String[]> params = request.getParameterMap();

        if (params.containsKey("sort") && params.containsKey("order"))
        {
            String sort = params.get("sort")[0];
            String order = params.get("order")[0];
            Path<String> field = r.get(sort);
            if (order.equals("asc"))
                q.orderBy(cb.asc(field));
            else
                q.orderBy(cb.desc(field));
        }

        if (filters != null){
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, Object> entry : filters.entrySet()){
                Path<String> param = r.get(entry.getKey());
                predicates.add(cb.equal(param, entry.getValue()));
            }
            q.where(cb.and(predicates.toArray(new Predicate[]{})));
        }

        if (params.containsKey("search"))
        {
            String pattern = params.get("search")[0];

            if(!pattern.isEmpty()){
                Field[] fields = type.getDeclaredFields();
                List<Predicate> predicates = new ArrayList<>();

                for (int i = 0; i < fields.length; i++){
                    Field field = fields[i];
                    if (field.getType().equals(String.class)){
                        Path<String> param = r.get(field.getName());
                        predicates.add(cb.like(cb.lower(param), "%" + pattern.toLowerCase() + "%"));
                    }
                }
                q.where(cb.or(predicates.toArray(new Predicate[]{})));
            }
        }

        CriteriaQuery<Long> count = cb.createQuery(Long.class);
        count.select(cb.count(count.from(type)));
        Long counter = entityManager.createQuery(count).getSingleResult();

        int limit = isNumeric(params.get("limit")[0], Integer.MAX_VALUE);
        int offset = isNumeric(params.get("offset")[0], 0);

        CriteriaQuery<T> select = q.select(r);
        TypedQuery<T> tq = entityManager.createQuery(select);
        tq.setFirstResult(offset);
        tq.setMaxResults(limit);

        GridResult<T> result = new GridResult<>();

        result.setTotal(counter);
        result.setRows(tq.getResultList());

        filters = null;

        return result;
    }

    private int isNumeric(String string, int defaultValue){
        try{
            return Integer.parseInt(string);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}