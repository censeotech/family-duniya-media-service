package com.family.tree.media.service.service;

import com.family.tree.media.service.entity.Media;

import java.util.List;

public interface MediaService {
    public List<Media>  getMediaByUsers(List<Long> personIds);

}
