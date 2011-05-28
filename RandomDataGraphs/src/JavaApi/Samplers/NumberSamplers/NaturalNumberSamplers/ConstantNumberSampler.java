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
 * A simple constant Natural Number Sampler which always returns the value that was set,
 * Or the default value of 1.
 */
public class ConstantNumberSampler implements INaturalNumberSampler {

    /**
     * Class Members
     */

    int _constNumber = 1;

     /**
     * The method returns always the same constant number
     *
     * @return - A constant natural number (default is 1)
     */
    @Override
    public int getNextNatural() {
        return _constNumber;
    }

   /**
     * Enables the user to verify if the implementing instance is initialized
      * @return True iff the instance is initialized
     */
    @Override
    public boolean isInitialized() {
        return true;
    }

    /**
     *  Additional Public Methods
     */

    /**
     * Sets the number that will be returned by this sampler. Will override any previous value that was set.
     * @param constNum - The new value that will always be returned by this instance.
     */
    public void setNaturalNumber(int constNum)
    {
        if (constNum<1)
            throw new IllegalArgumentException("The constant number must be a positive natural number!");
        _constNumber = constNum;
    }
}
