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
import JavaApi.Samplers.NumberSamplers.NaturalNumberSamplers.StdNaturalNumberSampler;
import JavaApi.Samplers.NumberSamplers.RealNumberSamplers.RangeRealNumberSampler;
import JavaApi.Samplers.NumberSamplers.RealNumberSamplers.ConstantRealSampler;
import JavaApi.Samplers.NumberSamplers.RealNumberSamplers.IRealNumberSampler;
import JavaApi.Samplers.SamplingMode;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An abstract class which implements portions of the IQuerySampler interface which returns a random matching
 * from the set of results which are returned from applying a query pattern on a labeled graph.
 */
public abstract class StdQuerySampler implements IQuerySampler {

    /**
     * Class Members
     */
    protected boolean _isInitialized;
    protected ArrayList<QuerySolution> _results;
    protected QueryWrapper _qWrapper;
    protected StdNaturalNumberSampler _nSampler;
    protected int _maxRetResults;
    protected int _counter;
    protected String _baseQuery;

    /**
     * Public Methods
     */

    /**
     * Initializes this instance with the given data. Invoking this methods will cause a re-initialization
     * of the object regarding previous invocations
     * @param model - The model upon the given query will be executed for returning results
     */
    @Override
    public void init(Model model) {

        // Set the random number sampler
        _nSampler = new StdNaturalNumberSampler();

        // Get the query and return the results by applying it on the given model
        Query query = QueryFactory.create(_qWrapper.getQuery());
        _results = returnQueryResults(model, query);

        _counter = 0;

        // Set the nSampler according to the number of results that we got or that we wish to return
        IRealNumberSampler realNumSampler = _qWrapper.getRealNumberSampler();
        int total = _results.size();

        if (realNumSampler == null)
            _maxRetResults = total;
        else {
            double realNum = realNumSampler.getNext();
            if (realNumSampler.getClass() == ConstantRealSampler.class)
                _maxRetResults = (int) realNum;
            else if (realNumSampler.getClass() == RangeRealNumberSampler.class) {
                _maxRetResults = (int) realNum;
            } else
                _maxRetResults = (int) (realNum * (double) total);
        }

        _nSampler.setMaxValue(total - 1);

        // if we were able to set the results, the object is initialized
        _isInitialized = true;
    }

    /**
     * Another way to initialize the instance while using the caching mechanism
     * @param model - The model upon the given query will be executed for returning results
     * @param qCache - A specific instance for handling the query caching mechanism (see class documentation)
     * @param cache - Boolean that indicates if caching should be applied or not
     */
    @Override
    public void init(Model model, QueryCache qCache, boolean cache) {

        // Set the random number sampler
        _nSampler = new StdNaturalNumberSampler();

        //  Check if the cache has this query
        Query query = QueryFactory.create(_qWrapper.getQuery());
        if (qCache.cacheHasQuery(query)) {
            _results = qCache.getCachedResults(query);
            qCache.addToHitCount();
        } else {    // Run the query on the model and store the results (in case we should do so)
            _results = returnQueryResults(model, query);
            if (cache) {
                qCache.addCachedResults(query, _results);
            }
            qCache.addToQueryCount();
        }

        _counter = 0;

        // Set the nSampler according to the number of results that we got or that we wish to return
        IRealNumberSampler realNumSampler = _qWrapper.getRealNumberSampler();
        int total = _results.size();

        if (realNumSampler == null)
            _maxRetResults = total;
        else {
            double realNum = realNumSampler.getNext();
            if (realNumSampler.getClass() == ConstantRealSampler.class)
                _maxRetResults = (int) realNum;
            else if (realNumSampler.getClass() == RangeRealNumberSampler.class) {
                _maxRetResults = (int) realNum;
            } else
                _maxRetResults = (int) (realNum * (double) total);
        }

        _nSampler.setMaxValue(total - 1);

        // if we were able to set the results, the object is initialized
        _isInitialized = true;
    }

