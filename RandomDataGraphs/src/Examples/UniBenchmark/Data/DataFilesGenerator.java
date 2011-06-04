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

package Examples.UniBenchmark.Data;

import Examples.UniBenchmark.ViaJava.LubmJavaGenerator;
import SolutionConfig.Consts;
import SolutionConfig.SolutionConfigFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 */
public class DataFilesGenerator {

    private static final int MAX_UNI_LABELS =  LubmJavaGenerator.UNI_MAX;
    private static final int MAX_DEP_LABELS = MAX_UNI_LABELS * LubmJavaGenerator.DEP_MAX;
    private static final int MAX_FULL_PROF_LABELS = MAX_DEP_LABELS * LubmJavaGenerator.FULL_PROF_MAX;
    private static final int MAX_ASSO_PROF_LABELS = MAX_DEP_LABELS * LubmJavaGenerator.ASSO_PROF_MAX;
    private static final int MAX_ASSI_PROF_LABELS = MAX_DEP_LABELS * LubmJavaGenerator.ASSI_PROF_MAX;
    private static final int MAX_LECTURER_LABELS = MAX_DEP_LABELS * LubmJavaGenerator.LECTURER_MAX;
    private static final int MAX_FACULTY_MEMBERS = MAX_FULL_PROF_LABELS + MAX_ASSO_PROF_LABELS + MAX_ASSI_PROF_LABELS + MAX_LECTURER_LABELS;
    private static final int MAX_COURSE_LABELS = LubmJavaGenerator.COURSE_MAX * MAX_FACULTY_MEMBERS;
    private static final int MAX_GRAD_COURSE_LABELS = LubmJavaGenerator.GRAD_COURSE_MAX * MAX_FACULTY_MEMBERS;
    private static final int MAX_UNDER_STUDENT_LABELS = LubmJavaGenerator.UND_STUD_MAX * MAX_FACULTY_MEMBERS;
    private static final int MAX_GRAD_STUDENT_LABELS = LubmJavaGenerator.GRAD_STUD_MAX * MAX_FACULTY_MEMBERS;
    private static final int MAX_RES_GROUP_LABELS = MAX_DEP_LABELS * LubmJavaGenerator.RES_GROUP_MAX;
    private static final int MAX_FULL_PROF_PUB_LABELS =MAX_FULL_PROF_LABELS * LubmJavaGenerator.FULL_PROF_PUB_MAX;
    private static final int MAX_ASSO_PROF_PUB_LABELS = MAX_ASSO_PROF_LABELS * LubmJavaGenerator.ASSO_PROF_PUB_MAX;
    private static final int MAX_ASSI_PROF_PUB_LABELS = MAX_ASSI_PROF_LABELS * LubmJavaGenerator.ASSI_PROF_PUB_MAX;
    private static final int MAX_LECTURER_PUB_LABELS = MAX_LECTURER_LABELS * LubmJavaGenerator.LECTURER_PUB_MAX;

    private static final int MAX_PUB_LABELS =MAX_FULL_PROF_PUB_LABELS + MAX_ASSI_PROF_PUB_LABELS + MAX_ASSO_PROF_PUB_LABELS + MAX_LECTURER_PUB_LABELS;

// Label data-file paths
    public static final String UNI_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "UniversityNames.txt";
    public static final String DEP_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "DepartmentNames.txt";
    public static final String FULL_PROF_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "FullProfNames.txt";
    public static final String ASSO_PROF_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "AssoProfNames.txt";
    public static final String ASSI_PROF_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "AssiProfNames.txt";
    public static final String LECTURER_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "LecturerNames.txt";
    public static final String COURSE_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "CourseNames.txt";
    public static final String GRAD_COURSE_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "GraduateCourseNames.txt";
    public static final String RESEARCH_GROUP_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "ResearchGroup.txt";
    public static final String UNDER_STUDENT_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "UnderStudentNames.txt";
    public static final String GRAD_STUDENT_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "GradStudentNames.txt";
    public static final String FULL_PROF_PUB_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "FullProfPubNames.txt";
    public static final String ASSO_PROF_PUB_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "AssoProfPubNames.txt";
    public static final String ASSI_PROF_PUB_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "AssiProfPubNames.txt";
    public static final String LECTURER_PUB_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "LecturerPubNames.txt";
    public static final String PUB_DATA_PATH = SolutionConfigFile.BASE_PATH + "RandomDataGraphs" + Consts.pathSep + "src" + Consts.pathSep + "Examples.UniBenchmark" + Consts.pathSep + "Data" + Consts.pathSep + "PubNames.txt";
    
    
    public static void main(String[] args)
    {
        try {
            createTextFile(UNI_DATA_PATH, LubmJavaGenerator.RDF_TYPE_UNI, MAX_UNI_LABELS);
            createTextFile(DEP_DATA_PATH, LubmJavaGenerator.RDF_TYPE_DEP, MAX_DEP_LABELS);
            createTextFile(FULL_PROF_DATA_PATH, LubmJavaGenerator.RDF_TYPE_FULL_PROF, MAX_FULL_PROF_LABELS);
            createTextFile(ASSO_PROF_DATA_PATH, LubmJavaGenerator.RDF_TYPE_ASSO_PROF, MAX_ASSO_PROF_LABELS);
            createTextFile(ASSI_PROF_DATA_PATH, LubmJavaGenerator.RDF_TYPE_ASSI_PROF, MAX_ASSI_PROF_LABELS);
            createTextFile(LECTURER_DATA_PATH, LubmJavaGenerator.RDF_TYPE_LECTURER, MAX_LECTURER_LABELS);
            createTextFile(COURSE_DATA_PATH, LubmJavaGenerator.RDF_TYPE_COURSE, MAX_COURSE_LABELS);
            createTextFile(GRAD_COURSE_DATA_PATH, LubmJavaGenerator.RDF_TYPE_GRAD_COURSE, MAX_GRAD_COURSE_LABELS);
            createTextFile(RESEARCH_GROUP_DATA_PATH, LubmJavaGenerator.RDF_TYPE_RES_GROUP, MAX_RES_GROUP_LABELS);
            createTextFile(UNDER_STUDENT_DATA_PATH, LubmJavaGenerator.RDF_TYPE_UNDER_STUD, MAX_UNDER_STUDENT_LABELS);
            createTextFile(GRAD_STUDENT_DATA_PATH, LubmJavaGenerator.RDF_TYPE_GRAD_STUD, MAX_GRAD_STUDENT_LABELS);
            createTextFile(FULL_PROF_PUB_DATA_PATH, LubmJavaGenerator.RDF_TYPE_PUB, MAX_FULL_PROF_PUB_LABELS);
            createTextFile(ASSO_PROF_PUB_DATA_PATH, LubmJavaGenerator.RDF_TYPE_PUB, MAX_ASSO_PROF_PUB_LABELS);
            createTextFile(ASSI_PROF_PUB_DATA_PATH, LubmJavaGenerator.RDF_TYPE_PUB, MAX_ASSI_PROF_PUB_LABELS);
            createTextFile(LECTURER_PUB_DATA_PATH, LubmJavaGenerator.RDF_TYPE_PUB, MAX_LECTURER_PUB_LABELS);
            createTextFile(PUB_DATA_PATH, LubmJavaGenerator.RDF_TYPE_PUB, MAX_PUB_LABELS);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private static void createTextFile(String fileName, String label, int numOfLabels) throws IOException {
        File file = new File(fileName);
        if (file.exists())
            file.delete();
        file.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
        for (int i=0; i<numOfLabels; i++)
            out.write(label + i + "\n");
        out.close();
    }
}
