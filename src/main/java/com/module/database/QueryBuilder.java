package com.module.database;

import com.module.model.auth.Role;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class QueryBuilder {

    private String joinString;
    private String resultQuery;
    private List<String> isDeadValues = new LinkedList<>();
    private LinkedList<String> whereArguments = new LinkedList<>();

    public void getByAddress(String value) {
        this.whereArguments.add("v.address='" + value + "'");
    }

    public void getByCaseNumber(String value) {
        this.whereArguments.add("v.case_number='" + value + "'");
    }

    public void getByCategory(String value) {
        this.joinString += " LEFT JOIN categories cat ON cat.uuid=v.category_uuid";
        this.whereArguments.add(value);
    }

    public void getByDateOfBirth(String value) {
        this.whereArguments.add("v.date_of_birth='" + value + "'");
    }

    public void getByDateOfDeath(String value) {
        this.whereArguments.add("v.date_of_death='" + value + "'");
    }

    public void getByDistrictName(String value) {
        this.whereArguments.add(value);
    }

    public void getByDocumentSeries(String value) {
        this.joinString += " LEFT JOIN documents doc ON doc.veteran_uuid=v.uuid";
        this.whereArguments.add("doc.series='" + value + "'");
    }

    public void getByFamilyMembers(String value) {
        this.joinString += " LEFT JOIN family_members fm on v.uuid=fm.veteran_uuid";
        this.whereArguments.add("fm.relation_degree is null");
    }

    public void getByFirstName(String value) {
        this.whereArguments.add("v.first_name='" + value + "'");
    }

    public void getByHonor(String value) {
        this.joinString += " LEFT JOIN veteran_honors vh ON vh.veteran_uuid=v.uuid LEFT JOIN honors hon ON vh.honor_uuid=hon.uuid";
        this.whereArguments.add(value);
    }

    public void getByIsDead(String value) {
        if (value.equals("true")) {
            isDeadValues.add(String.valueOf(1));
        }
    }

    public void getByMarchingOrganization(String value) {
        this.whereArguments.add("v.marching_organization='" + value + "'");
    }

    public void getByMiddleName(String value) {
        this.whereArguments.add("v.middle_name='" + value + "'");
    }

    public void getByMilitaryCountry(String value) {
        if (this.isJoinNeed("mt")) {
            this.joinString += " LEFT JOIN military_terms mt ON mt.veteran_uuid=v.uuid";
        }
        this.whereArguments.add("mt.country='" + value + "'");
    }

    public void getByMilitaryLocality(String value) {
        if (this.isJoinNeed("mt")) {
            this.joinString += " LEFT JOIN military_terms mt ON mt.veteran_uuid=v.uuid";
        }
        this.whereArguments.add("mt.locality='" + value + "'");
    }

    public void getByMilitaryUnit(String value) {
        if (this.isJoinNeed("mt")) {
            this.joinString += " LEFT JOIN military_terms mt ON mt.veteran_uuid=v.uuid";
        }
        this.whereArguments.add("mt.unit='" + value + "'");
    }

    public void getByPosition(String value) {
        this.whereArguments.add("v.position=" + value);
    }

    public void getByRank(String value) {
        this.joinString += " LEFT JOIN ranks rank ON rank.uuid=v.rank_uuid";
        this.whereArguments.add("rank.name='" + value + "'");
    }

    public void getByRegionalExecutiveCommittee(String value) {
        this.whereArguments.add("v.regional_executive_committee='" + value + "'");
    }

    public void getByRegistrationAddress(String value) {
        this.whereArguments.add("v.registration_address='" + value + "'");
    }

    public void getBySecondName(String value) {
        this.whereArguments.add("v.second_name='" + value + "'");
    }

    public void getBySubcategory(String value) {
        this.joinString += " LEFT JOIN subcategories sub ON sub.uuid=v.subcategory_uuid";
        this.whereArguments.add(value);
    }

    public void getBySubdivision(String value) {
        this.whereArguments.add("v.subdivision='" + value + "'");
    }

    public void getByVillageExecutiveCommittee(String value) {
        this.whereArguments.add("v.village_executive_committee='" + value + "'");
    }

    public void getByWorkOrganization(String value) {
        this.joinString += " LEFT JOIN workplaces work ON work.veteran_uuid=v.uuid";
        this.whereArguments.add("work.organization='" + value + "'");
    }

    public void getByWoundType(String value) {
        this.joinString += " LEFT JOIN veteran_wounds vw ON vw.veteran_uuid=v.uuid LEFT JOIN wound_types wt ON vw.type_uuid=wt.uuid";
        this.whereArguments.add("wt.type='" + value + "'");
    }

    public String prepareSelectQuery(Map<String, String> filters, int page) {
        this.resetQuery();
        isDeadValues.add(String.valueOf(0));
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            try {
                java.lang.reflect.Method method = QueryBuilder.class.getMethod("getBy" + entry.getKey(), String.class);
                try {
                    method.invoke(this, entry.getValue());
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {

                }
            } catch (NoSuchMethodException e) {

            }
        }

        List<String> userAvailableDistricts = new LinkedList<>();
        for (Role role : (List<Role>) SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            userAvailableDistricts.add(role.getDistrict().getName());
        }
        String districtsPreparedQuery = " WHERE district.name in ('" + String.join("', '", userAvailableDistricts) + "')";
        String isDeadPreparedQuery = " AND v.is_dead in (" + String.join(", ", isDeadValues) + ")";

        String countQuery;
        String paginator = "";
        if (page == -2) {
            countQuery = "count(*)";
        } else {
            countQuery = "*";

            if (page != -1) {
                paginator = " LIMIT 50 OFFSET " + (page * 50);
            }
        }


        String query = "SELECT " + countQuery + " FROM veterans v LEFT JOIN districts district ON v.district_uuid=district.uuid";
        if (whereArguments.size() > 0) {
            this.resultQuery = query + this.joinString + districtsPreparedQuery + isDeadPreparedQuery + " AND " + String.join(" AND ", this.whereArguments);
        } else {
            this.resultQuery = query + districtsPreparedQuery + isDeadPreparedQuery;
        }

        return this.resultQuery + paginator;
    }

    private boolean isJoinNeed(String alias) {
        boolean needJoin = true;
        for (String argument : this.whereArguments) {
            if (argument.substring(0, alias.length()).equals(alias)) {
                needJoin = false;
            }
        }
        return needJoin;
    }

    private void resetQuery() {
        this.isDeadValues.clear();
        this.whereArguments.clear();
        this.joinString = "";
        this.resultQuery = "";
    }
}
