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

/**
 * A factory for creating IQuerySampler instances according to the smapling-mode set in the given QueryWrapper
 */
public class StdQuerySamplerFactory {

    /**
     * Query sampler factory method which returns the relevant instance according to the
     * given mode
     * @param qWrapper - The QueryWrapper which holds the sampling-mode upon which we'll decide which instance type to
     * return, and which will be used by this sampler
     * @return a IQuerySampler instance which uses the given sampling mode
     */
    public static IQuerySampler getStdQuerySampler(QueryWrapper qWrapper) {
        switch (qWrapper.getMode()) {
            case RANDOM_REPEATABLE:
                return new StdQuerySamplerWithRepImpl(qWrapper);
            case RANDOM_GLOBAL_DISTINCT:
            case RANDOM_LOCAL_DISTINCT:
                return new StdQuerySamplerNoRepImpl(qWrapper);
            default:
                throw new IllegalArgumentException("The given SamplingMode isn't supported: " + qWrapper.getMode());
        }
    }

}
