package fun.deepsky.springindeep.ui.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.cypher.query.Pagination;
import org.neo4j.ogm.cypher.query.SortOrder;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PagesService<T> {

	@Autowired
	private Session session;
	
	public Page<T> findAll(Class<T> clazz,Pageable pageable,Filters filters){
		Collection data = this.session.loadAll(clazz,filters,convert(pageable.getSort()),new Pagination(pageable.getPageNumber(), pageable.getPageSize()));
		return updatePage(pageable, new ArrayList(data));
	}
	
	private Page<T> updatePage(Pageable pageable,List<T> results){
		int pageSize = pageable.getPageSize();
		int pageOffset = pageable.getOffset();
		int total = pageOffset+results.size()+(results.size()==pageSize?pageSize:0);
		return new PageImpl(results,pageable,total);
	}
	
	private SortOrder convert(Sort sort) {
		SortOrder sortOrder = new SortOrder();
		if(sort != null) {
			Iterator it = sort.iterator();
			while(it.hasNext()) {
				Sort.Order order = (Sort.Order) it.next();
				if(order.isAscending()) {
					sortOrder.add(new String[] {order.getProperty()} );
				}else {
					sortOrder.add(SortOrder.Direction.DESC,new String[] {order.getProperty()});
				}
			}
		}
		return sortOrder;
	}
}
