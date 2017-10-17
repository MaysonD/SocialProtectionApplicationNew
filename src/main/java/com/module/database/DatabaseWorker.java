package com.module.database;


import com.module.model.auth.Role;
import com.module.model.auth.User;
import com.module.model.entity.*;
import com.module.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseWorker {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private HonorRepository honorRepository;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private WoundDisabilityRepository woundDisabilityRepository;

    @Autowired
    private WoundTypeRepository woundTypeRepository;

    @Autowired
    private VeteranRepository veteranRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RgvkRepository rgvkRepository;

    @Autowired
    private EntityManager entityManager;

    public void deleteHonor(HonorEntity honor) {
        honorRepository.delete(honor);
    }

    public void deleteRank(RankEntity rank) {
        rankRepository.delete(rank);
    }

    public void deleteRgvk(RgvkEntity rgvkEntity) {
        rgvkRepository.delete(rgvkEntity);
    }

    public void deleteVeteran(VeteranEntity veteranEntity) {
        veteranRepository.delete(veteranEntity);
    }

    public void deleteWoundDisability(WoundDisabilityEntity woundDisability) {
        woundDisabilityRepository.delete(woundDisability);
    }

    public void deleteWoundType(WoundTypeEntity woundType) {
        woundTypeRepository.delete(woundType);
    }

    public List<VeteranEntity> filterVeterans(String query) {
        return entityManager.createNativeQuery(query, VeteranEntity.class).getResultList();
    }

    public Set<String> getAutoCompleteList(String table, String field) {
        List<String> dblist = entityManager.createNativeQuery("SELECT " + field + " FROM " + table + " WHERE " + field + " IS NOT NULL ").getResultList();
        Set<String> dbset = new HashSet<String>(dblist);
        return dbset;

    }

    public List<CategoryEntity> getCategories() {
        return categoryRepository.findAll();
    }

    public List<DistrictEntity> getDistricts() {
        List<Role> roles = (List<Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<DistrictEntity> districts = new LinkedList<>();
        for (Role role : roles) {
            districts.add(role.getDistrict());
        }

        return districts;
    }

    public List<HonorEntity> getHonors() {
        return honorRepository.findAll();
    }

    public List<RankEntity> getRanks() {
        return rankRepository.findAll();
    }

    public List<RgvkEntity> getRgvk() {
        return rgvkRepository.findAll();
    }

    public List<VeteranEntity> getVeterans() {
        return veteranRepository.findAll();
    }

    public int getVeteransCount(String query) {
        BigInteger veteransCount = (BigInteger) entityManager.createNativeQuery(query).getSingleResult();
        int intVeteransCount = veteransCount.intValue();
        return intVeteransCount;
    }

    public List<WoundDisabilityEntity> getWoundDisabilities() {
        return woundDisabilityRepository.findAll();
    }

    public List<WoundTypeEntity> getWoundTypes() {
        return woundTypeRepository.findAll();
    }

    public void saveHonor(HonorEntity honorEntity) {
        honorRepository.save(honorEntity);
    }

    public void saveRank(RankEntity rankEntity) {
        rankRepository.save(rankEntity);
    }

    public void saveRgvk(RgvkEntity rgvkEntity) {
        rgvkRepository.save(rgvkEntity);
    }

    public void saveUser(User honorEntity) {
        userRepository.save(honorEntity);
    }

    public void saveVeteran(VeteranEntity veteranEntity) {
        veteranRepository.save(veteranEntity);
    }

    public void saveWoundDisability(WoundDisabilityEntity woundDisabilityEntity) {
        woundDisabilityRepository.save(woundDisabilityEntity);
    }

    public void saveWoundType(WoundTypeEntity woundTypeEntity) {
        woundTypeRepository.save(woundTypeEntity);
    }
}
