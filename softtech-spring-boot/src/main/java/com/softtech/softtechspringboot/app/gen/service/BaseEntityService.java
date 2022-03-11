package com.softtech.softtechspringboot.app.gen.service;

import com.softtech.softtechspringboot.app.gen.entity.BaseAdditionalFields;
import com.softtech.softtechspringboot.app.gen.entity.BaseEntity;
import com.softtech.softtechspringboot.app.gen.enums.BaseErrorClassNameMessage;
import com.softtech.softtechspringboot.app.gen.enums.GenErrorMessage;
import com.softtech.softtechspringboot.app.gen.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public abstract class BaseEntityService<E extends BaseEntity, D extends JpaRepository<E, Long>> {

    private final D dao;

    public List<E> findAll(){
        return dao.findAll();
    }

    public Optional<E> findById(Long id){
        return dao.findById(id);
    }

    public E save(E entity){

        setAdditionalFields(entity);
        entity = dao.save(entity);

        return entity;
    }

    private void setAdditionalFields(E entity) {

        BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();

        Long currentCustomerId = getCurrentCustomerId();

        if (baseAdditionalFields == null){
            baseAdditionalFields = new BaseAdditionalFields();
            entity.setBaseAdditionalFields(baseAdditionalFields);
        }

        if (entity.getId() == null){
            baseAdditionalFields.setCreateDate(new Date());
            baseAdditionalFields.setCreatedBy(currentCustomerId);
        }

        baseAdditionalFields.setUpdateDate(new Date());
        baseAdditionalFields.setUpdatedBy(currentCustomerId);
    }

    public void delete(E entity){
        dao.delete(entity);
    }

    @SneakyThrows
    public E getByIdWithControl(Long id) {

        Optional<E> entityOptional = findById(id);

        E entity;
        if (entityOptional.isPresent()){
            entity = entityOptional.get();
        } else {
            throw new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND,new BaseErrorClassNameMessage(entityClassName()));
        }

        return entity;
    }

    public boolean existsById(Long id){
        return dao.existsById(id);
    }

    public D getDao() {
        return dao;
    }

    //TODO: control after jwt
    private Long getCurrentCustomerId() {
        Long currentCustomer = null;
        return currentCustomer;
    }

    //isteği atılan entity nin adını dönüyor
    public String entityClassName() throws Exception {
        try {
            Class<E> cls = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            return cls.getSimpleName();
        }catch (Exception e){
            throw new RuntimeException("somethings wrong");
        }

    }



}
