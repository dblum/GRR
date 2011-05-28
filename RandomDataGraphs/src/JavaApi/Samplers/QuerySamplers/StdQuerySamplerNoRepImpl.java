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

import com.hp.hpl.jena.query.QuerySolution;

/**
 * A specific implementation of the StdQuerySampler abstract class which will return results
 * without repetitions
 */
public class StdQuerySamplerNoRepImpl extends StdQuerySampler {

    /**
     * The constructor must be protected - Instance should be created via the factory
     * @param qWrapper - The QueryWrapper that will be used by this sampler
     */
    protected StdQuerySamplerNoRepImpl(QueryWrapper qWrapper) {
        _qWrapper = qWrapper;
        _baseQuery = qWrapper.getQuery();
    }

    /**
     * Returns the next matching (QuerySolution) according the mode that was set for this instance.
     * @return - The next matching (QuerySolution) according the mode that was set for this instance.
     * @throws IllegalStateException - In case that this instance wasn't initialized or if there are no query results
     * left to return.
     */
    public QuerySolution getNextMatching() throws IllegalStateException {

        if (!_isInitialized)
            throw new IllegalStateException("The object wasn't initialized!");
        if (!hasNext())
            throw new IllegalStateException("There are no results left");

        QuerySolution solution;
        int entry;

        // Get the next random entry, remove it from the ArrayList of results and update the nSampler's range
        entry = _nSampler.getNextNatural();

        // We move this result to the end of the list
        int endOfRelevantList = _nSampler.getMaxValue();
        solution = _results.remove(entry);
        _results.add(endOfRelevantList, solution);

        _counter++;

        if (hasNext())
            _nSampler.setMaxValue(endOfRelevantList - 1);

        return solution;
    }

}
