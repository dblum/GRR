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

package JavaApi.RandomDataGraph.QueryOptimization;

import RdfApi.QueryOptimizationMode;
import JavaApi.Samplers.QuerySamplers.IQuerySampler;
import JavaApi.Samplers.SamplingMode;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.syntax.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Class for handling all of the query caching mechanism
 */
public class QueryCache {

    /**
     * Class members
     */
    private QueryModeParamsWrapper[] _modeParamsArray;
    private QueryOptimizationMode _mode;
    private HashMap<Query, ArrayList<QuerySolution>> _queryResultsCache;

    // Members for caching analysis only
    private int _hitCount;
    private int _queryCount;

    /**
     * Constructors
     */

    /**
     * Constructor that sets only the caching mode
     * @param mode - The caching mode to be used
     */
    public QueryCache(QueryOptimizationMode mode) {
        this(mode, null);
    }

    /**
     * Constructor that sets the caching mode and array of QueryModeParamsWrapper
     * @param mode - The caching mode to be used
     * @param modeParamsArray - The array of QueryModeParamsWrapper (see class documentation)
     */
    public QueryCache(QueryOptimizationMode mode, QueryModeParamsWrapper[] modeParamsArray) {
        _modeParamsArray = modeParamsArray;
        _mode = mode;
        _queryResultsCache = new HashMap<Query, ArrayList<QuerySolution>>();
        // For caching analysis
        _hitCount = 0;
        _queryCount = 0;
    }

    /**
     * Public Methods
     */

    /**
     * A method that returns a boolean indicating if the query's results should be cached,
     * according to the mode and other parameters
     * @param index - The depth in which we are running this query (for more details consult
     * algorithm's documentation or the GRR paper). In general a construction command can have
     * multiple queries, each might refer to previous query results. 
     * @return - True if the results of this query should be cached
     */
    public boolean shouldCache(int index) {
        if (_mode == QueryOptimizationMode.NO_CACHE)
            return false;
        if (_mode == QueryOptimizationMode.ALWAYS_CACHE)
            return true;
        // This means we are using smart caching - non dynamic
        if (_modeParamsArray == null)
            return index != 0;
        // ---------------------------------------------------------------------------------------------------------------
        // Now we need to check if the following is true regarding this query:
        // for all j < index
        // 1) Qj runs in global-distinct mode
        //  - and -
        // 2) All output parameters of Qj are input parameters of Qindex
        // Note: index==1 is a special case (we never cache the results of the first level
        // ---------------------------------------------------------------------------------------------------------------
        if (index==0)
                return false;

        boolean modesTestFail = false;
        boolean varsTestFail = false;
        ArrayList<String> allOutVars = new ArrayList<String>();
        // Check (1)
        for (int j=0; j<index; j++)
       {
           if (_modeParamsArray[j].getQMode()!= SamplingMode.RANDOM_GLOBAL_DISTINCT)
           {
               modesTestFail = true;
               break;
           }
           allOutVars.addAll(_modeParamsArray[j].getQOutParams());
       }
       // Check (2)
        ArrayList<String> inVars = _modeParamsArray[index].getQInParams();
       for (String outVar : allOutVars)
       {
           if (!inVars.contains("?" + outVar))
           {
               varsTestFail = true;
               break;
           }
       }

       return (modesTestFail || varsTestFail);

    }

    /**
     * Returns true if query can be found in the cache 
     * @param query - The searched query
     * @return - True if query can be found in the cache, otherwise False
     */
    public boolean cacheHasQuery(Query query) {
        return _queryResultsCache.containsKey(query);
    }

    /**
     * Returns the cached results of the given query
     * @param query - The query that we wish to get its cached results
     * @return - The cached results of the given query 
     */
    public ArrayList<QuerySolution> getCachedResults(Query query) {
        return _queryResultsCache.get(query);
    }

    /**
     * Adds an pair of query and its results to the cache
     * @param query - The added query
     * @param solutions - The cached results
     */
    public void addCachedResults(Query query, ArrayList<QuerySolution> solutions)
    {
        _queryResultsCache.put(query, solutions);
    }

    /**
     * A special method for updating a query parameters in case that the query is a 'dynamic'
     * query which relies on previous query results
     * @param index - The depth in which we are running this query (for more details consult
     * algorithm's documentation or the GRR paper).
     * @param qSampler - The query-sampler holding the relevant query
     * @param dynamicParams - The array of dynamic parameters used in previous queries
     */
    public void updateQueryParams(int index, IQuerySampler qSampler, ArrayList<String> dynamicParams) {
        QueryModeParamsWrapper qModeParamsWrapper = _modeParamsArray[index];
        String queryStr = qSampler.getQueryWrapper().getQuery();
        Query query = QueryFactory.create(queryStr);
        // Update the out parameters
        ArrayList<String> qOutParams = (ArrayList<String>) query.getResultVars();
        qModeParamsWrapper.setQOutParams(qOutParams);
        // Update the in parameters
        ArrayList<String> qInParams = new ArrayList<String>();
        Element elem = query.getQueryPattern();
        Set<Var> set = elem.varsMentioned();
        Var[] arr = set.toArray(new Var[set.size()]);
        for (Var var : arr)
            qInParams.add(var.toString());
        qInParams.addAll(dynamicParams);
        qModeParamsWrapper.setQInParams(qInParams);

    }

    /**
     * Returns the hash-map containing the mapping between queries and the actual results
     * @return - The hash-map containing the mapping between queries and the actual results
     */
    public HashMap<Query, ArrayList<QuerySolution>> getQueryResultsCache() {
        return _queryResultsCache;
    }

    /**
     * Helper public methods for cache analysis
     */

    /**
     * Method that increments the cache hit counter by 1
     */
    public void addToHitCount()
    {
        _hitCount += 1;
    }

    /**
     * Method that increments the query counter by 1
     */
    public void addToQueryCount() {
        _queryCount += 1;
    }

    /**
     * Method that returns the cache hit counter
     * @return - The cache hit counter
     */
    public int getHitCount() {
        return _hitCount;
    }

    /**
     * Methods that returns the query counter
     * @return - The query counter
     */
    public int getQueryCount() {
        return _queryCount;
    }


}