    /**
     * A method for updating a query based on previous query results that might be used in the given query
     * @param model - The relevant model upon which query is executed
     * @param queryAttributeVariableMap - A Mapping of attributes to query variables
     * @param queryAttributeResultMap - A mapping of attributes to actual query results
     * @return - Array-List of String type containing all of the dynamic variables (variables which are mapped by previous
     * queries
     */
    @Override
    public ArrayList<String> updateQueryVariables
            (Model model,
             HashMap<String, String> queryAttributeVariableMap,
             HashMap<String, String> queryAttributeResultMap) {

        ArrayList<String> dynamicVars = new ArrayList<String>();

        String strQuery = _baseQuery;
        String newStrQuery;
        for (String key : queryAttributeVariableMap.keySet()) {
            if (strQuery.contains(queryAttributeVariableMap.get(key))) {
                newStrQuery = strQuery.replaceAll(queryAttributeVariableMap.get(key), "<" + queryAttributeResultMap.get(key) + ">");
                strQuery = newStrQuery;
                dynamicVars.add(key);
            }
        }
        _qWrapper = new QueryWrapper(strQuery, _qWrapper.getMode(), _qWrapper.getRealNumberSampler(), _qWrapper.isDynamic());

        return dynamicVars;
    }

    /**
     * Returns true iff the Query Sampler was initialized by invoking the init() method.
     * @return - True iff the Query Sampler was initialized by invoking the init() method.
     */
    @Override
    public boolean isInitialized() {
        return _isInitialized;
    }

    /**
     * Returns true iff there are results that could be returned by this instance.
     * @return - True iff there are results that could be returned by this instance.
     * @throws IllegalStateException - In case the object wasn't initialized.
     */
    @Override
    public boolean hasNext() throws IllegalStateException {
        if (!_isInitialized)
            throw new IllegalStateException("The object wasn't initialized!");

        return (_results.size() > 0 && _counter < _maxRetResults);
    }

    /**
     * Returns the next matching (QuerySolution) according the mode that was set for this instance.
     * @return - The next matching (QuerySolution) according the mode that was set for this instance.
     * @throws IllegalStateException - In case that this instance wasn't initialized or if there are no query results
     *                               left to return.
     */
    @Override
    public abstract QuerySolution getNextMatching() throws IllegalStateException;

    /**
     * Returns the number of solutions that were returned by the query
     * @return The number of solutions that were returned by the query
     */
    @Override
    public int getNumberOfSolutions() {
        if (!isInitialized())
            throw new IllegalStateException("The Query-Sampler wasn't initialized");
        return _results.size();
    }

    /**
     * Returns the QueryWrapper instance used by this sampler
     * @return - The QueryWrapper instance used by this sampler
     */
    @Override
    public QueryWrapper getQueryWrapper() {
        return _qWrapper;
    }

    /**
     * Returns true if the QueryWrapper is dynamic (see class documentation), otherwise false
     * @return true if the QueryWrapper is dynamic (see class documentation), otherwise false
     */
    @Override
    public boolean isDynamic() {
        return _qWrapper.isDynamic();
    }

    /**
     * Returns the sampling-mode used by the QueryWrapper instance used by this sampler
     * @return - The sampling-mode used by the QueryWrapper instance used by this sampler
     */
    @Override
    public SamplingMode getMode() {
        return _qWrapper.getMode();
    }

    /**
     * Resets the counter of this instance to be zero
     */
    @Override
    public void resetCounter() {
        _counter = 0;
    }

    /**
     * Protected Methods
     */

    /**
     * Executes the query on the given model, while returning an array-list of QuerySolution instances holding all of
     * the results returned in the result-set.
     *
     * @param model - The model upon the given query will be executed for returning results
     * @param query - The query that will be executed on the given model for returning results
     * @return - An array-list of QuerySolution instances holding all of the results returned in the result-set.
     */
    protected ArrayList<QuerySolution> returnQueryResults(Model model, Query query) {
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = qe.execSelect();
        //ResultSetMem rsm = new ResultSetMem(resultSet);
        //int size = rsm.size();
        ArrayList<QuerySolution> results = new ArrayList<QuerySolution>();
        while (resultSet.hasNext())
            results.add(resultSet.next());
        // --------------------------------------------------------------------------------------------------------------------------
        // Note to myself :)
        // Jena Utils implementation  - less efficient
        //results = (ArrayList<QuerySolution>) ResultSetFormatter.toList(resultSet);
        // --------------------------------------------------------------------------------------------------------------------------

        return results;
    }


}
