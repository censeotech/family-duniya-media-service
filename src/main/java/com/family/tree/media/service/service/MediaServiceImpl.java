package com.family.tree.media.service.service;

import com.family.tree.media.service.entity.Media;
import com.family.tree.media.service.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public abstract class MediaServiceImpl implements MediaService{

    @Autowired
    private MediaRepository mediaRepository;

    @Transactional(readOnly = true)
    public List<Media> getMediaByUsers(List<Long> personIds) {
        return mediaRepository.findByPersonIdIn(personIds);
    }


}
