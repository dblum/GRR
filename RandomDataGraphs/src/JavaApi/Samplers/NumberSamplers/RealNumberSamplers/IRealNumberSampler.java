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

package JavaApi.Samplers.NumberSamplers.RealNumberSamplers;

/**
 * The most basic interface for a Real Number Sampler
 */
public interface IRealNumberSampler {

    /**
     * Returns a real number according to the specific class which implements this interface
     * @return a real (double) number
     */
    public double getNext();

    /**
     * Enables the user to verify if the implementing instance is initialized
      * @return True iff the instance is initialized
     */
    public boolean isInitialized();

    /**
     * Returns the minimum value that this sampler could return
     * @return The minimum value that this sampler could return
     */
    public double getMinVal();

     /**
     * Returns the maximum value that this sampler could return
     * @return The maximum value that this sampler could return
     */
    public double getMaxVal();

}