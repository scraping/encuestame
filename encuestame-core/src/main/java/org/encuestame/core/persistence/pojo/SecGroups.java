/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.persistence.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * SecGroups.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 * @version $Id$
 */
@Entity
@Table(name = "sec_groups")
public class SecGroups {

    private Long groupId;
    private String groupName;
    private String groupDescriptionInfo;
    private Long idState;
    private Set<SecUserSecondary> secUserSecondaries = new HashSet<SecUserSecondary>();
    private Set<Project> projects = new HashSet<Project>();
    private Set<SecPermission> secPermissions = new HashSet<SecPermission>();

    /**
     * @return groupId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id", unique = true, nullable = false)
    public Long getGroupId() {
        return this.groupId;
    }

    /**
     * @param groupId groupId
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return groupName
     */
    @Column(name = "name", length = 50)
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * @param groupName groupName
     */
    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return groupDescriptionInfo
     */
    @Column(name = "des_info")
    public String getGroupDescriptionInfo() {
        return this.groupDescriptionInfo;
    }

    /**
     * @param groupDescriptionInfo groupDescriptionInfo
     */
    public void setGroupDescriptionInfo(String groupDescriptionInfo) {
        this.groupDescriptionInfo = groupDescriptionInfo;
    }

    /**
     * @return idState
     */
    @Column(name = "id_state", nullable = true)
    public Long getIdState() {
        return this.idState;
    }

    /**
     * @param idState idState
     */
    public void setIdState(Long idState) {
        this.idState = idState;
    }

    /**
     * @return the secPermissions
     */
    @ManyToMany()
    @JoinTable(name="sec_group_permission",
               joinColumns={@JoinColumn(name="sec_id_group")},
               inverseJoinColumns={@JoinColumn(name="sec_id_permission")})
    public Set<SecPermission> getSecPermissions() {
        return secPermissions;
    }

    /**
     * @param secPermissions the secPermissions to set
     */
    public void setSecPermissions(Set<SecPermission> secPermissions) {
        this.secPermissions = secPermissions;
    }

    /**
     * @return the secUserSecondaries
     */
    @ManyToMany()
    @JoinTable(name="sec_user_group",
               joinColumns={@JoinColumn(name="sec_id_group")},
               inverseJoinColumns={@JoinColumn(name="sec_id_secondary")})
    public Set<SecUserSecondary> getSecUserSecondaries() {
        return secUserSecondaries;
    }

    /**
     * @param secUserSecondaries the secUserSecondaries to set
     */
    public void setSecUserSecondaries(Set<SecUserSecondary> secUserSecondaries) {
        this.secUserSecondaries = secUserSecondaries;
    }

    /**
     * @return the projects
     */
    @ManyToMany()
    @JoinTable(name="sec_project_group",
              joinColumns={@JoinColumn(name="sec_id_group")},
              inverseJoinColumns={@JoinColumn(name="cat_id_project")})
    public Set<Project> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}