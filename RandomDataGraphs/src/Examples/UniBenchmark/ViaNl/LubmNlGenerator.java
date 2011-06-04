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
package Examples.UniBenchmark.ViaNl;

import Examples.Utils.ExpLogger;
import NaturalLanguageApi.NLParser;
import RdfApi.QueryOptimizationMode;
import RdfApi.RdfBasedRandomGraphAPI;
import SolutionConfig.Consts;
import SolutionConfig.SolutionConfigFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 */
public class LubmNlGenerator {

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";



    private static String INPUT_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples" + Consts.pathSep + "UniBenchmark" + Consts.pathSep + "ViaNl" + Consts.pathSep + "InputFiles" + Consts.pathSep + "";
    private static String OUTPUT_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples" + Consts.pathSep + "UniBenchmark" + Consts.pathSep + "ViaNl" + Consts.pathSep + "OutputFiles" + Consts.pathSep + "";
    private static String S_FUNCTION_PATH = INPUT_PATH + "SamplerFunctionInput.txt";
    private static String TYPE_PROP_PATH = INPUT_PATH + "TypePropertyMappingsInput.txt";
    //private static String NL_GEN_INPUT_PATH = INPUT_PATH + "NLGeneratorUsage.txt";
    //private static String NL_GEN_RDF_OUTPUT_PATH = OUTPUT_PATH + "RdfGenOutput.rdf";
    //private static String NL_GEN_FINAL_OUTPUT_PATH = OUTPUT_PATH + "GraphOutput.rdf";

    public static void main(String args[]) throws Exception {

        /*QueryOptimizationMode mode = null;
        int modeType = Integer.parseInt(args[args.length-2]);
        switch (modeType)    {
            case 0:
                mode =QueryOptimizationMode.SMART_CACHE;
                break;
           case 1:
                mode =QueryOptimizationMode.ALWAYS_CACHE;
                break;
           case 2:
                mode =QueryOptimizationMode.NO_CACHE;
                break;
        }

        int index = Integer.parseInt(args[args.length-1]);
        */

        int index = 0;
        QueryOptimizationMode mode =  QueryOptimizationMode.SMART_CACHE;


        System.out.println("######################### STARTING ITERATION: " + index + " !  ##################################");
        System.out.println("------------------------- CACHING-MODE             : " + mode + " !    ----------------------------------");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

        System.out.println("NL File parsing start at: " + sdf.format(cal.getTime()));
        NLParser nlParser = NLParser.create(INPUT_PATH);
        String nlInputFile = INPUT_PATH + "NLGeneratorUsage" + index + ".txt";
        nlParser.parseNLGeneratorFiles(S_FUNCTION_PATH, TYPE_PROP_PATH, nlInputFile);
        System.out.println("NL File parsing ended at: " + sdf.format(cal.getTime()));

        System.out.println("Generating RDF generator file start at: " + sdf.format(cal.getTime()));
        String nlRdfOutputFile = OUTPUT_PATH + "RdfGenOutput" + index + ".rdf";
        nlParser.generateRdfGeneratorFile(nlRdfOutputFile);
        System.out.println("Generating RDF generator file ended at: " + sdf.format(cal.getTime()));

        //   Generate the random graph at the given destination
        System.out.println("Generating final RDF file start at: " + sdf.format(cal.getTime()));


        //  ---------------------- FOR LOGGING ONLY -----------------------------------------------------
        String[] types = new String[]{"ub:University", "ub:Department", "ub:Faculty", "ub:Student", "ub:Course", "ub:Publication"};
        String logFileName = INPUT_PATH;
        ExpLogger expLogger = new ExpLogger(types, logFileName, mode);
        expLogger.logModeIteration(mode, index);
        // --------------------------------------------------------------------------------------------------------

        String graphOutputFile = OUTPUT_PATH + "GraphOutput" + index + ".rdf";
        RdfBasedRandomGraphAPI rdfGrrApi = new RdfBasedRandomGraphAPI();
        rdfGrrApi.generateGraph(nlRdfOutputFile, graphOutputFile, mode, expLogger, true);
        System.out.println("Generating final RDF ended at: " + sdf.format(cal.getTime()));

        //  ---------------------- FOR LOGGING ONLY -----------------------------------------------------
        expLogger.createOrAppendToLog();
        // --------------------------------------------------------------------------------------------------------


    }

}
