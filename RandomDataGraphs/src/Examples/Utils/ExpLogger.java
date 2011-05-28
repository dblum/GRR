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

package Examples.Utils;

import JavaApi.RandomDataGraph.QueryOptimization.QueryCache;
import RdfApi.QueryOptimizationMode;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 */
public class ExpLogger {

    private String[] _types;
    private String _outputPath;
    private QueryOptimizationMode _optMode;

    private long _startTime;
    private long _endTime;
    private int _totalCacheHits;
    private int _totalQueryExecutions;
    private long _totalStatements;
    private HashMap<String, Integer> _typeCountMap;

    private static final String LOG_FILE_NAME = "log.txt";
    private static final String CACHE_FILE_NAME = "cacheSizeLog.txt";
    private static final String CACHE_TEMP_FILE_NAME = "cache.txt";

    public ExpLogger(String[] types, String outputPath, QueryOptimizationMode optMode) {
        _types = types;
        _outputPath = outputPath;
        _optMode = optMode;
    }

    public void startLogging() {
        Calendar cal = Calendar.getInstance();
        _startTime = cal.getTimeInMillis();
    }


    public void endLogging() {
        Calendar cal = Calendar.getInstance();
        _endTime = cal.getTimeInMillis();
    }

    public void collectData(Model model, HashMap<String, String> nsMap)
    {
        _totalStatements = ExpUtils.getNumberOfTriples(model);
        _typeCountMap = ExpUtils.countResources(model, nsMap, _types);
    }

    public void addToCacheHitCount(int hitCount)
    {
        _totalCacheHits += hitCount;
    }

    public void addToQueryExecutionCount(int queryExecCount)
    {
        _totalQueryExecutions += queryExecCount;
    }

    public void createOrAppendToLog() throws IOException {
        String logLine = "" + _optMode.toString() + "|" + _startTime + "|" + _endTime + "|" + _totalCacheHits + "|" + _totalQueryExecutions + "|" + _totalStatements;
        for (String type : _typeCountMap.keySet())
            logLine += "|" + type + "|" + _typeCountMap.get(type);
        FileWriter out = new FileWriter(_outputPath + LOG_FILE_NAME, true);
        BufferedWriter writer = new BufferedWriter(out);
        writer.write(logLine + "\n");
        writer.close();
        out.close();
    }

    public void logCacheSize(QueryCache qCache) throws IOException {
        String fileName = _outputPath + CACHE_FILE_NAME;
        String tmpFileName = _outputPath + CACHE_TEMP_FILE_NAME;
        // Serialize the cache into a file
        HashMap<Query, ArrayList<QuerySolution>> map = qCache.getQueryResultsCache();
        FileWriter out = new FileWriter(tmpFileName);
        BufferedWriter writer = new BufferedWriter(out);
        for (Query query : map.keySet())
        {
            writer.write(query.toString());
            ArrayList<QuerySolution> solutions = map.get(query);
            for (QuerySolution sol : solutions)
            {
                writer.write(sol.toString());
            }
           writer.write("\n");
        }


        
        writer.close();
        out.close();

        File file = new File(tmpFileName);
        long size = file.length();

        file.delete();

        FileWriter outlog = new FileWriter(fileName, true);
        BufferedWriter writerlog = new BufferedWriter(outlog);
        writerlog.write(size + "|");

        writerlog.close();
        outlog.close();


    }

    public void logModeIteration(QueryOptimizationMode mode, int iteration) throws IOException {
        String fileName = _outputPath + CACHE_FILE_NAME;

        FileWriter outlog = new FileWriter(fileName, true);
        BufferedWriter writerlog = new BufferedWriter(outlog);
        writerlog.write("\n" + mode + "|" + iteration + "|");

        writerlog.close();
        outlog.close();


    }

}
