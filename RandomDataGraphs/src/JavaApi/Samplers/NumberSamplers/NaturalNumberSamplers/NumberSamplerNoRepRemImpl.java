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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A standard implementation of the INaturalNumberSampler interface which uses the Java
 * standard implementation of random numbers but holds an internal state which enables it to
 * return numbers without repetitions
 */
public class NumberSamplerNoRepRemImpl extends NumberSamplerNoRep {

    /**
     * Consts
     */
    private static final int FIRST_CELL = 0;

    /**
     * Class Members
     */
    private ArrayList<Integer> _remainingNumbers;

    /**
     * Constructor - Protected since the factory should be used
     */
    protected NumberSamplerNoRepRemImpl() {
        _random = new Random();
        _maxVal = Integer.MAX_VALUE;
        _minVal = 0;
        _isInitialized = false;
        _remainingNumbers = new ArrayList<Integer>();
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
        return _remainingNumbers.remove(FIRST_CELL);
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
         _remainingNumbers = new ArrayList<Integer>();
        for (int i = _minVal; i <= _maxVal; i++)
            _remainingNumbers.add(i);
         Collections.shuffle(_remainingNumbers, _random);
        _isInitialized = true;
    }

    /**
     * Return true iff there are more numbers to be returned from the given range
     * @return a number from the given range (without repetitions).
     */
    public boolean hasNextNumber()
    {
        return (_remainingNumbers.size() != 0);            
    }

}