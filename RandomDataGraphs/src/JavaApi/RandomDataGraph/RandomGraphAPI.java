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

package JavaApi.RandomDataGraph;

import Examples.Utils.ExpLogger;
import JavaApi.RandomDataGraph.Matchers.IMatcher;
import JavaApi.RandomDataGraph.Patterns.ConstructionPattern;
import JavaApi.RandomDataGraph.QueryOptimization.QueryCache;
import JavaApi.RandomDataGraph.QueryOptimization.QueryModeParamsWrapper;
import JavaApi.RandomDataGraph.RandomDataGraphExceptions.RdfNodeExistsInModelException;
import JavaApi.Samplers.NumberSamplers.NaturalNumberSamplers.INaturalNumberSampler;
import JavaApi.Samplers.QuerySamplers.IQuerySampler;
import JavaApi.Samplers.QuerySamplers.QueryWrapper;
import JavaApi.Samplers.QuerySamplers.StdQuerySamplerFactory;
import JavaApi.Samplers.SamplingMode;
import RdfApi.QueryOptimizationMode;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.syntax.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * The Java API that implements the logic and algorithm of the GRR system (see relevant paper and documentation)
 */
public class RandomGraphAPI {

    /**
     * Public Methods - API
     */

    // ---------------------------------------------------------------------------------------------------
    // Composite construction commands
    // ---------------------------------------------------------------------------------------------------

    /**
     * Construction Command (without dynamic queries)
     * For more information please review the GRR documentation
     * @param model - The model which will be used for querying and updating
     * @param qWrappers - Ordered Array-List of QueryWrapper instances which represent the queries and relevant query
     * meta-data needed for execution
     * @param nSampler - The number-sampler used to define how many times to create the pattern 
     * @param cPattern - The actual construction pattern used
     * @param matcher - The matcher matches the pattern to the different results returned by searching the pattern in
     * the model (mapping between existing nodes to nodes in the pattern itself
     * @param mode - The query optimization mode to be used
     * @param expLogger - Internal class used for performance monitoring. Users should pass null here
     * @return - The updated model
     * @throws Exception - Currently throws a generic exception in any case that the recursive construction failed
     */
    public Model construct(
            Model model,
            ArrayList<QueryWrapper> qWrappers,
            INaturalNumberSampler nSampler,
            ConstructionPattern cPattern,
            IMatcher matcher,
            QueryOptimizationMode mode,
            ExpLogger expLogger) throws Exception {

        // Initial verification of the parameters
        verifyInitialization(nSampler);

        // We create a temp model upon which we'll do all of the changes (otherwise we'll query an updated model that
        // might not match the original assumptions we had)
        Model tempModel = ModelFactory.createDefaultModel();
        tempModel.setNsPrefixes(model.getNsPrefixMap());
        // Create a QueryCache instance
        QueryCache qCache = new QueryCache(mode);

        // Set some helper variables
        ArrayList<QuerySolution> solutions = new ArrayList<QuerySolution>();
        int index = 1;
        IQuerySampler[] qSamplers = (qWrappers == null) ? new IQuerySampler[0] : new IQuerySampler[qWrappers.size()];
        for (int i = 0; i < qSamplers.length; i++)
            qSamplers[i] = StdQuerySamplerFactory.getStdQuerySampler(qWrappers.get(i));

        // Run the recursive command
        try {
            constructRecursive(
                    model,
                    tempModel,
                    qSamplers,
                    nSampler,
                    cPattern,
                    solutions,
                    matcher,
                    index,
                    qCache);
        }
        catch (RdfNodeExistsInModelException e) {
            e.printStackTrace();
        }

        if (expLogger != null) {
            expLogger.addToCacheHitCount(qCache.getHitCount());
            expLogger.addToQueryExecutionCount(qCache.getQueryCount());
            expLogger.logCacheSize(qCache);
        }

        // Update the model with the changes we made on tempModel
        model.add(tempModel);
        tempModel.close();
        return model;

    }

