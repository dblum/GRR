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

import JavaApi.Samplers.NumberSamplers.RealNumberSamplers.IRealNumberSampler;
import JavaApi.Samplers.SamplingMode;

/**
 * A wrapper class for query management (execution and sampling results)
 */
public class QueryWrapper {

    /**
     * Private Class Members
     */
    private String _query;
    private SamplingMode _mode;
    private IRealNumberSampler _realNumberSampler;
    private boolean _isDynamic;

    
     /**
     * Simple constructor that initializes the query and sampling mode
     * @param stringQuery - The actual SPARQL query
     * @param mode - The sampling mode used to return results
     */
    public QueryWrapper(String stringQuery, SamplingMode mode) {
         this(stringQuery, mode, null, false);
    }

     /**
     * Constructor that sets the IRealNumberSampler that will be used to return a portion of the results in addition to
     * setting the query and sampling-mode
     * @param stringQuery - The actual SPARQL query
     * @param mode - The sampling mode used to return results
     * @param realNumSampler - The IRealNumberSampler instance that will be used for the sampling
     */
    public QueryWrapper(String stringQuery, SamplingMode mode, IRealNumberSampler realNumSampler) {
        this(stringQuery, mode, realNumSampler, false);
    }

    /**
     * Constructor that sets all parameters, but specifically enables the user to define this query as a dynamic query
     * Which means that it uses results of previous queries in it.
     * @param stringQuery - The actual SPARQL query
     * @param mode - The sampling mode used to return results
     * @param realNumSampler - The IRealNumberSampler instance that will be used for the sampling
     * @param isDynamic - Sets/Marks this query as a dynamic query (query which uses results of previous queries in it) 
     */
    public QueryWrapper(String stringQuery, SamplingMode mode, IRealNumberSampler realNumSampler, boolean isDynamic) {
        _query = stringQuery;
        _realNumberSampler = realNumSampler;
        _mode = mode;
        _isDynamic = isDynamic;
    }

    /**
     * Returns the string query
     * @return - The string query
     */
    public String getQuery() {
        return _query;
    }

    /**
     * Returns the sampling-mode used to return the query results
     * @return - The sampling-mode used to return the query results
     */
    public SamplingMode getMode() {
        return _mode;
    }


    /**
     * Returns the IRealNumberSampler instance used to return query results
     * @return - The IRealNumberSampler instance used to return query results
     */
    public IRealNumberSampler getRealNumberSampler() {
        return _realNumberSampler;
    }

    /**
     * Returns true iff the query is a dynamic query, otherwise false
     * @return - True iff the query is a dynamic query, otherwise false
     */
    public boolean isDynamic() {
        return _isDynamic;
    }
}
