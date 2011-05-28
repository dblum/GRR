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

package JavaApi.Samplers.DictionarySamplers;


import JavaApi.Samplers.NumberSamplers.NaturalNumberSamplers.*;
import JavaApi.Samplers.SamplingMode;

import java.io.IOException;

/**
 * A counter dictionary sampler - uses some base label and adds an incremental counter at the end
 * in order to create labels
 */
public class CounterDictionarySampler implements IDictionarySampler {

    /**
     * Class Members
     */
    private boolean _isInitialized;
    private CyclicCounterNumberSampler _noRepNSampler;
    private StdNaturalNumberSampler _nSampler;
    private SamplingMode _samplingMode;
    private String _label;

    /**
     * Constructor that initializes the instance with some default values. Most important
     * is that the sampling mode is set to SamplingMode.RANDOM_GLOBAL_DISTINCT
     */
    public CounterDictionarySampler() {
        _isInitialized = false;
        _samplingMode = SamplingMode.RANDOM_GLOBAL_DISTINCT;
        _noRepNSampler = new CyclicCounterNumberSampler();
        _nSampler = new StdNaturalNumberSampler();
    }

    /**
     * IDictionarySampler - Interface Implementation
     */

    /**
     * Initiates the instance by setting the label value to be the given value
     * (Note that this is an ugly workaround that uses the string value (which
     * should be the name of the file that holds the labels. This will be replaced
     * in a later stage)
     * @param label - The label value that this sampler will return
     * @throws IOException - In practice can't happen (again bad interface implementation
     * design)
     */
    @Override
    public void init(String label) throws IOException {

        _label = label;
        _isInitialized = true;
    }

    /**
     * Returns true iff the Dictionary Sampler was initialized
     * @return - True iff the Dictionary Sampler was initialized
     */
    @Override
    public boolean isInitialized() {
        return _isInitialized;
    }

    /**
     * Returns a label which is constructed in the following way: it return the string label
     * that was set upon init and adds an integer counter value at the end of the string label
     * According to the sample mode the sampler can become uninitialized when it reaches max int,
     * (while using RANDOM_GLOBAL_DISTINCT) or return to 1 in a cyclic manner (when using RANDOM_REPEATABLE)
     * @return - A string label ending with an integer value
     * @throws IllegalStateException - If the instance wasn't initialized
     */
    @Override
    public String getRandomLabel() throws IllegalStateException {

        if (!_isInitialized)
            throw new IllegalStateException("Instance wasn't initialized by calling init()");

        if (!hasNext())
        {
            _isInitialized = false;
            throw new IllegalStateException("d-sampler doesn't have any values left");
        }

        int index;
        String label;

        switch (_samplingMode) {
            case RANDOM_GLOBAL_DISTINCT:
                index = _noRepNSampler.getNextNatural();
                label = _label + index;
                if (!hasNext()) // No more values left in the list, so the instance becomes uninitialized
                    _isInitialized = false;
                break;
            case RANDOM_REPEATABLE:
                index = _nSampler.getNextNatural();
                label = _label + index;
                break;
            default:
                throw new IllegalArgumentException("The current sampling mode isn't supported: " + _samplingMode);
        }

        return label;
    }

    /**
     * Additional Public Methods
     */

    /**
     * Sets the sampling mode that is being used in this instance.
     * (With/Without repetition)
     *
     * @param mode
     */
    public void setSamplingMode(SamplingMode mode) {
        _samplingMode = mode;
    }

    /**
     * Returns true iff the sampler can return the next value
     * @return - True iff the sampler can return the next value, otherwise False
     */
    public boolean hasNext() {
         return true;
    }

}