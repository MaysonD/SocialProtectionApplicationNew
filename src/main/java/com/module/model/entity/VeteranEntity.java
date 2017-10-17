package com.module.model.entity;

import com.module.xml.adapters.BooleanAdapter;
import com.module.xml.adapters.LocalDateAdapter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@XmlRootElement
@Entity
@Table(name = "veterans")
public class VeteranEntity {

    @Column(name = "address")
    private String address;

    @Column(name = "burial_place")
    private String burialPlace;

    @Column(name = "case_number")
    private String caseNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_uuid")
    private CategoryEntity category;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_death")
    private LocalDate dateOfDeath;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "veteran", targetEntity = DisplacementEntity.class, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<DisplacementEntity> displacements = new LinkedList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_uuid")
    private DistrictEntity district;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "veteran", targetEntity = DocumentEntity.class, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<DocumentEntity> documents = new LinkedList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "veteran", targetEntity = FamilyMemberEntity.class, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<FamilyMemberEntity> familyMembers = new LinkedList<>();

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "is_dead")
    private Boolean isDead;

    @Column(name = "marching_organization")
    private String marchingOrganization;

    @Column(name = "middle_name")
    private String middleName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rank_uuid")
    private RankEntity militaryRank;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "veteran", targetEntity = MilitaryTermEntity.class, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<MilitaryTermEntity> militaryTerms = new LinkedList<>();

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "position")
    private String position;

    @Column(name = "regional_executive_committee")
    private String regionalExecutiveCommittee;

    @Column(name = "registration_address")
    private String registrationAddress;

    @Column(name = "second_name")
    private String secondName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcategory_uuid")
    private SubcategoryEntity subcategory;

    @Column(name = "subdivision")
    private String subdivision;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rgvk_uuid")
    private RgvkEntity rgvk;

    @Id
    @GeneratedValue(strategy = IDENTITY, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "com.module.helpers.UuidAutoGenerator")
    @Column(name = "uuid", columnDefinition = "BINARY(16)")
    private UUID uuid;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "veteran", targetEntity = VeteranHonorEntity.class, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<VeteranHonorEntity> veteranHonors = new LinkedList<>();

    @Column(name = "village_executive_committee")
    private String villageExecutiveCommittee;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "veteran", targetEntity = WorkPlaceEntity.class, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<WorkPlaceEntity> workPlaces = new LinkedList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "veteran", targetEntity = HelpEntity.class, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<HelpEntity> helpList = new LinkedList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "veteran", targetEntity = VeteranWoundEntity.class, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<VeteranWoundEntity> wounds = new LinkedList<>();

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if ((object == null) || !(object instanceof VeteranEntity))
            return false;

        final VeteranEntity b = (VeteranEntity) object;

        return uuid != null && b.getUuid() != null && uuid.equals(b.getUuid());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBurialPlace() {
        return burialPlace;
    }

    public void setBurialPlace(String burialPlace) {
        this.burialPlace = burialPlace;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public List<DisplacementEntity> getDisplacements() {
        return displacements;
    }

    public void setDisplacements(List<DisplacementEntity> displacements) {
        this.displacements = displacements;
        for (DisplacementEntity displacementEntity : displacements) {
            displacementEntity.setVeteran(this);
        }
    }

    public DistrictEntity getDistrict() {
        return district;
    }

    public void setDistrict(DistrictEntity district) {
        this.district = district;
    }

    public List<DocumentEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentEntity> documents) {
        this.documents = documents;
        for (DocumentEntity documentEntity : documents) {
            documentEntity.setVeteran(this);
        }
    }

    public List<FamilyMemberEntity> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMemberEntity> familyMembers) {
        this.familyMembers = familyMembers;
        for (FamilyMemberEntity familyMemberEntity : familyMembers) {
            familyMemberEntity.setVeteran(this);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<HelpEntity> getHelpList() {
        return helpList;
    }

    public void setHelpList(List<HelpEntity> helpList) {
        this.helpList = helpList;
        for (HelpEntity helpEntity : helpList) {
            helpEntity.setVeteran(this);
        }
    }

    public Boolean getIsDead() {
        return isDead;
    }

    @XmlJavaTypeAdapter(value = BooleanAdapter.class)
    public void setIsDead(Boolean dead) {
        isDead = dead;
    }

    public String getMarchingOrganization() {
        return marchingOrganization;
    }

    public void setMarchingOrganization(String marchingOrganization) {
        this.marchingOrganization = marchingOrganization;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public RankEntity getMilitaryRank() {
        return militaryRank;
    }

    public void setMilitaryRank(RankEntity rank) {
        this.militaryRank = rank;
    }

    public List<MilitaryTermEntity> getMilitaryTerms() {
        return militaryTerms;
    }

    public void setMilitaryTerms(List<MilitaryTermEntity> militaryTerms) {
        this.militaryTerms = militaryTerms;
        for (MilitaryTermEntity militaryTermEntity : militaryTerms) {
            militaryTermEntity.setVeteran(this);
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] bytes) throws SQLException {
        this.photo = bytes;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRegionalExecutiveCommittee() {
        return regionalExecutiveCommittee;
    }

    public void setRegionalExecutiveCommittee(String regionalExecutiveCommittee) {
        this.regionalExecutiveCommittee = regionalExecutiveCommittee;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public RgvkEntity getRgvk() {
        return rgvk;
    }

    public void setRgvk(RgvkEntity rgvk) {
        this.rgvk = rgvk;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public SubcategoryEntity getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(SubcategoryEntity subcategory) {
        this.subcategory = subcategory;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<VeteranHonorEntity> getVeteranHonors() {
        return veteranHonors;
    }

    public void setVeteranHonors(List<VeteranHonorEntity> veteranHonor) {
        this.veteranHonors = veteranHonor;
        for (VeteranHonorEntity veteranHonorEntity : veteranHonor) {
            veteranHonorEntity.setVeteran(this);
        }
    }

    public String getVillageExecutiveCommittee() {
        return villageExecutiveCommittee;
    }

    public void setVillageExecutiveCommittee(String villageExecutiveCommittee) {
        this.villageExecutiveCommittee = villageExecutiveCommittee;
    }

    public List<WorkPlaceEntity> getWorkPlaces() {
        return workPlaces;
    }

    public void setWorkPlaces(List<WorkPlaceEntity> workPlaces) {
        this.workPlaces = workPlaces;
        for (WorkPlaceEntity workPlaceEntity : workPlaces) {
            workPlaceEntity.setVeteran(this);
        }
    }

    public List<VeteranWoundEntity> getWounds() {
        return wounds;
    }

    public void setWounds(List<VeteranWoundEntity> wounds) {
        this.wounds = wounds;
        for (VeteranWoundEntity veteranWoundEntity : wounds) {
            veteranWoundEntity.setVeteran(this);
        }
    }

    public void removeDisplacement(DisplacementEntity displacement) {
        getDisplacements().remove(displacement);
        displacement.setVeteran(null);
    }

    public void removeDocument(DocumentEntity document) {
        getDocuments().remove(document);
        document.setVeteran(null);
    }

    public void removeFamilyMember(FamilyMemberEntity member) {
        getFamilyMembers().remove(member);
        member.setVeteran(null);

    }

    public void removeMilitaryTerm(MilitaryTermEntity militaryTerm) {
        getMilitaryTerms().remove(militaryTerm);
        militaryTerm.setVeteran(null);

    }

    public void removeVeteranHonor(VeteranHonorEntity honor) {
        getVeteranHonors().remove(honor);
        honor.setVeteran(null);

    }

    public void removeWorkPlace(WorkPlaceEntity place) {
        getWorkPlaces().remove(place);
        place.setVeteran(null);

    }

    public void removeWound(VeteranWoundEntity wound) {
        getWounds().remove(wound);
        wound.setVeteran(null);

    }

    @Override
    public String toString() {
        return "VeteranEntity{" +
                "uuid=" + uuid +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", district=" + district +
                ", middleName='" + middleName + '\'' +
                ", caseNumber='" + caseNumber + '\'' +
                ", subdivision='" + subdivision + '\'' +
                ", address='" + address + '\'' +
                ", registrationAddress='" + registrationAddress + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfDeath=" + dateOfDeath +
                ", regionalExecutiveCommittee='" + regionalExecutiveCommittee + '\'' +
                ", villageExecutiveCommittee='" + villageExecutiveCommittee + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", marchingOrganization='" + marchingOrganization + '\'' +
                ", burialPlace='" + burialPlace + '\'' +
                ", isDead=" + isDead +
                ", position='" + position + '\'' +
                ", rank=" + militaryRank +
                ", category=" + category +
                ", subcategory=" + subcategory +
                ", familyMembers=" + familyMembers +
                ", workPlaces=" + workPlaces +
                ", wounds=" + wounds +
                ", documents=" + documents +
                ", displacements=" + displacements +
                ", militaryTerms=" + militaryTerms +
                ", veteranHonors=" + veteranHonors +
                '}';
    }

}
