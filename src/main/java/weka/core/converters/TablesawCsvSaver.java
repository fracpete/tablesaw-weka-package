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
 * TablesawCsvSaver.java
 * Copyright (C) 2021 University of Waikato, Hamilton, NZ
 */

package weka.core.converters;

import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvWriteOptions;
import weka.core.Capabilities;
import weka.core.Capabilities.Capability;
import weka.core.Instances;
import weka.core.RevisionUtils;
import weka.core.TablesawUtils;

import java.io.IOException;

/**
 * CSV saver using the Tablesaw dataframe library.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class TablesawCsvSaver
  extends AbstractFileSaver
  implements BatchConverter {

  private static final long serialVersionUID = 1919058595269288676L;

  /**
   * Constructor
   */
  public TablesawCsvSaver() {
    resetOptions();
  }

  /**
   * Returns a string describing this Saver
   *
   * @return a description of the Saver suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String globalInfo() {
    return "CSV saver using the Tablesaw dataframe library.\n"
      + "https://jtablesaw.github.io/tablesaw/";
  }

  /**
   * Returns a description of the file type.
   *
   * @return a short file description
   */
  @Override
  public String getFileDescription() {
    return new TablesawCsvLoader().getFileDescription();
  }

  /**
   * Gets all the file extensions used for this type of file
   *
   * @return the file extensions
   */
  @Override
  public String[] getFileExtensions() {
    return new TablesawCsvLoader().getFileExtensions();
  }

  /**
   * Returns the Capabilities of this saver.
   *
   * @return the capabilities of this object
   * @see Capabilities
   */
  @Override
  public Capabilities getCapabilities() {
    Capabilities result = super.getCapabilities();

    // attributes
    result.enableAllAttributes();
    result.enable(Capability.MISSING_VALUES);

    // class
    result.enableAllClasses();
    result.enable(Capability.MISSING_CLASS_VALUES);
    result.enable(Capability.NO_CLASS);

    return result;
  }

  /**
   * Writes to a file in batch mode To be overridden.
   *
   * @throws IOException exception if writting is not possible
   */
  @Override
  public void writeBatch() throws IOException {
    Instances 			data;
    Table			table;
    CsvWriteOptions.Builder	builder;

    if (getInstances() == null)
      throw new IOException("No instances to save");

    if (getRetrieval() == INCREMENTAL)
      throw new IOException("Batch and incremental saving cannot be mixed.");

    setRetrieval(BATCH);
    setWriteMode(WRITE);

    data = getInstances();
    table = TablesawUtils.instancesToTable(data);
    builder = CsvWriteOptions.builder(retrieveFile())
      .header(true);
    table.write().csv(builder.build());

    setWriteMode(WAIT);
    resetWriter();
    setWriteMode(CANCEL);
  }

  /**
   * Returns the revision string.
   *
   * @return the revision
   */
  @Override
  public String getRevision() {
    return RevisionUtils.extract("$Revision: 1 $");
  }

  /**
   * Main method.
   *
   * @param args should contain the options of a Saver.
   */
  public static void main(String[] args) {
    runFileSaver(new TablesawCsvSaver(), args);
  }
}
