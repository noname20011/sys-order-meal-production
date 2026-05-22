package sys_order_meal_healthy.helper.base.repository;

import sys_order_meal_healthy.config.Translator;
import sys_order_meal_healthy.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

@NoRepositoryBean

public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    @Transactional(readOnly = true)
    default T findByIdOrThrow(ID id) {
        return findById(id)
                .orElseThrow(() ->
                new NotFoundException(Translator.toLocale("entity.not.found", id)));
    }

    @Transactional(readOnly = true)
    default T findByIdOrThrowWithMessage(ID id, String entityName) {
        return findById(id)
                .orElseThrow(() ->
                        new NotFoundException(Translator.toLocale("exception.entity.not.found", entityName, id)));
    }


    @Transactional(readOnly = true)
    default List<T> findAllSorted(Sort sort) {
        return findAll(sort);
    }

    @Transactional(readOnly = true)
    default Page<T> paginate(Pageable pageable) {
        return findAll(pageable);
    }


    @Transactional(readOnly = true)
    default List<T> getAll() {
        return findAll();
    }

    @Transactional
    default T save(T entity, Function<T, T> callback) {
        T result = save(entity);
        if (callback != null) {
            callback.apply(result);
        }
        return result;
    }

    @Transactional
    default T update(ID id, T entity) {

        T existingEntity = findByIdOrThrow(id);

        org.springframework.beans.BeanUtils.copyProperties(entity, existingEntity, "id");

        return save(existingEntity);
    }

    @Transactional
    default T update(ID id, T entity, Function<T, T> callback) {
        T result = findByIdOrThrow(id);

        T saved = save(result);
        if (callback != null) {
            callback.apply(saved);
        }

        return save(saved);
    }

    @Transactional
    default void delete(ID id) {
        T result = findByIdOrThrow(id);
        delete(result);
    }

    @Transactional
    default T delete(ID id, Function<T, T> callback) {
        T result = findByIdOrThrow(id);
        deleteById(id);
        if (callback != null) {
            callback.apply(result);
        }
        return result;
    }
}
