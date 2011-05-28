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

package JavaApi.Samplers.NumberSamplers.NaturalNumberSamplers;

/**
 * An extension to the most basic Natural Number Sampler which adds the ability to set both min and max
 * values that might be returned by this sampler
 */
public interface IStdNaturalNumberSampler extends INaturalNumberSampler {

    /**
     * Sets the max value that the sampler will return, if not invoked the sampler
     * will use max-int as default.
     * This is a non-mandatory method for using the natural number generator.
     * @param maxVal - The max number that this sampler will return (inclusive)
     * @throws IllegalArgumentException - If the given number is negative (n<0) or if it's smaller than the min-val.
     */
    public void setMaxValue(int maxVal) throws IllegalArgumentException;

    /**
     * Sets the min value that the sampler will return, if not invoked the sampler
     * will use zero as default.
     * This is a non-mandatory method for using the natural number generator.
     * @param minVal - The min number that this sampler will return (inclusive)
     * @throws IllegalArgumentException - If the given number is negative (n<0) or if it's bigger than the max-val.
     */
    public void setMinValue(int minVal) throws IllegalArgumentException;

    /**
     * Returns the max value that the given instance could return
     * @return - The max value that the given instance could return
     */
    public int getMaxValue();


}
