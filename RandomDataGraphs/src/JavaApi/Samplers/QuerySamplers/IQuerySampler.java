/****************************************************************************************
 * Copyright (C) 2010,2011 The Hebrew University of Jerusalem, 
 * Department of Computer Science and Engineering
 * Project: GRR - Generating Random RDF
 * Author:	Daniel Blum, The Hebrew University of Jerusalem, 
 * Department of Computer Science and Engineering, http://www.cs.huji.ac.il/~danieb12/
 *
 * This file is part of GRR.
 * GRR is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Please note that no part of this code is officially supported by HUJI
 * (The Hebrew University of Jerusalem) or any of its developers.
 *
 *  GRR is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with GRR (COPYRIGHT-gpl.txt).  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************************/

package JavaApi.Samplers.QuerySamplers;

import JavaApi.RandomDataGraph.QueryOptimization.QueryCache;
import JavaApi.Samplers.SamplingMode;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An interface for a Query Result Sampler, called q-sampler for short, which chooses a matching
 * from the set of results which are returned from applying a query pattern on a labeled graph.
 */
public interface IQuerySampler {

    /**
     * Initializes this instance with the given data. Invoking this methods will cause a re-initialization
     * of the object regarding previous invocations
     * @param model - The model upon the given query will be executed for returning results
     */
    public void init(Model model);

    /**
     * Initializes this instance with the given data. Invoking this methods will cause a re-initialization
     * of the object regarding previous invocations
     * @param model - The model upon the given query will be executed for returning results
     * @param qCache - A specific instance for handling the query caching mechanism (see class documentation)
     * @param cache - Boolean that indicates if caching should be applied or not
     */
    public void init(Model model, QueryCache qCache, boolean cache);

    /**
     * A method for updating a query based on previous query results that might be used in the given query
     * @param model - The relevant model upon which query is executed
     * @param queryAttributeVariableMap - A Mapping of attributes to query variables
     * @param queryAttributeResultMap - A mapping of attributes to actual query results
     * @return Array-List of String type containing all of the dynamic variables (variables which are mapped by previous
     * queries
     */
    public ArrayList<String> updateQueryVariables
            (Model model,
             HashMap<String, String> queryAttributeVariableMap,
             HashMap<String, String> queryAttributeResultMap);

    /**
     * Returns true iff the Query Sampler was initialized by invoking the init() method.
     * @return - True iff the Query Sampler was initialized by invoking the init() method.
     */
    public boolean isInitialized();

    /**
     * Returns true iff there are results that could be returned by this instance.
     * @return - True iff there are results that could be returned by this instance.
     * @throws IllegalStateException - In case the object wasn't initialized.
     */
    public boolean hasNext() throws IllegalStateException;

    /**
     * Returns the next matching (QuerySolution) according the mode that was set for this instance.
     * @return - The next matching (QuerySolution) according the mode that was set for this instance.
     * @throws IllegalStateException - In case that this instance wasn't initialized or if there are no query results
     * left to return.
     */
    public QuerySolution getNextMatching() throws IllegalStateException;

    /**
     * Returns the number of solutions that were returned by the query
     * @return The number of solutions that were returned by the query
     */
    public int getNumberOfSolutions();

    /**
     * Returns the QueryWrapper instance used by this sampler
     * @return - The QueryWrapper instance used by this sampler
     */
    public QueryWrapper getQueryWrapper();

    /**
     * Returns true if the QueryWrapper is dynamic (see class documentation), otherwise false
     * @return true if the QueryWrapper is dynamic (see class documentation), otherwise false
     */
    public boolean isDynamic();

    /**
     * Returns the sampling-mode used by the QueryWrapper instance used by this sampler
     * @return - The sampling-mode used by the QueryWrapper instance used by this sampler
     */
    public SamplingMode getMode();

    /**
     * Resets the counter of this instance to be zero
     */
    public void resetCounter();
}
