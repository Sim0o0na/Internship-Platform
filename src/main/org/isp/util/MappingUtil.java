package org.isp.util;

import org.isp.tasks.models.entities.Task;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public final class MappingUtil {
    public static <S, D> D convert(S source, Class<D> destinationClass){
        ModelMapper mapper = new ModelMapper();
        D result = mapper.map(source, destinationClass);
        return result;
    }
    public static <S,D> List<D> convert(List<S> sourceList,
                                        Class<D> destinationClass){
        ModelMapper mapper = new ModelMapper();
        List<D> resultList = new ArrayList<>();
        for (S sourceObject : sourceList) {
            D mappedObject = mapper.map(sourceObject, destinationClass);
            resultList.add(mappedObject);
        }
        return resultList;
    }

    public static <S,D> Set<D> convert(Set<S> sourceList,
                                       Class<D> destinationClass){
        ModelMapper mapper = new ModelMapper();
        Set<D> resultList = new HashSet<>();
        for (S sourceObject : sourceList) {
            D mappedObject = mapper.map(sourceObject, destinationClass);
            resultList.add(mappedObject);
        }
        return resultList;
    }

    public static <S, D> Page<D> convert(Page<S> sourcePage, Class<D> destinationPageClass) {
        ModelMapper mapper = new ModelMapper();
        Page<D> targetPage = sourcePage.map(t -> mapper.map(t, destinationPageClass));
        return targetPage;
    }
}