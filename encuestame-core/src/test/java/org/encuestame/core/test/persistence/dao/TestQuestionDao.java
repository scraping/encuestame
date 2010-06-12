/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.core.test.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.encuestame.core.persistence.dao.QuestionDaoImp;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.test.service.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link QuestionDaoImp}.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 14, 2010 12:18:42 AM
 * @version $Id$
 */
public class TestQuestionDao extends AbstractBase{

    /** {@link SecUsers} **/
    private SecUsers user;

    /**
     * Before.
     */
    @Before
    public void before(){
        this.user = createUser("testEncuesta", "testEncuesta123");
        createQuestion("Do you like soccer?",  this.user);
        createQuestion("Do you like apple's?",  this.user);
        createQuestion("Do you like iPods?",  this.user);
    }


    /**
     * Test create question.
     */
    @Test
    public void testCreateQuestion(){
        final Questions question = createQuestion("Why encuestame is better than polldady?", this.user);
        final Questions retrieveQuestion = getQuestionDaoImp().retrieveQuestionById(question.getQid());
        assertEquals("Questions should be equals",question.getQid() , retrieveQuestion.getQid());
    }

    /**
     * Test retrieveQuestionsByName.
     */
    @Test
    public void testRetrieveQuestionsByName(){
        final List<Questions> listOfQuestions = getQuestionDaoImp().retrieveQuestionsByName("iPods",  this.user.getUid());
        assertEquals("Results should be equals", 1,  listOfQuestions.size());
    }

    /**
     * Test Retrieve Indexed QuestionsByName.
     */
    @Test
    public void testRetrieveIndexedQuestionsByName(){
        final List<Questions> listOfQuestions = getQuestionDaoImp().retrieveIndexQuestionsByKeyword("Do",  this.user.getUid());
        //TODO: need check this search
        assertEquals("Results should be equals", 0,  listOfQuestions.size());
    }
}
