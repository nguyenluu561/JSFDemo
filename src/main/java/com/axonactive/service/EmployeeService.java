package com.axonactive.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.axonactive.bom.EmployeeBOM;
import com.axonactive.entity.EmployeeEntity;
import com.axonactive.persistence.AbstractCRUDBean;
import com.axonactive.persistence.PersistenceService;

@Stateless
public class EmployeeService extends AbstractCRUDBean<EmployeeEntity> {

	@PersistenceContext
	Session session;

	@PersistenceContext
	EntityManager em;

	@Inject
	private PersistenceService<EmployeeEntity> persistenceService;

	@Override
	protected PersistenceService<EmployeeEntity> getPersistenceService() {
		return this.persistenceService;
	}

	public EmployeeEntity toEntity(EmployeeBOM bom) {
		if (bom != null) {
			return new EmployeeEntity(bom.getName(), bom.getEmail(), bom.getGender(), bom.getAge(),
					bom.getDepartment());
		}
		return null;
	}

	public EmployeeBOM toBom(EmployeeEntity entity) {
		if (entity != null) {
			return new EmployeeBOM(entity.getId(), entity.getName(), entity.getEmail(), entity.getGender(),
					entity.getAge(), entity.getDepartment());
		}
		return null;
	}

	public List<EmployeeEntity> toFileEntities(List<EmployeeBOM> boms) {
		if (boms == null) {
			return Collections.emptyList();
		}
		List<EmployeeEntity> entities = new ArrayList<>();
		boms.stream().map(each -> toEntity(each)).filter(Objects::nonNull).forEach(entity -> entities.add(entity));
		return entities;
	}

	public List<EmployeeBOM> toBoms(List<EmployeeEntity> entities) {
		if (entities == null) {
			return Collections.emptyList();
		}
		List<EmployeeBOM> boms = new ArrayList<>();
		entities.stream().map(each -> toBom(each)).filter(Objects::nonNull).forEach(bom -> boms.add(bom));
		return boms;
	}

	@SuppressWarnings("unchecked")
	public List<EmployeeEntity> readAll() {

		// JPA QL
		// Select từ tên Envity tương ứng với bảng cần thao tác
		// Các thuộc tính phải ghi đúng như được định nghĩa ở entity, phân biệt hoa
		// thường,
		// nhưng sort lại không kèm hoa thường
//		Query ejbQuery = em.createQuery("select e from EmployeeEntity e order by\r\n" + 
//				"e.name asc");
//		List<EmployeeEntity> result = ejbQuery.getResultList();

		// SQL native
		// Câu sql bình thường, tên bảng tương tự trong db
		// Phần addEntity, tham số 1 nên giống tên bảng trong db hoặc giống tên bảng
		// được dùng trong câu sql
		// có thể bỏ trống trong trường hợp 1 bảng
		// tham số 2 là Entity ở backend
//		SQLQuery sqlQuery = session.createSQLQuery("select * from Employee e order by e.name desc")
//									.addEntity("Employee", EmployeeEntity.class);
//		List<EmployeeEntity> result = sqlQuery.list();

		// Procedure
//		SQLQuery query = session.createSQLQuery("select * from get_all_employee()").addEntity(EmployeeEntity.class);
//		List<EmployeeEntity> result = query.list();

		// NamedQuery
//		String gender = "Male";
//		String Name = "test%";
//		Query query =
//				em.createNamedQuery("getEmployeeListWithNamedQuery")
//				.setParameter("gender", gender)
//				.setParameter("name", Name);		
//
//		List<EmployeeEntity> result = query.getResultList();

		// NamedProcedured
		// cần setparameter tương ứng phần định nghĩa, có thể thêm attr name ở phần
		// parameter để set dữ liệu
		// thay vì dùng số như ví dụ bên dưới
		
		Query query = em.createNamedStoredProcedureQuery("fn_getEmployeesOfOneDepartment");
		query.setParameter(2, "commercial");
		List<EmployeeEntity> result = query.getResultList();
		
		return result;
//		
//		CriteriaBuilder cr = bu
//
//		return (EmployeeEntity.class).add(Restrictions.eq("gender", gender))
//				.add(Restrictions.like("name", "test%")).list();

	}

}