    /**
     * Construction Command (with dynamic queries)
     * For more information please review the GRR documentation
     * @param model - The model which will be used for querying and updating
     * @param qWrappers - Ordered Array-List of QueryWrapper instances which represent the queries and relevant query
     * meta-data needed for execution
     * @param queryAttributeVariableMap - A mapping of attributes to query variables to be used in resolving the actual
     * values of dynamic variables
     * @param nSampler - The number-sampler used to define how many times to create the pattern
     * @param cPattern - The actual construction pattern used
     * @param matcher - The matcher matches the pattern to the different results returned by searching the pattern in
     * the model (mapping between existing nodes to nodes in the pattern itself
     * @param mode - The query optimization mode to be used
     * @param expLogger - Internal class used for performance monitoring. Users should pass null here
     * @return - The updated model
     * @throws Exception - Currently throws a generic exception in any case that the recursive construction failed
     */
    public Model constructDynamic(
            Model model,
            ArrayList<QueryWrapper> qWrappers,
            HashMap<String, String> queryAttributeVariableMap,
            INaturalNumberSampler nSampler,
            ConstructionPattern cPattern,
            IMatcher matcher,
            QueryOptimizationMode mode,
            ExpLogger expLogger) throws Exception {

        // Initial verification of the parameters
        verifyInitialization(nSampler);

        // We create a temp model upon which we'll do all of the changes (otherwise we'll query an updated model that
        // might not match the original assumptions we had)
        Model tempModel = ModelFactory.createDefaultModel();
        tempModel.setNsPrefixes(model.getNsPrefixMap());

        // Set some helper variables
        ArrayList<QuerySolution> solutions = new ArrayList<QuerySolution>();
        int index = 1;
        IQuerySampler[] qSamplers = (qWrappers == null) ? new IQuerySampler[0] : new IQuerySampler[qWrappers.size()];
        for (int i = 0; i < qSamplers.length; i++)
            qSamplers[i] = StdQuerySamplerFactory.getStdQuerySampler(qWrappers.get(i));

        // Set the caching
        QueryModeParamsWrapper[] modeOutParamsArray = new QueryModeParamsWrapper[qSamplers.length];
        for (int j = 0; j < qSamplers.length; j++)
            modeOutParamsArray[j] = parseQuerySampler(qSamplers[j], j);

        QueryCache qCache = new QueryCache(mode, modeOutParamsArray);

        HashMap<String, String> queryAttributeResultMap = new HashMap<String, String>();

        // Run the recursive command
        try {
            constructDynamicRecursive(
                    model,
                    tempModel,
                    qSamplers,
                    queryAttributeVariableMap,
                    queryAttributeResultMap,
                    nSampler,
                    cPattern,
                    solutions,
                    matcher,
                    index,
                    qCache);
        }
        catch (RdfNodeExistsInModelException e) {
            e.printStackTrace();
        }

        if (expLogger != null) {
            expLogger.addToCacheHitCount(qCache.getHitCount());
            expLogger.addToQueryExecutionCount(qCache.getQueryCount());
            expLogger.logCacheSize(qCache);
        }

        // Update the model with the changes we made on tempModel
        model.add(tempModel);
        tempModel.close();
        return model;
    }


