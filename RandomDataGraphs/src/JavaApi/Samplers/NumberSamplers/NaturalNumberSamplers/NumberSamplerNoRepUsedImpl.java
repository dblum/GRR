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

import java.util.HashSet;
import java.util.Random;

/**
 * A standard implementation of the INaturalNumberSampler interface which uses the Java
 * standard implementation of random numbers but holds an internal state which enables it to
 * return numbers without repetitions
 */
public class NumberSamplerNoRepUsedImpl extends NumberSamplerNoRep {

    /**
     * Class Members
     */
    private HashSet<Integer> _usedNumbers;
    private int _totalUsed;


    /**
     * Constructor - Protected since the factory should be used
     */
    protected NumberSamplerNoRepUsedImpl() {
        _random = new Random();
        _maxVal = Integer.MAX_VALUE;
        _minVal = 0;
        _isInitialized = false;
        _usedNumbers = new HashSet<Integer>();
        _totalUsed = 0;
    }

    /**
     * INaturalNumberSampler - Interface Implementation
     */

    /**
     * The method returns a natural number between 0 to max number set by the user.
     * If no setMaxValue wasn't invoked, the max value will be max-int.
     * @return - A natural number between 0 to the given number n.
     */
    @Override
    public int getNextNatural() {
        if (!hasNextNumber())
            throw new IllegalStateException("No more numbers remaining!");

        int randVal;

        if (_usedNumbers.isEmpty()) {
            randVal = _random.nextInt(_rangeSize) + _minVal;
        } else {
            Integer randValInteger;
            do {
                randVal = _random.nextInt(_rangeSize) + _minVal;
                randValInteger = new Integer(randVal);
            }
            while (_usedNumbers.contains(randValInteger));
        }

         _usedNumbers.add(randVal);
        _totalUsed += 1;
        return randVal;
    }


    /**
     * Additional Public Methods
     */

    /**
     * Initializes the instance according to the given values:
     * - max value:                    Default value is Integer.MAX_VALUE
     * - min value:                    Default value is 0
     */
    public void init() {
        _usedNumbers = new HashSet<Integer>();
        _isInitialized = true;
    }

    /**
     * Return true iff there are more numbers to be returned from the given range
     * @return a number from the given range (without repetitions).
     */
    public boolean hasNextNumber() {
        return (_totalUsed < _rangeSize);
    }
}