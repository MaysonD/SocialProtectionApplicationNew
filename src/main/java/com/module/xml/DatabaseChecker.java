package com.module.xml;

import com.module.model.entity.*;
import com.module.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseChecker {

    @Autowired
    private HonorRepository honorRepository;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private RgvkRepository rgvkRepository;

    @Autowired
    private WoundDisabilityRepository woundDisabilityRepository;

    @Autowired
    private WoundTypeRepository woundTypeRepository;

    private Map<String, List<String>> cache = new LinkedHashMap<>();

    public DatabaseChecker() {
        cache.put("Rank", new LinkedList<>());
        cache.put("Honor", new LinkedList<>());
        cache.put("Rgvk", new LinkedList<>());
        cache.put("Disability", new LinkedList<>());
        cache.put("Type", new LinkedList<>());
    }

    public HonorEntity checkHonor(HonorEntity honorEntity) {
        if (honorEntity != null) {
            HonorEntity honor = null;

            for (String existingHonor : cache.get("Honor")) {
                if (honorEntity.getName().equals(existingHonor)) {
                    return honorEntity;
                }
            }

            honor = honorRepository.findByName(honorEntity.getName());
            if (honor != null) {
                if (honor.getUuid().equals(honorEntity.getUuid())) {
                    saveToCache("Honor", honor.getName());
                } else {
                    honorEntity.setUuid(honor.getUuid());
                }
            } else {
                honorRepository.save(honorEntity);
                saveToCache("Honor", honorEntity.getName());
            }
        }

        return honorEntity;
    }

    public RankEntity checkRank(RankEntity rankEntity) {
        if (rankEntity != null) {
            RankEntity rank = null;

            for (String existingRank : cache.get("Rank")) {
                if (rankEntity.getName().equals(existingRank)) {
                    return rankEntity;
                }
            }

            rank = rankRepository.findByName(rankEntity.getName());
            if (rank != null) {
                if (rank.getUuid().equals(rankEntity.getUuid())) {
                    saveToCache("Rank", rank.getName());
                } else {
                    rankEntity.setUuid(rank.getUuid());
                }
            } else {
                rankRepository.save(rankEntity);
                saveToCache("Rank", rankEntity.getName());
            }
        }

        return rankEntity;
    }

    public RgvkEntity checkRgvk(RgvkEntity rgvkEntity) {
        if (rgvkEntity != null) {
            RgvkEntity rgvk = null;

            for (String existingRgvk : cache.get("Rgvk")) {
                if (rgvkEntity.getName().equals(existingRgvk)) {
                    return rgvkEntity;
                }
            }

            rgvk = rgvkRepository.findByName(rgvkEntity.getName());
            if (rgvk != null) {
                if (rgvk.getUuid().equals(rgvkEntity.getUuid())) {
                    saveToCache("Rgvk", rgvk.getName());
                } else {
                    rgvkEntity.setUuid(rgvk.getUuid());
                }
            } else {
                rgvkRepository.save(rgvkEntity);
                saveToCache("Rgvk", rgvkEntity.getName());
            }
        }

        return rgvkEntity;
    }

    public WoundDisabilityEntity checkWoundDisability(WoundDisabilityEntity woundDisabilityEntity) {
        if (woundDisabilityEntity != null) {
            WoundDisabilityEntity disability = null;

            for (String existingDisability : cache.get("Disability")) {
                if (woundDisabilityEntity.getDisability().equals(existingDisability)) {
                    return woundDisabilityEntity;
                }
            }

            disability = woundDisabilityRepository.findByDisability(woundDisabilityEntity.getDisability());
            if (disability != null) {
                if (disability.getUuid().equals(woundDisabilityEntity.getUuid())) {
                    saveToCache("Disability", disability.getDisability());
                } else {
                    woundDisabilityEntity.setUuid(disability.getUuid());
                }
            } else {
                woundDisabilityRepository.save(woundDisabilityEntity);
                saveToCache("Disability", woundDisabilityEntity.getDisability());
            }
        }

        return woundDisabilityEntity;
    }

    public WoundTypeEntity checkWoundType(WoundTypeEntity woundTypeEntity) {
        if (woundTypeEntity != null) {
            WoundTypeEntity type = null;

            for (String existingWoundType : cache.get("Type")) {
                if (woundTypeEntity.getType().equals(existingWoundType)) {
                    return woundTypeEntity;
                }
            }

            type = woundTypeRepository.findByType(woundTypeEntity.getType());
            if (type != null) {
                if (type.getUuid().equals(woundTypeEntity.getUuid())) {
                    saveToCache("Type", type.getType());
                } else {
                    woundTypeEntity.setUuid(type.getUuid());
                }
            } else {
                woundTypeRepository.save(woundTypeEntity);
                saveToCache("Type", woundTypeEntity.getType());
            }
        }

        return woundTypeEntity;
    }

    private void saveToCache(String cacheType, String cachedName) {
        List<String> existingNames = cache.get(cacheType);
        existingNames.add(cachedName);
        cache.put(cacheType, existingNames);
    }

}