    /**
     * The recursive version of construct (without dynamic variables)
     * @param model - The model which will be used for querying and updating
     * @param tempModel - The temp work model which will be updating and finally merged into the original model
     * @param qSamplers - An ordered array of query-samplers that will be used
     * @param nSampler - The number-sampler used to define how many times to create the pattern
     * @param cPattern - The actual construction pattern used
     * @param solutions - Array-List of QuerySolutions that will be used for mapping the actual model and the pattern
     * that defines how to create and update the model
     * @param matcher - The matcher matches the pattern to the different results returned by searching the pattern in
     * the model (mapping between existing nodes to nodes in the pattern itself
     * @param index - The recursion depth (matches the depth of the query being executed
     * @param qCache - A wrapper for all of the query-caching operations
     * @throws RdfNodeExistsInModelException - Thrown in case that we are trying to create a duplicate node
     * @throws IOException - Thrown in case of an IO failure (e.g. usage of files to load dictionary-samplers)
     */
    public void constructRecursive(
            Model model,
            Model tempModel,
            IQuerySampler[] qSamplers,
            INaturalNumberSampler nSampler,
            ConstructionPattern cPattern,
            ArrayList<QuerySolution> solutions,
            IMatcher matcher,
            int index,
            QueryCache qCache) throws RdfNodeExistsInModelException, IOException {

        // If we reached the most inner loop - Stop and invoke the simple-construction
        if (index > qSamplers.length) {

            // Set the mapping of all query solutions to actual returned nodes
            if (matcher != null)
                matcher.setNodesMapping(solutions, cPattern.getOldNodes());
            // Get the random number of iterations to invoke the pattern
            int n = nSampler.getNextNatural();
            for (int i = 0; i < n; i++) {
                // Apply the construction pattern on the model
                cPattern.applyPatternOnModel(tempModel);
            }
        } else {
            IQuerySampler outerQSampler = qSamplers[index - 1];
            // If the qSampler wasn't initialized of if we got a local-distinct we must init/re-init it
            if (!outerQSampler.isInitialized() || outerQSampler.getMode() == SamplingMode.RANDOM_LOCAL_DISTINCT) {
                boolean cache = qCache.shouldCache(index);
                outerQSampler.init(model, qCache, cache);
            }

            // For each result (or till it reaches the max defined by the given qWrapper)
            while (outerQSampler.hasNext()) {
                QuerySolution qs1 = outerQSampler.getNextMatching();
                solutions.add(qs1);
                // apply the pattern
                constructRecursive(
                        model,
                        tempModel,
                        qSamplers,
                        nSampler,
                        cPattern,
                        solutions,
                        matcher,
                        index + 1,
                        qCache);
                // remove the last solution
                solutions.remove(qs1);
            }
            // reset the counter of the outer q-sampler
            outerQSampler.resetCounter();
        }

    }

