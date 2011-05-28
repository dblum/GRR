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

import JavaApi.Samplers.SamplingMode;

import java.util.ArrayList;

/**
 * Class that is a wrapper for all of a query related parameters (see members)
 */
public class QueryModeParamsWrapper {

    /**
     * Class members
     */

    // The sampling mode used to return the query results
    private SamplingMode _qMode;
    // The out parameters of the query
    private ArrayList<String> _qOutParams;
    // the in parameters of the query
    private ArrayList<String> _qInParams;

    /**
     * Constructor that sets all of the class members
     * @param qMode - The sampling mode that will be used
     * @param qOutParams - The out parameters of the query
     * @param qInParams - The in parameters of the query
     */
    public QueryModeParamsWrapper(SamplingMode qMode, ArrayList<String> qOutParams, ArrayList<String> qInParams) {
        _qMode = qMode;
        _qOutParams = qOutParams;
        _qInParams = qInParams;
    }

    /**
     * Public Methods
     */

    /**
     *Returns the sampling mode to be used
     * @return - The sampling mode to be used
     */
    public SamplingMode getQMode() {
        return _qMode;
    }

    /**
     * Returns the our parameters of the query
     * @return - The our parameters of the query
     */
    public ArrayList<String> getQOutParams() {
        return _qOutParams;
    }

    /**
     * Returns the in parameters of the query
     * @return - The in parameters of the query
     */   
    public ArrayList<String> getQInParams() {
        return _qInParams;
    }

    /**
     * Sets the array of in parameters (usually used for updating dynamic parameters)
     * @param qInParams - The new in parameters
     */
    public void setQInParams(ArrayList<String> qInParams) {
        _qInParams = qInParams;
    }

    /**
     * Sets the array of out parameters (usually used for updating dynamic parameters)
     * @param qOutParams - The new out parameters
     */
    public void setQOutParams(ArrayList<String> qOutParams) {
        _qOutParams = qOutParams;
    }
}
