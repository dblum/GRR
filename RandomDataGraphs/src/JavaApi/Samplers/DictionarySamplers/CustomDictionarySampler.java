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

import JavaApi.Samplers.NumberSamplers.NaturalNumberSamplers.StdNaturalNumberSampler;
import SolutionConfig.Consts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Another Dictionary-Sampler which returns a label value (with repetitions) from a given set of
 * distribution which is defined in the loaded file (see init() file for specific format information).
 */
public class CustomDictionarySampler implements IDictionarySampler {

    /**
     * Inner class that represents a label-probability pair
     */

    class LabelProbPair {

        /**
         * Inner class members
         */
        private String _label;
        private int _prob;

        /**
         * Constructor
         * @param label - The label in the pair
         * @param prob - The probability of getting the given label
         */
        LabelProbPair(String label, int prob) {
            _label = label;
            _prob = prob;
        }

        /**
         * Label getter
         * @return - The label
         */
        String getLabel() {
            return _label;
        }

        /**
         * Probability getter
         * @return - The probability
         */
        int getProb() {
            return _prob;
        }
    }


    /**
     * Consts
     */
    private static final int FULL_PRCT = 100;
    private static final int LINE_GROUP_SIZE = 2;

    private static final String TEXT_FILE = "txt";

    /**
     * Class Members
     */
    private boolean _isInitialized;
    private StdNaturalNumberSampler _nSampler;
    private String[] _labelValues;

    /**
     * Constructor
     */
    public CustomDictionarySampler() {
        _isInitialized = false;
        _nSampler = new StdNaturalNumberSampler();
        _labelValues = new String[FULL_PRCT];
    }

    /**
     * IDictionarySampler - Interface Implementation
     */

    /**
     * Initializes this instance with the data found in the given file. Invoking this methods will re-initialize any
     * previous invocation of any of the init methods.
     * Each line in the file should be in the following format: <LabelName> | <Probability in %> for example
     * Car | 10%
     * Train | 25%
     * Bus | 50%
     * Bicycle | 15%
     * Notice that all values should sum up to 100% (Otherwise an exception will be thrown). And the probability
     * should be rounded into natural numbers.
     *
     * @param fileName - A file containing the data that this instance will use for returning random values.
     * This file could be of the following types: *.txt, *.xml - But in the format that is specified in the project's
     * documentation.
     */
    @Override
    public void init(String fileName) throws IOException {

        HashSet<String> labelSet = new HashSet<String>();

        // Decide what is the file type
        String fileTypeName = getFileType(fileName);

        // Parse the file  according to its type
        if (fileTypeName.equals(TEXT_FILE)) {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            int offset = 0;
            int lineIndex = 0;

            // Go over all of the lines in the file
            while ((line = br.readLine()) != null) {

                // If the line is a comment (starts with // ) or empty line we skip it
                if (emptyOrCommentLine(line))
                    continue;

                // Check if the line is in the correct format
                if (!lineInValidFormat(line))
                    throw new IllegalArgumentException("The file isn't in the correct format! Please read this class documentation");

                // Split the line and read the label and it's probability
                LabelProbPair labelProbPair = parseLine(line, lineIndex);
                String label = labelProbPair.getLabel();
                int prob = labelProbPair.getProb();

                // Check if the label was already present in the list
                if (labelSet.contains(label))
                    throw new IllegalArgumentException("File contains duplicated values! Found [: " + label + "] twice");
                labelSet.add(label);

                // We create an array in size 100 which contains pointer to the string values
                for (int i = 0; i < prob; i++)
                    _labelValues[i + offset] = label;

                // Update the array offset
                offset += prob;

                // Update the line index
                lineIndex++;
            }
            br.close();

            // Verify if the probabilities add up to 100%
            if (offset != FULL_PRCT)
                throw new IllegalArgumentException("The total percentage of values in the file doesn't add up to 100%");
        } else
            throw new IllegalArgumentException("File type isn't supported");

    }


    /**
     * Returns true iff the Dictionary Sampler was initialized by invoking one of the init() methods
     * @return - True iff the Dictionary Sampler was initialized by invoking one of the init() methods
     */
    @Override
    public boolean isInitialized() {
        return _isInitialized;
    }

    /**
     * Returns a random label value from the dictionary that was supplied in the init() method
     * @return - A random label value from the dictionary that was supplied in the init() method
     * @throws IllegalStateException - If the instance wasn't initialized by an invocation of init()
     */
    public String getRandomLabel() throws IllegalStateException {
        _nSampler = new StdNaturalNumberSampler();
        _nSampler.setMaxValue(FULL_PRCT);
        int randVal = _nSampler.getNextNatural();
        return _labelValues[randVal];
    }


    /**
     * Private Methods
     */

    /**
     * Returns a string representation of the file type according to a given full file name
     * @param fileName - The parsed file name from which we'll return the fileType
     * @return A string representation of the file type according to a given full file name
     */
    private String getFileType(String fileName) {
        String[] fileParts = fileName.split(".");
        return fileParts[fileParts.length-1].toLowerCase();
    }

    /**
     * Returns true iff the given file is either empty or represents a comment (starts with // )
     * @param line - The analyzed line
     * @return True iff the given file is either empty or represents a comment (starts with // )
     */
    private boolean emptyOrCommentLine(String line) {
        Pattern emptyLine = Pattern.compile("^[\\s]*$");
        Matcher emptyMatcher = emptyLine.matcher(line);
        Pattern commentLine = Pattern.compile("^//[\\w\\s]+");
        Matcher commentMatcher = commentLine.matcher(line);
        if (emptyMatcher.matches() || commentMatcher.matches())
            return true;
        return false;
    }

    /**
     * Returns true iff the given line is in the correct format (See init() documentation)
     * @param line - The analyzed line
     * @return  True iff the given line is in the correct format
     */
    private boolean lineInValidFormat(String line) {
        Pattern dataLine = Pattern.compile("[\\w|\\s]+;[\\s]*[\\d]{1,3}%[\\s]*");
        Matcher dataMatcher = dataLine.matcher(line);
        if (dataMatcher.matches())
            return true;
        return false;
    }

    /**
     * Returns a LabelProbPair instance according to the given line
     * @param line - The analyzed line
     * @param lineIndex - The line index for giving better error description in case of a parsing error
     * @return a LabelProbPair instance according to the given line 
     */
    private LabelProbPair parseLine(String line, int lineIndex) {
        String label;
        int prob;
        try {
            String[] lineParts = line.split(";");
            if (lineParts.length != LINE_GROUP_SIZE)
                throw new IllegalArgumentException("The file isn't in the correct format! Please read this class documentation");
            label = lineParts[0];
            label.trim();
            String probStr = lineParts[1].trim();
            probStr = probStr.substring(0, probStr.length() - 1); // Remove the % char
            prob = Integer.parseInt(probStr);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse the file in line: " + lineIndex);
        }

        return new LabelProbPair(label, prob);
    }
}
