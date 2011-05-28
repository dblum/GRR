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

import java.util.Random;

/**
 * A standard implementation of the IStdRealNumberSampler interface which uses the Java
 * standard implementation of random numbers generation (acting as
 * a simple wrapper which exposes the needed interface)
 */
public class RangeRealNumberSampler implements IStdRealNumberSampler {

    /**
     * Class Members
     */
    private double _maxVal;
    private double _minVal;
    private Random _random;
    private boolean _isInitialized;

    /**
     * Constructor
     */
    public RangeRealNumberSampler() {
        _random = new Random();
        _maxVal = Double.MAX_VALUE;
        _minVal = 0;
        _isInitialized = true;
    }

    /**
     * INaturalNumberSampler - Interface Implementation
     */

    /**
     * Sets the max value that the sampler will return, if not invoked the sampler
     * will use max-int as default.
     * This is a non-mandatory method for using the natural number generator.
     * @param maxVal - The max number that this sampler will return (inclusive)
     * @throws IllegalArgumentException - If the given number is negative (n<0) or if it's smaller than the min-val.
     */
    @Override
    public void setMaxValue(double maxVal) throws IllegalArgumentException {
        if (maxVal < 0)
            throw new IllegalArgumentException("Negative value for the the sampler: " + maxVal + " is not valid");
        if (maxVal < _minVal)
            throw new IllegalArgumentException("The given max-value: " + maxVal + " is smaller than the min-value: " + _minVal);

        _maxVal = maxVal;
    }

    /**
     * Sets the min value that the sampler will return, if not invoked the sampler
     * will use zero as default.
     * This is a non-mandatory method for using the natural number generator.
     * @param minVal - The min number that this sampler will return (inclusive)
     * @throws IllegalArgumentException - If the given number is negative (n<0) or if it's bigger than the max-val.
     */
    @Override
    public void setMinValue(double minVal) throws IllegalArgumentException {
        if (minVal < 0)
            throw new IllegalArgumentException("Negative value for the the sampler: " + minVal + " is not valid");
        if (minVal > _maxVal)
            throw new IllegalArgumentException("The given min-value: " + minVal + " is bigger than the max-value: " + _maxVal);

        _minVal = minVal;
    }

    /**
     * Returns a real number according to the specific class which implements this interface
     * @return a real (double) number
     */
    @Override
    public double getNext() {
        // Standard pattern for getting a random value within a given range of min-max
        return _minVal + (_random.nextDouble() * (_maxVal - _minVal));
    }

    /**
     * Enables the user to verify if the implementing instance is initialized
      * @return True iff the instance is initialized
     */
    @Override
    public boolean isInitialized() {
        return _isInitialized;
    }

     /**
     * Returns the minimum value that this sampler could return
     * @return The minimum value that this sampler could return
     */
    @Override
    public double getMinVal() {
        return _minVal;
    }

    /**
     * Returns the maximum value that this sampler could return
     * @return The maximum value that this sampler could return
     */
    @Override
    public double getMaxVal() {
        return _maxVal;
    }

    /**
     * Additional Public Methods
     */

    /**
     * Enables the user to set the seed for the random generator.
     * This is a non-mandatory method for using the natural number generator.
     *
     * @param seed - The seed that will be used for generating numbers.
     */
    public void setSeed(long seed) {
        _random.setSeed(seed);
    }
}