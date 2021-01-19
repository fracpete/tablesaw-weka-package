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
 * SampleN.java
 * Copyright (C) 2021 University of Waikato, Hamilton, NZ
 */

package weka.core.tablesaw;

import tech.tablesaw.api.Table;
import weka.core.Option;
import weka.core.Utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * Returns a sub-sample of specified size.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class SampleN
  extends AbstractRowOperation {

  private static final long serialVersionUID = 2062384379312131809L;

  /** the default size. */
  public final static int DEFAULT_SIZE = 100;

  /** the size. */
  protected int m_Size = DEFAULT_SIZE;

  /**
   * Returns a string describing this object.
   *
   * @return a description of the Loader suitable for
   * displaying in the explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Returns a sub-sample of specified size.";
  }

  /**
   * Sets the sample size.
   *
   * @param value	the sample size &lt;=0 for all
   */
  public void setSize(int value) {
    if (value <= 0)
      value = -1;
    m_Size = value;
  }

  /**
   * Returns the sample size.
   *
   * @return		the sample size &lt;=0 for all
   */
  public int getSize() {
    return m_Size;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String sizeTipText() {
    return "The size of the sample to return.";
  }

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  public Enumeration listOptions() {
    Vector result = new Vector();

    result.addElement(new Option("\tThe size of the sample to generate\n"
      + "\t(default: " + DEFAULT_SIZE + ")",
      "size", 1, "-size <int>"));

    return result.elements();
  }

  /**
   * Parses a given list of options.
   *
   *
   * @param options the list of options as an array of strings
   * @throws Exception if an option is not supported
   */
  public void setOptions(String[] options) throws Exception {
    String 	tmp;

    tmp = Utils.getOption("size", options);
    if (!tmp.isEmpty())
      setSize(Integer.parseInt(tmp));
    else
      setSize(DEFAULT_SIZE);

    Utils.checkForRemainingOptions(options);
  }

  /**
   * Gets the current settings of the Apriori object.
   *
   * @return an array of strings suitable for passing to setOptions
   */
  public String[] getOptions() {
    List<String> result;

    result = new ArrayList<String>();

    result.add("-size");
    result.add("" + getSize());

    return result.toArray(new String[0]);
  }

  /**
   * Processes the rows.
   *
   * @param table the table to work on
   * @return the updated table
   */
  @Override
  public Table processRows(Table table) {
    if (m_Size <= 0)
      return table;

    if (m_Size >= table.rowCount()) {
      System.err.println("Sample size >= rows in table, just returning table as is!");
      return table;
    }

    return table.sampleN(m_Size);
  }
}