    /**
     * 
     * @param model - The model which will be used for querying and updating
     * @param tempModel - The temp work model which will be updating and finally merged into the original model
     * @param qSamplers - An ordered array of query-samplers that will be used
     * @param queryAttributeVariableMap - A mapping of attributes to query variables to be used in resolving the actual
     * values of dynamic variables
     * @param queryAttributeResultMap - A mapping of attributes to actual results returned from queries that will be
     * used in the next queries (dynamic variables) and in the final creation command
     * @param nSampler - The number-sampler used to define how many times to create the pattern
     * @param cPattern - The actual construction pattern used
     * @param solutions - Array-List of QuerySolutions that will be used for mapping the actual model and the pattern
     * that defines how to create and update the model
     * @param matcher - The matcher matches the pattern to the different results returned by searching the pattern in
     * the model (mapping between existing nodes to nodes in the pattern itself
     * @param index - The recursion depth (matches the depth of the query being executed
     * @param qCache - A wrapper for all of the query-caching operations
     * @throws RdfNodeExistsInModelException - Thrown in case that we are trying to create a duplicate node
     * @throws IOException - Thrown in case of an IO failure (e.g. usage of files to load dictionary-samplers)
     */
    public void constructDynamicRecursive(
            Model model,
            Model tempModel,
            IQuerySampler[] qSamplers,
            HashMap<String, String> queryAttributeVariableMap,
            HashMap<String, String> queryAttributeResultMap,
            INaturalNumberSampler nSampler,
            ConstructionPattern cPattern,
            ArrayList<QuerySolution> solutions,
            IMatcher matcher,
            int index,
            QueryCache qCache) throws RdfNodeExistsInModelException, IOException {

        // If we reached the most inner loop - Stop and invoke the simple-construction
        if (index > qSamplers.length) {

            // Set the mapping of all query solutions to actual returned nodes
            matcher.setNodesMapping(solutions, cPattern.getOldNodes());
            // Get the random number of iterations to invoke the pattern
            int n = nSampler.getNextNatural();
            for (int i = 0; i < n; i++) {
                // Apply the construction pattern on the model
                cPattern.applyPatternOnModel(tempModel);
            }
        } else {

            IQuerySampler outerQSampler = qSamplers[index - 1];
            // if this is a dynamic query
            if (outerQSampler.isDynamic()) {
                // Update the query and the queryCache
                ArrayList<String> queryDynamicVars = outerQSampler.updateQueryVariables(model, queryAttributeVariableMap, queryAttributeResultMap);
                qCache.updateQueryParams(index - 1, outerQSampler, queryDynamicVars);
            }
            boolean cache = qCache.shouldCache(index - 1);
            outerQSampler.init(model, qCache, cache);

            // For each result (or till it reaches the max defined by the given qWrapper)
            while (outerQSampler.hasNext()) {
                QuerySolution qs1 = outerQSampler.getNextMatching();
                // Update the values to be given to the 2nd query
                ArrayList<String> addedAttribute = new ArrayList<String>();
                for (String att : queryAttributeVariableMap.keySet()) {
                    if (qs1.contains(att)) {
                        queryAttributeResultMap.put(att, qs1.get(att).toString());
                        addedAttribute.add(att);
                    }
                }

                solutions.add(qs1);

                // apply the pattern
                constructDynamicRecursive(
                        model,
                        tempModel,
                        qSamplers,
                        queryAttributeVariableMap,
                        queryAttributeResultMap,
                        nSampler,
                        cPattern,
                        solutions,
                        matcher,
                        index + 1,
                        qCache);

                // remove the last solution
                solutions.remove(qs1);
                // remove the last dynamic mapping
                for (String att : addedAttribute)
                    queryAttributeResultMap.remove(att);

            }
            outerQSampler.resetCounter();
        }
    }

    //###############################################################################################################
    // Non API Methods
    //###############################################################################################################

    /**
     * Private methods
     */

    /**
     * A method that validates that the given n-sampler was initialized
     * @param nSampler - The n-sampler to be verified
     */
    private void verifyInitialization(INaturalNumberSampler nSampler) {
        if (!nSampler.isInitialized())
            throw new IllegalArgumentException("The Number-Sampler wasn't initialized!");
    }

    /**
     * A method that returns a QueryModeParamsWrapper based on the analysis of the given q-sampler and depth of
     * execution (index). The returned instance contains the sampling-mode and lists of in and out parameters of the
     * query this q-sampler contains
     * @param qSampler - The analyzed q-sampler
     * @param index - The depth of the recursion
     * @return - A QueryModeParamsWrapper which holds all of a query related parameters (see class documentation)
     */
    private QueryModeParamsWrapper parseQuerySampler(IQuerySampler qSampler, int index) {

        ArrayList<String> qInParams;
        ArrayList<String> qOutParams;
        if (qSampler.isDynamic()) {
            qInParams = null;
            qOutParams = null;
        } else {
            String queryStr = qSampler.getQueryWrapper().getQuery();
            Query query = QueryFactory.create(queryStr);
            // Update the out parameters
            qOutParams = (ArrayList<String>) query.getResultVars();
            // Update the in parameters
            qInParams = new ArrayList<String>();
            Element elem = query.getQueryPattern();
            Set<Var> set = elem.varsMentioned();
            Var[] arr = set.toArray(new Var[set.size()]);
            for (Var var : arr)
                qInParams.add(var.toString());
        }
        SamplingMode mode = qSampler.getMode();
        if (index == 0 && mode == SamplingMode.RANDOM_LOCAL_DISTINCT)
            mode = SamplingMode.RANDOM_GLOBAL_DISTINCT;

        return new QueryModeParamsWrapper(mode, qOutParams, qInParams);
    }


}
