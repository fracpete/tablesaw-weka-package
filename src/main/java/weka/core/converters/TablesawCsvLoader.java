/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * TablesawCsvLoader.java
 * Copyright (C) 2021 University of Waikato, Hamilton, NZ
 */

package weka.core.converters;

import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.RevisionUtils;
import weka.core.TablesawUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * Reads CSV files using the Tablesaw library.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class TablesawCsvLoader
  extends AbstractFileLoader
  implements BatchConverter {

  private static final long serialVersionUID = -5864770513695200290L;

  /** the loaded data. */
  protected Instances m_Data;

  /**
   * Returns a string describing this Loader
   *
   * @return 		a description of the Loader suitable for
   * 			displaying in the explorer/experimenter gui
   */
  public String globalInfo() {
    return "Reads CSV files using the Tablesaw library.\n"
      + "https://jtablesaw.github.io/tablesaw/";
  }

  /**
   * Get the file extension used for this type of file
   *
   * @return the file extension
   */
  @Override
  public String getFileExtension() {
    return ".csv";
  }

  /**
   * Gets all the file extensions used for this type of file
   *
   * @return the file extensions
   */
  @Override
  public String[] getFileExtensions() {
    return new String[]{".csv"};
  }

  /**
   * Get a one line description of the type of file
   *
   * @return a description of the file type
   */
  @Override
  public String getFileDescription() {
    return "Tablesaw CSV files";
  }

  /**
   * Resets the Loader ready to read a new data set
   *
   * @throws IOException        if something goes wrong
   */
  public void reset() throws IOException {
    m_structure = null;
    m_Data      = null;

    setRetrieval(NONE);

    if (m_File != null)
      setFile(new File(m_File));
  }

  /**
   * Resets the Loader object and sets the source of the data set to be
   * the supplied File object.
   *
   * @param file 		the source file.
   * @throws IOException        if an error occurs
   */
  public void setSource(File file) throws IOException {
    m_structure = null;
    m_Data      = null;

    setRetrieval(NONE);

    if (file == null)
      throw new IOException("Source file object is null!");

    try {
      if (file.getName().endsWith(FILE_EXTENSION_COMPRESSED))
	setSource(new GZIPInputStream(new FileInputStream(file)));
      else
	setSource(new FileInputStream(file));
    }
    catch (FileNotFoundException ex) {
      throw new IOException("File not found");
    }

    m_sourceFile = file;
    m_File       = file.getAbsolutePath();
  }

  /**
   * Resets the Loader object and sets the source of the data set to be
   * the supplied InputStream.
   *
   * @param in 			the source InputStream.
   * @throws IOException        if initialization of reader fails.
   */
  public void setSource(InputStream in) throws IOException {
    m_File = (new File(System.getProperty("user.dir"))).getAbsolutePath();
  }

  /**
   * Determines and returns (if possible) the structure (internally the
   * header) of the data set as an empty set of instances.
   * If not yet read, also reads the full dataset into m_Data.
   *
   * @return 			the structure of the data set as an empty set
   * 				of Instances
   * @throws IOException        if an error occurs
   */
  public Instances getStructure() throws IOException {
    if (m_structure != null)
      return new Instances(m_structure, 0);

    try {
      return new Instances(getDataSet(), 0);
    }
    catch (IOException ioe) {
      // just re-throw it
      throw ioe;
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Return the full data set. If the structure hasn't yet been determined
   * by a call to getStructure then method should do so before processing
   * the rest of the data set.
   *
   * @return 			the structure of the data set as an empty
   * 				set of Instances
   * @throws IOException        if there is no source or parsing fails
   */
  @Override
  public Instances getDataSet() throws IOException {
    Table 			table;
    CsvReadOptions.Builder	builder;

    builder = CsvReadOptions.builder(m_sourceFile)
      .header(true)
      .sample(true);
    table = Table.read().csv(builder.build());

    return TablesawUtils.tableToInstances(table);
  }

  /**
   * CommonCSVLoader is unable to process a data set incrementally.
   *
   * @param structure		ignored
   * @return 			never returns without throwing an exception
   * @throws IOException        always. CommonCSVLoader is unable to process a
   * 				data set incrementally.
   */
  public Instance getNextInstance(Instances structure) throws IOException {
    throw new IOException("Incremental loading not supported!");
  }

  /**
   * Returns the revision string.
   *
   * @return		the revision
   */
  public String getRevision() {
    return RevisionUtils.extract("$Revision: 1 $");
  }

  /**
   * Main method.
   *
   * @param args 	should contain the name of an input file.
   */
  public static void main(String[] args) {
    runFileLoader(new TablesawCsvLoader(), args);
  }
}
