package com.family.tree.media.service.repository;

import org.springframework.data.repository.CrudRepository;
import com.family.tree.media.service.entity.Media;

import java.util.List;

public interface MediaRepository extends CrudRepository<Media,Long> {
    List<Media> findByPersonIdIn(List<Long> personIds);
}
