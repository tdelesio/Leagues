package com.delesio.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.type.NullableType;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.delesio.model.IFilterablePersistantObject;
import com.delesio.model.IPersistable;
import com.delesio.model.QueryParameter;
import com.delesio.util.TimeHelper;

public abstract class AbstractDao extends HibernateDaoSupport {
	protected HibernateTemplate hibernateTemplate;
	protected List<Object> parameters;
	protected List<NullableType> types;
	protected IFilterablePersistantObject filter;
	private QueryParameter rowCountQueryParmater = new QueryParameter(0, 1);
	// init
	public void init() {
		hibernateTemplate = getHibernateTemplate();
		//hibernateTemplate.setCacheQueries(true);
	}
	
	protected void addMatchingCondition(StringBuilder hql, String value, String name) {
		if (value != null) {
			hql.append("and upper(target.");
			hql.append(name);
			hql.append(") like (?)");
			parameters.add("%" + value.toUpperCase() + "%");
			types.add(Hibernate.STRING);
		}
	}
	
	protected void addDateRangeMatchCondition(StringBuilder hql, Date value, String name)
	{
		if (value != null)
		{
			hql.append("and target.");
			hql.append(name);
			hql.append(" > ? and target.");
			hql.append(name);
			hql.append(" < ?");
			parameters.add(TimeHelper.getBegin(value));
			types.add(Hibernate.DATE);
			parameters.add(TimeHelper.getEnd(value));
			types.add(Hibernate.DATE);
		}
	}
	
	protected void addMatchingJoinCondition(StringBuilder hql, String value, String name, int joinNumber) {
		if (value != null) {
			hql.append("and upper(join");
			hql.append(joinNumber);
			hql.append(".");
			hql.append(name);
			hql.append(") like (?)");
			parameters.add("%" + value.toUpperCase() + "%");
			types.add(Hibernate.STRING);
		}
	}
	
	public Criteria createCriteria(Class criteriaClass, QueryParameter queryParams) {  
		  Criteria criteria = getSession().createCriteria(criteriaClass);
		  String sort = queryParams.getSort();
		  boolean isDescending = queryParams.isDescending();
		  
		  if(sort != null && isDescending)
		   criteria.addOrder(Order.desc(sort));
		  
		  else if(sort != null && !isDescending)
		   criteria.addOrder(Order.asc(sort));
		  
		  criteria.setFirstResult(queryParams.getFirst());
		  criteria.setMaxResults(queryParams.getCount());
		  return criteria;
	 }
	
	public QueryParameter getRowCountQueryParams()
	{
		return rowCountQueryParmater;
	}
	public int getCriteriaRowCount(Criteria criteria) {
		  return (Integer)criteria.setProjection(
		    Projections.projectionList().add(
		      Projections.rowCount())).list().get(0);
		 }
	protected void addOrderByClause(StringBuilder hql) {
		QueryParameter queryParam = filter.getQueryParameter();
		if (!filter.isCount() && queryParam != null && queryParam.hasSort()) {
			hql.append("order by upper(target.");
			hql.append(queryParam.getSort());
			hql.append(") ");
			hql.append(queryParam.isSortAsc() ? "asc" : "desc");
		}
	}
	
	protected void addCountClause(StringBuilder hql) {
		if (filter.isCount()) {
			hql.append("select count(*) ");
		}
	}
	
	public void setFilter(IFilterablePersistantObject filter) {
		if (filter == null) {
			throw new IllegalArgumentException("Null value not allowed.");
		}
		this.filter = filter;
	}

	public Object[] getParameters() {
		return parameters.toArray(new Object[0]);
	}

	public NullableType[] getTypes() {
		return types.toArray(new NullableType[0]);
	}
	
	protected IPersistable extractSingleElement(List<IPersistable> list)
	{
		if (list.isEmpty())
		{
			return null;
		}
		else
		{
			return list.get(0);
		}
	}
}
