package com.crystal.service.shared;

import com.crystal.model.shared.GridResult;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
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

@Service
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
        CriteriaQuery<Long> count = cb.createQuery(Long.class);
        Root<T> r = q.from(type);
        Root<T> rCount = count.from(type);

        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        Map<String, String[]> params = request.getParameterMap();

        if (params.containsKey("sort") && params.containsKey("order")) {
            String sort = params.get("sort")[0];
            String order = params.get("order")[0];
            Path<String> field = r.get(sort);
            if (order.equals("asc"))
                q.orderBy(cb.asc(field));
            else
                q.orderBy(cb.desc(field));
        }

        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> predicatesCount = new ArrayList<>();
        List<Predicate> predicatesFilter = new ArrayList<>();
        List<Predicate> predicatesFilterCount = new ArrayList<>();
        List<Predicate> predicatesSearch = new ArrayList<>();
        List<Predicate> predicatesSearchCount = new ArrayList<>();

        if (filters != null){
            for (Map.Entry<String, Object> entry : filters.entrySet()){
                Path<String> param = r.get(entry.getKey());
                predicatesFilter.add(cb.equal(param, entry.getValue()));
                param = rCount.get(entry.getKey());
                predicatesFilterCount.add(cb.equal(param, entry.getValue()));
            }
        }

        if (params.containsKey("search")) {
            String pattern = params.get("search")[0];
            Field[] fields = type.getDeclaredFields();

            for (int i = 0; i < fields.length; i++){
                Field field = fields[i];
                if (field.getType().equals(String.class)){
                    Path<String> param = r.get(field.getName());
                    predicatesSearch.add(cb.like(cb.lower(param), "%" + pattern.toLowerCase() + "%"));
                    param = rCount.get(field.getName());
                    predicatesSearchCount.add(cb.like(cb.lower(param), "%" + pattern.toLowerCase() + "%"));
                }
            }
        }

        if (!predicatesFilter.isEmpty() && !predicatesSearch.isEmpty()){
            q.where(cb.and(cb.and(predicatesFilter.toArray(new Predicate[]{})), cb.or(predicatesSearch.toArray(new Predicate[]{}))));
            count.where(cb.and(cb.and(predicatesFilterCount.toArray(new Predicate[]{})), cb.or(predicatesSearchCount.toArray(new Predicate[]{}))));
        }else if(!predicatesFilter.isEmpty()){
            q.where(cb.and(predicatesFilter.toArray(new Predicate[]{})));
            count.where(cb.and(predicatesFilterCount.toArray(new Predicate[]{})));
        }else if(!predicatesSearch.isEmpty()){
            q.where(cb.or(predicatesSearch.toArray(new Predicate[]{})));
            count.where(cb.or(predicatesSearchCount.toArray(new Predicate[]{})));
        }

        count.select(cb.count(rCount));
        Long counter = entityManager.createQuery(count).getSingleResult();

        int limit = params.containsKey("limit") ? isNumeric(params.get("limit")[0], Integer.MAX_VALUE) : Integer.MAX_VALUE;
        int offset = params.containsKey("offset") ? isNumeric(params.get("offset")[0], 0) : 0;

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