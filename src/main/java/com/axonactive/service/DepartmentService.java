package com.axonactive.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.axonactive.bom.DepartmentBOM;
import com.axonactive.entity.DepartmentEntity;
import com.axonactive.entity.EmployeeEntity;
import com.axonactive.persistence.AbstractCRUDBean;
import com.axonactive.persistence.PersistenceService;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

@Stateless
public class DepartmentService extends AbstractCRUDBean<DepartmentEntity> {

	@PersistenceContext
	Session session;

	@PersistenceContext
	EntityManager em;

	@Inject
	private PersistenceService<DepartmentEntity> persistenceService;

	@Override
	protected PersistenceService<DepartmentEntity> getPersistenceService() {
		return this.persistenceService;
	}

	public DepartmentEntity toEntity(DepartmentBOM bom) {
		if (bom != null) {
			return new DepartmentEntity(bom.getId(), bom.getName());
		}
		return null;
	}

	public DepartmentBOM toBom(DepartmentEntity entity) {
		if (entity != null) {
			return new DepartmentBOM(entity.getId(), entity.getName());
		}
		return null;
	}

	public List<DepartmentEntity> toEntities(List<DepartmentBOM> boms) {
		if (boms == null) {
			return Collections.emptyList();
		}
		List<DepartmentEntity> entities = new ArrayList<>();
		boms.stream().map(each -> toEntity(each)).filter(Objects::nonNull).forEach(entity -> entities.add(entity));
		return entities;
	}

	public List<DepartmentBOM> toBoms(List<DepartmentEntity> entities) {
		if (entities == null) {
			return Collections.emptyList();
		}
		List<DepartmentBOM> boms = new ArrayList<>();
		entities.stream().map(each -> toBom(each)).filter(Objects::nonNull).forEach(bom -> boms.add(bom));
		return boms;
	}

	@SuppressWarnings("unchecked")
	public List<DepartmentEntity> readAll() {

		// JPA QL
//		Query ejbQuery = em.createQuery("select d from DepartmentEntity d order by d.name");
//		List<DepartmentEntity> result = ejbQuery.getResultList();

		
		//SQL native
		SQLQuery sqlQuery = session.createSQLQuery("select * from Department d order by d.name desc")
				.addEntity("Department", DepartmentEntity.class);
		List<DepartmentEntity> result = sqlQuery.list();
		
		return result;
	}
}
