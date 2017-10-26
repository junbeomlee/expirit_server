package com.tanx.expirit;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tanx.expirit.util.DTOMapper;

import rx.Observable;
import rx.Single;

/*
 * template rest controller for crud
 * issue -> error handling
 * 
 */
public abstract class RestControllerTemplate<T, F, ID extends Serializable> {

	private CrudRepository<T, ID> repository;
	private Class<F> dtoType;
	private Class<T> entityType;

	public RestControllerTemplate(CrudRepository<T, ID> repository,Class<T> entityType, Class<F> dtoType) {
		this.repository = repository;
		this.dtoType=dtoType;
		this.entityType=entityType;
	}

	/*
	 * error 처리
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Single<F> findOne(@PathVariable ID id) {

		return Observable.defer(() -> {
			T entity = repository.findOne(id);
			if (entity == null)
				throw new RuntimeException("wrong request id");
			return Observable.just(entity);
		}).map(entity -> DTOMapper.mapper.map(entity, dtoType))
		.toSingle();
	}

	@RequestMapping(method = RequestMethod.GET)
	public Single<List<F>> getList() {
		//throw new LoginFailException("login Fail");
		return Observable.defer(() -> Observable.from(repository.findAll()))
				.map(entity -> DTOMapper.mapper.map(entity, dtoType))
				.toList()
				.toSingle();
	}

//	@RequestMapping(method=RequestMethod.POST)
//	public Single<F> insert(@RequestBody F entityDTO){
//		return Observable.defer(()->{
//			T entity=DTOMapper.mapper.map(entityDTO, entityType);
//			return Observable.just(repository.save(entity));
//		}).map(entity->DTOMapper.mapper.map(entity, dtoType))
//		.toSingle();
//	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Single<Void> delete(@PathVariable ID id){
		return Observable.<Void>defer(()->{
			repository.delete(id);
			return Observable.empty();
		})
		.toSingle();
	}
}
